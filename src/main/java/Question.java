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
        private ArrayList<Answer> answers;

        /**
         * Type is an enum; Can be Type.SINGLE_ANSWER, Type.MULTIPLE_ANSWER
         * @param type
         * @param prompt
         */
        public Question(Type type, String prompt){
            this.type = type;
            this.prompt = prompt;
        }


        public void addAnswer(Answer answer){
            answers.add(answer);
        }

        /**
         * @param answers Indexes that are marked as answers
         * @return score for question
         */
        public float answer(ArrayList<Integer> answers){
            if(type == Type.SINGLE_ANSWER){
                if(this.answers.get(answers.getFirst()).isCorrect()){
                    return 1;
                }
                return 0;
            }
            if(type == Type.MULTIPLE_ANSWER){
                float score = 0;
                for(int i = 0; i < this.answers.size(); i++){
                    if(answers.contains(i)){
                        if(this.answers.get(i).isCorrect()){
                            score += 1;
                        }
                        else{
                            score -= 1;
                        }
                    }
                }
                return Math.max(0, score) / this.answers.size();
            }
            return 0;
        }
    }
