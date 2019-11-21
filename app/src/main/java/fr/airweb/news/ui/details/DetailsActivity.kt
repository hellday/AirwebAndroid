package fr.airweb.news.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import fr.airweb.news.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the data from the intent
        val titleExtra = intent.getStringExtra("title")
        val pictureExtra = intent.getStringExtra("picture")
        val contentExtra = intent.getStringExtra("content")

        // Bind them to the view
        title_details.text = titleExtra

        Glide.with(this)
            .load(pictureExtra)
            .into(picture_details)

        content_details.text = contentExtra
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
