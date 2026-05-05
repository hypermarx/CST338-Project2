import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static DatabaseManager instance;
    private static Connection conn = null;


    private DatabaseManager(){
        String url = System.getProperty("app.db.url", "jdbc:sqlite:quiz.db");
        try{
            conn = DriverManager.getConnection(url);
        }
        catch(SQLException e){
            throw new RuntimeException("Failed to connect to database", e);
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

    public void initSchemaForTests(){
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(
                    """
                        create table user
                        (
                            user_id  integer
                                constraint user_pk
                                    primary key autoincrement,
                            password text,
                            admin    boolean,
                            username text
                                constraint username
                                    unique
                        );
                        """);
            stmt.execute(
                    """
                            create table subject
                                    (
                                        owner_id   integer
                                            constraint ownerid_fk
                                                references user,
                                        visibility text,
                                        subject    text,
                                        subject_id integer
                                            constraint subject_pk
                                                primary key autoincrement
                                    );
            
                        """);
            stmt.execute(
                    """
                            create table access
                                                    (
                                                        user_id      integer
                                                            constraint userid___fk
                                                                references user,
                                                        access_level text,
                                                        subject_id   integer
                                                            constraint subjectid___fk
                                                                references subject
                                                    );
            
                        """);
            stmt.execute(
                    """
                           create table question
                                                   (
                                                       question    text,
                                                       question_id integer
                                                           constraint question_pk
                                                               primary key autoincrement,
                                                       subject     integer
                                                           constraint question___fk
                                                               references subject,
                                                       type        TEXT
                                                   ); 
                        """);
            stmt.execute(
                    """
                           create table answer
                                                   (
                                                       question    integer
                                                           constraint question___fk
                                                               references question,
                                                       isCorrect   boolean,
                                                       answer_text text,
                                                       answer_id   integer
                                                           constraint answer_pk
                                                               primary key autoincrement
                                                   ); 
            
                        """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            e.printStackTrace();
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
                    "UPDATE user SET (username, password, admin) = (?, ?, ?) WHERE user_id = ?"
            );
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setBoolean(3, isAdmin);
            pstmt.setInt(4, userID);
            pstmt.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
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
            if(rs.next()) {
                return new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBoolean(3)
                );
            }
            throw(new RuntimeException("Failed to find user"));
        }
        catch(SQLException e){
            e.printStackTrace();
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
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Delete a user by ID. Cannot be reversed.
     * @param userID
     * @return success
     */
    public void deleteUser(int userID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM user WHERE user_id = ?"
            );
            pstmt.setInt(1, userID);
            pstmt.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("User deletion failed");
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
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Get all IDs of quizzes belonging to a user
     * @param uid
     * @return an ArrayList of ids; Can be empty
     */
    public ArrayList<Integer> getQuizzesByUID(int uid){
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
            e.printStackTrace();
            return result;
        }
    }

    /**
     * Add a quiz to the database
     * Does not add the questions in the quiz
     * @return the ID of the quiz
     */
    public int addQuiz(Quiz quiz){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO subject(subject, owner_id) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setString(1, quiz.getSubject());
            pstmt.setInt(2, quiz.getUid());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                return(rs.getInt(1));
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Get a quiz by ID
     * @param quizid
     * @return a quiz object containing all the questions in the quiz.
     * Just returns the quiz if there are no questions.
     */
    public Quiz getQuizbyID(int quizid){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT question, question_id, type FROM question WHERE subject = ?"
            );
            pstmt.setInt(1, quizid);
            ResultSet rs = pstmt.executeQuery();
            pstmt = conn.prepareStatement("SELECT subject FROM subject WHERE subject_id = ?");
            pstmt.setInt(1, quizid);

            ResultSet subject = pstmt.executeQuery();
            Quiz quiz = new Quiz(quizid);
            if(subject.next()){
                quiz.setSubject(subject.getString(1));
            }

            if(!rs.next()){
                return quiz;
            }
            do {
                String prompt = rs.getString(1);
                int qid = rs.getInt(2);
                QuestionType type = QuestionType.valueOf(rs.getString(3));
                quiz.addQuestion(
                        new Question(qid, type, prompt)
                );
            } while(rs.next());
            return quiz;
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Unable to find a quiz with the associated ID");
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
                    "INSERT INTO question(question, subject, type) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
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
            e.printStackTrace();
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
                    "UPDATE question SET (question, type) = (?, ?) WHERE question_id = ?"
            );
            pstmt.setString(1, question.getPrompt());
            pstmt.setString(2, question.getType().name());
            pstmt.setInt(3, questionID);
            return(pstmt.executeUpdate() == 1);
        }
        catch (SQLException e){
            e.printStackTrace();
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
    public void deleteQuestion(int questionID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM question WHERE question_id = ?"
            );
            PreparedStatement ansStmt = conn.prepareStatement(
                    "DELETE FROM answer WHERE question = ?"
            );
            pstmt.setInt(1, questionID);
            ansStmt.setInt(1, questionID);
            pstmt.execute();
            ansStmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Question deletion failed");
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
                    "INSERT INTO answer(question, isCorrect, answer_text) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
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
            e.printStackTrace();
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
                    "UPDATE answer SET(isCorrect, answer_text) = (?, ?) WHERE answer_id = ?"
            );
            pstmt.setBoolean(1, answer.isCorrect());
            pstmt.setString(2, answer.getText());
            pstmt.setInt(3, answerID);
            return pstmt.executeUpdate() == 1;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAnswer(int ansID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM answer WHERE answer_id = ?"
            );
            pstmt.setInt(1, ansID);
            pstmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Answer deletion failed");
        }
    }

    /**
     * Deletes a quiz, as well as associated questions.
     * @param quiz
     * @param quizID
     * @return success
     */
    public void deleteQuiz(Quiz quiz, int quizID){
        try{
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM subject WHERE owner_id = ?"
            );
            pstmt.setInt(quizID, 1);
            pstmt.execute();
            //Delete questions and answers
            for(Question q : quiz.getQuestions()){
                deleteQuestion(q.getQuestionID());
            }
            pstmt.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Quiz deletion failed");
        }
    }
}
