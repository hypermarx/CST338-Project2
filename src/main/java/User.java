/**
 * This class does not create objects that are committed to the database. Attributes found here
 * can be used to commit to the database, or may be created as read from the database.
 */
public class User {
    private int id;
    private String username;
    private boolean isAdmin;

    /**
     * Creates a temporary user object for use in code; Is not committed to the database.
     * Use the database's CREATE USER method to create a user that is committed to the database. (should be handled
     * by SceneController)
     * @param id
     * @param username
     * @param isAdmin
     */
    public User(int id, String username, boolean isAdmin){
        this.id = id;
        this.username = username;
        this.isAdmin = isAdmin;
    }


}
