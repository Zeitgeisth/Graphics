import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SideMenu extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SideMenu() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setAlignmentY(0f);
//		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new GridLayout(0,1,1,1));
		JLabel titleLabel = new JLabel("GUI Controls");
		titleLabel.setAlignmentX(LEFT_ALIGNMENT);
		titleLabel.setBorder(new EmptyBorder(5,5,5,5));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(200,200,200));
		this.add(titleLabel);
		
		//this.setBackground(Color.DARK_GRAY);
//		
//	    JButton jButton1 = new JButton();
//	    JButton jButton2 = new JButton();
//	    JButton jButton3 = new JButton();
//	    JButton jButton4 = new JButton();
//
//        
//        this.add(jButton1);
//        this.add(jButton2);
//        this.add(jButton3);
//        this.add(jButton4);
//        this.add((new JPanel()).add(new JSlider(JSlider.HORIZONTAL)));
        
        
	}
	
	public void addSliderFloat3(String title, int minVal, int maxVal, int[] defaultVals) {
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(LEFT_ALIGNMENT);
		titleLabel.setBorder(new EmptyBorder(5,5,5,5));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(220,220,220));
		
		//this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		JSlider slider1 = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, defaultVals[0]);
		JSlider slider2 = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, defaultVals[1]);
		JSlider slider3 = new JSlider(JSlider.HORIZONTAL, minVal, maxVal, defaultVals[2]);
		slider1.setFocusable(false);
		slider2.setFocusable(false);
		slider3.setFocusable(false);
		//Change listeners for all 3 sliders
		slider1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				System.out.println("Slider 1 changed to "+source.getValue());
				defaultVals[0] = source.getValue();
				
			}
		});
		slider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				System.out.println("Slider 2 changed to "+source.getValue());
				defaultVals[1] = source.getValue();
				
			}
		});
		slider3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				System.out.println("Slider 3 changed to "+source.getValue());
				defaultVals[2] = source.getValue();			
			}	
		});
		this.add(titleLabel);
		this.add(slider1);
		this.add(slider2);
		this.add(slider3);
		
	}
	
	public void addRadioGroup(String title, int n, String[] componentTitles, int[] selected ) {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(LEFT_ALIGNMENT);
		titleLabel.setBorder(new EmptyBorder(5,5,5,5));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(220,220,220));
		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		JRadioButton[] btns = new JRadioButton[n];
		//Create the radio buttons.
		for (int i=0;i<n;i++) {
			JRadioButton rBtn = new JRadioButton(componentTitles[i]);
			rBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
			rBtn.setActionCommand(i+"");
			rBtn.setFocusable(false);
			if (i==selected[0])
				rBtn.setSelected(true);
			group.add(rBtn);
			btns[i] = rBtn;
			
		}
		//Add and set listeners
		this.add(titleLabel);
		for (int i=0;i<n;i++) {
			this.add(btns[i]);
			btns[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Radio selected : "+e.getActionCommand());
					selected[0] = Integer.parseInt(e.getActionCommand());	
				}
			});
		}
		
	}
	
	public void addCheckBox(String title,String subText, boolean[] isChecked) {
		JLabel titleLabel = new JLabel(title);
		titleLabel.setAlignmentX(LEFT_ALIGNMENT);
		titleLabel.setBorder(new EmptyBorder(5,5,5,5));
		titleLabel.setOpaque(true);
		titleLabel.setBackground(new Color(220,220,220));
		
		JCheckBox checkbox = new JCheckBox(subText);
		checkbox.setSelected(isChecked[0]);
		checkbox.setFocusable(false);
		checkbox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.DESELECTED)
					isChecked[0] = false;
				else if (e.getStateChange() == ItemEvent.SELECTED)
					isChecked[0] = true;
				System.out.println("Checkbox status: " +isChecked[0]);
			}
			
		});
		
		this.add(titleLabel);
		this.add(checkbox);
	}
	
	


}
