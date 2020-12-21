package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SeamCarving {
	Color[][] matrix;
	Double[][] energyMatrix;
	private int height, width;
	
	public enum SeamTypes
	{
	 VERTICAL, HORIZONTAL
	}
	
	public SeamCarving(Color[][] matrix) {
		setMatrix(matrix);
	}
	
	public void setMatrix(Color[][] matrix) {
		this.matrix = matrix;
		this.height = matrix[0].length;
		this.width = matrix.length;
		
		energyMatrix = new Double[width][height];
		
		System.out.println("Generated matrix (" + this.width + "x" + this.height + ").");
	}
	
	public Double[][] getEnergyMatrix() {
		return energyMatrix;
	}
	
	public Double[][] generateEnergyMatrix() {
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				Double currentEnergyValue = getEnergy(x,y);
				energyMatrix[x][y] = currentEnergyValue;
				
				//System.out.println(x + "," + y + " = " + currentEnergyValue);
			}
		}
		
		return getEnergyMatrix();
	}
	
	public int[] findOptimalSeam(Double[][] energyMatrix, SeamTypes seamType) {
		 //da wir keine Pointer in Java haben, merken wir uns hiermit den Pointer auf die obere Zeile
		double[][] weightings = new double[width][height];
		int[][] pointerMatrix = new int[width][height];
		//minSeam is changed within the if-clause
		int[] minSeam = new int[0];
		
		//TODO: Optimize Source Code for vertical/horizontal here
		
		if(seamType==SeamTypes.VERTICAL) {
			for(int x = 0;x < width;x++) weightings[x][0] = energyMatrix[x][0];
			
			for(int y = 1; y < height; y++) {
				for(int x = 0; x < width; x++) {
					double d_1 = fetchEnergy(weightings, x-1,y-1);
					double d0 = fetchEnergy(weightings, x,y-1);
					double d1 = fetchEnergy(weightings, x+1,y-1);
					double minimum = min(d_1, d0, d1);
					
					weightings[x][y] = minimum + energyMatrix[x][y];
					
					if(minimum==d_1) {
						pointerMatrix[x][y-1] = -1;
					} else if(minimum==d0) {
						pointerMatrix[x][y-1] = 0;
					} else {
						pointerMatrix[x][y-1] = 1;
					}
				}
			}
			
			int localMinimum = 0;
			for(int i=0;i < width;i++) {
				if(weightings[i][height-1] < weightings[localMinimum][height-1]) {
					localMinimum = i;
				}
			}

			minSeam = new int[height];
			minSeam[height-1] = localMinimum;
			
			int nextPoint = localMinimum;
			for(int y=height-2;y>=0;y--) {
				nextPoint = nextPoint + pointerMatrix[nextPoint][y];
				
				minSeam[y] = nextPoint;
			}
		} else {
			for(int y = 0;y < height;y++) weightings[0][y] = energyMatrix[0][y];
			
			for(int x = 1; x < width; x++) {
				for(int y = 0; y < height; y++) {
					double d_1 = fetchEnergy(weightings, x-1,y-1);
					double d0 = fetchEnergy(weightings, x-1,y);
					double d1 = fetchEnergy(weightings, x-1,y+1);
					double minimum = min(d_1, d0, d1);
					
					weightings[x][y] = minimum + energyMatrix[x][y];
					
					if(minimum==d_1) {
						pointerMatrix[x-1][y] = -1;
					} else if(minimum==d0) {
						pointerMatrix[x-1][y] = 0;
					} else {
						pointerMatrix[x-1][y] = 1;
					}
				}
			}
			
			int localMinimum = 0;
			for(int i=0;i < height;i++) {
				if(weightings[width-1][i] < weightings[width-1][localMinimum]) {
					localMinimum = i;
				}
			}

			minSeam = new int[width];
			minSeam[width-1] = localMinimum;
			
			int nextPoint = localMinimum;
			for(int x=width-2;x>=0;x--) {
				nextPoint = nextPoint + pointerMatrix[x][nextPoint];
				
				minSeam[x] = nextPoint;
			}
		}
		
		return minSeam;
	}
	
	public double fetchEnergy(double[][] energyMatrix, int x, int y) {
		if(x < 0 || x >= width) {
			return Double.MAX_VALUE;
		} else if (y < 0 || y >= height) {
			return Double.MAX_VALUE;
		} else {
			return energyMatrix[x][y];
		}
	}
	
	public double getEnergy(int x, int y) {
		Double returnDouble = Double.MAX_VALUE;
		
		//Sanity check
		if(matrix!=null && (x>=0 && x < width) && (y>=0 && y < height)) {
			//Approximate energy function with the sum of the squares of the local gradients in x- and y- direction
			Double xApprox;
			Color cx1, cx2, cy1, cy2;
			Double yApprox;
			
			if(x == 0) {
				cx1 = matrix[x][y];
				cx2 = matrix[x+1][y];
			} else if(x == (width-1)){
				cx1 = matrix[x-1][y];
				cx2 = matrix[x][y];
			} else {
				cx1 = matrix[x-1][y];
				cx2 = matrix[x+1][y];
			}
			
			if(y == 0) {
				cy1 = matrix[x][y];
				cy2 = matrix[x][y+1];
			} else if(y == (height-1)){
				cy1 = matrix[x][y-1];
				cy2 = matrix[x][y];
			} else {
				cy1 = matrix[x][y-1];
				cy2 = matrix[x][y+1];
			}
			
			xApprox = Math.pow(cx1.getRed() - cx2.getRed(), 2)
					+ Math.pow(cx1.getBlue() - cx2.getBlue(), 2)
					+ Math.pow(cx1.getGreen() - cx2.getGreen(), 2);
			yApprox = Math.pow(cy1.getRed() - cy2.getRed(), 2)
					+ Math.pow(cy1.getBlue() - cy2.getBlue(), 2)
					+ Math.pow(cy1.getGreen() - cy2.getGreen(), 2);
			
			returnDouble = Math.sqrt(xApprox + yApprox);
		}
		
		return returnDouble;
	}
	
	public Double max(double d1, double d2, double d3) {
		//hardcoden alles runter weil es am schnellsten für java compiler geht
		if(d1 >= d2 && d1 >= d3) {
			return d1;
		} else if(d2 >= d1 && d2 >= d3) {
			return d2;
		} else {
			return d3;
		}
	}
	
	public Double min(double d1, double d2, double d3) {
		//hardcoden alles runter weil es am schnellsten für java compiler geht
		if(d1 <= d2 && d1 <= d3) {
			return d1;
		} else if(d2 <= d1 && d2 <= d3) {
			return d2;
		} else {
			return d3;
		}
	}
	
	public void removeSeams(int x, int y) {
		//TODO: Optimize Source Code for vertical/horizontal here
		
		for(int i=0;i<x;i++) {
			removeSeamFromMatrix(findOptimalSeam(energyMatrix, SeamTypes.VERTICAL), SeamTypes.VERTICAL);
			
			//Update Energy-Matrix
			generateEnergyMatrix();
			
			System.out.println("Removed " + (i+1) + "/" + x + " vertical seams so far");
		}
		
		for(int i=0;i<y;i++) {
			removeSeamFromMatrix(findOptimalSeam(energyMatrix, SeamTypes.HORIZONTAL), SeamTypes.HORIZONTAL);
			
			//Update Energy-Matrix
			generateEnergyMatrix();
			
			System.out.println("Removed " + (i+1) + "/" + y + " horizontal seams so far");
		}
	}
	
	public BufferedImage bounceImage() {
		BufferedImage returnImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < width; x++) {
		    for (int y = 0; y < height; y++) {
		    	returnImage.setRGB(x, y, matrix[x][y].getRGB());
		    }
		}
		
		return returnImage;
	}
	
	public void removeSeamFromMatrix(int[] verticalSeam, SeamTypes seamType) {
		Color[][] newMatrix = matrix;
		//TODO: Optimize Source Code for vertical/horizontal here
		
		if(seamType==SeamTypes.VERTICAL) {
			newMatrix = new Color[width-1][height];
			
			for(int y=0;y<height;y++) {
				int position = 0;
				
				for(int x=0;x<width;x++) {
					if(x!=verticalSeam[y]) {
						newMatrix[position][y] = matrix[x][y];
						position++;
					} 
				}
			}
		} else {
			newMatrix = new Color[width][height-1];
			
			for(int x=0;x<width;x++) {
				int position = 0;
				
				for(int y=0;y<height;y++) {
					if(y!=verticalSeam[x]) {
						newMatrix[x][position] = matrix[x][y];
						position++;
					} 
				}
			}
		}
		
		setMatrix(newMatrix);
	}
}
