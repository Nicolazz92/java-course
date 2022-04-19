package org.velikokhatko.part2.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.IntStream;

public class ServletPerformanceTestingTest {

    @Test
    public void doGet() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/java_training_war/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServletPerformanceTestingApi api = retrofit.create(ServletPerformanceTestingApi.class);

        IntStream.range(0, 100)
                .parallel()
                .mapToObj(a -> {
                    try {
                        return api.checkPerformance().execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return new ServletPerformanceTestingApi.Resp();
                })
                .peek(resp -> System.out.println(Objects.requireNonNull(resp).message))
                .forEach(resp -> Assert.assertNotNull(resp.message));
    }
}