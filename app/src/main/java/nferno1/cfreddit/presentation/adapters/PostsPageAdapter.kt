package nferno1.cfreddit.presentation.adapters

import android.app.ActionBar.LayoutParams
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nferno1.cfreddit.domain.model.PostEntity
import nferno1.cfreddit.presentation.screens.subreddits.PostViewGroup

class PostsPageAdapter(
    private val onPostClick: (String) -> Unit,
    private val onVoteButtonsClick: (Int, String, Int) -> Unit,
    private val onAuthorClick: (String) -> Unit
) : PagingDataAdapter<PostEntity, PostViewHolder>(PostsDiffUtilCallback()) {

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        val item = getItem(position) ?: return

        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        holder.viewGroup.layoutParams = layoutParams

        holder.viewGroup.setupView(
            post = item,
            onPostClick = onPostClick,
            onVoteClick = { dir, id -> onVoteButtonsClick(dir, id, position) },
            onAuthorClick = onAuthorClick
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(PostViewGroup(parent.context))
    }
}

class PostViewHolder(val viewGroup: PostViewGroup) : RecyclerView.ViewHolder(viewGroup.rootView)

class PostsDiffUtilCallback : DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return oldItem == newItem
    }
}