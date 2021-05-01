package runner;

/**
 * 继承的方法实现
 */
public class RunnerExtend extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "执行" + i);
        }
    }
}