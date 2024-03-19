package com.example.swol.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swol.R
import com.example.swol.data.ExerciseEntity

class DailyExerciseAdapter(private var exercises: List<ExerciseEntity>) :
    RecyclerView.Adapter<DailyExerciseAdapter.ViewHolder>() {

    var onDeleteClick: ((ExerciseEntity) -> Unit)? = null

    fun getPreferredUnits(context: Context): String {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("units", "lbs") ?: "lbs"
    }
    fun submitList(newExercises: List<ExerciseEntity>) {
        exercises = newExercises
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val units = getPreferredUnits(holder.itemView.context)
        val exercise = exercises[position]
        holder.nameTextView.text = exercise.name
        holder.categoryTextView.text = exercise.category
        holder.weightTextView.text = "Weight: ${exercise.weight} $units"
        holder.setsTextView.text = "Sets: ${exercise.sets}"
        holder.repsTextView.text = "Reps: ${exercise.reps}"

        holder.deleteButton.setOnClickListener {
            onDeleteClick?.invoke(exercise)
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }
    fun getExercises(): List<ExerciseEntity> {
        return exercises
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.exercise_name)
        val categoryTextView: TextView = itemView.findViewById(R.id.exercise_category)
        val weightTextView: TextView = itemView.findViewById(R.id.exercise_weight)
        val setsTextView: TextView = itemView.findViewById(R.id.exercise_sets)
        val repsTextView: TextView = itemView.findViewById(R.id.exercise_reps)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)
    }

}
