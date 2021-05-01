import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class UserDao {

    private Connection conn;

    public UserDao(Connection conn) {
        this.conn = conn;
    }

    public int addBalance(int id, int amount) {
        String sql = "UPDATE jdbc SET balance = balance + ? WHERE id = ?";
        int res = 0;
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, amount);
            ptmt.setInt(2, id);
            res = ptmt.executeUpdate();
            ptmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public int subBalance(int id, int amount) {
        String sql = "UPDATE jdbc SET balance = balance - ? WHERE id = ? and balance >=  ?";
        int res = 0;
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, amount);
            ptmt.setInt(2, id);
            ptmt.setInt(3, amount);
            res = ptmt.executeUpdate();

            ptmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (res == 0) {
            throw new RuntimeException();
        }
        return res;
    }

    public User queryById(int id) {
        User user = null;
        String sql = "SELECT * FROM jdbc WHERE id = ?";
        try {
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1, id);
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setId(rs.getInt("id"));
                user.setAge(rs.getInt("age"));
                user.setBalance(rs.getInt("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
