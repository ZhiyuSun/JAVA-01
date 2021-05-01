import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public int addUser(User user) throws SQLException {
        Connection conn = NativeUtil.getConnection();
        String sql = "INSERT INTO jdbc(`name`, `age`) VALUES (?, ?)";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, user.getName());
        ptmt.setInt(2, user.getAge());
        boolean res = ptmt.execute();
        ptmt.close();
        if (res) {
            return 1;
        } else {
            return 0;
        }
    }

    // 返回受影响的行？
    public int updateUser(User user) throws SQLException {
        Connection conn = NativeUtil.getConnection();
        String sql = "UPDATE jdbc SET age = ? WHERE id = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, user.getAge());
        ptmt.setInt(2, user.getId());
        int res = ptmt.executeUpdate();
        ptmt.close();
        if (res == 0) {
            throw new SQLException("row is 0");
        }
        return res;
    }

    public int delUser(int id) throws SQLException {
        Connection conn = NativeUtil.getConnection();
        String sql = "DELETE from jdbc WHERE id = ?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1, id);
        boolean res = ptmt.execute();
        ptmt.close();
        if (res) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<User> query() throws SQLException {
        Connection conn = NativeUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from jdbc");

        List<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();
            user.setName(rs.getString("name"));
            user.setId(rs.getInt("id"));
            user.setAge(rs.getInt("age"));
            users.add(user);
        }
        rs.close();
        stmt.close();

        return users;
    }
}
