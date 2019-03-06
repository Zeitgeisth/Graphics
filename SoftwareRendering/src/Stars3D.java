
public class Stars3D {
	private final float m_spread;
	private final float m_speed;
	
	private final float m_starX[];
	private final float m_starY[];
	private final float m_starZ[];
	
	public Stars3D(int numStars, float spread, float speed){
		m_spread = spread;
		m_speed = speed;
		
		m_starX = new float[numStars];
		m_starY = new float[numStars];
		m_starZ = new float[numStars];
		
		for(int i = 0; i < m_starX.length; i++){
			InitStar(i);
		}
	}
	
	private void InitStar(int index){
		m_starX[index] = 2 * ((float)Math.random() - 0.5f) * m_spread;
		m_starY[index] = 2 * ((float)Math.random() - 0.5f) * m_spread;
		m_starZ[index] = 2 * ((float)Math.random() + 0.00001f) * m_spread;
	}
	
	public void UpdateAndRender(RenderContext target, float delta){
		
		final float tanHalfFov = (float)Math.tan(Math.toRadians(90.0/2.0));
		
		target.Clear((byte)0x00);
		float halfWidth = target.GetWidth()/2.0f;
		float halfHeight = target.GetHeight()/2.0f;
		int triangleBuilderCounter = 0;
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		for(int i = 0; i < m_starX.length; i++){
			m_starZ[i] -= delta  * m_speed;
			if(m_starZ[i] <= 0){
				InitStar(i);
			}
			
			int x = (int)((m_starX[i]/(m_starZ[i] * tanHalfFov)) * halfWidth + halfWidth);
			int y = (int)((m_starY[i]/(m_starZ[i] * tanHalfFov)) * halfHeight + halfHeight);
			
			if(x < 0 || x >= target.GetWidth() || (y < 0 || y >= target.GetHeight())){
				InitStar(i);
				continue;
			}
//			else{
//				target.DrawPixel(x, y, (byte)0xFF, (byte)0x00, (byte)0xFF, (byte)0xFF);
//			}
			
			triangleBuilderCounter++;
			if(triangleBuilderCounter == 1){
				x1 = x;
				y1 = y;
			}else if(triangleBuilderCounter == 2){
				x2 = x;
				y2 = y;
			}else if(triangleBuilderCounter == 3){
				triangleBuilderCounter = 0;
//				Vertex v1 = new Vertex(new Vector4f(-1, -1, 0, 1), new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
//				Vertex v2 = new Vertex(new Vector4f(-1, -1, 0, 1), new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
//				Vertex v3 = new Vertex(new Vector4f(-1, -1, 0, 1), new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));
//				
				//target.FillTriangle(v1, v2, v3);
			}
		}
		
	}
}
