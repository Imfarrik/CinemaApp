package com.example.skillcinema.ui.custom_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.skillcinema.R
import com.example.skillcinema.databinding.DatePickerYearBinding
import java.util.*

const val SPAN = 4
const val STEP = 11

class DatePickerYear
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var currentSelectYear: Int
        private set
    private var currentFromYear: Int = 0
    private var currentToYear: Int = 0
    private var onClick: ((Int) -> Unit)? = null

    val binding = DatePickerYearBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)

        binding.years.columnCount = SPAN

        val calendar = Calendar.getInstance()
        currentSelectYear = calendar.get(Calendar.YEAR)

        addTextView(currentSelectYear - STEP, currentSelectYear)

        checkedTextView(currentSelectYear)

        binding.left.setOnClickListener {
            binding.years.removeAllViews()
            addTextView(currentFromYear - STEP, currentFromYear)
            checkedTextView(currentSelectYear)
        }

        binding.right.setOnClickListener {
            binding.years.removeAllViews()
            addTextView(currentToYear, currentToYear + STEP)
            checkedTextView(currentSelectYear)
        }
    }

    private fun addTextView(fromYear: Int, toYear: Int) {
        for (year in fromYear..toYear) {
            val textView = createTextView(year)

            binding.years.addView(textView)

            textView.setOnClickListener {
                currentSelectYear = textView.id

                checkedTextView(textView)

                binding.years.children.filter { view -> view.id != textView.id }.forEach {
                    uncheckedTextView(it as TextView)
                }

                onClick?.invoke(currentSelectYear)
            }
        }

        binding.fromYearToYear.text = "$fromYear - $toYear"

        currentFromYear = fromYear
        currentToYear = toYear
    }

    private fun createTextView(
        year: Int,
    ): TextView {
        val textView = TextView(binding.root.context)

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        textView.setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.black
            )
        )

        val params = (binding.years.layoutParams as LayoutParams)

        params.leftMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                .toInt()
        params.rightMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                .toInt()
        params.topMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                .toInt()
        params.bottomMargin =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                .toInt()
        textView.layoutParams = params

        textView.id = year
        textView.text = year.toString()
        textView.typeface = Typeface.DEFAULT_BOLD

        return textView
    }

    private fun checkedTextView(year: Int) {
        binding.years.children.find { view -> view.id == year }?.apply {
            checkedTextView(this as TextView)
        }
    }

    private fun checkedTextView(textView: TextView) {
        textView.setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.main
            )
        )
    }

    private fun uncheckedTextView(textView: TextView) {
        textView.setTextColor(
            ContextCompat.getColor(
                this.context,
                R.color.black
            )
        )
    }

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }
}