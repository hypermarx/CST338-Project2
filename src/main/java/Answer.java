public class Answer {
    private String text;
    private boolean correct;

    public Answer(String text, boolean correct){
        this.text = text;
        this.correct = correct;
    }

    public String getText(){
        return text;
    }

    public boolean isCorrect(){
        return correct;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setCorrect(boolean isCorrect){
        this.correct = isCorrect;
    }
}
