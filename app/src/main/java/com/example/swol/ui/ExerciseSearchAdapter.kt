package com.example.swol.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.R
import com.example.swol.data.Datapoint
import com.google.android.material.card.MaterialCardView

class ExerciseSearchAdapter(private var datapoints: List<Datapoint>):
    RecyclerView.Adapter<ExerciseSearchAdapter.ViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION
    private var selectedItem: Datapoint? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isSelected = position == selectedPosition
        holder.bind(datapoints[position], isSelected)
        holder.itemView.isSelected = position == selectedPosition
        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            selectedItem = datapoints[selectedPosition]
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    fun getSelectedItem(): Datapoint? = selectedItem

    fun updateExercises(newData: List<Datapoint>?) {
        datapoints = newData ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datapoints.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.tv_name)
        private val categoryTextView: TextView = view.findViewById(R.id.tv_category)
        private val cardView: MaterialCardView = view as MaterialCardView

        fun bind(exercise: Datapoint, isSelected: Boolean) {
            nameTextView.text = exercise.name
            categoryTextView.text = exercise.category

            if (isSelected) {
                cardView.setCardBackgroundColor(cardView.context.getColor(R.color.selected_item_background))
            } else {
                cardView.setCardBackgroundColor(cardView.context.getColor(android.R.color.transparent))
            }
        }
    }
}