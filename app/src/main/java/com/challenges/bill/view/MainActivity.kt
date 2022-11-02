package com.challenges.bill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.challenges.bill.business.MainVMFactory
import com.challenges.bill.business.MainViewModel
import com.challenges.bill.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainVMFactory()
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //in super class
        ActivityMainBinding.inflate(LayoutInflater.from(this)).let {
            this@MainActivity.binding = it
            setContentView(it.root)
        }

        //view setup logic
        this.binding.list.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        //text changed listener on search bar, need to debounce job so we only start a coroutine job
        // after a given delay (via viewModel), cancelling it if invoked again before the delay
        // from the listener


        this.observeViewModelEmissions()
    }

    override fun onResume() {
        super.onResume()
        this.viewModel.getImages()
    }

    private fun observeViewModelEmissions() {
        this.viewModel.observeViewState(this) { viewState ->
            when (viewState) {
                is MainViewModel.ViewState.Content -> {
                    //update adapter data
                    val adapter: ImageListAdapter? = this.binding.list.adapter as? ImageListAdapter
                    if (adapter == null) {
                        this.binding.list.adapter = ImageListAdapter(
                            imagePage = viewState.imagePage,
                            onFetchThumbnail = { url: String, position: Int ->
                                this.viewModel.getImageThumbnail(
                                    url,
                                    position
                                ) { resultingThumbnail ->
                                    //todo: fix to return bitmap from lambda
                                }
                            }
                        )
                    } else {
                        adapter.updateDataSet(viewState.imagePage)
                    }
                }
                //etc.. loading state, empty, state, etc..
            }
        }
        this.viewModel.observeEvents(this) { events ->
            when (events) {
                is MainViewModel.Events.OnError -> Toast.makeText(
                    this,
                    events.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}