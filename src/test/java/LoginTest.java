import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Description: A test for the LoginScene. I included tests to check for empty username & empty password.
 * I also included one test that has an AssertTrue, where everything meets the criteria for signing up! Not
 * sure if its needed, but figured I would include it since I saw it in past projects (Jotto tests) in this class.
 * April 26, 2026
 * @author Anjelina Jasso
 */

public class LoginTest {
    @Test
    public void emptyUsername() {
        String username = "";
        String password = "pass";
        assertFalse(validLogin(username, password));
    }
    @Test
    public void emptyPassword() {
        String username = "user";
        String password = "";
        assertFalse(validLogin(username, password));
    }
    @Test
    public void emptyUserAndPass(){
        String username = "";
        String password = "";
        assertFalse(validLogin(username, password));
    }
    @Test
    public void testValidLogin() {
        String username = "user";
        String password = "pass";
        assertTrue(validLogin(username, password));
    }
    public boolean validLogin(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }
}
