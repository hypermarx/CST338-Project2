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

    private void addQuestion(Question.Type type, String prompt){
        questions.add(new Question(type, prompt));
    }

    /**
     * Take each question in the quiz and return the final score
     * @param answers An ArrayList of Boolean ArrayLists; Each inner arraylist represents the
     *                answers given for the question at its index.
     * @return Total score for quiz
     */
    public float take(ArrayList<ArrayList<Integer>> answers){
        float score = 0;
        for(int i = 0; i < this.questions.size(); i++){
            score += answer(i, answers.get(i));
        }
        return score;
    }

    /**
     * Answer an individual question by index, return score
     * @param index
     * @param answers Indexes marked as answers
     * @return
     */
    public float answer(int index, ArrayList<Integer> answers){
        return questions.get(index).answer(answers);
    }
}
