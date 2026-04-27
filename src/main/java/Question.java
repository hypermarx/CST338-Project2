import java.util.ArrayList;
import java.util.HashMap;

public class Question {
    /*Questions can also have their own score instead of all being worth an equal amount, if wanted,
    in which case implementation would be slightly different*/

        public enum Type{
            SINGLE_ANSWER,
            MULTIPLE_ANSWER
        }

        private Type type;
        private String prompt;
        private HashMap<String, Boolean> answers;

        /**
         * Type is an enum; Can be Type.SINGLE_ANSWER, Type.MULTIPLE_ANSWER
         * @param type
         * @param prompt
         * @param answers Text for the answer, true/false as to whether it's correct
         */
        public Question(Type type, String prompt, HashMap<String, Boolean> answers){
            this.type = type;
            this.prompt = prompt;
            this.answers = answers;
        }


        /**
         * @param answers Indexes that are marked as answers
         * @return score for question
         */
        public float answer(ArrayList<Integer> answers){
            //Get the type of question. Scoring is based on type.
            //If type is SINGLE_ANSWER, answers should only have one "True" in it.
            //As long as the user's answer is a correct answer, it should return 1.
            //If type is MULTIPLE_ANSWER, each answer is weighted an equal amount (1/AnswerCount)
            //Every answer should either give (1/AnswerCount) points, or zero, depending on whether
            //it was correct or not. Use answers.size() to get answercount.
            return 0;
        }
    }
