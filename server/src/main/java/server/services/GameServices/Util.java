package server.services.GameServices;

import commons.Questions.Question;

public class Util {
    /**
     * Generates and get the string that is mix of alphabet and numbers
     * @param n the length of the string
     * @return the generated string contains mix of alphabet and numbers
     */
    public static String getAlphaNumericString(int n) {
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {
                int index
                        = (int) (AlphaNumericString.length()
                        * Math.random());
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }

    /**
     * Get the question type of the given question. Open question, KnowledgeQuestion, ComparisonQuestion, AlternativeQuestion
     * @param question qeustion that the question type is interested
     * @return the string contains the question type
     */
    public static String getQuestionType(Question question) {
        return question.getClass().toString().substring(24);
    }
}
