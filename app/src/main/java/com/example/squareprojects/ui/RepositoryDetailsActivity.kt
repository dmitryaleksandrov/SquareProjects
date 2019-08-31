package com.example.squareprojects.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.squareprojects.R
import com.example.squareprojects.api.model.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_repository_details.*

class RepositoryDetailsActivity : AppCompatActivity() {

    companion object {

        private const val KEY_REPOSITORY = "repository"

        fun createExtras(repository: Repository): Bundle {
            return Bundle().apply { putParcelable(KEY_REPOSITORY, repository) }
        }
    }

    private val repository: Repository get() = intent.getParcelableExtra(KEY_REPOSITORY)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_repository_details)

        title = repository.fullName

        Picasso.get().load(repository.owner.avatarUrl).into(avatar)

        description.text = repository.description
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(
            R.anim.slide_in_left_to_right,
            R.anim.slide_out_left_to_right
        )
    }
}