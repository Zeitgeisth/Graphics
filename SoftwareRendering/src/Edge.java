
public class Edge {
	private float m_x;
	private float m_xStep;
	private int m_yStart;
	private int m_yEnd;
	private float m_texCoordX;
	private float m_texCoordY;
	private float m_texCoordXStep;
	private float m_texCoordYStep;
	private float m_oneOverZ;
	private float m_oneOverZStep;
	private float m_depth;
	private float m_depthStep;
	
	private float m_lightAmt;
	private float m_lightAmtStep;
	
	public float GetOneOverZ(){ return m_oneOverZ;}
	public float GetTexCoordX(){ return m_texCoordX; }
	public float GetTexCoordY(){ return m_texCoordY; }
	public float GetDepth(){ return m_depth; }
	public float GetLightAmt() { return m_lightAmt; }
	
	public float GetX(){ return m_x ;}
	public int GetYStart(){ return m_yStart ; }
	public int GetYEnd(){ return m_yEnd ; }
	
	public Edge(Gradients gradients, Vertex minYVert, Vertex maxYVert, int minYVertIndex){
		m_yStart =  (int)Math.ceil(minYVert.GetY());
		m_yEnd = 	(int)Math.ceil(maxYVert.GetY());
		
		float yDist = maxYVert.GetY() - minYVert.GetY();
		float xDist = maxYVert.GetX() - minYVert.GetX();
		
		float yPreStep = m_yStart - minYVert.GetY();
		m_xStep = (float)xDist/(float)yDist;
		m_x= minYVert.GetX() + yPreStep * m_xStep;
		float xPreStep = m_x - minYVert.GetX();
		
		m_texCoordX = gradients.GetTexCoordX(minYVertIndex) + gradients.GetTexCoordXXStep() * xPreStep + gradients.GetTexCoordXYStep() * yPreStep;
		m_texCoordXStep = gradients.GetTexCoordXYStep() + gradients.GetTexCoordXXStep() * m_xStep;
		
		m_texCoordY = gradients.GetTexCoordY(minYVertIndex) + gradients.GetTexCoordYXStep() * xPreStep + gradients.GetTexCoordYYStep() * yPreStep;
		m_texCoordYStep = gradients.GetTexCoordYYStep() + gradients.GetTexCoordYXStep() * m_xStep;
		
		m_oneOverZ = gradients.GetOneOverZ(minYVertIndex) + gradients.GetOneOverZXStep() * xPreStep + gradients.GetOneOverZYStep() * yPreStep;
		m_oneOverZStep = gradients.GetOneOverZYStep() + gradients.GetOneOverZXStep() * m_xStep;
		
		m_depth = gradients.GetDepth(minYVertIndex) + gradients.GetDepthXStep() * xPreStep + gradients.GetDepthYStep()*yPreStep;
		m_depthStep = gradients.GetDepthYStep() + gradients.GetDepthXStep() * m_xStep;
		
		m_lightAmt = gradients.GetLightAmt(minYVertIndex) +
				gradients.GetLightAmtXStep() * xPreStep +
				gradients.GetLightAmtYStep() * yPreStep;
		m_lightAmtStep = gradients.GetLightAmtYStep() + gradients.GetLightAmtXStep() * m_xStep;
	}
	
	public void Step(){
		m_x += m_xStep;
		m_texCoordX += m_texCoordXStep;
		m_texCoordY += m_texCoordYStep;
		m_oneOverZ += m_oneOverZStep;
		m_depth += m_depthStep;
		m_lightAmt += m_lightAmtStep;
	}
	
}