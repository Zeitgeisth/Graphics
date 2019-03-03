import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Main {
	static float[] transAmount = {0.0f, 0.0f, 2.0f}; 
	static float[] scaleAmount = {1.0f, 1.0f, 1.0f};
	static int[] rotationAmount = {176, 0, 0};
	static int[] viewSelection = {0};
	static boolean[] isChecked = {true};
	static float inc = 0.05f;
	public static class MKeyListener extends KeyAdapter{
		 @Override
		 public void keyPressed(KeyEvent event) {
			 System.out.println(event.getKeyChar());
			 switch(event.getKeyChar()) {
			 case 'a':
				 transAmount[0] -= inc;
				 break;
			 case 'd':
				 transAmount[0] += inc;
				 break;
			 case 's':
				 transAmount[1] -= inc;
				 break;
			 case 'w':
				 transAmount[1] += inc;
				 break;
			 case 'z':
				 transAmount[2] -= inc;
				 break;
			 case 'x':
				 transAmount[2] += inc;
				 break;
			 }
	   }

	}
     public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
	 
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	Display display = new Display(1080, 720, "Rendering"); 
    	display.setOnClickListener(new MKeyListener());
    	display.setSideMenu(setUpSideMenu());
    	
    	
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
    	int previousViewIndex = 0;
    	
    	while(true){
    		long currentTime = System.nanoTime();
    		float delta =  (float) ((float)(currentTime - previousTime)/(1000000000.0));
    		previousTime = currentTime;
    		if (isChecked[0])
    			target.Clear((byte)0x00);
    		else
    			target.Clear((byte)0x22);
    		target.ClearDepthBuffer();
    		//stars.UpdateAndRender(target, delta);
    		
    		/*for(int j = 100; j < 400; j++){
    			target.DrawScanBuffer(j, 200, 500);
    		}
    		*/
    		
    		rotCounter += delta;
    		Matrix4f scaling = new Matrix4f().InitScale(1.0f, 1.0f, 1.0f);
    		Matrix4f translation = new Matrix4f().InitTranslation(transAmount[0],transAmount[1], transAmount[2]);
//    		Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
    		Matrix4f rotation = new Matrix4f().InitRotation(rotationAmount[0]/45.0f, 
    				rotationAmount[1]/45.0f, rotationAmount[2]/45.0f);
    		Matrix4f transform = projection.Mul(scaling.Mul(translation.Mul(rotation)));
    		
    		//target.FillTriangle(minYVert.Transform(transform), midYVert.Transform(transform), maxYVert.Transform(transform), texture);
    		//target.DrawMesh(mesh, transform, texture);
    		
    		//Views
    		if (previousViewIndex != viewSelection[0]) {
    			switch(viewSelection[0]) {
    			case 0:
    				rotationAmount[0]=193;rotationAmount[1]=0;rotationAmount[2]=0;
    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.8f;
    				break;
    			case 1:
    				rotationAmount[0]=131;rotationAmount[1]=0;rotationAmount[2]=0;
    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.5f;
    				break;
    			case 2:
    				rotationAmount[0]=212;rotationAmount[1]=80;rotationAmount[2]=0;
    				transAmount[0]   =0  ;transAmount   [1]=-0.3f;transAmount[2]   =1.5f;
    				break;
    			case 3:
    				rotationAmount[0]=368;rotationAmount[1]=0;rotationAmount[2]=140;
    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.5f;
    				break;
    			}
    			previousViewIndex = viewSelection[0];
    		}
    		
    		mesh.Draw(target, transform, texture);
    		display.SwapBuffers();
    	}
    	
     }
     
     public static SideMenu setUpSideMenu() {
    	 SideMenu sidemenu = new SideMenu();
     	//sidemenu.add(new JLabel("this is a sentence"));
     	 //int[] defaultVals = {1,4,9};
         //sidemenu.addSliderFloat3("Slider float 3", 1, 10, defaultVals);
         
         
         
         sidemenu.addSliderFloat3("Rotation", 0, 720, rotationAmount);
         String[] titles = {"Front","Top","Side", "Opposite"};
         
         sidemenu.addRadioGroup("View", 4, titles, viewSelection);
         
         sidemenu.addCheckBox("Day/Night", "isDay", isChecked);
         return sidemenu;
     }
     
}
