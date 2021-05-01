package runner;

import java.util.concurrent.Callable;

public class MyThread2 implements Callable<Integer> {
    int sum = 0;

    @Override
    public Integer call() throws Exception {
        sum=Method.sum();
        return sum;
    }
}