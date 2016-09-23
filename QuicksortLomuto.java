package com.alg;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 *  CS4050 - Algorithms and Algorithm Analysis
 *
 *  QuicksortLomuto.java
 *
 *  Imports a provided file containing consecutive integers in random order into an array to
 *  use the Lomuto's Partioning Quicksort algorithm to sort the array while calculating the
 *  time cost of sorting the provided data.
 **/

public class QuicksortLomuto {

    private int[] data;
    private int len;

    // create int array for importing data
    public QuicksortLomuto(int max) {
        data = new int[max];
        len = 0;
    }

    // init (helper function)
    public void quickSort() {
        recursiveQuicksortLomuto(0, len - 1);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  recursiveQuicksortLomuto implements Lomuto's
     *  partitioning algorithm to determine the
     *  partitioning while sorting.
     * * * * * * * * * * * * * * * * * * * * * * * * */
    public void recursiveQuicksortLomuto(int left, int right) {
        // use quicksort if rage is two or more items otherwise no need to sort single items
        if (right - left <= 0)
            return;
        else {
            long pivot = data[right]; // set pivot [right element]
            int partition = lomutoPartition(left, right, pivot); // determine partition
            recursiveQuicksortLomuto(left, partition - 1);    // sort left partition
            recursiveQuicksortLomuto(partition + 1, right);   // sort right partition
        }
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  lomutoPartition determines partition flow
     *  using Lomuto's partitioning algorithm
     * * * * * * * * * * * * * * * * * * * * * * * * */
    int lomutoPartition(int left, int right, long pivot) {
        int i = left;

        for(int j = left; j <= right-1; j++) {
            if(data[j] <= pivot) {
                swap(j, i);
                i++;
            }
        }
        swap(i, right);
        return i;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  swap indexes [ints] of two variables/arrays
     * * * * * * * * * * * * * * * * * * * * * * * * */
    public void swap(int d1, int d2) {
        int temp = data[d1];
        data[d1] = data[d2];
        data[d2] = temp;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  Array builder for raw data
     * * * * * * * * * * * * * * * * * * * * * * * * */
    public void insert(int value) {
        data[len] = value;
        len++;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  Display the current contents of 'data' array
     *  primarily for debugging purposes. [unused]
     * * * * * * * * * * * * * * * * * * * * * * * * */
    public void display() {
        System.out.print("Data ");
        for (int j = 0; j < len; j++)
            System.out.print(data[j] + " ");
        System.out.println("");
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * *
     *  readArrayFromFile reads a list of integers
     *  from a provided file and adds each of them
     *  to an Array of appropriate size.
     * * * * * * * * * * * * * * * * * * * * * * * * */
    public static int[] ReadArrayFromFile(String file) {
        int [] numArray;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file))))
        {
            // use amount of numbers contained in file to determine the length of the Array
            String[] arr = reader.lines().toArray(size->new String[size]);
            numArray = new int [arr.length];
            for (int i = 0; i < arr.length; i++)   // convert Array contents from String to Integer
            {
                numArray[i] = Integer.valueOf(arr[i]);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return numArray;
    }

    public static void main(String[] args) {
        int [] Array;
        Array = ReadArrayFromFile(System.getProperty("user.dir") + "/src/com/alg/Randomized_Numbers_5M.txt"); // filepath

        if (Array!=null)    // is it even worth it?
        {
            String name = "timing";
            long first_ns, last_ns, time_ns;
            float time_ms;

            QuicksortLomuto arr = new QuicksortLomuto(Array.length); // init array

            int n;
            for (int i=0; i < Array.length; i++)    // convert and fill array
            {
                n = Array[i];
                arr.insert(n);
            }
            System.out.println("Running QuicksortLomuto.java . . .");
            //arr.display();  // displays the unsorted numbers (original array)

            /** START TIMER **/
            first_ns = System.nanoTime();

            /** QUICKSORT EXECUTE **/
            arr.quickSort();

            /** STOP TIMER **/
            last_ns = System.nanoTime();

            //arr.display();    // displays sorted numbers (final sorted array)
            time_ns = last_ns - first_ns;
            time_ms = ((float)time_ns/1000000);
            System.out.println("\nUsing a quicksort with Lomuto's partitioning algorithm on an array of " + Array.length +
                               " unordered \nsequential numbers required the following amount of time: \n");
            System.out.println(time_ns + " nanoseconds");
            System.out.println(time_ms + " milliseconds");

        }


    }

}
