package com.codigo.ms_registros.retrofit;

import com.codigo.ms_registros.aggregates.response.ResponseReniec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ClientReniecService {

    @GET("v2/reniec/dni")
    Call<ResponseReniec> getInfoReniec(@Header("Authorization") String token,
                                       @Query("numero") String numero);
}
