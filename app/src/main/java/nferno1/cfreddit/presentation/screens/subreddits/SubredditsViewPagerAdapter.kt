package nferno1.cfreddit.presentation.screens.subreddits

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SubredditsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewSubredditsFragment()
            1 -> PopularSubredditsFragment()
            else -> throw java.lang.IllegalArgumentException("Invalid position $position")
        }
    }
}