package com.davidsantiagoiriarte.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.davidsantiagoiriarte.presentation.gifslist.GifsFragment
import com.davidsantiagoiriarte.presentation.util.TABS_NUMBER

class SectionsPagerAdapter(
    fa: FragmentActivity
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return TABS_NUMBER
    }

    override fun createFragment(position: Int): Fragment {
        return GifsFragment.newInstance(position)
    }

}
