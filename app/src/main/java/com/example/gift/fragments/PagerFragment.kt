package com.example.gift.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.gift.R
import com.example.gift.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PagerFragment : Fragment(R.layout.fragment_pager) {

    private lateinit var tab: TabLayout
    private lateinit var pager: ViewPager2
    private lateinit var adapter: PagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab = view.findViewById(R.id.tab)
        pager = view.findViewById(R.id.pager)
        adapter = PagerAdapter(this)

        pager.adapter = adapter

        TabLayoutMediator(tab, pager){tab, position ->
            when(position){
                0->tab.text = "your posts"
                1->tab.text = "other posts"
                2->tab.text = "profile"
            }
        }.attach()
    }
}