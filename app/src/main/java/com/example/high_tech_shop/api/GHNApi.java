package com.example.high_tech_shop.api;

import com.example.high_tech_shop.addressAPI.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GHNApi {

    @GET("province")
    Call<ApiResponse<List<Province>>> getProvinces(@Header("token") String token);

    @GET("district")
    Call<ApiResponse<List<District>>> getDistricts(@Header("token") String token, @Query("province_id") Integer provinceId);

    @GET("ward")
    Call<ApiResponse<List<Ward>>>getWards(@Header("token") String token, @Query("district_id") Integer districtId);
}
