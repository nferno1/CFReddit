package nferno1.cfreddit.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.SubredditItemBinding
import nferno1.cfreddit.domain.ApiResult
import nferno1.cfreddit.domain.model.IsSubscriber
import nferno1.cfreddit.domain.model.SubredditEntity
import nferno1.cfreddit.presentation.UiText

class SubredditsPageAdapter(
    private val onItemClick: (SubredditEntity) -> Unit,
    private val onSubscribeClick: (String, Boolean, Int) -> Unit,
    private val onShareClick: (String) -> Unit
) : PagingDataAdapter<SubredditEntity, SubredditViewHolder>(SubredditsDiffUtilCallback()) {

    fun updateElement(data: ApiResult<Int>) {
        data.data?.let { position ->
            snapshot()[position]?.let { subreddit ->
                subreddit.userIsSubscriber = when (data) {
                    is ApiResult.Loading -> IsSubscriber(
                        subreddit.userIsSubscriber.isSubscribed,
                        true
                    )

                    else -> IsSubscriber(!subreddit.userIsSubscriber.isSubscribed, false)
                }
            }
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: SubredditViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            name.text = item.displayNamePrefixed
            description.text = item.publicDescription
            subscribers.text = UiText.ResourceString(
                R.string.subscribed,
                item.subscribers
            ).asString(holder.itemView.context)

            if (item.userIsSubscriber.isLoading) {
                followButton.apply {
                    isEnabled = false
                    setColorFilter(context.getColor(R.color.dark_grey))
                }
            } else {
                if (item.userIsSubscriber.isSubscribed) {
                    followButton.apply {
                        setImageResource(R.drawable.ic_unfollow)
                        setColorFilter(context.getColor(R.color.red))
                    }
                } else {
                    followButton.apply {
                        setImageResource(R.drawable.ic_follow)
                        setColorFilter(context.getColor(R.color.green))
                    }
                }
            }

            shareButton.setOnClickListener {
                onShareClick(item.selfUrl)
            }

            followButton.setOnClickListener {
                onSubscribeClick(item.displayName, item.userIsSubscriber.isSubscribed, position)
            }

            Glide.with(logo.context)
                .load(item.subredditIcon)
                .placeholder(R.drawable.reddit_placeholder)
                .circleCrop()
                .into(logo)
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubredditViewHolder {
        return SubredditViewHolder(
            SubredditItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

class SubredditViewHolder(val binding: SubredditItemBinding) : RecyclerView.ViewHolder(binding.root)

class SubredditsDiffUtilCallback : DiffUtil.ItemCallback<SubredditEntity>() {
    override fun areItemsTheSame(oldItem: SubredditEntity, newItem: SubredditEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SubredditEntity, newItem: SubredditEntity): Boolean {
        return oldItem == newItem
    }
}