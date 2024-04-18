// Eugenio Wu
// S23: Steganography Lab A1

import java.awt.Color;

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

    }
}