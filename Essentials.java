/**
 * File: essentials.java
 * File Created: Sunday, 19 January 2020 19:00 PM
 * @author Wisdom Kalu
 * @version 1.0
 * Modified By: Wisdom Tochi Kalu
 * Last Modified: Sunday, 19 January 2020 19:24 PM
 * Copyright @2020 Wisdom Tochi Kalu
 * Brief:
 * ------
 * This program 
 * ------
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Essentials {
    public String item;
    public int quantity;
    public double price;

    /**
     * A defualt constructor 
     */
    public Essentials() { }

    /**
     * A constructor with parameters
     * @param item initialises the name of the item 
     * @param quantity Initialises the quantity of the item 
     * @param price Initialises the price of the commodity
     */
    public Essentials(String item, int quantity, double price) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    public void setItem (String x) {
        this.item = x;
    }

    public String getItem() {
        return item;
    }

    public void setQuantity (int y) {
        this.quantity = y;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice (double z) {
        this.price = z;
    }
   
    public double getPrice() {
        return price;
    }

   

  public void readFromFile() throws Exception { 
    // pass the path to the file as a parameter 
    File file = 
      new File("C:\\Users\\tochi\\Desktop\\test.txt"); 
    Scanner sc = new Scanner(file); 
  
    while (sc.hasNextLine()) 
      System.out.println(sc.nextLine()); 
  } 



    public static void main(String[] args) {
        

        try {
            Essentials wisd = new Essentials();
            PrintWriter printWriter = null;
            printWriter = new PrintWriter(new FileOutputStream("Essentials_stock.txt", true));
            
            printWriter.printf("ITEM " + "   " + " QUANTITY " +"   " + " PRICE ");
            printWriter.println();
            // A scanner method to get the item from the user
            for (int j =0; j<2; j++) {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter Items at the Shop: ");
                String str = input.nextLine();
                System.out.print("Enter the Quantity: ");
                int i = input.nextInt();
                System.out.print("Enter the Price: ");
                double d = input.nextDouble();

                wisd.setItem(str);
                wisd.setQuantity(i);
                wisd.setPrice(d);
 
                printWriter.printf(wisd.getItem() + "        " + wisd.getQuantity() + "         " + wisd.getPrice());
                printWriter.println();
            }
            printWriter.close();
        }
        
        catch(FileNotFoundException fnfe) {
            fnfe.getMessage();
        }
    } 
}