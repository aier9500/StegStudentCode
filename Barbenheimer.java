import java.util.Scanner;

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

        Picture altHeimer = altHeimer(whichPic);
        altHeimer.explore();
        
    }

    /**
     * @param whichPic 
     * @param howPink int variable specifying how intense the pink hue will be
     * @return a picture with pink Barbie hue according to the intensity specified by howPink
     */

    public static Picture barbie(String whichPic, int howPink) {

        Picture barbie = new Picture(whichPic);
        
        Pixel[][] barbiePixels = barbie.getPixels2D(); 

        // The hue we are going to use is R246, G88, B184

        int r = 245; 
        int g = 88; 
        int b = 184; 

        for (Pixel[] row : barbiePixels) {

            for (Pixel col : row) {

                int newR = (col.getRed() + (r * howPink)) / (howPink + 1); 
                int newG = (col.getGreen() + (g * howPink)) / (howPink + 1); 
                int newB = (col.getBlue() + (b * howPink)) / (howPink + 1); 
                col.setRed(newR);
                col.setGreen(newG);
                col.setBlue(newB);
            }
        }

        return barbie; 
    }
    
    /**
     * @param whichPic
     * @return a grayscale picture
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

    // Below is a versino of oppenheimer() made using while loops
    
    public static Picture altHeimer(String whichPic) {
        
        Picture oppenheimer = new Picture(whichPic);

        Pixel[][] oppenPixels = oppenheimer.getPixels2D();

        int row = 0; 

        while (row < oppenPixels.length) {

            int col = 0; 

            while (col < oppenPixels[row].length) {

                int average = (oppenPixels[row][col].getRed() + oppenPixels[row][col].getGreen() + oppenPixels[row][col].getBlue()) / 3; 
                oppenPixels[row][col].setRed(average);
                oppenPixels[row][col].setGreen(average);
                oppenPixels[row][col].setBlue(average);
                col++; 
            }
        
            row++;
        }
        
        return oppenheimer;
    }
        
    
}
