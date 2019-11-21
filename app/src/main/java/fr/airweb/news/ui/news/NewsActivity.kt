package fr.airweb.news.ui.news

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import fr.airweb.news.R
import fr.airweb.news.adapter.NewsAdapter
import fr.airweb.news.data.network.response.Result
import fr.airweb.news.di.injectViewModel
import fr.airweb.news.ui.about.AboutActivity
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.filter_dialog.view.*
import javax.inject.Inject


class NewsActivity : DaggerAppCompatActivity(){

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NewsViewModel

    private var newsAdapter = NewsAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //viewModel = injectViewModel(viewModelFactory)
        viewModel = injectViewModel(viewModelFactory)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = newsAdapter
        }

        // Search Bar Interaction
        setSearchInput()

        // Swipe Refresh
        swipe_refresh.setOnRefreshListener {
            subscribeUi()
        }

        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.news.observe(this, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    progressBar_recycler.visibility = View.INVISIBLE
                    swipe_refresh.isRefreshing = false

                    result.data?.let {
                        val mutableList = it.news.toMutableList()
                        newsAdapter = NewsAdapter(mutableList)
                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@NewsActivity)
                            adapter = newsAdapter
                        }
                    }
                }
                Result.Status.LOADING -> progressBar_recycler.visibility = View.VISIBLE
                Result.Status.ERROR -> {
                    progressBar_recycler.visibility = View.INVISIBLE
                    swipe_refresh.isRefreshing = false
                    Snackbar.make(findViewById(android.R.id.content), result.message!!, Snackbar.LENGTH_LONG).show()
                }

            }
        })
    }


    private fun setUpSpinners(view: View) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val spinnerType = view.spinner_type

        ArrayAdapter.createFromResource(
            this,
            R.array.type,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerType.adapter = adapter
        }

        val spinnerFilterby = view.spinner_filterby
        ArrayAdapter.createFromResource(
            this,
            R.array.filterby,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerFilterby.adapter = adapter
        }
    }

    private fun setSearchInput() {
        search_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable) {
                return
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                Log.d("Activity Search", "onTextChanged")
                newsAdapter.filter.filter(s)
            }
        })
    }


    // Set the Options Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_contact -> {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_filter ->{
            buildAlertDialog()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun buildAlertDialog() {
        // Inflate the dialog
        val dialogView = LayoutInflater.from(this).inflate(R.layout.filter_dialog, null)

        // Set up Spinners
        setUpSpinners(dialogView)

        // AlertDialogBuilder
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Filtres :")

        // Show dialog
        val alertDialog = dialogBuilder.show()

        // Button Click
        dialogView.dialog_filterbutton.setOnClickListener {
            // Filter by
            if(dialogView.spinner_filterby.selectedItem.toString() == "Titre") {
                newsAdapter.sortByTitle()
            }
            if(dialogView.spinner_filterby.selectedItem.toString() == "Date") {
                newsAdapter.sortByDate()
            }

            // By Type
            val typeSpinner = dialogView.spinner_type.selectedItem.toString()
            newsAdapter.filter.filter(typeSpinner)

            // Clear Search Text
            search_input.text.clear()
            search_input.clearFocus()

            alertDialog.hide()
        }
    }

}
