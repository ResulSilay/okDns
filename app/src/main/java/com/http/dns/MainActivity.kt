package com.http.dns

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.http.dns.api.ApiCallBack
import com.http.dns.api.ApiClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onFetch(view: View) {
        ApiClient.getInstance("https://thepiratebay.org/")?.fetch("#", object : ApiCallBack {
            override fun success(response: Response) {
                Log.d(ApiClient.TAG, response.body?.string().toString())
            }

            override fun failure(e: IOException) {
                e.printStackTrace()
            }
        })
    }
}