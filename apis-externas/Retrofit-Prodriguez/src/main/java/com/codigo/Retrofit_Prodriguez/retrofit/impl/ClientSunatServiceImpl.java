package com.codigo.Retrofit_Prodriguez.retrofit.impl;

import com.codigo.Retrofit_Prodriguez.aggregates.constants.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientSunatServiceImpl {
    private static Retrofit retrofit = null;

    //getRetrofitSunat
    public static Retrofit getRetrofitSunat(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
