package nferno1.cfreddit.presentation.screens.subreddits

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import nferno1.cfreddit.R
import nferno1.cfreddit.databinding.PostItemBinding
import nferno1.cfreddit.domain.model.ImagePostEntity
import nferno1.cfreddit.domain.model.PostEntity
import nferno1.cfreddit.domain.model.TextPostEntity

class PostViewGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    private var binding: PostItemBinding

    init {
        binding = PostItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun setupView(
        post: PostEntity,
        onPostClick: (String) -> Unit,
        onVoteClick: (Int, String) -> Unit,
        onAuthorClick: (String) -> Unit
    ) {
        fillView(post)
        setupShareClickListener(post)
        setupOnRootClickListener(post.id, onPostClick)
        setupVoteButtonsClickListener(post.isLikedByUser.isLiked) { dir ->
            onVoteClick(
                dir,
                post.id
            )
        }
        setUpOnAuthorClick(post.author, onAuthorClick)
    }

    private fun fillView(post: PostEntity) {
        with(binding) {
            postScore.text = post.score
            commentsNumber.text = post.numComments.toString()
            if (post.isLikedByUser.isLiked == true) voteUpButton.setColorFilter(context.getColor(R.color.green))
            if (post.isLikedByUser.isLiked == false) voteDownButton.setColorFilter(
                context.getColor(
                    R.color.red
                )
            )
            subreddit.text = post.subreddit
            author.text = post.author
            title.text = post.title

            if (post is TextPostEntity) {
                contentTextView.visibility = View.VISIBLE
                contentTextView.text = post.selfText
                contentImageView.visibility = View.GONE
            }
            if (post is ImagePostEntity) {
                contentImageView.visibility = View.VISIBLE
                Glide.with(contentImageView)
                    .load(post.imageUrl)
                    .into(contentImageView)
                contentTextView.visibility = View.GONE
            }
        }
    }

    private fun setupShareClickListener(post: PostEntity) {
        binding.shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND).also {
                it.putExtra(Intent.EXTRA_TEXT, post.selfUrl)
                it.type = "text/plain"
            }
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpOnAuthorClick(userName: String, onAuthorClick: (String) -> Unit) {
        binding.author.setOnClickListener {
            onAuthorClick(userName)
        }
    }

    private fun setupOnRootClickListener(postId: String, onPostClick: (String) -> Unit) {
        binding.root.setOnClickListener {
            onPostClick(postId)
        }
    }

    private fun setupVoteButtonsClickListener(isLikedByUser: Boolean?, onVoteButtonsClick: (Int) -> Unit) {
        binding.voteUpButton.setOnClickListener {
            if (isLikedByUser == true) onVoteButtonsClick(0)
            else onVoteButtonsClick(1)
        }
        binding.voteDownButton.setOnClickListener {
            if (isLikedByUser == false) onVoteButtonsClick(0)
            else onVoteButtonsClick(-1)
        }
    }
}