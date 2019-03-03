import java.io.IOException;

public class Main {
     public static void main(String[] args) throws IOException{
    	 
    	Display display = new Display(1080, 800, "Rendering"); 
    	RenderContext target = display.GetFrameBuffer();
    	Mesh mesh = new Mesh("./res/Wembley.obj");
    	Stars3D stars = new Stars3D(3, 64.0f, 20.0f);
    	Bitmap texture = new Bitmap("./res/Wembley.jpg");
		Vertex minYVert = new Vertex(new Vector4f(-1, -1, 0, 1), 
		                             new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex midYVert = new Vertex(new Vector4f(0, 1, 0, 1), 
		                             new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
		Vertex maxYVert = new Vertex(new Vector4f(1, -1, 0, 1), 
		                             new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));

    	
    	Matrix4f projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f), (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 100.0f);
    	float rotCounter = 0.0f;
    	long previousTime = System.nanoTime();
    	
    	while(true){
    		long currentTime = System.nanoTime();
    		float delta =  (float) ((float)(currentTime - previousTime)/(1000000000.0));
    		previousTime = currentTime;
    		target.Clear((byte)0x00);
    		target.ClearDepthBuffer();
    		//stars.UpdateAndRender(target, delta);
    		
    		/*for(int j = 100; j < 400; j++){
    			target.DrawScanBuffer(j, 200, 500);
    		}
    		*/
    		
    		rotCounter += delta;
    		Matrix4f translation = new Matrix4f().InitTranslation(0.0f, 0.0f, 3.0f);
    		Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
    		Matrix4f transform = projection.Mul(translation.Mul(rotation));
    		
    		//target.FillTriangle(minYVert.Transform(transform), midYVert.Transform(transform), maxYVert.Transform(transform), texture);
    		target.DrawMesh(mesh, transform, texture);
    		display.SwapBuffers();
    	}
    	
     }
}
