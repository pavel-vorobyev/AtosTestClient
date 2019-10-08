package com.pavelvorobyev.atostest.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pavelvorobyev.atostest.view.availablerooms.AvailableRoomsFragment
import com.pavelvorobyev.atostest.view.myrooms.MyRoomsFragment

class ViewPagerAdapter(manager: FragmentManager,
                       val pageTitles: Array<String>) : FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = ArrayList<Fragment>()

    init {
        pages.add(AvailableRoomsFragment())
        pages.add(MyRoomsFragment())
    }

    override fun getItem(position: Int): Fragment = pages[position]
    override fun getCount(): Int = pages.size
    override fun getPageTitle(position: Int): CharSequence? = pageTitles[position]
}