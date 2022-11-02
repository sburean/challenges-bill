package com.challenges.bill.view

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenges.bill.databinding.ImageViewBinding
import com.challenges.bill.model.ImagePage

//todo: data set should be list of hits (individual images)
class ImageListAdapter(
    private var imagePage: ImagePage,
    private val onFetchThumbnail: (String, Int) -> Bitmap
) : RecyclerView.Adapter<ImageListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {
        val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context))
        return ImageListViewHolder(binding, onFetchThumbnail)
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, position: Int) {
        holder.bind(imagePage.hits[position].previewURL, position)
    }

    override fun getItemCount(): Int = this.imagePage.hits.size

    //todo: updating data set should be with individual images
    fun updateDataSet(updatedImagePage: ImagePage) {
        this.imagePage = updatedImagePage
        this.notifyDataSetChanged()
    }

}

class ImageListViewHolder(
    private val binding: ImageViewBinding,
    private val onFetchThumbnail: (String, Int) -> Bitmap
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(thumbnailUrl: String, position: Int) {
        //todo: can convert string to url here

        val image: Bitmap = this.onFetchThumbnail(thumbnailUrl, position)
        this.binding.image.setImageBitmap(image)

    }

}