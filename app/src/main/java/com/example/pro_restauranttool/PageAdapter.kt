package com.example.pro_restauranttool

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class PageAdapter (fm : FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentOne()
            }
            else -> {
                FragmentTwo()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Gasthaus"
            else -> return "Gastgarten"
        }
    }
}