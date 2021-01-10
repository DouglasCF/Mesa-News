package br.com.fornaro.mesanews.features.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fornaro.mesanews.databinding.ItemHighlightsBinding
import br.com.fornaro.mesanews.domain.models.News

class HighlightsAdapter : RecyclerView.Adapter<HighlightsAdapter.ViewHolder>() {

    var data = emptyList<News>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemHighlightsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    class ViewHolder(
        private val binding: ItemHighlightsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) = with(binding) {
            this.news = news
            executePendingBindings()
        }
    }
}