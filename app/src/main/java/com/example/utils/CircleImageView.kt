package com.example.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min


class CircleImageView : AppCompatImageView {
    private var clipPath: Path? = null

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    ) {
        init()
    }

    private fun init() {
        clipPath = Path()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        clipPath!!.reset()
        clipPath!!.addCircle(
            w / 2f, h / 2f,
            (min(w.toDouble(), h.toDouble()) / 2f).toFloat(), Path.Direction.CW
        )
        clipPath!!.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(clipPath!!)
        super.onDraw(canvas)
        canvas.restore()
    }
}
