import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RenderContext extends Bitmap {

	private float[] m_zBuffer;
	
	public RenderContext(int width, int height) {
		super(width, height);
		m_zBuffer = new float[width * height];
	}
	
	public void ClearDepthBuffer(){
		for(int i = 0; i < m_zBuffer.length; i++){
			m_zBuffer[i] = Float.MAX_VALUE;
		}
	}
	
//	public void DrawMesh(Mesh mesh, Matrix4f transform, Bitmap texture){
//		for(int i = 0; i < mesh.GetNumIndices(); i += 3){
//			FillTriangle(mesh.GetVertex(mesh.GetIndex(i)).Transform(transform),
//					mesh.GetVertex(mesh.GetIndex(i + 1)).Transform(transform),
//					mesh.GetVertex(mesh.GetIndex(i + 2)).Transform(transform),
//					texture);
//		}
//	}
//	
	public void FillTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture,Vector3f viewDir, Vector3f lightPos){
		Matrix4f screenSpaceTransform = new Matrix4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
		Matrix4f identity = new Matrix4f().InitIdentity();
		Vertex minYVert = v1.Transform(screenSpaceTransform, identity).PerspectiveDivide();
		Vertex midYVert = v2.Transform(screenSpaceTransform, identity).PerspectiveDivide();
		Vertex maxYVert = v3.Transform(screenSpaceTransform, identity).PerspectiveDivide();
		if(minYVert.TriangleArea(maxYVert, midYVert) >= 0) return;
		
		if(maxYVert.GetY() < midYVert.GetY()){
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}
		
		if(midYVert.GetY() < minYVert.GetY()){
			Vertex temp = midYVert;
			midYVert = minYVert;
			minYVert = temp;
		}
		
		if(maxYVert.GetY() < midYVert.GetY()){
			Vertex temp = maxYVert;
			maxYVert = midYVert;
			midYVert = temp;
		}
		
		
		
		ScanTriangle(minYVert, midYVert, maxYVert,
				minYVert.TriangleArea(maxYVert, midYVert) >= 0, texture,
				viewDir, lightPos);
			
	}
	
	
	
	public void ScanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, 
			boolean handedness, Bitmap texture,Vector3f viewDir, Vector3f lightPos){
		Gradients gradients = new Gradients(minYVert, midYVert, maxYVert, viewDir, lightPos);
		Edge topToBottom    = new Edge(gradients, minYVert, maxYVert, 0);
		Edge topToMiddle    = new Edge(gradients, minYVert, midYVert, 0);
		Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);
		
		
		TriangleMesh tMesh = new TriangleMesh();
		tMesh.v0 = minYVert;
		tMesh.v1 = midYVert;
		tMesh.v2 = maxYVert;
		
		
		ScanEdges(gradients, topToBottom, topToMiddle, handedness, texture, tMesh, viewDir, lightPos);
		ScanEdges(gradients, topToBottom, middleToBottom, handedness, texture, tMesh,viewDir, lightPos);
		
	}
	
	public void ScanEdges(Gradients gradients, Edge a, Edge b, boolean handedness, Bitmap texture,
			TriangleMesh tMesh,Vector3f viewDir, Vector3f lightPos){
		Edge left = a;
		Edge right = b;
		if(handedness){
			Edge temp = left;
			left = right;
			right = temp;
		}
		
		int yStart = b.GetYStart();
		int yEnd = b.GetYEnd();
		
		for(int j = yStart; j < yEnd; j++){
			DrawScanLine(gradients, left, right, j, texture, tMesh, viewDir,  lightPos);
			left.Step();
			right.Step();
		}
	}
	
	private void DrawScanLine(Gradients gradients, Edge left, Edge right, int j, Bitmap texture,
			TriangleMesh tMesh,Vector3f viewDir, Vector3f lightPos){
		int xMin = (int)Math.ceil(left.GetX());
		int xMax = (int)Math.ceil(right.GetX());
		float xPrestep = xMin - left.GetX();

//		float xDist = right.GetX() - left.GetX();
//		float texCoordXXStep = (right.GetTexCoordX() - left.GetTexCoordX())/xDist;
//		float texCoordYXStep = (right.GetTexCoordY() - left.GetTexCoordY())/xDist;
//		float oneOverZXStep = (right.GetOneOverZ() - left.GetOneOverZ())/xDist;
//		float depthXStep = (right.GetDepth() - left.GetDepth())/xDist;

		// Apparently, now that stepping is actually on pixel centers, gradients are
		// precise enough again.
		float texCoordXXStep = gradients.GetTexCoordXXStep();
		float texCoordYXStep = gradients.GetTexCoordYXStep();
		float oneOverZXStep = gradients.GetOneOverZXStep();
		float depthXStep = gradients.GetDepthXStep();
		float lightAmtXStep = gradients.GetLightAmtXStep();

		float texCoordX = left.GetTexCoordX() + texCoordXXStep * xPrestep;
		float texCoordY = left.GetTexCoordY() + texCoordYXStep * xPrestep;
		float oneOverZ = left.GetOneOverZ() + oneOverZXStep * xPrestep;
		float depth = left.GetDepth() + depthXStep * xPrestep;
		float goraudLightAmt = left.GetLightAmt() + lightAmtXStep * xPrestep;

		for(int i = xMin; i < xMax; i++){
			int index = i + j * GetWidth();
			if(depth < m_zBuffer[index]){
				
				m_zBuffer[index] = depth;
				float z = 1.0f/oneOverZ;
				int srcX = (int)((texCoordX * z) * (float)(texture.GetWidth() - 1) + 0.5f);
				int srcY = (int)((texCoordY * z) * (float)(texture.GetHeight() - 1) + 0.5f);
				
				//Lighting phong
//				Vector3f b = tMesh.Barycentric(new Vector3f((float)(i), (float)(j), 0.0f));
//				
//				Vertex v = new Vertex(
//								tMesh.v0.GetPosition().Mul(b.x).Add(tMesh.v1.GetPosition().Mul(b.y)
//										.Add(tMesh.v2.GetPosition().Mul(b.z))),
//								tMesh.v0.GetTexCoords().Mul(b.x).Add(tMesh.v1.GetTexCoords().Mul(b.y)
//										.Add(tMesh.v2.GetTexCoords().Mul(b.z))),
//								tMesh.v0.GetNormal().Mul(b.x).Add(tMesh.v1.GetNormal().Mul(b.y)
//										.Add(tMesh.v2.GetNormal().Mul(b.z)))
//						);
//				float phongLightAmt = LightingModel.CalculateLighting(v, viewDir, lightPos);
				
				//CopyPixel(i, j, srcX, srcY, texture, phongLightAmt);
				CopyPixel(i, j, srcX, srcY, texture, goraudLightAmt);
			}

			oneOverZ += oneOverZXStep;
			texCoordX += texCoordXXStep;
			texCoordY += texCoordYXStep;
			depth += depthXStep;
			goraudLightAmt += lightAmtXStep;
		}
	}

	
	private void ClipPolygonComponent(List<Vertex> vertices, int componentIndex, 
			float componentFactor, List<Vertex> result){
		Vertex previousVertex = vertices.get(vertices.size() - 1);
		float previousComponent = previousVertex.Get(componentIndex) * componentFactor;
		boolean previousInside = previousComponent <= previousVertex.GetPosition().GetW();

		Iterator<Vertex> it = vertices.iterator();
		while(it.hasNext()){
			Vertex currentVertex = it.next();
			float currentComponent = currentVertex.Get(componentIndex) * componentFactor;
			boolean currentInside = currentComponent <= currentVertex.GetPosition().GetW();

			if(currentInside ^ previousInside){
				float lerpAmt = (previousVertex.GetPosition().GetW() - previousComponent) /
					((previousVertex.GetPosition().GetW() - previousComponent) - 
					 (currentVertex.GetPosition().GetW() - currentComponent));

				result.add(previousVertex.Lerp(currentVertex, lerpAmt));
			}

			if(currentInside){
				result.add(currentVertex);
			}

			previousVertex = currentVertex;
			previousComponent = currentComponent;
			previousInside = currentInside;
		}
	}
	
	private boolean ClipPolygonAxis(List<Vertex> vertices, List<Vertex> auxillaryList,
			int componentIndex){
		ClipPolygonComponent(vertices, componentIndex, 1.0f, auxillaryList);
		vertices.clear();

		if(auxillaryList.isEmpty()){
			return false;
		}

		ClipPolygonComponent(auxillaryList, componentIndex, -1.0f, vertices);
		auxillaryList.clear();

		return !vertices.isEmpty();
	}
	
	
	public void DrawTriangle(Vertex v1, Vertex v2, Vertex v3, Bitmap texture, 
			Vector3f viewDir, Vector3f lightPos){
		boolean v1Inside = v1.IsInsideViewFrustum();
		boolean v2Inside = v2.IsInsideViewFrustum();
		boolean v3Inside = v3.IsInsideViewFrustum();

		if(v1Inside && v2Inside && v3Inside){
			FillTriangle(v1, v2, v3, texture, viewDir, lightPos);
			return;
		}

		if(!v1Inside && !v2Inside && !v3Inside){
			return;
		}

		List<Vertex> vertices = new ArrayList<>();
		List<Vertex> auxillaryList = new ArrayList<>();
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);

		if(ClipPolygonAxis(vertices, auxillaryList, 0) &&
				ClipPolygonAxis(vertices, auxillaryList, 1) &&
			ClipPolygonAxis(vertices, auxillaryList, 2)){
			Vertex initialVertex = vertices.get(0);

			for(int i = 1; i < vertices.size() - 1; i++){
				FillTriangle(initialVertex, vertices.get(i), vertices.get(i + 1), texture, viewDir, lightPos);
			}
		}
	}
	
//	public void drawPlane(Vertex[] vert, int[] indices,Matrix4f translation, Bitmap texture){
//		for(int i = 0; i < indices.length ; i += 3){
//			this.DrawTriangle(vert[indices[i]].Transform(translation), vert[indices[i+1]].Transform(translation), vert[indices[i+2]].Transform(translation), texture);
//		}
//	}

}
