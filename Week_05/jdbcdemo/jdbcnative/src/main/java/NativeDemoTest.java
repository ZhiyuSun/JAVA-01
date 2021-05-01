import java.sql.SQLException;
import java.util.List;

public class NativeDemoTest {


    public static void main(String[] args) throws SQLException {

        // 查询
        query();
        // 插入
        insert();
        // 更新
        update();
        // 删除
        delete();

    }

    public static void delete() throws SQLException {
        UserDao userDao = new UserDao();
        userDao.delUser(3);
    }

    public static void update() throws SQLException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setId(1);
        user.setAge(11);
        System.out.println(userDao.updateUser(user));
    }

    public static void insert() throws SQLException {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setName("bbb");
        user.setAge(12);
        userDao.addUser(user);
    }

    public static void query() throws SQLException {
        UserDao userDao = new UserDao();
        List<User> users = userDao.query();
        for (User user : users) {
            System.out.println(user.getName() + " : " + user.getAge() + "岁");
        }
    }
}
