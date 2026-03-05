/*
 * MIT License
 *
 * Copyright (c) 2026 David Diaz
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

package com.dmdiaz.currency.core.data.testing.dispatcher

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import javax.inject.Inject


class NetworkMockDispatcher @Inject constructor (
) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(request.path){
            "/rates?base=USD" ->{
                MockResponse().setResponseCode(200).setBody(USD_RATES_RESPONSE)
            }
            else -> {
                MockResponse().setResponseCode(404)
            }
        }
    }

    companion object {
        private const val USD_RATES_RESPONSE = """{"date":"2024-06-19","base":"USD","rates":{"EUR":0.9303190994511118,"USD":1.0,"JPY":157.94957670480974,"BGN":1.8195180947064844,"CZK":23.174248767327192,"DKK":6.939436226625733,"GBP":0.7857009954414365,"HUF":368.7226718764536,"PLN":4.028281700623314,"RON":4.6300120941482925,"SEK":10.432598381244768,"CHF":0.8843613359382269,"ISK":138.896641548051,"NOK":10.562843055167923,"TRY":32.53791050330263,"AUD":1.5001395478649178,"BRL":5.4404130616801565,"CAD":1.3710112568611035,"CNY":7.256954135268398,"HKD":7.805842403944553,"IDR":16382.94725090706,"ILS":3.7173690575867524,"INR":83.43287747697461,"KRW":1380.8261233603125,"MXN":18.474555772630012,"MYR":4.706484324123174,"NZD":1.6306633175179086,"PHP":58.755233044934414,"SGD":1.350730300493069,"THB":36.670387943064476,"ZAR":18.02511861568518}}"""
    }

}