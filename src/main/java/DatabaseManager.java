import java.sql.*;

public class DatabaseManager {
    private static Connection conn = null;


    private DatabaseManager(){
        if(conn == null){
            String url = "jdbc:sqlite:quiz.db";
            try{
                conn = DriverManager.getConnection(url);
            }
            catch(SQLException e){
                System.out.println(e.getStackTrace());
            }
        }
    }

    /**
     *
     * @return instance of databaseManager, singleton
     */
    public static DatabaseManager getInstance(){
        return new DatabaseManager();
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
}
