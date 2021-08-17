package com.mimarketplace.ui


import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mimarketplace.R
import com.mimarketplace.domain.autosuggest.usecases.GetSuggestUseCase
import com.mimarketplace.domain.category.models.MainCategory
import com.mimarketplace.domain.category.usecases.GetCategoriesUseCase
import com.mimarketplace.domain.item.models.Item
import com.mimarketplace.ui.autosuggest.SearchBottomSheetDialog
import com.mimarketplace.ui.category.fragments.CategoryFragment
import com.mimarketplace.ui.item.ItemDatailActivity
import com.mimarketplace.ui.item.fragments.ItemListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<RelativeLayout>(R.id.search_bar)
        searchBar.setOnClickListener {
            val bottomSheet = SearchBottomSheetDialog(onSearch)
            bottomSheet.show(supportFragmentManager, "bottomSheet")
        }

        supportFragmentManager.beginTransaction().replace(R.id.frame, CategoryFragment.newInstance(onCategoryClick)).commit()
    }

    private val onItemClick: (Item) -> Unit = {
        val intent = Intent(this, ItemDatailActivity::class.java).apply {
            putExtra("itemId", it.id)
        }
        startActivity(intent)
    }

    private val onCategoryClick: (MainCategory) -> Unit = {
        supportFragmentManager.beginTransaction().replace(R.id.frame, ItemListFragment.newInstance ("CATEGORY", it.id, onItemClick)).commit()
    }

    private val onSearch: (String) -> Unit = {
        supportFragmentManager.beginTransaction().replace(R.id.frame, ItemListFragment.newInstance ("TEXT", it, onItemClick)).commit()
    }
}