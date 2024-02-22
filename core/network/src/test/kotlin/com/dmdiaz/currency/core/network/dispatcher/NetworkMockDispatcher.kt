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

package com.dmdiaz.currency.core.network.dispatcher

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import javax.inject.Inject


class NetworkMockDispatcher @Inject constructor (
) : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        return when(request.path){
            "rates" ->{
                MockResponse().setResponseCode(200).setBody(RATES_RESPONSE)
            }
            else -> {
                MockResponse().setResponseCode(404)
            }
        }
    }

    companion object {
        private const val RATES_RESPONSE = """{"date":"2024-02-20","base":"USD","rates":{"EUR":0.925754489909276,"USD":1.0,"JPY":150.13886317348638,"BGN":1.810590631364562,"CZK":23.541010923902977,"DKK":6.900759118681726,"GBP":0.7930012960562859,"HUF":359.45195334197365,"PLN":4.003703017959637,"RON":4.607294945380485,"SEK":10.385576745047214,"CHF":0.8818737270875764,"ISK":137.28939085354565,"NOK":10.486483984447325,"TRY":30.903999259396407,"AUD":1.5235141640436956,"BRL":4.954730605443436,"CAD":1.3480836882058878,"CNY":7.194778744676912,"HKD":7.821421958896501,"IDR":15660.451768191073,"ILS":3.656915386039622,"INR":82.95176819107573,"KRW":1333.7900388816886,"MXN":17.014626920940568,"MYR":4.797537493056841,"NZD":1.6194223291982965,"PHP":56.059988890946116,"SGD":1.3446583965932233,"THB":36.04980559155712,"ZAR":18.947694871320127}}"""
    }

}