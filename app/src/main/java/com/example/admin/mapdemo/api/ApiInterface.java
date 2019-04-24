package com.example.admin.mapdemo.api;

import com.example.admin.mapdemo.model.BusData;
import com.example.admin.mapdemo.model.BusHistory;
import com.example.admin.mapdemo.model.BusHistoryResult;
import com.example.admin.mapdemo.model.BusList;
import com.example.admin.mapdemo.model.DriverData;
import com.example.admin.mapdemo.model.DriverList;
import com.example.admin.mapdemo.model.RealmData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("project/busRegistration.php")
    @FormUrlEncoded
    Call<BusData> register(@Field("busId") String busId,
                           @Field("serviceNumber") String serviceNumber,
                           @Field("destination") String destination);
    @GET("project/getAllBuses.php")
    Call<BusList> getBuses();
    @POST("project/getSingleBusData.php")
    @FormUrlEncoded
    Call<BusList>getSingleBusData(@Field("busId") String busId);
    @POST("project/updateBusDetails.php")
    @FormUrlEncoded
    Call<BusData> updateBusDetail(@Field("busId") String busId,
                                  @Field("serviceNumber") String serviceNumber,
                                  @Field("destination") String destination);

    @POST("project/DeleteBus.php")
    @FormUrlEncoded
    Call<BusData>deleteBus(@Field("busId") String busId);

    @POST("project/DriverRegistration.php")
    @FormUrlEncoded
    Call<BusData> registerDriver(@Field("fullName") String driverFullname,
                                 @Field("phone1") String driverPhone1,
                                 @Field("phone2") String driverPhone2,
                                 @Field("age") String driverAge,
                                 @Field("address") String driverAddress,
                                 @Field("username") String driverUserName,
                                 @Field("password") String driverPassword,
                                 @Field("busNumber") String driverBusNumber);
    @GET("project/GetAlldrivers.php")
    Call<DriverList> getDrivers();
    @POST("project/GetSingleDriverData.php")
    @FormUrlEncoded
    Call<DriverList>getSingleDriverData(@Field("username") String username);
    @POST("project/UpdateDriverDetails.php")
    @FormUrlEncoded
    Call<BusData> updateDriverDetail(@Field("driverFullName") String driverFullName,
                                        @Field("driverPhone1") String driverPhone1,
                                        @Field("driverPhone2") String driverPhone2,
                                        @Field("driverAge" )String driverAge,
                                        @Field("driverAddress" )String driverAddress,
                                        @Field("driverUserName" )String driverUserName,
                                        @Field("driverPassword" )String driverPassword,
                                        @Field("driverBusId" )String driverBusId
                                        );
    @POST("project/DeleteDriver.php")
    @FormUrlEncoded
    Call<BusData>deleteDriver(@Field("userName") String userName);

    @POST("project/BusHistoryList.php")
    @FormUrlEncoded
    Call<BusHistoryResult>getBusHistory(@Field("busId") String busId);

    @POST("project/realm.php")
    @FormUrlEncoded
    Call<RealmData> sendData(@Field("resultData") String resultdata);

    @POST("project/driverLogin.php")
    @FormUrlEncoded
    Call<DriverList>getSingleDriverData1(@Field("username") String username,
                                         @Field("password") String password);

}
