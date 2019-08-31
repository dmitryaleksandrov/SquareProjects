package com.example.squareprojects.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.squareprojects.R
import com.example.squareprojects.api.model.Repository
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_repository_list.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso


class RepositoryListActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_ERROR_SCREEN = 1
        private const val USER = "square"
        private const val LIMIT = 10

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repository>() {

            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }

    @Inject
    lateinit var viewModelProviderFactory: RepositoryListViewModel.Factory

    private lateinit var viewModel: RepositoryListViewModel

    private val adapter = RepositoryListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_repository_list)

        showProgress()

        listView.adapter = adapter
        listView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        listView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(RepositoryListViewModel::class.java)

        viewModel.loadData(USER, LIMIT).observe(this, Observer { repositories ->
            if (repositories != null) {
                adapter.submitList(repositories)
                hideProgress()
            } else {
                onError()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_ERROR_SCREEN && resultCode == Activity.RESULT_OK) {
            viewModel.updateData()
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onItemClick(repository: Repository) {
        startActivity(Intent(this, RepositoryDetailsActivity::class.java).apply {
            putExtras(RepositoryDetailsActivity.createExtras(repository))
        })

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left)
    }

    private fun onError() {
        startActivityForResult(
            Intent(this, ErrorActivity::class.java),
            REQUEST_ERROR_SCREEN
        )

        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left)
    }

    private fun showProgress() {
        listView.visibility = View.INVISIBLE
        progressView.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        listView.visibility = View.VISIBLE
        progressView.visibility = View.GONE
    }

    inner class RepositoryListAdapter :
        ListAdapter<Repository, RepositoryViewHolder>(DIFF_CALLBACK) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
            val itemView = layoutInflater.inflate(R.layout.item_repository, parent, false)
            return RepositoryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
            holder.bindTo(getItem(position))
        }
    }


    inner class RepositoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameView = itemView.findViewById<TextView>(R.id.name)
        private val starCountView = itemView.findViewById<TextView>(R.id.startCount)
        private val languageView = itemView.findViewById<TextView>(R.id.language)
        private val licenseView = itemView.findViewById<TextView>(R.id.license)
        private val avatarView = itemView.findViewById<ImageView>(R.id.avatar)

        fun bindTo(repository: Repository) {
            // TODO: Consider to use Data Binding library
            nameView.text = repository.name
            starCountView.text = repository.starCount.toString()
            languageView.text = repository.language
            licenseView.text = repository.license.name

            Picasso.get().load(repository.owner.avatarUrl).into(avatarView)

            itemView.setOnClickListener { onItemClick(repository) }
        }
    }
}
