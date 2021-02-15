/*
 * MIT License
 *
 * Copyright (c) 2021 David Diaz
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


package com.example.currency.di



import com.example.currency.BuildConfig
import com.example.currency.data.remote.handlers.NetworkHandler
import com.example.currency.data.remote.services.APIRatesService
import com.example.currency.data.remote.sources.RatesRemoteDataSource
import com.example.currency.data.serializers.BigDecimalSerializer
import com.example.currency.data.serializers.CurrencyUnitSerializer
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.util.*

val networkModule = module {

    val apiURL = "https://api.exchangeratesapi.io/"

    fun provideInterceptors(debug: Boolean): Array<Interceptor> {

        val interceptors = mutableListOf<Interceptor>()

        // Log network bodies to help debug
        if (debug) {
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
        return interceptors.toTypedArray()
    }

    fun provideHttpClient(
        vararg interceptors: Interceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .apply { interceptors.forEach { addInterceptor(it) } }
            .build()
    }

    fun provideMoshi() = Moshi.Builder()
        .add(Date::class.java,  Rfc3339DateJsonAdapter())
        .add(BigDecimalSerializer)
        .add(CurrencyUnitSerializer)
        .build()


    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(apiURL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    single { provideInterceptors(BuildConfig.DEBUG) }

    single { provideHttpClient(interceptors = get()) }

    single { provideMoshi() }

    single { provideRetrofit(client = get(), moshi = get()) }

    factory { APIRatesService.create(retrofit = get()) }

    factory { NetworkHandler(context = get()) }

    single { RatesRemoteDataSource(service = get(), networkHandler = get()) }
}