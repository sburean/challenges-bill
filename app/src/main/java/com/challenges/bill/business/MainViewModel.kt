package com.challenges.bill.business

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenges.bill.dataAccess.IImageRepo
import com.challenges.bill.model.ImagePage
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class MainViewModel(
    private val repo: IImageRepo
) : ViewModel() {

    //todo: base class
    private val viewState: MutableLiveData<ViewState> = MutableLiveData()
    private val events: MutableLiveData<Events> = MutableLiveData()

    private val thumbnailJobs: MutableList<ThumbnailJob> = ArrayList()

    private val imagePage: ImagePage? = null

    //todo: base class
    sealed class ViewState {
        data class Content(
            val imagePage: ImagePage
        ) : ViewState()
        //object Loading: ViewState()
        //etc..
    }

    //todo: base class
    sealed class Events {
        data class OnError(val message: String) : Events()
        //etc..
    }

    //todo: base class
    fun observeViewState(lifecycleOwner: LifecycleOwner, onChanged: (ViewState) -> Unit) {
        this.viewState.observe(lifecycleOwner, onChanged)
    }

    fun observeEvents(lifecycleOwner: LifecycleOwner, onChanged: (Events) -> Unit) {
        this.events.observe(lifecycleOwner, onChanged)
    }

    fun getImages() {
        if (this.imagePage == null) {
            //set loading state
//            this@MainViewModel.viewState.value = ViewState.Loading()
            this.loadImages()
        } else {
            this@MainViewModel.viewState.value = ViewState.Content(imagePage = this.imagePage)
        }
    }

    fun getImageThumbnail(url: String, position: Int, onComplete: (Bitmap) -> Unit) {

        //first check if we already have a job for given position
        //if we do, cancel it first

        val thumbnailJob = ThumbnailJob(
            job = this.internalFetchThumbnail(url, position){ resultingThumbnail ->
                onComplete(resultingThumbnail)
            },
            position = position
        )
        this.thumbnailJobs.add(thumbnailJob)

    }

    private fun internalFetchThumbnail(url: String, position: Int, onComplete: (Bitmap) -> Unit): Job =
        this.viewModelScope.launch {
            with(this@MainViewModel) {
                this.repo.getImageThumbnail(url)
                    .map {
                        // withContext(Dispatchers.IO) for bitmap conversion, caching, etc..
                        // need to parse responseBody's byte stream into BitMap via BitMapFactory & invoke onComplete
                    }
                    .onFailure {
                        //show error
                        this@MainViewModel.events.value = Events.OnError(it.message ?: "failed to fetch thumbnail at $position")
                    }
            }
        }

    //page param, search param
    private fun loadImages() = this.viewModelScope.launch {
        with(this@MainViewModel) {
            this.repo.getImagePage()
                .map {
                    //update UI
                    this@MainViewModel.viewState.value = ViewState.Content(imagePage = it)
                }
                .onFailure {
                    //show error
                    this@MainViewModel.events.value = Events.OnError(it.message ?: "failed to fetch image page")
                }
        }
    }

    private data class ThumbnailJob(
        private val job: Job,
        private val position: Int
    )

}