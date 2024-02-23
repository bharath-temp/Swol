package com.example.swol

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.data.Datapoint

class ExercisesAdapter(private var datapoints: List<Datapoint>):
    RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datapoints[position])
    }

    fun updateExercises(newData: List<Datapoint>?) {
        datapoints = newData ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = datapoints.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val nameTextView: TextView = view.findViewById(R.id.tv_name)
        private val categoryTextView: TextView = view.findViewById(R.id.tv_category)
        fun bind(exercise: Datapoint) {
            nameTextView.text = exercise.name
            categoryTextView.text = exercise.category
        }
    }
}