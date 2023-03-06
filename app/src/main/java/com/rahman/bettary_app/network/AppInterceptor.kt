package com.rahman.bettary_app.network

import android.content.SharedPreferences
import com.rahman.bettary_app.persentation.constants.SharedConstant
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AppInterceptor @Inject constructor() : Interceptor{

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
        val token:String?  = sharedPreferences.getString(SharedConstant.token,"");
        if(token !=null && token.isNotBlank())
            request.addHeader("Authorization","token $token")
        return chain.proceed(request.build())
    }
}