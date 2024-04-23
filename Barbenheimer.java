import java.util.Scanner;
import java.awt.Color;
import java.util.ArrayList;

/**
 * This program asks the user for a picture  within this package (you can add a picture)
 * and then it returns a copy with a Barbie hue, and another with Oppenheimer hue
 */

public class Barbenheimer {
    
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in); 
        
        System.out.print("Please enter the exact name of the picture you wish to add a hue to (please include the image type extension): ");
        String whichPic = scan.nextLine();
        System.out.println();

        System.out.print("In the case of the Barbie picture, how pink do you want the hue to be from 1 to 5: "); 
        int howPink = scan.nextInt(); 
        System.out.println();
        
        Picture barbie = barbie(whichPic, howPink);
        Picture oppenheimer = oppenheimer(whichPic);
        barbie.explore();
        oppenheimer.explore();
    }

    /**
     * 
     * @param whichPic 
     * @param howPink int variable specifying how intense the pink hue will be
     * @return a picture with pink Barbie hue according to the intensity specified by howPink
     */
  public static Picture barbie(String whichPic, int howPink) {

        // The hue we are going to use is R246, G88, B184

        Picture barbie = new Picture(whichPic);
        Pixel[][] barbiePixels = barbie.getPixels2D();
        
        double pinkVal = howPink / 100;
                if (howPink == 5){
                    pinkVal = 0.8;
                }

        for (Pixel[] row : barbiePixels){
            for (Pixel col : row){
                int redVal = col.getRed() - 246;
                int greenVal = col.getGreen() - 88;
                int blueVal = col.getBlue() - 184;
                

                //Check if the RGB value of the pixel is lower than default pink value of R246, G88, B184
                //Add the difference if it's lower, subtract if it's higher. Then multiply by pinkVal

                //Check the red value
                if (col.getRed() == 246){
                    col.setRed((int)(col.getRed() * pinkVal));
                }
                if ( redVal < 0){
                    col.setRed((int)((col.getRed() + redVal) * pinkVal));
                }
                else if (redVal > 0){
                    col.setRed((int)((col.getRed() - redVal) * pinkVal));
                }


                //Check the green value

                if (col.getGreen() == 88){
                    col.setGreen((int)(col.getGreen() * pinkVal));
                }
                if ( greenVal < 0){
                    col.setGreen((int)((col.getGreen() + redVal) * pinkVal));
                }
                else if (greenVal > 0){
                    col.setGreen((int)((col.getGreen() - greenVal) * pinkVal));
                }

                //Check the blue value
                if (col.getBlue() == 184){
                    col.setBlue((int)(col.getBlue() * pinkVal));
                }
                if ( blueVal < 0){
                    col.setBlue((int)((col.getBlue() + blueVal) * pinkVal));
                }
                else if (blueVal > 0){
                    col.setBlue((int)((col.getBlue() - blueVal) * pinkVal));
                }
                
            }
          
        }
        return barbie; 
    }

    /**
     * @param whichPic 
     * @return a picture with Oppenheimer hue (gray)
     */
    public static Picture oppenheimer(String whichPic) {

        Picture oppenheimer = new Picture(whichPic); 

        Pixel[][] oppenPixels = oppenheimer.getPixels2D();

        for (Pixel[] row : oppenPixels) {

            for (Pixel col : row) {
                
                int average = (col.getRed() + col.getGreen() + col.getBlue()) / 3; 
                col.setRed(average);
                col.setGreen(average);
                col.setBlue(average);
            }
        }


        return oppenheimer; 
    }
}
