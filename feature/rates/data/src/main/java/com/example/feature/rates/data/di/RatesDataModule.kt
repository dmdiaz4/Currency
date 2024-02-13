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

package com.example.feature.rates.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.data.local.PersistingDatabase
import com.example.core.data.local.daos.DBRatesDao
import com.example.feature.rates.data.RatesRepositoryImpl
import com.example.feature.rates.data.local.datastore.RatesSettingsDataStore
import com.example.feature.rates.domain.RatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore(name = "rates_settings")

@Module
@InstallIn(SingletonComponent::class)
object RatesDataModule {

    @Provides
    fun provideDBRatesDao(
        database: PersistingDatabase
    ): DBRatesDao {
        return database.ratesDao()
    }


    @Provides
    fun provideRatesSettingsDataStore(
        @ApplicationContext context: Context
    ) : RatesSettingsDataStore{
        return object: RatesSettingsDataStore, DataStore<Preferences> by context.dataStore {}
    }

    @Provides
    @Singleton
    fun provideRatesRepository(
        ratesRepositoryImpl: RatesRepositoryImpl
    ): RatesRepository = ratesRepositoryImpl

}