package com.example.squareprojects.ui

import androidx.lifecycle.*
import com.example.squareprojects.api.model.Repository
import com.example.squareprojects.core.IGitHubManager
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import javax.inject.Inject

class RepositoryListViewModel(private val gitHubManager: IGitHubManager) : ViewModel(), AnkoLogger {

    private lateinit var user: String
    private var limit: Int = 10

    private lateinit var liveData: MutableLiveData<List<Repository>?>

    fun loadData(user: String, limit: Int): LiveData<List<Repository>?> {
        this.user = user
        this.limit = limit

        liveData = MutableLiveData()

        updateData()

        return liveData
    }

    fun updateData() {
        viewModelScope.launch {
            val delayJob = async { delay(1_500) }

            try {
                val repositories = gitHubManager.getRepositoriesByUser(
                    this@RepositoryListViewModel.user,
                    this@RepositoryListViewModel.limit
                )

                delayJob.cancel()

                liveData.value = repositories

            } catch (t: Throwable) {
                error("Failed to get repository list", t)

                delayJob.await()

                liveData.value = null
            }
        }
    }

    class Factory @Inject constructor(private val gitHubManager: IGitHubManager) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepositoryListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepositoryListViewModel(gitHubManager) as T
            }

            throw IllegalArgumentException("Unknown view model")
        }

    }

}