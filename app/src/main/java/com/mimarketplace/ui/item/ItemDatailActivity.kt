package com.mimarketplace.ui.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mimarketplace.R
import com.mimarketplace.databinding.ActivityItemDatailBinding
import com.mimarketplace.domain.item.usecases.GetItemsByIdUseCase
import com.mimarketplace.ui.item.adapters.ItemImageViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ItemDatailActivity : AppCompatActivity() {

    var binding: ActivityItemDatailBinding?  = null

    @Inject
    lateinit var getItemsByIdUseCase: GetItemsByIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_datail)

        val itemId = intent.getStringExtra("itemId")
        subscribeUi(this, itemId)
    }

    private fun subscribeUi(context: AppCompatActivity, itemId: String?) {
        GlobalScope.launch(Dispatchers.IO) { // launch a new coroutine in background and continue
            try {
                val item = getItemsByIdUseCase.invoke(itemId!!)

                GlobalScope.launch(Dispatchers.Main) {
                    binding?.item = item

                    // Initializing the ViewPagerAdapter
                    val mViewPagerAdapter = ItemImageViewPagerAdapter(context, item.pictures)

                    // Adding the Adapter to the ViewPager
                    binding?.imageViewPager?.adapter = mViewPagerAdapter
                }
            } catch (e : Error) {
                println("--Error: " + e.message)
            }

        }
    }
}