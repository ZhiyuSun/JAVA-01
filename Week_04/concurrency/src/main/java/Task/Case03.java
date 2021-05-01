package Task;

import runner.Method;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/5/1 21:05
 */
public class Case03 {
    private static CountDownLatch count = new CountDownLatch(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int[] sum = {0};
        long start = System.currentTimeMillis();
        int answer = Method.sum();
        System.out.println("标准计算结果为：" + answer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sum[0] = Method.sum();
                count.countDown();
            }
        }).start();
        count.await();
        // 确保拿到result 并输出
        System.out.println("异步计算结果为：" + sum[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }
}
