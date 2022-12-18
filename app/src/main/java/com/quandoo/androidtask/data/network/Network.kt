package com.quandoo.androidtask.data.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

fun createNetworkClient(baseUrl: String) =
    retrofitClient(baseUrl, createHttpClient())

private fun createLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.setLevel(if (com.quandoo.androidtask.BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
    return logging
}
private fun createHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    val logging = createLoggingInterceptor()
    // add logging as last interceptor for better debugging
    httpClient.addInterceptor(logging)
    return httpClient.build()
}

private fun retrofitClient(
    baseUrl: String,
    httpClient: OkHttpClient
): Retrofit {
    return Retrofit.Builder()
        .client(httpClient)
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()
        )
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl(baseUrl)
        .build()

}
