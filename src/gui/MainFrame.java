package gui;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import logic.ImageHandler;
import logic.SeamCarving;

public class MainFrame extends javax.swing.JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public BufferedImage currentPicture;
	public String imagePath;
	public JSpinner spinnerX, spinnerY;
	public JLabel picLabel;
	
	public static void main(String[] args) {
    	MainFrame thisFrame = new MainFrame();
		
    	//boot per command line
	    if(args.length > 0) {
			ImageHandler ih = new ImageHandler();
			
			SeamCarving sc = new SeamCarving(ih.imageToArray(ih.fetchImage(args[0])));
			sc.generateEnergyMatrix();

			sc.removeSeams(Integer.valueOf(args[1]), Integer.valueOf(args[2]));
			
			String outputPath = args[0] + "seamcarving_" + args[1] + "_" + args[2] + ".png";
			
    		try {
				ImageIO.write(sc.bounceImage(), "png", new File(outputPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	    } else {
			thisFrame.execute();
	    }
	}
	
	public void convert() {
		ImageHandler ih = new ImageHandler();
		//BufferedImage image = ih.fetchImage(imagePath);
		Color[][] matrix = ih.imageToArray(currentPicture);
		
		SeamCarving sc = new SeamCarving(matrix);
		sc.generateEnergyMatrix();

		sc.removeSeams((int)spinnerX.getValue(), (int)spinnerY.getValue());
		currentPicture = scaledDownVersion(sc.bounceImage());
		
		picLabel.setIcon(new ImageIcon(currentPicture));
	}
	
	public void execute() {
	    MainFrame frame=new MainFrame();
	    frame.initComponents();
	    frame.setTitle("Seam Carving");
	    frame.setSize(1600, 900);
	    frame.setResizable(true);
	    frame.setLocation(50, 50);
	    frame.setVisible(true);
	}
	
	public BufferedImage scaledDownVersion(BufferedImage currentPicture) {
		double width = currentPicture.getWidth();
		double height = currentPicture.getHeight();
		
		//damit das Bild nicht aus dem Rahmen platzt 
		double max_x = 1400, max_y = 788;
		double rescaleFactor = 1;
		
		if(width > max_x) {
			rescaleFactor = max_x / width;
		}
		if(height > (rescaleFactor*max_y)) {
			rescaleFactor = (rescaleFactor*max_y) / height;
		}
		
		System.out.println("Scaled down from " + width + "," + height + " -> " + rescaleFactor*width + "," + rescaleFactor*height + " (Factor: " + rescaleFactor + ")");
		
		Image scaledImage = currentPicture.getScaledInstance((int)(rescaleFactor*width), (int)(rescaleFactor*height), java.awt.Image.SCALE_SMOOTH);
		
		BufferedImage returnImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
		returnImage.getGraphics().drawImage(scaledImage, 0, 0, null);
		
		return returnImage;
	}
	
	private void initComponents() {
		JTextField imageURL = new JTextField("C:\\picture.jpg", 30);
		getContentPane().add(imageURL);
		
        picLabel = new JLabel();
        add(picLabel);
        
        JButton searchButton = new javax.swing.JButton();
        searchButton.setText("Browse for Image File...");
        getContentPane().add(searchButton);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                
                int returnValue = fc.showOpenDialog(null);
                
                if(returnValue == JFileChooser.APPROVE_OPTION) {
                	imageURL.setText(fc.getSelectedFile().getAbsolutePath());
                	
                	try {
                		currentPicture = ImageIO.read(new File(fc.getSelectedFile().getAbsolutePath()));
						picLabel.setIcon(new ImageIcon(scaledDownVersion(currentPicture)));
						imagePath = fc.getSelectedFile().getAbsolutePath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
                }
            }
        });

    	JButton loadButton = new javax.swing.JButton();
		loadButton.setText("Load Image");
		getContentPane().add(loadButton);
		
		loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		currentPicture = ImageIO.read(new File(imageURL.getText()));
					picLabel.setIcon(new ImageIcon(scaledDownVersion(currentPicture)));
					imagePath = imageURL.getText();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
		
		JButton saveButton = new javax.swing.JButton();
		saveButton.setText("Save Image");
		getContentPane().add(saveButton);
		
		saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		String outputPath = imagePath + "seamcarving_" + spinnerX.getValue() + "_" + spinnerY.getValue() + ".png";
            		ImageIO.write(currentPicture, "png", new File(outputPath));
            		
            		JOptionPane.showMessageDialog(null,"Carved image has been exported to " + outputPath, "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
        });
		
		SpinnerModel modelX = new SpinnerNumberModel(10, 0, 1000, 1);     
		SpinnerModel modelY = new SpinnerNumberModel(10, 0, 1000, 1);
		spinnerX = new JSpinner(modelX);
		spinnerY = new JSpinner(modelY);
		
		getContentPane().add(spinnerX);
		getContentPane().add(spinnerY);
		
		
		JButton carveButton = new javax.swing.JButton();
		carveButton.setText("Execute Seam-Carving");
		getContentPane().add(carveButton);
		
		carveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	convert();
            }
        });

        pack();
        setVisible(true);
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.FlowLayout());

        pack();
    } 
}