package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler {
	public ImageHandler() {
		
	}	
	
	public BufferedImage fetchImage(String filePath) {
		BufferedImage returnImage = null;
		
		try {
			returnImage = ImageIO.read(new File(filePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return returnImage;
	}
	
	public Color[][] imageToArray(BufferedImage sourceImage) {
	      int width = sourceImage.getWidth();
	      int height = sourceImage.getHeight();
	      
	      Color[][] result = new Color[width][height];

	      for (int col = 0; col < width; col++) {
	         for (int row = 0; row < height; row++) {
	            result[col][row] = new Color(sourceImage.getRGB(col, row));
	         }
	      }

	      return result;
	}
}
