/*
 * MIT License
 *
 * Copyright (c) 2024 David Diaz
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


package com.dmdiaz.currency.core.network.di



import com.dmdiaz.currency.core.network.BuildConfig
import com.dmdiaz.currency.core.network.serializers.BigDecimalSerializer
import com.dmdiaz.currency.core.network.serializers.CurrencyUnitSerializer
import com.dmdiaz.currency.core.network.serializers.DateSerializer
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val interceptors = mutableListOf<Interceptor>()

        // Log network bodies to help debug
        if (BuildConfig.DEBUG) {
            interceptors.add(HttpLoggingInterceptor().apply { setLevel(Level.BODY) })
        }

        // Add environment headers to requests
        val interceptorHeaders = Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(request)
            /* Hack alert: This is so EitherCallAdapterFactory always has SOMETHING in the
            *  Retrofit Response body otherwise it can't adapt it into either<errorDTO, successDTO>.
            *  To simulate the normal Retrofit Response<Void> use instead ResponseE<errorDTO, NoContent>
            * */
            if (response.code == HttpURLConnection.HTTP_NO_CONTENT) {
                response = response.newBuilder()
                    .code(200)
                    .body("{}".toResponseBody("application/json".toMediaTypeOrNull()))
                    .build()
            }
            response
        }
        interceptors.add(interceptorHeaders)

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .apply { interceptors.forEach { addInterceptor(it) } }
            .build()
    }


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(DateSerializer)
            .add(BigDecimalSerializer)
            .add(CurrencyUnitSerializer)
            .build()
    }

    @Provides
    @Singleton
    open fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}