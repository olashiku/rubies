package com.qucoon.rubiesnigeria.repository.rest

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.qucoon.rubiesnigeria.service.GetContactFromPhoneWorker


 interface WorkerRepository{
     fun getAllContacts()
 }

class WorkerRepositoryImpl:WorkerRepository {

    override fun getAllContacts() {
        val networkRequiredConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workManager = WorkManager.getInstance()
        val bankListUpdateWorker = OneTimeWorkRequestBuilder<GetContactFromPhoneWorker>()
            .setConstraints(networkRequiredConstraint)
            .addTag("UPDATING contacts")
            .build()
        workManager.cancelAllWorkByTag("UPDATING contacts")
        workManager.cancelAllWorkByTag("UPDATING contacts")
        workManager.beginWith(bankListUpdateWorker).enqueue()
    }
}