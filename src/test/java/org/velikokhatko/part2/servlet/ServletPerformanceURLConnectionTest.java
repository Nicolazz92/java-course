package org.velikokhatko.part2.servlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServletPerformanceURLConnectionTest {
    private static final String BASE_URL = "http://localhost:8080/java_training_war";
    private static final String ADDICTION_URL = "velikokhatko/servlet/performance";

    @Test
    public void doGet() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    URLConnection connection = new URL(String.join("/", BASE_URL, ADDICTION_URL)).openConnection();
                    connection.setConnectTimeout(2000);
                    InputStream inputStream = connection.getInputStream();
                    try (InputStreamReader in = new InputStreamReader(inputStream);
                         BufferedReader bufferedReader = new BufferedReader(in)) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
        Assert.assertTrue(executorService.awaitTermination(100, TimeUnit.SECONDS));
    }
}