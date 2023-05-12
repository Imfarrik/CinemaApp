package com.example.skillcinema.ui.helpers

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    private val calendar = Calendar.getInstance(TimeZone.getDefault())

    fun insets(view: View) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            view.updatePadding(top = statusBarHeight, bottom = navBarHeight)
            insets
        }
    }

    fun setToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getMonth(): String {
        val simpleDateFormat = SimpleDateFormat("MMMM", Locale.US)
        return simpleDateFormat.format(calendar.time).uppercase()
    }

    fun getYear(): Int {
        return calendar.get(Calendar.YEAR)
    }
}