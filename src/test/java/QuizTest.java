import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//Tests made with dummy quiz of ID 0001 in mind
public class QuizTest {
    Quiz quiz;

    @BeforeEach
    void setUp(){
        Quiz quiz = new Quiz();
    }

    @AfterEach
    void tearDown(){
        quiz = null;
    }

    @Test
    public void getQuestions(){
        assertEquals(4, quiz.getQuestionCount());
    }

    @Test
    public void take(){
        assertEquals(3.5, quiz.take());
    }
}
