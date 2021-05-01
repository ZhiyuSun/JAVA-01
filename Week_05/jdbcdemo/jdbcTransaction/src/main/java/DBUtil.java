import com.sun.jndi.cosnaming.CNCtx;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final ThreadLocal<Connection> CONNECTION_HOLD;
    private static final String URL;
    // private static final String DRIVER;
    // 线程池
    private static final HikariDataSource DATA_SOURCE;


    static {
        CONNECTION_HOLD = new ThreadLocal<Connection>();
        URL = "jdbc:mysql://39.100.2.247:3306/test?user=root&password=root";
        // DRIVER = "com.mysql.jdbc.Driver";
        DATA_SOURCE = new HikariDataSource();
        DATA_SOURCE.setJdbcUrl("jdbc:mysql://39.100.2.247:3306/test");
        DATA_SOURCE.setUsername("root");
        DATA_SOURCE.setPassword("root");
        DATA_SOURCE.setMaximumPoolSize(20);
    }

    /**
     *  获取数据库连接
     */
    public static Connection getConnection() {
        // 获取Thread.currentThread的数据库连接
        Connection conn = CONNECTION_HOLD.get();
        if (conn == null) {
            try {
                // JDBC创建连接
                // conn = DriverManager.getConnection(URL);

                // 线程池获取连接
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_HOLD.set(conn);
            }
        }
        return conn;
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        // conn如果无效最终执行sql也会失败
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLD.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                CONNECTION_HOLD.remove();
            }
        }
    }

    /**
     * 回滚
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_HOLD.remove();
            }
        }
    }
}
