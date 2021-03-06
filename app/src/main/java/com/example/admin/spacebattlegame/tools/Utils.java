package com.example.admin.spacebattlegame.tools;

import java.util.Random;

/**
 * Created by Jialin Liu on 04/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class Utils {



    public static double clamp(double min, double v, double max) {
        if (v > max) {
            return max;
        }

        if (v < min) {
            return min;
        }

        return v;
    }

    public static int clamp(int min, int v, int max) {
        if (v > max) {
            return max;
        }

        if (v < min) {
            return min;
        }

        return v;
    }

    /**
     * Adds a small noise to the input value.
     * @param input value to be altered
     * @param epsilon relative amount the input will be altered
     * @param random random variable in range [0,1]
     * @return epsilon-random-altered input value
     */
    public static double noise(double input, double epsilon, double random)
    {
        if(input != -epsilon) {
            return (input + epsilon) * (1.0 + epsilon * (random - 0.5));
        }else {
            //System.out.format("Utils.tiebreaker(): WARNING: value equal to epsilon: %f\n",input);
            return (input + epsilon) * (1.0 + epsilon * (random - 0.5));
        }
    }

    //Normalizes a value between its MIN and MAX.
    public static double normalise(double a_value, double a_min, double a_max)
    {
        if(a_min < a_max)
            return (a_value - a_min)/(a_max - a_min);
        else    // if bounds are invalid, then return same value
            return a_value;
    }

    public static Object choice(Object[] elements, Random rnd)
    {
        return elements[rnd.nextInt(elements.length)];
    }

    public static int choice(int[] elements, Random rnd)
    {
        return elements[rnd.nextInt(elements.length)];
    }

    public static int argmax (double[] values)
    {
        int maxIndex = -1;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < values.length; i++) {
            double elem = values[i];
            if (elem > max) {
                max = elem;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static double[] meanArray(double[][] matrix) {
        double[] res = new double[matrix[0].length];
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[0].length; j++) {
                res[j] += matrix[i][j];
            }
        }
        for (int j=0; j<matrix[0].length; j++) {
            res[j] /= matrix.length;
        }
        return res;
    }

    public static int randomInRange(Random rdm, int min, int max, int gap) {
        int unit = (max-min)/gap;
        return (rdm.nextInt(unit+1)*gap+min);
    }
}
