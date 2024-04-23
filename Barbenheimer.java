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

        Picture barbie = new Picture(whichPic);



        return barbie; 
    }
    
    public static Picture oppenheimer(String whichPic) {

        Picture oppenheimer = new Picture(whichPic); 



        return oppenheimer; 
    }
}
