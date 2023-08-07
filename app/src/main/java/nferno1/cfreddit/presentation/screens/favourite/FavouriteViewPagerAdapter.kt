package nferno1.cfreddit.presentation.screens.favourite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavouriteViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavouriteSubredditsFragment()
            1 -> FavouritePostsFragment()
            else -> throw java.lang.IllegalArgumentException("Invalid position $position")
        }
    }
}