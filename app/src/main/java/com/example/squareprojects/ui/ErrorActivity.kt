package com.example.squareprojects.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.squareprojects.R
import kotlinx.android.synthetic.main.activity_error.*

class ErrorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_error)

        title = getString(R.string.error_activity_title)

        tryAgainButton.setOnClickListener {
            setResult(Activity.RESULT_OK)

            finish()
            overridePendingTransition(
                R.anim.slide_in_left_to_right,
                R.anim.slide_out_left_to_right
            )
        }
    }

    override fun onBackPressed() {
    }
}