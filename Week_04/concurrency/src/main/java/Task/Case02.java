package Task;

import runner.Method;
import runner.MyThread2;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.*;

/**
 * @Description:
 * @Author: sunzhiyu
 * @CreateDate: 2021/5/1 20:56
 */

// 思路2，线程池开启一个线程，然后主线程睡眠
public class Case02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        int answer = Method.sum();
        System.out.println("标准计算结果为：" + answer);
        ExecutorService executorService = Executors.newCachedThreadPool();
        Callable<Integer> callable = new MyThread2();
        Future future = executorService.submit(callable);
        Thread.sleep(1000L);
        // 确保拿到result 并输出
        System.out.println("异步计算结果为：" + future.get());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        return;
    }
}
