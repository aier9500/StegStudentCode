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


	/**
	 * Circles area where the two images are not the same
	 * @param pic1
	 * @param pic2
	 * @return
	 */

	public static Picture showDifferentArea(Picture pic1, ArrayList<Point> arr) {

		Picture newPic = new Picture(pic1);
		Pixel[][] newPix = newPic.getPixels2D();
		Point begin = arr.getFirst();
		Point end = arr.getLast();
		int length = end.getX() - begin.getX();
		int height = end.getY() - begin.getY();

		// Draws top line
		for (int i = 0; i < length; i++) {

			newPix[begin.getY()][begin.getX() + i].setRed(255);
			newPix[begin.getY()][begin.getX() + i].setGreen(0);
			newPix[begin.getY()][begin.getX() + i].setBlue(0);
		}

		// Draws bottom line
		for (int i = 0; i < length; i++) {

			newPix[end.getY()][begin.getX() + i].setRed(255);
			newPix[end.getY()][begin.getX() + i].setGreen(0);
			newPix[end.getY()][begin.getX() + i].setBlue(0);
		}

		// Draws left line
		for (int i = 0; i < height; i++) {

			newPix[begin.getY() + i][begin.getX()].setRed(255);
			newPix[begin.getY() + i][begin.getX()].setGreen(0);
			newPix[begin.getY() + i][begin.getX()].setBlue(0);
		}

		// Draws right line
		for (int i = 0; i < height; i++) {

			newPix[begin.getY() + i][end.getX()].setRed(255);
			newPix[begin.getY() + i][end.getX()].setGreen(0);
			newPix[begin.getY() + i][end.getX()].setBlue(0);
		}


		return newPic;
	}

	
	// Lab 4 attempt 2 starts here

	/**
	 * Takes a string consisting of letters and spaces and
	 * encodes the string into an arraylist of integers.
	 * The integers are 1-26 for A-Z, 27 for space, and 0 for end of
	 * string. The arraylist of integers is returned.
	 * @param s string consisting of letters and spaces
	 * @return ArrayList containing integer encoding of uppercase
	 * version of s
	 */

	public static ArrayList<Integer> encodeString(String s) {

		s = s.toUpperCase();
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < s.length(); i++) {

			if (s.substring(i,i+1).equals(" ")) {
				
				result.add(27);

			} else {

				result.add(alpha.indexOf(s.substring(i,i+1))+1);
			}
		}

		result.add(0);
		return result;
	}


	/** 
	 *
	 * Returns the string represented by the codes arraylist.
	 * 1-26 = A-Z, 27 = space
	 * @param codes encoded strinng
	 * @return decoded string
	 */
	
	private static String decodeString(ArrayList<Integer> codes) {
		String result = "";
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < codes.size(); i++) {

			if (codes.get(i) == 27) {

				result = result + 1; 

			} else {

				result = result +
					alpha.substring(codes.get(i)-1,codes.get(i));
			}
		}

		return result;
	}


	/** 
	 * Given a number from 0 to 63, creates and returns a 3-element
	 * int array consisting of the integers representing the
	 * pairs of bits in the number from right to left. 
	 * @param num number to be broken up
	 * @return bit pairs in number
	 */

	private static int[] getBitPairs(int num) {
		int[] bits = new int[3];
		int code = num; 

		for (int i= 0; i < 3; i++) {

			bits[i] = code % 4;
			code = code / 4; 
		}

		return bits; 
	}


	/** 
	 * Hide a string (msut be only capital letters and spaces) in a
	 * picture.
	 * The string always starts in the upper left corner.
	 * @param source picture to hide string in
	 * @return picture with hidden string
	 */

	public static void hideText(Picture source, String s) {

		Pixel[][] sourceArr = source.getPixels2D(); // the picture

		ArrayList<Integer> encoded = encodeString(s); // the string converted into number
		int count = 0; // used to index the string
		
		for (int row = 0; row < sourceArr.length; row++) {

			for (int col = 0; col < sourceArr[row].length; col++) {
				
				if (count < s.length()) {

					int[] bitsPairs = getBitPairs(encoded.get(count));

					// hides text in lower two bits of pixel
					clearLow(sourceArr[row][col]);
					sourceArr[row][col].setRed(sourceArr[row][col].getRed() + bitsPairs[0]);
					sourceArr[row][col].setGreen(sourceArr[row][col].getGreen() + bitsPairs[1]);
					sourceArr[row][col].setBlue(sourceArr[row][col].getBlue() + bitsPairs[2]);
					System.out.println("Hide successful, count = " + count);

				} else {

					// sets all rgb values to 0
					clearLow(sourceArr[row][col]);
					System.out.println("Break successful");
					break; 
				}

				count++; 
			}
		}
	}


	// Everything above works
	
	/** 
	 * Returns a string hidden in a picture
	 * @param source picture with hidden string
	 * @return revealed string
	 */

	public static String revealText(Picture source) {
		
		String hiddenText = ""; 
		Pixel[][] sourcePixels = source.getPixels2D();
		boolean stop = false; 
		ArrayList<Integer> encodedString = new ArrayList<Integer>();

		for (int row = 0; row < sourcePixels.length; row++) {

			for (int col = 0; col < sourcePixels[row].length; col++) {

				int red = sourcePixels[row][col].getRed();
				int green = sourcePixels[row][col].getGreen();
				int blue = sourcePixels[row][col].getBlue();

				int combined = red + (green * 4) + (blue * 16); 

				if (combined == 0) {

					stop = true; 
					break;
				}

				encodedString.add(combined); 
			}

			if (stop) break; 
		}
		
		
		hiddenText = decodeString(encodedString); 
		return hiddenText; 
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

		// Following tests showDifferentArea()
		Picture hall = new Picture("femaleLionAndHall.jpg");
		Picture robot2 = new Picture("robot.jpg");
		Picture flower2 = new Picture("flower1.jpg");
		// hide pictures
		Picture hall2 = hidePicture(hall, robot2, 50, 300);
		Picture hall3 = hidePicture(hall2, flower2, 115, 275);
		hall3.explore();
		if (!isSame(hall, hall3)) {

			Picture hall4 = showDifferentArea(hall, findDifferences(hall, hall3));
			hall4.show();
			Picture unhiddenHall3 = revealPicture(hall3);
			unhiddenHall3.show();
		}

		// Lab 4 begins here
		Picture barbaraS = new Picture("barbaraS.jpg"); 
		String hide = "Good morning"; 
		hideText(barbaraS, hide);

		// Everything above works

		System.out.println(revealText(barbaraS));
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