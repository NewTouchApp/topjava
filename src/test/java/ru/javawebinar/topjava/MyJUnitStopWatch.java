package ru.javawebinar.topjava;

import org.slf4j.Logger;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import static org.slf4j.LoggerFactory.getLogger;

public class MyJUnitStopWatch extends Stopwatch {
    private static final Logger log = getLogger(MyJUnitStopWatch.class);
    public static StringBuilder resultLog = new StringBuilder();

    private static void writeLog(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        //log.error(String.format("Test %s, status - %s is performed in %d nanosecond",testName, status, nanos));
        System.out.println(String.format("Test %s, status - %s is performed in %d nanosecond",testName, status, nanos));
        resultLog.append(String.format("Test %s, status - %s is performed in %d nanosecond",testName, status, nanos)+ "\n");
    }

    @Override
    protected void succeeded(long nanos, Description description) {
        writeLog(description, "succeeded", nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        writeLog(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        writeLog(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        writeLog(description, "finished", nanos);
    }
}
