package nferno1.cfreddit.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.SubredditHeaderLayoutBinding
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.model.IsSubscriber
import nferno1.cfreddit.domain.model.SubredditEntity
import nferno1.cfreddit.presentation.UiText

class HeaderAdapter(
    private val onSubscribeClick: (String, Boolean, Int) -> Unit
) : Adapter<HeaderAdapter.HeaderViewHolder>() {

    class HeaderViewHolder(val binding: SubredditHeaderLayoutBinding) : ViewHolder(binding.root)

    private lateinit var data: SubredditEntity

    @SuppressLint("NotifyDataSetChanged")
    fun setData(subreddit: SubredditEntity) {
        this.data = subreddit
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSubscribeInfo(subscribeResult: ApiResult<Int>) {
        if (subscribeResult is ApiResult.Loading) {
            this.data.userIsSubscriber.isLoading = true
        } else {
            this.data.userIsSubscriber = IsSubscriber(!data.userIsSubscriber.isSubscribed, false)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            SubredditHeaderLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {

        if (this::data.isInitialized) {

            val context = holder.binding.root.context
            val bannerUrl = data.bannerImage
            val bannerColor = data.bannerColor
            val icon = data.subredditIcon
            val name = data.displayName
            val subs = data.subscribers
            val isSubscribed = data.userIsSubscriber.isSubscribed
            val isLoading = data.userIsSubscriber.isLoading

            with(holder.binding) {

                if (bannerColor.isNotBlank()) {
                    banner.setBackgroundColor(Color.parseColor(bannerColor))
                }
                if (bannerUrl.isNotBlank()) {
                    banner.setBackgroundColor(context.getColor(R.color.transparent))
                    Glide.with(context)
                        .load(bannerUrl)
                        .into(banner)
                }
                if (icon.isNotBlank()) {
                    Glide.with(context)
                        .load(icon)
                        .into(logo)
                } else {
                    logo.setImageResource(R.drawable.reddit_placeholder)
                }
                if (name.isNotBlank()) {
                    subredditName.text = name
                }
                if (subs.isNotBlank()) {
                    subscribers.text = UiText.ResourceString(
                        R.string.subscribed,
                        subs
                    ).asString(context)
                }
                joinButton.apply {
                    isEnabled = !isLoading
                    text =
                        if (isSubscribed) UiText.ResourceString(R.string.leave)
                            .asString(context)
                        else UiText.ResourceString(R.string.join).asString(context)
                    setOnClickListener {
                        onSubscribeClick(name, isSubscribed, position)
                    }
                }
            }
        }
    }
}