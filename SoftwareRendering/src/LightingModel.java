
public class LightingModel {
	
	//Keep value between 0 and 1
	private static float Saturate(float val)
	{
		if(val > 1.0f)
			return 1.0f;
		if(val < 0.0f)
			return 0.0f;
		return val;
	}
	
	
	public static float CalculateLighting(Vertex v, Vector3f viewDir, Vector3f lightPos) {
		//v.m_texCoords.setX(Math.max(0.0f, Math.min(v.m_texCoords.GetX(), 1.0f)));
		//v.m_texCoords.setY(Math.max(0.0f, Math.min(v.m_texCoords.GetY(), 1.0f)));
		
		//Lighting calculation
		Vector3f normal = new Vector3f(v.m_normal.GetX(), v.m_normal.GetY(), v.m_normal.GetZ()).Normalized();
//		Vector3f light = new Vector3f(lightPos.x - v.GetPosition().GetX(),
//				lightPos.y -v.GetPosition().GetY(),
//				lightPos.z - v.GetPosition().GetZ()).Normalized();
//		
		Vector3f light = lightPos.Normalized();
		
		
		Vector3f halfway = new Vector3f(light.x+viewDir.x, light.y+viewDir.y, light.z+viewDir.z).Normalized(); 
		  
		//Constant ambient
		float ambient = 0.2f;
		
		
		//Diffuse
		float diffuse = normal.Dot(light);
		diffuse  = Saturate(diffuse * 0.6f);
		
		
		//Specular
		float specular = halfway.Dot(normal);
		specular = Saturate(specular);
		specular = (float) Math.pow(specular, 4.0f);
		
		
		//Total effect of light
		float totalColor = ambient + diffuse + specular;
		totalColor = Saturate(totalColor);
		
		
		return totalColor;
	}
	
	
	//public static float

}
