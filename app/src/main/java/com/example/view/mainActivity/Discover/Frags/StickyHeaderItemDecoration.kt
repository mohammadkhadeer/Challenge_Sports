package com.example.view.mainActivity.Discover.Frags

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.view.mainActivity.Discover.Frags.adapters.ProfileViewAdapter
class StickyHeaderItemDecoration(
    private val adapter: ProfileViewAdapter,
    private val layoutManager: GridLayoutManager
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) return

        val currentHeaderPosition = findHeaderPositionForItem(topChildPosition)
        if (currentHeaderPosition == RecyclerView.NO_POSITION) return

        val currentHeaderView = getHeaderViewForItem(currentHeaderPosition, parent) ?: return
        fixLayoutSize(parent, currentHeaderView)

        val contactPoint = currentHeaderView.bottom
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        if (isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, currentHeaderView, childInContact)
        } else {
            drawHeader(c, currentHeaderView)
        }
    }

    private fun getHeaderViewForItem(headerPosition: Int, parent: RecyclerView): View? {
        val headerViewHolder = adapter.createViewHolder(parent, adapter.getItemViewType(headerPosition))
        adapter.bindViewHolder(headerViewHolder, headerPosition)
        return headerViewHolder.itemView
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint && child.top <= contactPoint) {
                return child
            }
        }
        return null
    }

    private fun findHeaderPositionForItem(itemPosition: Int): Int {
        for (position in itemPosition downTo 0) {
            if (isHeader(position)) {
                return position
            }
        }
        return RecyclerView.NO_POSITION
    }

    private fun isHeader(position: Int): Boolean {
        return adapter.getItemViewType(position) == ProfileViewAdapter.TYPE_HEADER
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeight = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
