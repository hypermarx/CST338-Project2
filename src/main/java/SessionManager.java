public class SessionManager {
    private int userID = -1;
    private static SessionManager instance = null;
    int quizID = -1;

    /**
     * Singleton; One session can exist at a time.
     * @return
     */
    public static SessionManager getInstance(){
        if(instance == null){
            instance = new SessionManager();
        }
        return instance;
    }

    private SessionManager(){

    }

    public void setUserID(int uid){
        this.userID = uid;
    }

    public int getUserID(){
        return userID;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public void clearSession(){
        instance = null;
        userID = -1;
        quizID = -1;
    }
}
