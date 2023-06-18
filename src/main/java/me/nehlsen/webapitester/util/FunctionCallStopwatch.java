package me.nehlsen.webapitester.util;

import org.springframework.util.StopWatch;

import java.util.function.Supplier;

public class FunctionCallStopwatch<T> {

    StopWatch stopWatch = new StopWatch();

    public T run(Supplier<T> fn) {
        stopWatch.start();
        T t = fn.get();
        stopWatch.stop();

        return t;
    }

    public long getTimeMillis() {
        return stopWatch.getTotalTimeMillis();
    }
}
