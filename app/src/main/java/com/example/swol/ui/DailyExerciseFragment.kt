package com.example.swol.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.swol.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DailyExerciseFragment : Fragment(R.layout.fragment_daily_exercise_summary) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val selectDateButton: Button = view.findViewById(R.id.btn_select_date)

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        selectDateButton.text = dateFormat.format(currentDate)

        selectDateButton.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(childFragmentManager, "DATE_PICKER")

            datePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.timeInMillis = selection
                val selectedDate = calendar.time
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                selectDateButton.text = dateFormat.format(selectedDate)

                Log.d("Date:", "Selected date: ${dateFormat.format(selectedDate)}")
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.swol_exercise_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_share -> {
                            share()
                            true
                        }
                        R.id.action_settings -> {
                            findNavController().navigate(R.id.navigate_to_settings)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.STARTED
        )
    }

    private fun share() {
        //val shareText = getString(R.string.share_text, "Hello")
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            //putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_TEXT, "Hellow")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, null))
    }

}
