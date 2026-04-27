import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class DatabaseManagerTest {
    //Use in memory database for testing
    @BeforeEach
    void setUp(){
        System.setProperty("app.db.url", "jdbc::sqlite::memory");
        DatabaseManager.resetForTesting();
    }

    @AfterEach
    void tearDown(){
        DatabaseManager.resetForTesting();
    }
}
