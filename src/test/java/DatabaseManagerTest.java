import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DatabaseManagerTest {
    //Use in memory database for testing
    DatabaseManager db = null;
    @BeforeEach
    void setUp(){
        System.setProperty("app.db.url", "jdbc:sqlite::memory:");
        DatabaseManager.resetForTesting();
        db = DatabaseManager.getInstance();
        db.initSchemaForTests();
    }

    @AfterEach
    void tearDown(){
        DatabaseManager.resetForTesting();
    }

    @Test
    void connection(){
        Assertions.assertDoesNotThrow(() -> db = DatabaseManager.getInstance());
    }

    @Test
    void userCRUD(){
        int uid = db.createUser("hipah", "testpass", true);
        User hipah = db.getUser(uid);
        Assertions.assertEquals("hipah", hipah.getUsername());

        db.updateUser(uid, "HIPAH", "testpass", true);
        hipah = db.getUser(uid);
        Assertions.assertEquals("HIPAH", hipah.getUsername());

        Assertions.assertDoesNotThrow(() -> db.deleteUser(uid));

        Assertions.assertThrows(RuntimeException.class, () -> db.getUser(uid));
    }

    @Test
    void quizCRUD(){
        int uid = db.createUser("hipah", "testpass", true);
        Quiz quiz = new Quiz(uid);
        quiz.setSubject("I like sheep");
        int quizID = db.addQuiz(quiz);
        Assertions.assertNotEquals(-1, quizID);
        Quiz q2 = db.getQuizbyID(quizID);
        Assertions.assertEquals("I like sheep", q2.getSubject());
        ArrayList<Integer> quizzes = db.getQuizzesByUID(uid);
        Assertions.assertEquals(quizID, quizzes.getFirst());
        Assertions.assertDoesNotThrow(() -> db.deleteQuiz(q2, quizID));
    }

    /**
     * Test that the enum properly translates back and forth between the database and code.
     */
    @Test
    void questionRead(){
        int uid = db.createUser("hipah", "testpass", true);
        Quiz quiz = new Quiz(uid);
        //Add question to quiz on code side
        Question question = new Question(QuestionType.SINGLE_ANSWER, "Are you reading this?");
        quiz.addQuestion(question);

        //Add question to quiz on database side
        int quizid = db.addQuiz(quiz);
        db.addQuestion(quizid, question);

        //Check equality
        Quiz result = db.getQuizbyID(quizid);
        QuestionType type = result.getQuestions().getFirst().getType();
        Assertions.assertEquals(QuestionType.SINGLE_ANSWER, type);
    }
}
