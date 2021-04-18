package com.app.refine.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.app.refine.singleton.DataStoreInstance
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ConfigRepository {

    var isSetConfigLiveData = MutableLiveData<Boolean>()

    suspend fun setConfigData(context: Context) {
        val updatedOn = DataStoreInstance.getString("updatedOn")

        val querySnapshot = FirebaseFirestore
            .getInstance()
            .collection("configs")
            .whereGreaterThan("updatedOn", updatedOn)
            .get().await()

        if (!querySnapshot.isEmpty) {
            val snapshots = querySnapshot.documents
            for (snapshot in snapshots) {
                snapshot.data?.forEach {
                    if (it.value is String) {
                        DataStoreInstance.setString(
                            snapshot.id + "_" + it.key,
                            it.value as String
                        )
                    } else if (it.value is Boolean) {
                        DataStoreInstance.setBoolean(
                            snapshot.id + "_" + it.key,
                            it.value as Boolean
                        )
                    }
                }
            }
        }

        isSetConfigLiveData.postValue(true)

    }

    fun isSetConfigLiveData(): Boolean = isSetConfigLiveData.value ?: false
}