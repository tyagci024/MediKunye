package com.example.gitdeneme.serviceDrug

import androidx.lifecycle.LiveData
import com.example.gitdeneme.model.Drug

    class DrugRepo(private val drugDao: DrugDao) {

        val readAllData: LiveData<List<Drug>> = drugDao.getAllDrugs()

        suspend fun addDrug(drug: Drug) {
            drugDao.addDrug(drug)
        }

        suspend fun deleteDrug(drug: Drug) {
            drugDao.deleteDrug(drug)

        }

        suspend fun updateDrug(drug: Drug) {
            drugDao.updateDrug(drug)

        }

        suspend fun deleteAll() {
            drugDao.deleteAll()
        }

    }
