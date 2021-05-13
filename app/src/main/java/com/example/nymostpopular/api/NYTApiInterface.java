package com.example.nymostpopular.api;

import com.example.nymostpopular.R;
import com.example.nymostpopular.pojo.MostViewedResponse;

import retrofit2.Call;
import retrofit2.http.GET;

import static android.provider.Settings.Global.getString;

//import static android.provider.Settings.System.getString;

public interface NYTApiInterface {
    @GET("viewed/7.json?api-key=VADRFOOxKcytqSAj9OpzLzdQPDo3x9Fn")
    Call<MostViewedResponse> getMostViewedList();
}
