import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Bitmap {
	private final int m_width;
	private final int m_height;
	private final byte m_components[];
	
	public byte GetComponent(int index) { return m_components[index]; }
	
	public Bitmap(int width, int height){
		m_width = width;
		m_height = height;
		m_components = new byte[width * height * 4];
	}
	
	public Bitmap(String fileName) throws IOException
	{
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = ImageIO.read(new File(fileName));

		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++)
		{
			int pixel = imgPixels[i];

			components[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
			components[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		m_width = width;
		m_height = height;
		m_components = components;
	}
	
	public void Clear(byte shade){
		Arrays.fill(m_components, shade);
	}
	
	public void DrawPixel(int x, int y, byte a, byte r, byte g, byte b){
		int index = (x + y * m_width) * 4;
		m_components[index      ] = a;
		m_components[index + 1  ] = r;
		m_components[index + 2  ] = g;
		m_components[index + 3  ] = b;
		
	}
	public void CopyToByteArray(byte[] dest){
		for(int i = 0; i< m_height * m_width; i++){
			dest[ i * 3     ] = m_components[i * 4 + 1];
			dest[ i * 3 + 1 ] = m_components[i * 4 + 2];
			dest[ i * 3 + 2 ] = m_components[i * 4 + 3];
		}
	}
	
	public void CopyPixel(int destX, int destY, int srcX, int srcY, Bitmap src)
	{
		int destIndex = (destX + destY * m_width) * 4;
		int srcIndex = (srcX + srcY * src.GetWidth()) * 4;
		m_components[destIndex    ] = src.GetComponent(srcIndex);
		m_components[destIndex + 1] = src.GetComponent(srcIndex + 1);
		m_components[destIndex + 2] = src.GetComponent(srcIndex + 2);
		m_components[destIndex + 3] = src.GetComponent(srcIndex + 3);
	}
	
	public int GetWidth(){
		return this.m_width;
	}
	public int GetHeight(){
		return this.m_height;
	}
}
