package com.codigo.Retrofit_Prodriguez.retrofit;

import com.codigo.Retrofit_Prodriguez.aggregates.response.ResponseSunat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientSunatService {

    @GET("/v2/sunat/ruc/full")
    Call<ResponseSunat> getInfoSunat(@Header("Authorization") String token,
                                     @Query("numero") String numero);
}
