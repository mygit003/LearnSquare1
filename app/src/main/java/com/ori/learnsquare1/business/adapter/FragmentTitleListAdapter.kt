package com.ori.learnsquare1.business.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 创建人: zhengpf
 * 修改时间: 2020/7/11 15:26
 * 类说明:
 */
class FragmentTitleListAdapter(val list: MutableList<Fragment>, val titles: MutableList<String>, fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        return list[position]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles?.get(position)
    }
}