import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseManager instance;
    private static Connection conn = null;


    private DatabaseManager(){
        String url = "jdbc:sqlite:quiz.db";
        try{
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    /**
     *
     * @return instance of databaseManager, singleton
     */
    public static DatabaseManager getInstance(){
        if(conn == null) {
            instance = new DatabaseManager();
            return instance;
        }
        return instance;
    }

    public void close() {
        conn = null;
    }

    static void resetForTesting(){
        if(conn != null){
            conn = null;
        }
    }

    //Passwords are not currently hashed; Fix later
    /**
     * Create a user; Username must be unique.
     * @param username
     * @param password
     * @param isAdmin
     * @return userID, or -1 if the user was not successfully created.
     */
    public int createUser(String username, String password, boolean isAdmin){
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (username, password, admin) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            int result = -1;
            if(rs.next()){
                result = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            return result;
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    /**
     * Update a user
     * @param userID
     * @param username
     * @param password
     * @param isAdmin
     */
    public void updateUser(int userID, String username, String password, boolean isAdmin){
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE user SET (username, password, admin) VALUES (?, ?, ?) WHERE userID = ?"
            );
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            pstmt.setInt(4, userID);
            pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
        }
    }

    /**
     * Returns a new user object with the ID, username, and privileges of the found user.
     * Throws an exception if user not found.
     * @param userID
     * @return
     */
    public User getUser(int userID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT user_id, username, admin FROM user WHERE user_id = ?"
            );
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            return new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getBoolean(3)
            );
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
            throw(new RuntimeException("Failed to find user"));
        }
    }

    /**
     * Gets a user ID by username; Returns -1 if not found
     * @param username
     * @return
     */
    public int getUserID(String username){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT user_id FROM user WHERE username = ?"
            );
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    /**
     * Delete a user by ID. Cannot be reversed.
     * @param userID
     * @return
     */
    public boolean deleteUser(int userID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM user WHERE user_id = ?"
            );
            pstmt.setInt(1, userID);
            return pstmt.execute();
        }
        catch(SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public int login(String username, String password){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
              "SELECT user_id FROM user WHERE username = ? AND password = ?"
            );
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    /**
     * Get all IDs of quizzes belonging to a user
     * @param uid
     * @return an ArrayList of ids; Can be empty
     */
    public ArrayList<Integer> getQuizzes(int uid){
        ArrayList<Integer> result = new ArrayList<>();
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT subject_id FROM subject WHERE owner_id = ?"
            );
            pstmt.setInt(1, uid);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
            return result;
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return result;
        }
    }

    /**
     * Get a quiz by ID
     * @param quizid
     * @return a quiz object containing all the questions in the quiz.
     */
    public Quiz getQuizbyID(int quizid){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT question, question_id, type FROM question WHERE subject = ?"
            );
            pstmt.setInt(1, quizid);
            ResultSet rs = pstmt.executeQuery();
            Quiz quiz = new Quiz(quizid);
            while(rs.next()){
                String prompt = rs.getString(1);
                int qid = rs.getInt(2);
                QuestionType type = QuestionType.valueOf(rs.getString(3));
                quiz.addQuestion(
                        new Question(qid, type, prompt)
                );
            }
            return quiz;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return null;
        }
    }

    /**
     * Add a question by ID. Does not add any answers.
     * @param quizid
     * @param question
     * @return question ID
     */
    public int addQuestion(int quizid, Question question){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO question(question, subject, type) VALUES(?, ?, ?)"
            );
            pstmt.setString(1, question.getPrompt());
            pstmt.setInt(2, quizid);
            pstmt.setString(3, question.getType().name());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    /**
     * Change a question's prompt, and whether it's multiple or single answer
     * @param questionID
     * @return success
     */
    public boolean editQuestion(int questionID, Question question){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE question SET (question, type) VALUES(?, ?) WHERE question_id = ?"
            );
            pstmt.setString(1, question.getPrompt());
            pstmt.setString(2, question.getType().name());
            pstmt.setInt(3, questionID);
            return(pstmt.executeUpdate() == 1);
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Deletes a question by ID, as well as the associated answers.
     * Deletion of the item in the associated question in relevant quiz
     * to ensure matching the database properly must be handled in the relevant controller.
     * @param questionID
     * @return success
     */
    public boolean deleteQuestion(int questionID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM question WHERE question_id = ?"
            );
            PreparedStatement ansStmt = conn.prepareStatement(
                    "DELETE FROM answer WHERE question = ?"
            );
            pstmt.setInt(1, questionID);
            ansStmt.setInt(1, questionID);
            return pstmt.execute() && ansStmt.execute();
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Add an answer
     * @param answer
     * @return ID of the answer
     */
    public int addAnswer(Answer answer, int QuestionID){
        try {
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO answer(question, isCorrect, answer_text) VALUES (?, ?, ?)"
            );
            pstmt.setInt(1, QuestionID);
            pstmt.setBoolean(2, answer.isCorrect());
            pstmt.setString(3, answer.getText());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
            return -1;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return -1;
        }
    }

    /**
     * Update an answer
     * @param answer
     * @param answerID
     * @return success
     */
    public boolean updateAnswer(Answer answer, int answerID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "UPDATE answer SET(isCorrect, answer_text) VALUES(?, ?) WHERE answer_id = ?"
            );
            pstmt.setBoolean(1, answer.isCorrect());
            pstmt.setString(2, answer.getText());
            pstmt.setInt(3, answerID);
            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    public boolean deleteAnswer(int ansID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM answer WHERE answer_id = ?"
            );
            pstmt.setInt(1, ansID);
            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }

    /**
     * Deletes a quiz, as well as associated questions.
     * @param quiz
     * @return
     */
    public boolean deleteQuiz(Quiz quiz){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM subject WHERE owner_id = ?"
            );
            pstmt.setInt(quiz.getUid(), 1);
            boolean result = pstmt.execute();
            //Delete questions and answers
            for(Question q : quiz.getQuestions()){
                deleteQuestion(q.getQuestionID());
            }
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getStackTrace());
            return false;
        }
    }
}
