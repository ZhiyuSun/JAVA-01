import java.sql.SQLException;

public class TransactionDemoTest {

    public static void main(String[] args) throws SQLException {
        UserDao userDao = new UserDao(DBUtil.getConnection());

        // 查询余额
        User userA = userDao.queryById(1);
        User userB = userDao.queryById(2);
        System.out.println("--------转账前--------");
        System.out.println("user id:1 balance=" + userA.getBalance());
        System.out.println("user id:2 balance=" + userB.getBalance());

        // 转账
        DBUtil.beginTransaction();
        try {
            userDao.addBalance(2, 10);
            userDao.subBalance(1, 10);
        } catch (Exception e) {
            e.printStackTrace();
            DBUtil.rollbackTransaction();
        }
        DBUtil.commitTransaction();


        userDao = new UserDao(DBUtil.getConnection());
        userA = userDao.queryById(1);
        userB = userDao.queryById(2);
        System.out.println("--------转账后--------");
        System.out.println("user id:1 balance=" + userA.getBalance());
        System.out.println("user id:2 balance=" + userB.getBalance());
    }
}