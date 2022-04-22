package org.velikokhatko.part2.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServletPerformanceRetrofit2Test {

    @Test
    public void doGet() throws InterruptedException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/java_training_war/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServletPerformanceTestingApi api = retrofit.create(ServletPerformanceTestingApi.class);

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    ServletPerformanceTestingApi.Resp resp = api.checkPerformance().execute().body();
                    System.out.println(Objects.requireNonNull(resp).message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        Assert.assertTrue(executorService.awaitTermination(100, TimeUnit.SECONDS));
    }

    private interface ServletPerformanceTestingApi {

        @GET("velikokhatko/servlet/performance")
        Call<Resp> checkPerformance();

        class Resp {
            public String message;
        }
    }
}