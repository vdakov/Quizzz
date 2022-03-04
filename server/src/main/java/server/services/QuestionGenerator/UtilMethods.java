package server.services.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UtilMethods {

    public static List<Integer> generateRandomQuestionDistribution(int numberOfItems, int targetSum, int lowerBound, int upperBound, Random random) {
        List<Integer> distribution = new ArrayList<>();
        for(int i = 0; i < numberOfItems; i++) {
            distribution.add(lowerBound);
        }
        int currentSum = lowerBound * numberOfItems;

        if(currentSum > targetSum) {
            // throw exception maybe;
            return null;
        }

        System.out.println(distribution.size());

        while(currentSum < targetSum) {
            int element = random.nextInt(numberOfItems);
            int valueAt = distribution.get(element);
            if(valueAt < upperBound) {
                distribution.set(element, valueAt + 1);
                currentSum++;
            }
        }

        return distribution;
    }
}
