package com.http.dns.api

import okhttp3.Response
import java.io.IOException

interface ApiCallBack {
    fun success(response: Response)
    fun failure(e: IOException)
}