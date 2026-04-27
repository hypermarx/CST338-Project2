import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {
    private ArrayList<Question> questions;

    public Quiz(){
        questions = new ArrayList<>();
    }

    public int getQuestionCount(){
        return questions.size();
    }

    //Private because intended for use only within quiz.take()
    private ArrayList<Question> getQuestions(){
        return questions;
    }

    private void addQuestion(Question.Type type, String prompt, HashMap<String, Boolean> answers){
        questions.add(new Question(type, prompt, answers));
    }

    /**
     * Take each question in the quiz
     * * If you would prefer answer to answer one question at a time:
     *          -Change method signature to take index and an ArrayList<Boolean>
     *          -Answer the question at the index
     * @param answers An ArrayList of Boolean ArrayLists; Each inner arraylist represents the
     *                answers given for the question at its index.
     * @return Total score for quiz
     */
    public float take(ArrayList<ArrayList<Boolean>> answers){
        float score = 0;
        for(int i = 0; i < this.questions.size(); i++){
            score += questions.get(i).answer(answers.get(i));
        }
        return score;
    }

    //Question class is tied to quiz and thus only accessible within quiz
    private static class Question {
    /*Questions can also have their own score instead of all being worth an equal amount, if wanted,
    in which case implementation would be slightly different*/

        enum Type{
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
         * @param answers
         */
        Question(Type type, String prompt, HashMap<String, Boolean> answers){
            this.type = type;
            this.prompt = prompt;
            this.answers = answers;
        }


        /**
         * -Save the score in some way for final score
         * @param answers A 0 or 1 for every possible answer to indicate whether it's input as an answer
         *                (can be changed based on implementation for multiple answers)
         * @return score for question
         */
        public float answer(ArrayList<Boolean> answers){
            //Get the type of question. Scoring is based on type.
            //If type is SINGLE_ANSWER, answers should only have one "True" in it.
            //As long as the user's answer is a correct answer, it should return 1.
            //If type is MULTIPLE_ANSWER, each answer is weighted an equal amount (1/AnswerCount)
            //Every answer should either give (1/AnswerCount) points, or zero, depending on whether
            //it was correct or not. Use answers.size() to get answercount.
            return 0;
        }



    }
}
