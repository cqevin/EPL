package com.chriskevin.epl.favorite

import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.BindingAdapter
import com.chriskevin.epl.R
import com.chriskevin.epl.util.glidesvg.GlideApp
import com.chriskevin.epl.util.glidesvg.SvgSoftwareLayerSetter

@BindingAdapter("teamBadge")
fun ImageView.teamBadge(url: String?) {
    GlideApp.with(this)
        .`as`(PictureDrawable::class.java)
        .load(url)
        .placeholder(R.drawable.team_badge_placeholder)
        .listener(SvgSoftwareLayerSetter())
        .into(this)
}

@BindingAdapter(
    "paddingLeftSystemWindowInsets",
    "paddingTopSystemWindowInsets",
    "paddingRightSystemWindowInsets",
    "paddingBottomSystemWindowInsets",
    requireAll = false
)
fun View.applySystemWindowInsetsPadding(
    previousApplyLeft: Boolean,
    previousApplyTop: Boolean,
    previousApplyRight: Boolean,
    previousApplyBottom: Boolean,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) {
    if (previousApplyLeft == applyLeft &&
        previousApplyTop == applyTop &&
        previousApplyRight == applyRight &&
        previousApplyBottom == applyBottom
    ) {
        return
    }
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        val left = if (applyLeft) systemBars.left else 0
        val top = if (applyTop) systemBars.top else 0
        val right = if (applyRight) systemBars.right else 0
        val bottom = if (applyBottom) systemBars.bottom else 0

        view.setPadding(left, top, right, bottom)

        insets
    }
}