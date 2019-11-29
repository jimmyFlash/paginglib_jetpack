package com.jimmy.pagginglib_tutorial.views.customViews

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.jimmy.pagginglib_tutorial.R
import kotlinx.android.synthetic.main.view_info_card.view.*
import kotlin.properties.Delegates

class InfoCard : FrameLayout {

    /*
    This delegate takes a lambda as a parameter, which will be invoked every time the value of the
    property changes. We can use this callback to set the same value on the corresponding View.
    This can be viewed as a basic, manual form of data binding.

    To handle hiding the views when their content is empty, we can use isVisible from android-ktx,
     after setting the new values
     */
    var title: String? by Delegates.observable<String?>(null) { _, _, newTitle ->
        infoCardTitleText.text = newTitle
        infoCardTitleText.isVisible = !newTitle.isNullOrEmpty()
    }


    var content: String? by Delegates.observable<String?>(null) { _, _, newContent  ->
        infoCardContentText.text = newContent
        infoCardContentText.isVisible = !newContent.isNullOrEmpty()
    }


    var icon: Drawable? by Delegates.observable<Drawable?>(null) { _, _, newIcon  ->
        infoCardImage.setImageDrawable(newIcon)
        infoCardImage.isVisible = newIcon != null
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        initView(attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){

        initView(attrs)
    }

    init {
        inflate(context, R.layout.view_info_card, this)
    }

    /**
     * After obtaining the attribute values for our custom View, all we need to do is set the values
     * of our existing properties, which already know how to display this data on the UI
     */
    private fun initView(attrs: AttributeSet?) {
        attrs ?: return

        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.InfoCard)
        with(attributeValues) {
            try {
                icon = getDrawable(R.styleable.InfoCard_ic_icon)
                title = getString(R.styleable.InfoCard_ic_title)
                content = getString(R.styleable.InfoCard_ic_content)
            } finally {
                recycle()
            }
        }
    }
}