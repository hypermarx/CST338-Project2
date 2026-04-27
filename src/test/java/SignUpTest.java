import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Description: A test for the signup scene that checks username & password length,
 * confirm pass, empty usernames & empty passwords. I also included one test that
 * has an AssertTrue, where everything meets the criteria for signing up! Not sure
 * if its needed, but figured I would include it since I saw it in past projects (Jotto tests)
 * in this class.
 * April 26, 2026
 * @author Anjelina Jasso
 */

public class SignUpTest {
    @Test
    public void emptyUsername() {
        String username = "";
        String password = "pass";
        String confirmPass = "pass";
        assertFalse(validSignUp(username, password, confirmPass));
    }
    @Test
    public void emptyPassword() {
        String username = "user";
        String password = "";
        String confirmPass = "";
        assertFalse(validSignUp(username, password, confirmPass));
    }
    @Test
    public void emptyUserAndPass(){
        String username = "";
        String password = "";
        String confirmPass = "";
        assertFalse(validSignUp(username, password, confirmPass));
    }
    @Test
    public void passDontMatch() {
        String username = "user";
        String password = "pass";
        String confirmPass = "diffpass";
        assertFalse(validSignUp(username, password, confirmPass));
    }
    @Test
    public void passTooShort() {
        String username = "user";
        String password = "pass";
        String confirmPass = "pass";
        assertFalse(validSignUp(username,password, confirmPass));
    }
    @Test
    public void nameTooShort() {
        String username = "user";
        String password = "pass";
        String confirmPass = "pass";
        assertFalse(validSignUp(username,password, confirmPass));
    }
    @Test
    public void testValidSignUp() {
        String username = "userName";
        String password = "password";
        String confirmPass = "password";
        assertTrue(validSignUp(username, password, confirmPass));
    }
    public boolean validSignUp(String username, String password, String confirmPass) {
        return !username.isEmpty() && !password.isEmpty() && username.length() >= 5 && password.length() >= 8 && password.equals(confirmPass);
    }
}
