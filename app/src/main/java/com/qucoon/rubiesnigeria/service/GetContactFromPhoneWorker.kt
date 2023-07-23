package com.qucoon.rubiesnigeria.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.qucoon.rubiesnigeria.repository.rest.ContactsRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs


import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import org.koin.java.KoinJavaComponent.inject


class GetContactFromPhoneWorker (appContext: Context, workerParams: WorkerParameters): Worker(appContext,workerParams),CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job
     val contactsRepository: ContactsRepository by inject(ContactsRepository::class.java)

    companion object{
        val TAG = GetContactFromPhoneWorker::class.java.simpleName
    }

     fun  refreshSMSList():Boolean{
       return  runBlocking {
             val needsRetry = try {
                 contactsRepository.getContacts()
                 (false)
             }catch (ex:Exception){
                 Log.e(TAG,"Error : ${ex}")
                 true
             }
              needsRetry
         }
    }

    override fun doWork(): Result {
        return if(refreshSMSList()){
            Log.i(TAG,"Worker Needs Retry")
            Result.retry()
        }else{
            Log.i("GetSMSFromPhoneWorker","Worker Successful")
            Result.success()
        }

    }
}