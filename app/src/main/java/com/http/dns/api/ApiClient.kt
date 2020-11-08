package com.http.dns.api

import com.http.okdns.OkDns
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class ApiClient(private val baseUrl: String) {

    private var okHttpClient: OkHttpClient

    init {
        this.okHttpClient = OkHttpClient().newBuilder()
            .dns(getOkDns())
            .build()
    }

    private fun getOkDns(): OkDns {
        val okDns = OkDns.init(baseUrl)
        okDns.set("8.8.8.8")
        //okDns.set(listOf("8.8.8.8", "8.8.4.4"))
        return okDns
    }

    fun fetch(url: String, callBack: ApiCallBack) {
        val request = Request.Builder()
            .url(baseUrl + url)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callBack.failure(e)
            }

            override fun onResponse(call: Call, response: okhttp3.Response) {
                callBack.success(response)
            }
        })
    }

    companion object {
        const val TAG = "ApiClient"
        private val instance: ApiClient? = null

        fun getInstance(baseUrl: String): ApiClient? {
            if (instance == null)
                return ApiClient(baseUrl)

            return instance
        }
    }
}