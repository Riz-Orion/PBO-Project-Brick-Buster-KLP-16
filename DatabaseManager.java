import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/brickbuster";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    // Mendapatkan koneksi ke database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Menyimpan data pemain
    public void savePlayerData(String playerName, int score, int level) {
        String query = "INSERT INTO Leaderboard (player_name, score, level) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.setInt(3, level);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengambil data leaderboard berdasarkan skor tertinggi
    public List<String> getLeaderboardData() {
        List<String> leaderboardData = new ArrayList<>();
        String query = "SELECT player_name, score, level FROM Leaderboard ORDER BY score DESC LIMIT 10";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                String playerName = rs.getString("player_name");
                int score = rs.getInt("score");
                int level = rs.getInt("level");
                leaderboardData.add(rank + ". " + playerName + " - Level: " + level + " - Score: " + score);
                rank++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaderboardData;
    }
}
