package fr.airweb.news.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList
import fr.airweb.news.internal.GlideApp
import fr.airweb.news.internal.GlideAppModule
import fr.airweb.news.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.row_item.view.*

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(news: News) {
        // Title & Content
        itemView.title.text = news.title
        itemView.content.text = news.content

        // Picture : Glide
        GlideApp.with(itemView.picture.context)
            .load(news.picture)
            .into(itemView.picture)

        // OnClick Item
        itemView.setOnClickListener {
            //Toast.makeText(itemView.context, "Titre : ${news.title}", Toast.LENGTH_LONG).show()
            val intent = Intent(itemView.context, DetailsActivity::class.java)
            intent.putExtra("title", news.title)
            intent.putExtra("content", news.content)
            intent.putExtra("picture", news.picture)
            itemView.context.startActivity(intent)
        }
    }
}