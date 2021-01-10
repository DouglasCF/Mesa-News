package br.com.fornaro.mesanews.features.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fornaro.mesanews.databinding.ItemNewsBinding
import br.com.fornaro.mesanews.domain.models.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var data = emptyList<News>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    class ViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) = with(binding) {
            this.news = news
            executePendingBindings()
        }
    }
}