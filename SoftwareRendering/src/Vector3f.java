public class Vector3f
{
	float x;
	float y;
	float z;

	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f() {}
	
	

	public float Length()
	{
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	public float Max()
	{
		return Math.max(Math.max(x, y), z);
	}

	public float Dot(Vector3f r)
	{
		return x * r.x + y * r.y + z * r.z;
	}

	public Vector3f Normalized()
	{
		float length = Length();

		return new Vector3f(x / length, y / length, z / length);
	}
	


	
}