package Task;

import runner.Method;

import java.util.concurrent.ExecutionException;
/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/5/1 20:56
 */

// 思路1，简单开启一个线程，然后主线程睡眠
public class Case01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final int[] sum = {0};
        long start = System.currentTimeMillis();
        int answer = Method.sum();
        System.out.println("标准计算结果为：" + answer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                sum[0] = Method.sum();
            }
        }).start();
        Thread.sleep(1000L);
        // 确保拿到result 并输出
        System.out.println("异步计算结果为：" + sum[0]);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
        return;
    }
}
