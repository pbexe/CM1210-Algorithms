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

    public static void main(String[] args){
        try {
            List<String> data = parseData("data.txt");
            List<String> bubbleSorted = bubbleSort(data);
            List<String> mergeSorted = mergeSort(data);

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
     *
     * @return The parsed list of words
     *
     * @throws FileNotFoundException Thrown when the data file cannot be loaded
     *
     * @see main
     */
    public static List<String> parseData(String path) throws FileNotFoundException{
        List<String> words = new ArrayList<String>();
        try {
            File fileIn = new File(path);
            Scanner in = new Scanner(fileIn);

            while (in.hasNext()) {
                // Get a line of the text file
                String word = in.next().replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!words.contains(word) && word.length() > 3) {
                    words.add(word);
                }
                if (words.size() >= 1000) {
                    break;
                }
            }
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
        int n = dataUnsorted.size();
        String[] data = new String[dataUnsorted.size()];
        data = dataUnsorted.toArray(data);
        boolean done = false;
        int limit = n - 1;
        String temp;
        while (!done) {
            done = true;
            for (int j = 0; j < limit; j++) {
                if (data[j + 1].compareTo(data[j]) < 0 ) {
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
        List<String> data = new ArrayList<String>(dataUnsorted);
        if (data.size() <= 1) {
            return data;
        }

        List<String> leftBranch = new ArrayList<String>();
        List<String> rightBranch = new ArrayList<String>();
        int i = 0;
        for (String item : data) {
            if (i < data.size()/2) {
                leftBranch.add(item);
            } else {
                rightBranch.add(item);
            }
            i++;
        }

        leftBranch = mergeSort(leftBranch);
        rightBranch = mergeSort(rightBranch);
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
        List<String> merged = new ArrayList<String>();
        while (leftBranch.size() > 0 && rightBranch.size() > 0) {
            if (leftBranch.get(0).compareTo(rightBranch.get(0)) < 0) {
                merged.add(leftBranch.get(0));
                leftBranch.remove(0);
            } else {
                merged.add(rightBranch.get(0));
                rightBranch.remove(0);
            }
        }

        while (leftBranch.size() != 0) {
            merged.add(leftBranch.get(0));
            leftBranch.remove(0);
        }

        while (rightBranch.size() != 0) {
            merged.add(rightBranch.get(0));
            rightBranch.remove(0);
        }
        return merged;
    }
}
