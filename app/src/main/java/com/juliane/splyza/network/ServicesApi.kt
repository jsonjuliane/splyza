package com.juliane.splyza.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ServicesApi {

    @Mock
    @MockResponse(body = "retromock/teams.json")
    @GET("/teams")
    fun getTeams(): Call<ResponseBody>

}