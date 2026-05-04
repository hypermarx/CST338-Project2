import java.util.ArrayList;

public class Question {

    private int questionID;
        private QuestionType type;
        private String prompt;
        private ArrayList<Answer> answers;

        /**
         * Type is an enum; Can be Type.SINGLE_ANSWER, Type.MULTIPLE_ANSWER
         * @param type
         * @param prompt
         */
        public Question(QuestionType type, String prompt){
            this.type = type;
            this.prompt = prompt;
        }

        public Question(int questionID, QuestionType type, String prompt){
            this.questionID = questionID;
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
            if(type == QuestionType.SINGLE_ANSWER){
                if(this.answers.get(answers.getFirst()).isCorrect()){
                    return 1;
                }
                return 0;
            }
            if(type == QuestionType.MULTIPLE_ANSWER){
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
