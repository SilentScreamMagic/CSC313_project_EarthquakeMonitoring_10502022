/**
 * File: ArrayOutput.java
 * File Created: Sunday, 19 January 2020 19:00 PM
 * @author Wisdom Kalu
 * @version 1.0
 * Modified By: Wisdom Tochi Kalu
 * Last Modified: 
 * Copyright @2020 Wisdom Tochi Kalu
 * Brief:
 * ------
 * This program returns the observatory with the largest average “galamsey” colour value,
 * the largest “galamsey” colour value ever recorded, and a list of all "galamsey" 
 * recorded with colour value greater than a given/arbitrary number. 
 * ------
 */

 import java.util.*;

public class Monitoring {

    public Observation[] observe = new Observation[20];
    /**
     * 
     * @return the largest average colour value
     */
    public int getLargestAverage() {
 
        /**
         * The code below gets the sum of all the elements in the observarory array and strikes the average
         */
        int sum1 = 0;
        for (int i=0; i<observatory1; i++) {
            if (sum1<observe[i].getLargestColour()){
                sum1=observe[i].getLargestColour();
            }
            sum += observatory1[i];
        }
    }

    public int getLargestValue() {
        int sum = 0;
        for (int i = 0; i<observatory1; i++) {
            if (sum < observe[i].getAverageColour()){
                sum = observe[i].getAverageColour();
            }
            sum += observatory1[i];
        }
    }

}