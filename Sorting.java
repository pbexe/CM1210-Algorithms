import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
* Sorting
*/
public class Sorting {

    static int comparisons;
    static int swaps;
    static int moves;

    public static void main(String[] args){
        try {
            for (int i = 100; i <= 1000; i += 100) {
                List<String> data = parseData("data.txt", i);
                comparisons = 0;
                swaps = 0;
                long startBubble = System.nanoTime();
                List<String> bubbleSorted = bubbleSort(data);
                long endBubble = System.nanoTime();
                long durationBubble = (endBubble - startBubble);
                System.out.println("Bubble sort for " + Integer.toString(data.size()) +" items:");
                System.out.println("Comparisons:" + Integer.toString(comparisons));
                System.out.println("Swaps:" + Integer.toString(swaps));
                System.out.println("Duration:" + Long.toString(durationBubble) + "ns");
                comparisons = 0;
                moves = 0;
                long startMerge = System.nanoTime();
                List<String> mergeSorted = mergeSort(data);
                long endMerge = System.nanoTime();
                long durationMerge = (endMerge - startMerge);
                System.out.println("Merge sort " + Integer.toString(data.size()) + " items:");
                System.out.println("Comparisons:" + Integer.toString(comparisons));
                System.out.println("Moves:" + Integer.toString(moves));
                System.out.println("Duration:" + Long.toString(durationMerge) + "ns");
            }

        } catch (FileNotFoundException e) {
            System.out.println("The file could not be loaded");
            return;
        }
    }

    /**
     * Loads a data file and returns a parsed list of unique words longer than 3
     * characters with no capitals and no punctuation.
     *
     * @param path The path to the data file
     * @param length The number of words that shall be fetched
     *
     * @return The parsed list of words
     *
     * @throws FileNotFoundException Thrown when the data file cannot be loaded
     *
     * @see main
     */
    public static List<String> parseData(String path, int length) throws FileNotFoundException{
        // List to store the words in
        List<String> words = new ArrayList<String>();
        try {
            // Open and read the file
            File fileIn = new File(path);
            Scanner in = new Scanner(fileIn);

            // Iterate through the lines
            while (in.hasNext()) {
                // Get a line of the text file with no punctuation or capital letters
                String word = in.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                // Add the word to the list if it isn't already there and if it is longer than 3 characters
                if (!words.contains(word) && word.length() > 3) {
                    words.add(word);
                }
                // Stop adding words when enough have been found
                if (words.size() >= length) {
                    break;
                }
            }
            // Finish up
            in.close();
            return words;
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    /**
     * Improved implementation of the bubble sort algorithm
     *
     * @param dataUnsorted The data to sort
     *
     * @return the sorted data
     *
     * @see main
     * @see parseData
     * @see mergeSort
     */
    public static List<String> bubbleSort(List<String> dataUnsorted) {
        // Get the length of the data
        int n = dataUnsorted.size();
        // Create a new array to store the data in and then populate it
        String[] data = new String[dataUnsorted.size()];
        data = dataUnsorted.toArray(data);
        // Run the bubble sort algorithm
        boolean done = false;
        int limit = n - 1;
        String temp;
        while (!done) {
            comparisons++;
            done = true;
            for (int j = 0; j < limit; j++) {
                comparisons++;
                if (data[j + 1].compareTo(data[j]) < 0 ) {
                    swaps++;
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                    done = false;
                }
            }
            limit = limit - 1;
        }
        return Arrays.asList(data);
    }

    /**
     * Implementation of the merge sort algorithm
     *
     * @param dataUnsorted The data to sort
     *
     * @return the sorted data
     *
     * @see merge
     * @see main
     * @see parseData
     * @see bubbleSort
     */
    public static List<String> mergeSort(List<String> dataUnsorted) {
        // Copy the data into a new object
        List<String> data = new ArrayList<String>(dataUnsorted);

        // The base case
        if (data.size() <= 1) {
            comparisons++;
            return data;
        }

        // Create two branches for the data to be divided into
        List<String> leftBranch = new ArrayList<String>();
        List<String> rightBranch = new ArrayList<String>();
        int i = 0;
        // Divide the data equally amongst these two branches
        for (String item : data) {
            comparisons++;
            if (i < data.size()/2) {
                leftBranch.add(item);
                moves++;
            } else {
                rightBranch.add(item);
                moves++;
            }
            i++;
        }

        // Recursively divide the two branches until the base case is met
        leftBranch = mergeSort(leftBranch);
        rightBranch = mergeSort(rightBranch);
        // Finally, merge all of the sub-branches into one list
        return merge(leftBranch, rightBranch);
    }

    /**
     * Performs the merging role of the merge sort implementation
     *
     * @param leftBranch The left branch to merge
     * @param rightBranch The right branch to merge
     *
     * @return The two merged branches
     *
     * @see mergeSort
     */
    public static List<String> merge(List<String> leftBranch, List<String> rightBranch) {
        // A list to store the merged result in
        List<String> merged = new ArrayList<String>();
        // Repeat until one of the lists is empty
        while (leftBranch.size() > 0 && rightBranch.size() > 0) {
            comparisons++;
            // Add smallest value to the list first
            if (leftBranch.get(0).compareTo(rightBranch.get(0)) < 0) {
                merged.add(leftBranch.get(0));
                leftBranch.remove(0);
                moves++;
            } else {
                merged.add(rightBranch.get(0));
                rightBranch.remove(0);
                moves++;
            }
        }

        // If items remain in one of the lists, then add it to the end of the merged list
        while (leftBranch.size() != 0) {
            comparisons++;
            merged.add(leftBranch.get(0));
            leftBranch.remove(0);
            moves++;
        }

        while (rightBranch.size() != 0) {
            comparisons++;
            merged.add(rightBranch.get(0));
            rightBranch.remove(0);
            moves++;
        }
        return merged;
    }
}
