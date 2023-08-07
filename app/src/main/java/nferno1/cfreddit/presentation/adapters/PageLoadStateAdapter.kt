package nferno1.cfreddit.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import nferno1.cfreddit.databinding.LoadingItemBinding
import nferno1.cfreddit.databinding.LoadingItemBinding.*

class PageLoadStateAdapter : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = Unit

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }
}

class LoadStateViewHolder(binding: LoadingItemBinding) : ViewHolder(binding.root)