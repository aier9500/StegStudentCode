// Eugenio Wu
// S23: Steganography Lab A1

import java.awt.Color;
import java.util.ArrayList;

/**
 * Clear the lower (rightmost) two bits in a pixel.
 */
public class Steganography {
    

	/** 
	 * clears the lower two bits of a pixel
	 */

	public static void clearLow(Pixel p) {
		p.setRed((p.getRed() / 4) * 4);
		p.setBlue((p.getBlue() / 4) * 4);
		p.setGreen((p.getGreen() / 4) * 4);
	}
    

	/** 
	 * clears the lower two bits of every pixel in a picture
	 */
	
	public static Picture testClearLow(Picture pic) {

		Picture newPic = new Picture(pic);

		for (int row = 0; row < newPic.getHeight(); row++) {

			for (int col = 0; col < newPic.getWidth(); col++) {

				clearLow(newPic.getPixel(col, row));
			}
		}

		return newPic;
	}


    /** 
     * Set the lower 2 bits in a pixel to the highest 2 bits in c
     */

    public static void setLow(Pixel p, Color c) {
		clearLow(p);
		p.setRed(p.getRed() + (c.getRed() / 64));
		p.setBlue(p.getBlue() + (c.getBlue() / 64));
		p.setGreen(p.getGreen() + (c.getGreen() / 64));
	}


	/**
	 * @param pic target picture
	 * @param c color tint to add to picture
	 * @return a version of the target picture with c color tint
	 */

    public static Picture testSetLow(Picture pic, Color c) {
		
        Picture newPic = new Picture(pic);

		for (int row = 0; row < newPic.getHeight(); row++) {

			for (int col = 0; col < newPic.getWidth(); col++) {

				setLow(newPic.getPixel(col, row), c);
			}
		}

		return newPic;
	}


	/**
	 * @param hidden
	 * @return the 2-bit color hidden image 
	 */

	public static Picture revealPicture(Picture hidden) {

		Picture copy = new Picture(hidden); 
		Pixel[][] pixels = copy.getPixels2D();
		// Pixel[][] source = hidden.getPixels2D();

		for (int r = 0; r < pixels.length; r++) {

			for (int c = 0; c < pixels[0].length; c++) {
				// Color col = source[r][c].getColor();
				pixels[r][c].setRed(pixels[r][c].getRed() - ((pixels[r][c].getRed() / 64) * 64) + ((pixels[r][c].getRed() % 4) * 64));
				pixels[r][c].setGreen(pixels[r][c].getGreen() - ((pixels[r][c].getGreen() / 64) * 64) + ((pixels[r][c].getGreen() % 4) * 64));
				pixels[r][c].setBlue(pixels[r][c].getBlue() - ((pixels[r][c].getBlue() / 64) * 64) + ((pixels[r][c].getBlue() % 4) * 64));
			}
		}
		return copy; 

	}

	/**
	 * @param source image where to conceal
	 * @param secret image to conceal
	 * @return whether or not secret can be concealed within source
	 */
	
	public static boolean canHide(Picture source, Picture secret) {
        return (source.getWidth() >= secret.getWidth() && source.getHeight() >= secret.getHeight());
	}


	/**
	 * @param source image where to conceal
	 * @param secret image to conceal
	 * @return an image similar to source with secret concealed
	 */

	public static Picture hidePicture(Picture source, Picture secret) {
		
		if (!canHide(source, secret)) {

			return null;
		}
		
		Picture copy = new Picture(source);
		

		for (int row = 0; row < secret.getHeight(); row++) {

			for (int col = 0; col < secret.getWidth(); col++) {

				setLow(copy.getPixel(col, row), secret.getPixel(col, row).getColor());
			}
		}
		
		return copy;
	}


	/**
	 * 
	 * @param source image where to conceal
	 * @param secret image to conceal
	 * @param startRow the y axis of source to start the secret image
	 * @param startColumn the x axis of source to start the secret image
	 * @return an image similar to source with secret concealed
	 */

	public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn) {
		
		if (!canHide(source, secret)) {

			return null;
		}
		
		Picture copy = new Picture(source);
		

		for (int row = 0; row < secret.getHeight(); row++) {

			for (int col = 0; col < secret.getWidth(); col++) {

				setLow(copy.getPixel(col + startColumn, row + startRow), secret.getPixel(col, row).getColor());
			}
		}
		
		return copy;
	}


	/**
	 * 
	 * @param pic1
	 * @param pic2
	 * @return if the two pictures are exactly the same
	 */

	public static boolean isSame(Picture pic1, Picture pic2) {

		Pixel[][] pix1 = pic1.getPixels2D();
		Pixel[][] pix2 = pic2.getPixels2D();

		for (int row = 0; row < pix1.length; row++) {

			for (int col = 0; col < pix1[row].length; col++) {

				if (!pix1[row][col].getColor().equals(pix2[row][col].getColor())) {
					
					return false;
				}
			}
		}

		return true; 
	}


	/**
	 * @prerequisite the sizes of the two images are the same
	 * @param pic1
	 * @param pic2
	 * @return an array with the pixels that are different
	 */
	public static ArrayList<Point> findDifferences(Picture pic1, Picture pic2) {

		Pixel[][] pix1 = pic1.getPixels2D();
		Pixel[][] pix2 = pic2.getPixels2D();
		ArrayList<Point> pointList = new ArrayList<Point>(); 

		if (!canHide(pic1, pic2)) {

			return pointList;
		}

		for (int row = 0; row < pix1.length; row++) {

			for (int col = 0; col < pix1[row].length; col++) {

				if (!pix1[row][col].getColor().equals(pix2[row][col].getColor())) {

					pointList.add(new Point(col, row));
				}
			}
		}

		return pointList;
	}


	public static void main(String[] args) {

		
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        Picture copy = testClearLow(beach);
        copy.explore();
		
        
		Picture beach2 = new Picture("beach.jpg");
        beach2.explore();
        Picture copy2 = testSetLow(beach2, Color.PINK);
        copy2.explore();
		

		Picture copy3 = revealPicture(copy2);
		copy3.explore();


		Picture f1 = new Picture("flower1.jpg");
		f1.explore();
		Picture f2 = new Picture("flower2.jpg");
		hidePicture(f1, f2).explore();
		
		
		Picture beacher = new Picture("beach.jpg");
		Picture robot = new Picture("robot.jpg");
		Picture flower1 = new Picture("flower1.jpg");
		beacher.explore();
		// these lines hide 2 pictures
		Picture hidden1 = hidePicture(beach, robot, 65, 208);
		Picture hidden2 = hidePicture(hidden1, flower1, 280, 110);
		hidden2.explore();
		Picture unhidden = revealPicture(hidden2);
		unhidden.explore();


		// Following tests isSame()
		Picture swan = new Picture("swan.jpg");
		Picture swan2 = new Picture("swan.jpg");
		System.out.println("Swan and swan2 are the same: " + isSame(swan, swan2)); // should return true
		swan = testClearLow(swan);
		System.out.println("Swan and swan2 are the same (after clearLow run on swan): " + isSame(swan, swan2)); // should return false


		// Following tests findDifferences()
		Picture arch = new Picture("arch.jpg");
		Picture archh = new Picture("arch.jpg");
		Picture koala = new Picture("koala.jpg");
		Picture robot1 = new Picture("robot.jpg");
		ArrayList<Point> pointList = findDifferences(arch, archh);
		System.out.println("PointList after comparing two identical pictures has a size of: " + pointList.size());
		pointList = findDifferences(arch, koala);
		System.out.println("PointList after comparing two different sized pictures has a size of " + pointList.size()); 
		Picture arch2 = hidePicture(arch, robot1, 65, 102);
		pointList = findDifferences(arch, arch2);
		System.out.println("Pointlist after hiding a picture has a size of " + pointList.size());
		arch.show();
		arch2.show(); 
    }
}


/** 
 * Point class that holds coordinates
 */

class Point {
	
	private int x;
	private int y;

	public Point(int x, int y) {

		this.x = x; 
		this.y = y; 
	}

	public int getX() {

		return x; 
	}

	public int getY() {

		return y;
	}

	public void setX(int x) {

		this.x = x;
	}

	public void setY(int y) {

		this.y = y; 
	}
}