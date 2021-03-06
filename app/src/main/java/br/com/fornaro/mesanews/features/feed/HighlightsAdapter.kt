package br.com.fornaro.mesanews.features.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fornaro.mesanews.databinding.ItemHighlightsBinding
import br.com.fornaro.mesanews.domain.models.News

class HighlightsAdapter(
    private val viewModel: FeedViewModel,
    private val action: (News) -> Unit
) : RecyclerView.Adapter<HighlightsAdapter.ViewHolder>() {

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
            ).apply { viewModel = this@HighlightsAdapter.viewModel }
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], action)

    override fun getItemCount() = data.size

    class ViewHolder(
        private val binding: ItemHighlightsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News, action: (News) -> Unit) = with(binding) {
            this.news = news
            container.setOnClickListener { action.invoke(news) }
            executePendingBindings()
        }
    }
}