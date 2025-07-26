import java.sql.*;

public class UserDAO {
    public static boolean register(Connection conn, String username, String password, String role) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
    
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            return false;
        }
    }
    public static User login(Connection connection, String username, String password) {
        User user = null;
        try {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
