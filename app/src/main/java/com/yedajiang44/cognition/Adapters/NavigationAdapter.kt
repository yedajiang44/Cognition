package com.yedajiang44.cognition.Adapters

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yedajiang44.cognition.Models.NavigationItemModel
import com.yedajiang44.cognition.R

class NavigationAdapter :
    BaseQuickAdapter<NavigationItemModel, BaseViewHolder>(R.layout.item_navigation) {
    override fun convert(helper: BaseViewHolder, item: NavigationItemModel?) {
        Glide.with(context)
            .load(item?.thumbnail)
            .into(helper.getView(R.id.thumbnail))
    }
}