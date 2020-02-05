package com.yedajiang44.cognition.Adapters

import android.graphics.Color
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yedajiang44.cognition.Models.DetailItemModel
import com.yedajiang44.cognition.R
import jp.wasabeef.glide.transformations.BlurTransformation

class DetailAdapter :
    BaseQuickAdapter<DetailItemModel, BaseViewHolder>(R.layout.item_entrance) {
    override fun convert(helper: BaseViewHolder, item: DetailItemModel?) {
        if (!item?.thumbnail.equals("")) {
            Glide.with(context)
                .load(item?.thumbnail)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 8)))
                .into(helper.getView(R.id.background))
            Glide.with(context)
                .load(item?.thumbnail)
                .into(helper.getView(R.id.thumbnail))
        } else {
            val text = helper.getView<AppCompatTextView>(R.id.text)
            text.text = item?.name
            val color = (0..0xffffff).random()
            text.setBackgroundColor(
                Color.parseColor(
                    "#${String.format(
                        "%6s",
                        color.toString(16)
                    ).replace(' ', '0')}"
                )
            )
        }
    }
}