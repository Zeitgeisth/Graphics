import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class Display extends Canvas {
   private final JFrame m_frame;
   private final int width, height;
   private final RenderContext m_buffer;
   private final BufferedImage m_displayImage;
   private final byte[] m_displayComponents;
   private final BufferStrategy m_bufferStrategy;
   private final Graphics m_graphics;
   
   private final Input m_input;
   
   public Display(int width, int height, String title){
	   this.width = width;
	   this.height = height;
	   Dimension size = new Dimension(width, height);
	   setPreferredSize(size);
	   setMinimumSize(size);
	   setMaximumSize(size);
	   
	   m_buffer = new RenderContext(width, height);
	   m_displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	   m_displayComponents = ((DataBufferByte)m_displayImage.getRaster().getDataBuffer()).getData();
	   m_buffer.Clear((byte)0x80);
	   m_buffer.DrawPixel(100, 100, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00);
	   
	   
	   m_frame = new JFrame();
	   m_frame.setLayout(new FlowLayout());
	   m_frame.add(this);
	   m_frame.pack();
	   m_frame.setResizable(false);
	   m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   m_frame.setLocationRelativeTo(null);
	   m_frame.setTitle(title);
	   m_frame.setVisible(true);
	   
//	   m_frame.add(new JButton("test"));
//	   m_frame.pack();
	   
	   createBufferStrategy(1);
	   m_bufferStrategy = getBufferStrategy();
	   m_graphics = m_bufferStrategy.getDrawGraphics(); 
	   
	   
		m_input = new Input();
		addKeyListener(m_input);
		addFocusListener(m_input);
		addMouseListener(m_input);
		addMouseMotionListener(m_input);

		setFocusable(true);
		requestFocus();
	   
   }
   
   public void setSideMenu(SideMenu sideMenu) {
	   //JScrollPane pane = new JScrollPane();
	   //pane.add(sideMenu);
	   //m_frame.add(pane);
	   m_frame.add(sideMenu);
	   m_frame.pack();
   }
   
   
   public void setOnClickListener(KeyListener l) {
	   m_frame.addKeyListener(l);
   }
   
   public void SwapBuffers(){
	   m_buffer.CopyToByteArray(m_displayComponents);
	   m_graphics.drawImage(m_displayImage, 0, 0, m_buffer.GetWidth(), m_buffer.GetHeight(),null);
	   m_bufferStrategy.show();
   }
   
   public RenderContext GetFrameBuffer(){
	   return m_buffer;
   }
   public int GetWidth(){
	   return this.width;
   }
   public int GetHeight(){
	   return this.height;
   }
   
   public Input GetInput(){ 
	   return m_input; 
   }
}
