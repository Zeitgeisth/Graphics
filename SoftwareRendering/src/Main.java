import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Main {
	static float[] transAmount = {0.0f, 0.0f, 2.0f}; 
	static float[] scaleAmount = {1.0f, 1.0f, 1.0f};
	static int[] rotationAmount = {152, 0, 0};
	//static int[] rotationAmount = {201, 52, 11};
	static int[] rotationAmount2 = {0, 0, 0};
	static int[] transAmount2 = {0,0,24};
	static int[] viewSelection = {0};
	
	//rotation x 205
	//rollpitch z -208
	//cameratrns z 0
	
//	static int[] cameraTransform = {0, 20, 6};
//	static int[] rollPitchYaw = {0,0,0};
	
	static int[] cameraTransform = {0, 0, -25};
	static int[] rollPitchYaw = {0,0,0};
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
    	//display.setOnClickListener(new MKeyListener());
    	display.setSideMenu(setUpSideMenu());
    	
    	
    	
    	
    	RenderContext target = display.GetFrameBuffer();
    	Camera camera = new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
			   	(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f));
    	
    	
    	Mesh mesh = new Mesh("./res/Wembley.obj");
    	Stars3D stars = new Stars3D(3, 64.0f, 20.0f);
    	Bitmap texture = new Bitmap("./res/Wembley.jpg");
		Vertex minYVert = new Vertex(new Vector4f(-1, -1, 0, 1), 
		                             new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex midYVert = new Vertex(new Vector4f(0, 1, 0, 1), 
		                             new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
		Vertex maxYVert = new Vertex(new Vector4f(1, -1, 0, 1), 
		                             new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		
		
		Bitmap planeTexture = new Bitmap("./res/container.jpg");
		//Bitmap planeTexture = new Bitmap("./res/concrete.jpg");
		
		
		
//		Vertex v1 = new Vertex(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f), 
//		                             new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
//		Vertex v2 = new Vertex(new Vector4f(1.0f, 0.0f, 1.0f, 1.0f), 
//		                             new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
//		Vertex v3 = new Vertex(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), 
//		                             new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
//		Vertex v4 = new Vertex(new Vector4f(0.0f, 1.0f, 1.0f, 1.0f),
//									new Vector4f(1.0f, 0.5f, 0.0f, 0.0f));
//		Vertex v5 = new Vertex(new Vector4f(0.0f, 0.0f, -1.0f, 1.0f),
//				new Vector4f(1.0f, 0.5f, 0.0f, 0.0f));
//		Vertex v6 = new Vertex(new Vector4f(1.0f, 0.0f, -1.0f, 1.0f),
//				new Vector4f(1.0f, 0.5f, 0.0f, 0.0f));
//		Vertex v7 = new Vertex(new Vector4f(1.0f, 1.0f, -1.0f, 1.0f),
//				new Vector4f(1.0f, 0.5f, 0.0f, 0.0f));
//		Vertex v8 = new Vertex(new Vector4f(0.0f, 1.0f, -1.0f, 1.0f),
//				new Vector4f(1.0f, 0.5f, 0.0f, 0.0f));
//
//		Vertex[] vertices = {v1, v2, v3, v4, v5, v6, v7, v8};
//		int[] indices = {0, 1, 3, 
//						 1, 2, 3,
//						 0, 1, 4,
//						 1, 4, 5,
//						 4, 5, 7,
//						 5, 6, 7,
//						 2, 3, 7,
//						 2, 6, 7,
//						 2, 1, 5,
//						 2, 5, 6,
//						 0, 3, 4,
//						 3, 4, 7,
//		};
		Vertex v1 = new Vertex(new Vector4f(-0.5f, -0.5f, -0.5f, 1.0f), 
                               new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v2 = new Vertex(new Vector4f(0.5f, -0.5f, -0.5f, 1.0f), 
                			   new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v3 = new Vertex(new Vector4f(0.5f,  0.5f, -0.5f, 1.0f), 
 			                   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v4 = new Vertex(new Vector4f(0.5f,  0.5f, -0.5f, 1.0f), 
 			                   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v5 = new Vertex(new Vector4f(-0.5f, 0.5f, -0.5f, 1.0f), 
 			   new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v6 = new Vertex(new Vector4f(-0.5f, -0.5f, -0.5f, 1.0f), 
 			   new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		
		
		Vertex v7 = new Vertex(new Vector4f(-0.5f, -0.5f, 0.5f, 1.0f), 
                new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v8 = new Vertex(new Vector4f(0.5f, -0.5f, 0.5f, 1.0f), 
		 			   new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v9 = new Vertex(new Vector4f(0.5f,  0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v10 = new Vertex(new Vector4f(0.5f,  0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v11 = new Vertex(new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v12 = new Vertex(new Vector4f(-0.5f, -0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		
		Vertex v13 = new Vertex(new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f), 
                new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v14 = new Vertex(new Vector4f(-0.5f, 0.5f, -0.5f, 1.0f), 
		 			   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v15 = new Vertex(new Vector4f(-0.5f,  -0.5f, -0.5f, 1.0f), 
		                 new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v16 = new Vertex(new Vector4f(-0.5f,  -0.5f, -0.5f, 1.0f), 
		                 new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v17 = new Vertex(new Vector4f(-0.5f, -0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v18 = new Vertex(new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f), 
		 new Vector4f(1.0f, 0.0f, 1.0f, 0.0f));
		
		Vertex v19 = new Vertex(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 
                new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v20 = new Vertex(new Vector4f(0.5f, 0.5f, -0.5f, 1.0f), 
		 			   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v21 = new Vertex(new Vector4f(0.5f,  -0.5f, -0.5f, 1.0f), 
		                 new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v22 = new Vertex(new Vector4f(0.5f,  -0.5f, -0.5f, 1.0f), 
		                 new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v23 = new Vertex(new Vector4f(0.5f, -0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v24 = new Vertex(new Vector4f(0.5f, 0.5f, 0.5f, 1.0f), 
		 new Vector4f(1.0f, 0.0f, 1.0f, 0.0f));
		
		Vertex v25 = new Vertex(new Vector4f(-0.5f, -0.5f, -0.5f, 1.0f), 
                new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v26 = new Vertex(new Vector4f(0.5f, -0.5f, -0.5f, 1.0f), 
		 			   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v27 = new Vertex(new Vector4f(0.5f,  -0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v28 = new Vertex(new Vector4f(0.5f,  -0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v29 = new Vertex(new Vector4f(-0.5f, -0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v30 = new Vertex(new Vector4f(-0.5f, -0.5f, -0.5f, 1.0f), 
		 new Vector4f(0.0f, 1.0f, 1.0f, 0.0f));
		
		Vertex v31 = new Vertex(new Vector4f(-0.5f, 0.5f, -0.5f, 1.0f), 
                new Vector4f(0.0f, 1.0f, 0.0f, 0.0f));
		Vertex v32 = new Vertex(new Vector4f(0.5f, 0.5f, -0.5f, 1.0f), 
		 			   new Vector4f(1.0f, 1.0f, 0.0f, 0.0f));
		Vertex v33 = new Vertex(new Vector4f(0.5f,  0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v34 = new Vertex(new Vector4f(0.5f,  0.5f, 0.5f, 1.0f), 
		                 new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
		Vertex v35 = new Vertex(new Vector4f(-0.5f, 0.5f, 0.5f, 1.0f), 
		 new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Vertex v36 = new Vertex(new Vector4f(-0.5f, 0.5f, -0.5f, 1.0f), 
		 new Vector4f(0.0f, 1.0f, 1.0f, 0.0f));
		
		
		Vertex[] vertices = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12,v13,v14,v15,
				v16,v17,v18,v19,v20,v21,v22,v23,v24, v25,v26,v27,v28,v29,v30,v31,v32,v33,
				v34,v35,v36};
		int[] indices = {0, 1, 2, 
				 3, 4, 5,
				 6, 7, 8,
				 9, 10, 11,
				 12, 13, 14,
				 15, 16, 17,
				 18, 19, 20,
				 21, 22, 23,
				 24, 25, 26,
				 27, 28, 29,
				 30, 31, 32,
				 33, 34, 35,
};
		
    	
    	Matrix4f projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f), (float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 100.0f);
    	float rotCounter = 0.0f;
    	long previousTime = System.nanoTime();
    	int previousViewIndex = 0;
    	
    	while(true){
    		
    		long currentTime = System.nanoTime();
    		float delta =  (float) ((float)(currentTime - previousTime)/(1000000000.0));
    		previousTime = currentTime;
    		
    		camera.setTransform(new Transform(
    				new Vector4f(cameraTransform[0]/10.0f,
    				cameraTransform[1]/10.0f, cameraTransform[2]/10.0f, 1.0f),
    				toQuaternion(rollPitchYaw[0]/10.0f, rollPitchYaw[1]/10.0f, rollPitchYaw[2]/10.0f),
    				new Vector4f(1.0f,1.0f,1.0f)
    				)
    		);
    		camera.Update(display.GetInput(), delta);
    		Matrix4f vp = camera.GetViewProjection();
    		
    		if (isChecked[0])
    			target.Clear((byte)0x00);
    		else
    			target.Clear((byte)0x22);
    		target.ClearDepthBuffer();
    		
    		rotCounter += delta;
    		Matrix4f scaling = new Matrix4f().InitScale(1.0f, 1.0f, 1.0f);
    		Matrix4f translation = new Matrix4f().InitTranslation(transAmount[0],transAmount[1], transAmount[2]);
//    		Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
    		Matrix4f rotation = new Matrix4f().InitRotation(rotationAmount[0]/45.0f, 
    				rotationAmount[1]/45.0f, rotationAmount[2]/45.0f);
    		Matrix4f transform = projection.Mul(scaling.Mul(translation.Mul(rotation)));
    		
    		
    		
    		Matrix4f translation2 = new Matrix4f().InitTranslation(transAmount2[0]/10.0f,
    				transAmount2[1]/10.0f, transAmount2[2]/
					10.0f);
    		Matrix4f rotation2 = new Matrix4f().InitRotation(rotationAmount2[0]/45.0f, 
    				rotationAmount2[1]/45.0f, rotationAmount2[2]/45.0f);
    		Matrix4f transform2 = projection.Mul((translation2.Mul(rotation2)));
    		
    		//Views
    		if (previousViewIndex != viewSelection[0]) {
//    			switch(viewSelection[0]) {
//    			case 0:
//    				rotationAmount[0]=193;rotationAmount[1]=0;rotationAmount[2]=0;
//    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.8f;
//    				break;
//    			case 1:
//    				rotationAmount[0]=131;rotationAmount[1]=0;rotationAmount[2]=0;
//    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.5f;
//    				break;
//    			case 2:
//    				rotationAmount[0]=212;rotationAmount[1]=80;rotationAmount[2]=0;
//    				transAmount[0]   =0  ;transAmount   [1]=-0.3f;transAmount[2]   =1.5f;
//    				break;
//    			case 3:
//    				rotationAmount[0]=368;rotationAmount[1]=0;rotationAmount[2]=140;
//    				transAmount[0]   =0  ;transAmount   [1]=0;transAmount[2]   =1.5f;
//    				break;
//    			}
//    			previousViewIndex = viewSelection[0];
    		}
    		
    		//target.DrawTriangle(minYVert.Transform(transform), midYVert.Transform(transform), maxYVert.Transform(transform), texture);
    		//target.DrawMesh(mesh, transform, texture);
    		//mesh.Draw(target, transform, texture);
    		mesh.Draw(target, vp.Mul(transform), texture);
    		
    		
    		//target.drawPlane(vertices, indices, (transform2), planeTexture);
    		display.SwapBuffers();
    	}
    	
     }
     
     public static SideMenu setUpSideMenu() {
    	 SideMenu sidemenu = new SideMenu();
         
         sidemenu.addSliderFloat3("Rotation", 0, 720, rotationAmount);
         
         sidemenu.addSliderFloat3("Rotation ground", 0, 720, rotationAmount2);
         sidemenu.addSliderFloat3("Translation", -40, 40, transAmount2);
         String[] titles = {"Front","Top","Side", "Opposite"};
         
         //sidemenu.addRadioGroup("View", 4, titles, viewSelection);
         
         //sidemenu.addCheckBox("Day/Night", "isDay", isChecked);
         
         
         sidemenu.addSliderFloat3("Camera transform", -300, 300, cameraTransform);
         sidemenu.addSliderFloat3("Rollpitchyaw", -1800, 1800, rollPitchYaw);
         
         return sidemenu;
     }
     
     public static Quaternion toQuaternion( float yaw, float pitch, float roll) // yaw (Z), pitch (Y), roll (X)
     {
         // Abbreviations for the various angular functions
         float cy = (float)Math.cos(Math.toRadians(yaw * 0.5));
         float sy = (float)Math.sin(Math.toRadians(yaw * 0.5));
         float cp = (float)Math.cos(Math.toRadians(pitch * 0.5));
         float sp = (float)Math.sin(Math.toRadians(pitch * 0.5));
         float cr = (float)Math.cos(Math.toRadians(roll * 0.5));
         float sr = (float)Math.sin(Math.toRadians(roll * 0.5));

         Quaternion q = new Quaternion(
         cy * cp * sr - sy * sp * cr,
         sy * cp * sr + cy * sp * cr,
         sy * cp * cr - cy * sp * sr,
         cy * cp * cr + sy * sp * sr
         );
         return q;
     }
     
}
