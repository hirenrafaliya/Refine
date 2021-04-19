package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.model.Config.interContent
import com.app.refine.model.Config.interExit
import com.app.refine.model.Config.update
import com.app.refine.model.InterContent
import com.app.refine.model.InterExit
import com.app.refine.model.Update
import com.app.refine.singleton.DataStoreInstance
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class ConfigRepository {
    private val TAG = "conf_repo_tager"
    var isSetConfigLiveData = MutableLiveData<Boolean>()

    suspend fun setConfigDataFromDataStore() {
        val gson = Gson()
        update = gson.fromJson(DataStoreInstance.getString("update"), Update::class.java)
        interContent =
            gson.fromJson(DataStoreInstance.getString("update"), InterContent::class.java)
        interExit = gson.fromJson(DataStoreInstance.getString("update"), InterExit::class.java)
    }

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
                        update = snapshot.toObject(Update::class.java)!!
                        DataStoreInstance.setString("update", Gson().toJson(update))
                        DataStoreInstance.setString("updatedOn", update.updatedOn ?: "0")
                    }
                    "interContent" -> {
                        interContent = snapshot.toObject(InterContent::class.java)!!
                        DataStoreInstance.setString("interContent", Gson().toJson(interContent))
                        DataStoreInstance.setString("updatedOn", interContent.updatedOn ?: "0")
                    }
                    "interExit" -> {
                        interExit = snapshot.toObject(InterExit::class.java)!!
                        DataStoreInstance.setString("interExit", Gson().toJson(interExit))
                        DataStoreInstance.setString("updatedOn", interExit.updatedOn ?: "0")
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