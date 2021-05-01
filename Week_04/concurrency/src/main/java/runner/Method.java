package runner;

/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/5/1 20:54
 */
public class Method {

    public static int sum() {
        return fibo(36);
    }

    public static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }

}