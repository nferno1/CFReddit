package nferno1.cfreddit.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nferno1.cfreddit.databinding.CommentItemBinding
import nferno1.cfreddit.domain.model.CommentEntity

class CommentsAdapter(
    private val onAuthorClick: (String) -> Unit,
    private val onVoteClick: (Int, String, Int) -> Unit
) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var data: List<CommentEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CommentEntity>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            CommentItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val item = data.getOrNull(position)
        item?.let {
            with(holder.binding) {

                author.text = it.author
                body.text = it.body
                score.text = it.score
                author.setOnClickListener {
                    onAuthorClick(item.author)
                }
                voteUpButton.setOnClickListener {
                    if (item.isLikedByUser == true) onVoteClick(0, item.id, position)
                    else onVoteClick(1, item.id, position)
                }
                voteDownButton.setOnClickListener {
                    if (item.isLikedByUser == false) onVoteClick(0, item.id, position)
                    else onVoteClick(-1, item.id, position)
                }
            }
        }
    }
}