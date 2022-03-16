package server.services.GameServices;

import commons.Questions.Question;

public class Util {
        static String getAlphaNumericString(int n) {
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

        static String getQuestionType(Question question) {
            return question.getClass().toString().substring(24);
        }
}
