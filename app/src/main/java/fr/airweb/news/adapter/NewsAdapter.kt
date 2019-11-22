package fr.airweb.news.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.airweb.news.R
import fr.airweb.news.data.db.model.News
import fr.airweb.news.data.db.model.NewsList
import java.util.*
import kotlin.Comparator
import java.text.SimpleDateFormat




class NewsAdapter(private val news: MutableList<News>) : RecyclerView.Adapter<NewsViewHolder>(), Filterable {
    private var newsFiltered: MutableList<News> = news

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsFiltered.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsFiltered[position])
    }

    // Search Filter Method
    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val key = constraint.toString()

                if(key.isEmpty() || key.equals("Tout", ignoreCase = true)){
                    newsFiltered = news
                }else {
                    val listFiltered: MutableList<News> = mutableListOf()
                    for(row in news){
                        if(row.title.contains(key, true)){
                            listFiltered.add(row)
                        }else if(row.type.contains(key, true)){
                                listFiltered.add(row)
                        }
                    }
                    newsFiltered = listFiltered
                }

                val filterResults = FilterResults()
                filterResults.values = newsFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newsFiltered = results!!.values as MutableList<News>
                notifyDataSetChanged()
            }

        }
    }

    // Filter methods
    fun sortByTitle(){
        Collections.sort(newsFiltered, object : Comparator<News> {
            override fun compare(p0: News, p1: News): Int {
               return p0.title.compareTo(p1.title)
            }
        })
        notifyDataSetChanged()
    }

    fun sortByDate(){

        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        Collections.sort(newsFiltered, object : Comparator<News>{
            override fun compare(p0: News, p1: News): Int {
                val string0 = p0.dateformated.replace("/","-")
                val string1 = p1.dateformated.replace("/", "-")

                val date0 = dateFormat.parse(string0)
                val date1 = dateFormat.parse(string1)
                return date1.compareTo(date0)
            }
        })
        notifyDataSetChanged()
    }

    fun sortByType(typeFilter: String){
        Collections.sort(newsFiltered, object : Comparator<News>{
            override fun compare(p0: News, p1: News): Int {
                return p0.type.compareTo(p1.type, true)
            }

        })

    }
}

private class DiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.nid == newItem.nid
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}