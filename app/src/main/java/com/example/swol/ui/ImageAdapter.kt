package com.example.swol.ui

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.R
import com.example.swol.data.ImageEntity
import java.text.SimpleDateFormat
import java.util.*

class ImageAdapter(private var images: List<ImageEntity>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageItemView)
        val timestampView: TextView = view.findViewById(R.id.timestampTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.timestampView.text = formatDate(image.timestamp)
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(image.imageData, 0, image.imageData.size))

    }

    private fun formatDate(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        return dateFormat.format(Date(timestamp))
    }
    override fun getItemCount() = images.size

    fun setImages(images: List<ImageEntity>) {
        this.images = images
        notifyDataSetChanged()
    }
}
