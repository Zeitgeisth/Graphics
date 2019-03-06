
public class TriangleMesh {
	Vertex v0;
	Vertex v1;
	Vertex v2;

	public Vector3f Barycentric(Vector3f p){
	    /*
	      actually 2*a1 / 2 * a2 due to cross product
	      Calculation of barycentric coordinate
	      u = (Area of v1v2p)/(Area of triangle v0v1v2)
	      v = (Area of v2v0p)/(Area of triangle v0v1v2)
	      w = (Area of v0v1p)/(Area of triangle v0v1v2)
	    */
	    float u, v, w;
	    Vector3f e0 = new Vector3f(v1.m_pos.GetX() - v0.m_pos.GetX(),
	    		v1.m_pos.GetY() - v0.m_pos.GetY(),
	    		v1.m_pos.GetZ() - v0.m_pos.GetZ());
	    Vector3f e1 = new Vector3f(v2.m_pos.GetX() - v0.m_pos.GetX(),
	    		v2.m_pos.GetY() - v0.m_pos.GetY(),
	    		v2.m_pos.GetZ() - v0.m_pos.GetZ());
	    Vector3f e2 = new Vector3f(p.x - v0.m_pos.GetX(),
	    		p.y - v0.m_pos.GetY(),
	    		p.z - v0.m_pos.GetZ());
	    float den =  1.0f / v0.TriangleArea(v1, v2);

	    v = (e2.x * e1.y - e1.x * e2.y) * den;
	    w = (e0.x * e2.y - e2.x * e0.y) * den;

	    u = 1.0f - v - w;  
	    return new Vector3f(u, v, w);
	}
}
