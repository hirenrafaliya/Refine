package com.app.refine.singleton

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.first

object DataStoreInstance {

    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.createDataStore(name = "configs")
    }

    suspend fun setString(key: String, value: String) {
        dataStore.edit {
            it[preferencesKey<String>(key)] = value
        }
    }

    suspend fun getString(key: String): String {
        val preferences = dataStore.data.first()
        return preferences[preferencesKey<String>(key)] ?: "null"
    }

    suspend fun setBoolean(key: String, value: Boolean) {
        dataStore.edit {
            it[preferencesKey<Boolean>(key)] = value
        }
    }

    suspend fun getBoolean(key: String): Boolean {
        val preferences = dataStore.data.first()
        return preferences[preferencesKey(key)] ?: false
    }

    suspend fun setLong(key: String, value: Long) {
        dataStore.edit {
            it[preferencesKey<Long>(key)] = value
        }
    }

    suspend fun getLong(key: String): Int {
        val preferences = dataStore.data.first()
        return preferences[preferencesKey(key)] ?: 0
    }

    suspend fun setInt(key: String, value: Int) {
        dataStore.edit {
            it[preferencesKey<Int>(key)] = value
        }
    }

    suspend fun getInt(key: String): Int {
        val preferences = dataStore.data.first()
        return preferences[preferencesKey(key)] ?: 0
    }
}
