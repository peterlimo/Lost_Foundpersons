package com.example.lost_foundpersons;

import com.example.lost_foundpersons.data.Reset;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("sendOTP.php")
    Call<Reset> resetPassword(@Body Reset reset);
}
