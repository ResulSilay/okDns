package com.http.okdns

import android.os.StrictMode
import android.util.Log
import okhttp3.Dns
import org.xbill.DNS.*
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList


class OkDns(private var hostname: String) : Dns {

    private lateinit var ipAddress: InetAddress
    private lateinit var extendedResolver: ExtendedResolver

    init {
        getByName()
    }

    fun set(dnsAddressList: List<String>) {
        try {
            val resolveList = ArrayList<SimpleResolver>()
            dnsAddressList.forEach { host ->
                resolveList.add(SimpleResolver(host))
            }

            extendedResolver = ExtendedResolver(resolveList)
        } catch (e: UnknownHostException) {
            Log.w(TAG, e.message.toString())
        }
    }

    fun set(dnsAddress: String) {
        try {
            val customResolver = SimpleResolver(dnsAddress)
            extendedResolver = ExtendedResolver(listOf<Resolver>(customResolver))
        } catch (e: UnknownHostException) {
            Log.w(TAG, e.message.toString())
        }
    }

    private fun getByName() {
        try {
            if (!isInstance) {
                val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
                StrictMode.setThreadPolicy(policy)
                this.ipAddress = InetAddress.getByName(hostname)
            }
        } catch (e: UnknownHostException) {
            Log.w(TAG, e.message.toString())
        }
    }

    private fun initLookup() {
        if (!isInstance) {
            isInstance = true
            Lookup.setDefaultResolver(extendedResolver)
        }
    }

    override fun lookup(hostname: String): List<InetAddress> {
        try {
            initLookup()
            return Collections.singletonList(Address.getByName(hostname))
        } catch (e: UnknownHostException) {
            if (this.hostname == hostname)
                return listOf(ipAddress)
            throw e
        }
    }

    companion object {
        private val instance: OkDns? = null
        private const val TAG = "OkDns"
        private var isInstance = false

        fun init(hostname: String): OkDns {
            if (instance == null)
                return OkDns(hostname)
            return instance
        }
    }
}