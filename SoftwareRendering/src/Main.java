import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Main {
	static float[] transAmount = {0.0f, 0.0f, 2.0f}; 
	static float[] scaleAmount = {1.0f, 1.0f, 1.0f};
	static int[] rotationAmount = {212, 0, 0};
	
	
	static int[] rotationAmount2 = {0, 0, 0};
	static int[] transAmount2 = {0,0,24};
	
	static int[] viewSelection = {0};
	static int[] lightTrans = {0, 3, -100};
	static boolean[] isDay = {false};
	static boolean[] isLightOn = {false};
	static boolean isTop = false;
	
	static float inc = 0.05f;
	
    public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	Display display = new Display(1080, 720, "Graphics project"); 
    	display.setSideMenu(setUpSideMenu());
    	//display.setOnClickListener(new MKeyListener());

    	RenderContext target = display.GetFrameBuffer();
    	Camera camera = new Camera(new Matrix4f().InitPerspective((float)Math.toRadians(70.0f),
			   	(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 1000.0f));
    	
    	
    	Mesh mesh = new Mesh("./res/Wembley.obj");
		Mesh monkeyMesh = new Mesh("./res/smoothMonkey0.obj");
		//Mesh monkeyMesh = new Mesh("./res/jhyau.obj");
		Transform monkeyTransform = new Transform(new Vector4f(0,0.0f,3.0f));
    	
    	Bitmap texture = new Bitmap("./res/Wembley.jpg");		
		Bitmap planeTexture = new Bitmap("./res/container.jpg");
		Bitmap texture2 = new Bitmap("./res/bricks2.jpg");
		//Bitmap planeTexture = new Bitmap("./res/concrete.jpg");


    	
    	Matrix4f projection = new Matrix4f().InitPerspective((float)Math.toRadians(70.0f), 
    			(float)target.GetWidth()/(float)target.GetHeight(), 0.1f, 100.0f);
    	long previousTime = System.nanoTime();
    	int previousViewIndex = 0;
    	
    	//Lighting parameters
    	Vector3f viewDir = new Vector3f(0.0f, 0.0f, 1.0f);
    	Vector3f lightPos = new Vector3f(0.0f, 0.0f, 5.0f);
    	
    	while(true){
    		
    		long currentTime = System.nanoTime();
    		float delta =  (float) ((float)(currentTime - previousTime)/(1000000000.0));
    		previousTime = currentTime;
    		
    		//System.out.println(1/delta);

    		lightPos.x = lightTrans[0]/10.0f;
    		lightPos.y= lightTrans[1]/10.0f;
    		lightPos.z= lightTrans[2]/10.0f;
    		if (isTop) {
    			float temp = lightPos.z;
    			lightPos.z = -lightPos.y;
    			lightPos.y = temp;
    		}
    		//System.out.print("("+lightPos.x+","+lightPos.y+","+lightPos.z+")\n");
    		//System.out.println(camera.GetTransform().GetPos());
    		
    		camera.Update(display.GetInput(), delta);
    		Matrix4f vp = camera.GetViewProjection();
    		monkeyTransform = monkeyTransform.Rotate(new Quaternion(new Vector4f(0,1,0), delta));
    		
    		if (isDay[0])
    			target.Clear((byte)0x44);
    		else
    			target.Clear((byte)0x00);
    		target.ClearDepthBuffer();
    		
    	
    		
    		Matrix4f scaling = new Matrix4f().InitScale(0.00004f, 0.00004f, 0.00004f);
    		Matrix4f translation = new Matrix4f().InitTranslation(transAmount[0],transAmount[1], transAmount[2]);
    		//Matrix4f rotation = new Matrix4f().InitRotation(rotCounter, 0.0f, rotCounter);
    		Matrix4f rotation = new Matrix4f().InitRotation(rotationAmount[0]/45.0f, 
    				rotationAmount[1]/45.0f, rotationAmount[2]/45.0f);
    		Matrix4f transform = projection.Mul(translation.Mul(rotation.Mul(scaling)));
    		
    		
    		
    		Matrix4f translation2 = new Matrix4f().InitTranslation(transAmount2[0]/10.0f,
    				transAmount2[1]/10.0f, transAmount2[2]/
					10.0f);
    		Matrix4f rotation2 = new Matrix4f().InitRotation(rotationAmount2[0]/45.0f, 
    				rotationAmount2[1]/45.0f, rotationAmount2[2]/45.0f);
    		Matrix4f transform2 = projection.Mul((translation2.Mul(rotation2)));
    		
    		//Views
    		if (previousViewIndex != viewSelection[0]) {
    			switch(viewSelection[0]) {
    			case 0:
    				camera.Rotate(new Vector4f(0,0,1), new Vector4f(0,1,0));
    				//camera.TranslateTo(new Vector4f(-10.0f, 0.0f, -4.0f));
    				camera.TranslateTo(new Vector4f(-0.033387847f, 0.0f, -0.1415435f, 0.5733204f));
    				rotationAmount[0]=212;
    				isTop = false;
    				break;
    			case 1:
    				camera.Rotate(new Vector4f(0,0,1), new Vector4f(0,1,0));
    				camera.TranslateTo(new Vector4f(-0.033387847f, 0.0f, -0.1415435f, 0.5733204f));
    				rotationAmount[0]=133;
    				isTop = true;
    				break;
    			case 2:
    				camera.Rotate(new Vector4f(0,0,-1), new Vector4f(0,0,1));
    				camera.TranslateTo(new Vector4f(0.021202568f, 0.0747952f, 1.5814003f, -0.8784862f));
    				rotationAmount[0]=212;
    				isTop = false;
    				break;
    			}
    			previousViewIndex = viewSelection[0];
    		}
    		
    	    mesh.Draw(target, vp, (transform), texture, viewDir, lightPos);
    		//monkeyMesh.Draw(target, vp, transform.Mul((new Matrix4f()).InitScale(1.0f, 1.0f, 1.0f)
    		//		), texture2, viewDir, lightPos);
    		//target.drawPlane(vertices, indices, (transform2), planeTexture);
    		display.SwapBuffers();
    	}
    	
     }
     
     public static SideMenu setUpSideMenu() {
    	 SideMenu sidemenu = new SideMenu();
         
         sidemenu.addSliderFloat3("Rotation", 0, 720, rotationAmount);
    	 
         String[] titles = {"Front","Top", "Opposite"};
         
         sidemenu.addRadioGroup("View", 3, titles, viewSelection);
         sidemenu.addCheckBox("Day/Night", "isDay", isDay);
         sidemenu.addCheckBox("Light switch", "isOn", isLightOn);
         sidemenu.addSliderFloat3("Light pos", -100, 100, lightTrans);
         
         

         
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
     

     
}
