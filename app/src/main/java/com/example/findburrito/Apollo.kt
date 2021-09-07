package com.example.findburrito

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(AuthorizationInterceptor())
    .build()

val apolloClient = ApolloClient.builder()
    .serverUrl("https://api.yelp.com/v3/graphql")
    .okHttpClient(okHttpClient)
    .build()

private class AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer "
            )
            .build()

        return chain.proceed(request)
    }
}