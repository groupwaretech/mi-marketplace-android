package com.mimarketplace.ui.item.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.mimarketplace.R
import com.mimarketplace.domain.item.models.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*


class ItemImageViewPagerAdapter(private val context: Context, private val images: List<Picture>) : PagerAdapter() {

    // Layout Inflater
    var mLayoutInflater: LayoutInflater? = null

    init {
        mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return images.count()
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // inflating the item.xml
        val itemView: View = mLayoutInflater!!.inflate(R.layout.pager_adapter_item_image_view, container, false)

        // referencing the image view from the item.xml file
        val imageView = itemView.findViewById<AppCompatImageView>(R.id.item_AdapterImageView)
        val pageTextView = itemView.findViewById<AppCompatTextView>(R.id.page_TextView)
        pageTextView.text = "${position + 1}/${images.size}"

        // setting the image in the imageView
        GlobalScope.launch(Dispatchers.IO) {
            try {

                val url = URL(images[position].secure_url)
                val bmp: Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(bmp)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Adding the View
        Objects.requireNonNull(container).addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}