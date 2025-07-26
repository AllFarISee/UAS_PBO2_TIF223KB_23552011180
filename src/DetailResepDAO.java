import java.sql.*;
import java.util.*;

public class DetailResepDAO {
    // Mendapatkan detail resep berdasarkan ID resep
    public static List<DetailResep> getDetailResepByResepId(Connection connection, int resepId) {
        List<DetailResep> detailResepList = new ArrayList<>();
        String query = "SELECT * FROM detail_resep WHERE resep_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, resepId);
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int obatId = resultSet.getInt("obat_id");
                int jumlah = resultSet.getInt("jumlah");
                detailResepList.add(new DetailResep(resepId, obatId, jumlah));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return detailResepList;
    }
}
