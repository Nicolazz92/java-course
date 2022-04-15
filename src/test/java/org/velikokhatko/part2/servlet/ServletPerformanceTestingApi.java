package org.velikokhatko.part2.servlet;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServletPerformanceTestingApi {

    @GET("velikokhatko/servlet/performance")
    Call<Resp> checkPerformance();

    class Resp {
        public String message;
    }
}