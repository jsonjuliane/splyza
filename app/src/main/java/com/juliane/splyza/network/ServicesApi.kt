package com.juliane.splyza.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.juliane.splyza.model.BRole
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServicesApi {

    @Mock
    @MockResponse(body = "retromock/teams.json")
    @MockResponse(body = "retromock/teams2.json")
    @MockResponse(body = "retromock/teams3.json")
    @MockResponse(body = "retromock/teams4.json")
    @GET("/teams")
    fun getTeams(): Call<ResponseBody>

    @Mock
    @MockResponse(body = "retromock/qrcode.json")
    @MockResponse(body = "retromock/qrcode2.json")
    @MockResponse(body = "retromock/qrcode3.json")
    @MockResponse(body = "retromock/qrcode4.json")
    @POST("/teams/{team_id}/invites")
    fun updateRole(@Path("team_id") teamId: String, @Body payload: BRole): Call<ResponseBody>

}