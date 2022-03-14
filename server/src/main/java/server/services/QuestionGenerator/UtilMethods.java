package server.services.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UtilMethods {

    /**
     * Method that generate list of random numbers that are between a given interval and sum up to a given number
     *
     * @param numberOfItems the size of the desired list
     * @param targetSum     the desired sum of all the items in the list
     * @param lowerBound    the minimum value of an element in the list
     * @param upperBound    the maximum value of an element in the list
     * @param random        the random instance that will be used for randomisation
     * @return a list of integers that represent all the numbers that respects all conditions mentioned above or throws an exception if it is not possible
     */
    public static List<Integer> generateRandomQuestionDistribution(int numberOfItems, int targetSum, int lowerBound, int upperBound, Random random) {
        List<Integer> distribution = new ArrayList<>();
        for (int i = 0; i < numberOfItems; i++) {
            distribution.add(lowerBound);
        }
        int currentSum = lowerBound * numberOfItems;

        if (currentSum > targetSum || numberOfItems * upperBound < targetSum) {
            throw new IllegalArgumentException();
        }

        while (currentSum < targetSum) {
            int element = random.nextInt(numberOfItems);
            int valueAt = distribution.get(element);
            if (valueAt < upperBound) {
                distribution.set(element, valueAt + 1);
                currentSum++;
            }
        }

        return distribution;
    }
}
