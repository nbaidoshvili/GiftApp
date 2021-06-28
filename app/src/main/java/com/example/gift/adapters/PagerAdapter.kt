package com.example.gift.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gift.fragments.OtherPostsFragment
import com.example.gift.fragments.SettingsFragment
import com.example.gift.fragments.YourPostsFragment

class PagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> YourPostsFragment()
            1 -> OtherPostsFragment()
            2 -> SettingsFragment()
            else-> YourPostsFragment()
        }
    }
}