package gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;

import logic.ImageHandler;
import logic.SeamCarving;

public class MainFrame extends javax.swing.JFrame{
	public static void main(String[] args) {
	    // TODO code application logic here
		MainFrame thisFrame = new MainFrame();
		thisFrame.execute();
		
		ImageHandler ih = new ImageHandler();
		BufferedImage image = ih.fetchImage("C:\\Users\\Jonas Meise\\eclipse-workspace\\SeamCarving\\test3.png");
		Color[][] matrix = ih.imageToArray(image);
		
		SeamCarving sc = new SeamCarving(matrix);
		sc.generateEnergyMatrix();
		int[] optimalSeam = sc.findOptimalSeam(sc.getEnergyMatrix(), SeamCarving.SeamTypes.VERTICAL);
		
		for(int y=0;y < optimalSeam.length;y++) {
			//System.out.println(optimalSeam[y] + "," + y);
			image.setRGB(optimalSeam[y], y, getIntFromColor(255, 0, 0));
		}
		
		try {
			ImageIO.write(image, "jpg", new File("XD.jpg"));
			
			int k  = 300;
			int j = 0;
			sc.removeSeams(k, j);
			ImageIO.write(sc.bounceImage(), "jpg", new File("XD_" + k + ".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getIntFromColor(int Red, int Green, int Blue){
	    Red = (Red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
	    Green = (Green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
	    Blue = Blue & 0x000000FF; //Mask out anything not blue.

	    return 0xFF000000 | Red | Green | Blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
	
	public void execute() {
	    MainFrame frame=new MainFrame();
	    frame.initComponents();
	    frame.setTitle("Word Cloud");
	    frame.setSize(1000, 620);
	    frame.setResizable(false);
	    frame.setLocation(50, 50);
	    frame.setVisible(true);

	}
	
	private void initComponents() {
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JButton jButton4 = new javax.swing.JButton();
        JButton jButton5 = new javax.swing.JButton();
        JButton jButton6 = new javax.swing.JButton();
        
        JTextField textfield1 = new JTextField("Text field 1",10);
        JTextField textfield2 = new JTextField("Text field 2",10);
        JTextField textfield3 = new JTextField("Text field 3",10);
        getContentPane().add(textfield1);
        getContentPane().add(textfield2);
        getContentPane().add(textfield3);
       
        pack();
        setVisible(true);
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());
 
        jButton1.setText("jButton1");
        getContentPane().add(jButton1);
 
        jButton2.setText("jButton2");
        getContentPane().add(jButton2);
 
        jButton3.setText("jButton3");
        getContentPane().add(jButton3);
 
        jButton4.setText("jButton4");
        getContentPane().add(jButton4);
 
        jButton5.setText("jButton5");
        getContentPane().add(jButton5);
 
        jButton6.setText("jButton6");
        getContentPane().add(jButton6);
 
        pack();
    }
}