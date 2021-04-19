package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.model.Update
import com.app.refine.singleton.DataStoreInstance
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class ConfigRepository {
    private val TAG = "conf_repo_tager"
    var isSetConfigLiveData = MutableLiveData<Boolean>()

    suspend fun setConfigData() {
        Log.d(TAG, "setConfigData: ")
        var updatedOn = DataStoreInstance.getString("updatedOn")
        if (updatedOn == "null") updatedOn = "0"
        Log.d(TAG, "setConfigData: updatedOn=$updatedOn")

        val querySnapshot = FirebaseFirestore
            .getInstance()
            .collection("configs")
            .whereGreaterThan("updatedOn", updatedOn)
            .get().await()

        if (!querySnapshot.isEmpty) {
            val snapshots = querySnapshot.documents
            for (snapshot in snapshots) {
                Log.d(TAG, "setConfigData: ${snapshot.id}")
                when (snapshot.id) {
                    "update" -> {
                        val update = snapshot.toObject(Update::class.java)
                        Log.d(TAG, "setConfigData: ${Gson().toJson(update)}")
                        Log.d(TAG, "setConfigData: ${update.toString()}")
                        DataStoreInstance.setString("update", Gson().toJson(update))
                        DataStoreInstance.setString("updatedOn", update?.updatedOn ?: "0")
                    }
                }
            }
        } else {
            Log.d(TAG, "setConfigData: snapshot empty")
        }

        isSetConfigLiveData.postValue(true)

    }

    fun isSetConfigLiveData(): Boolean = isSetConfigLiveData.value ?: false
}