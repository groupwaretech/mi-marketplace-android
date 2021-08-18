package com.mimarketplace.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.mimarketplace.R
import com.mimarketplace.domain.category.models.MainCategory
import com.mimarketplace.domain.item.models.Item
import com.mimarketplace.ui.autosuggest.SearchBottomSheetDialog
import com.mimarketplace.ui.category.fragments.CategoryFragment
import com.mimarketplace.ui.item.ItemDatailActivity
import com.mimarketplace.ui.item.fragments.ItemListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var searchTextView: TextView? = null
    private var quitSearchImageButton: AppCompatImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBar = findViewById<ConstraintLayout>(R.id.search_bar)
        searchBar.setOnClickListener {
            val bottomSheet = SearchBottomSheetDialog(onSearch)
            bottomSheet.q = searchTextView?.text.toString()
            bottomSheet.show(supportFragmentManager, "bottomSheet")
        }

        quitSearchImageButton = findViewById<AppCompatImageButton>(R.id.quit_search_ImageButton)
        quitSearchImageButton?.setOnClickListener {
            searchTextView?.text = ""
            quitSearchImageButton?.visibility = View.GONE
            toHome()
        }

        searchTextView = findViewById<TextView>(R.id.search_text_TextView)

        toHome()
    }

    private fun toHome() {
        supportFragmentManager.beginTransaction().replace(R.id.frame, CategoryFragment.newInstance(onCategoryClick)).commit()
    }

    private val onItemClick: (Item) -> Unit = {
        val intent = Intent(this, ItemDatailActivity::class.java).apply {
            putExtra("itemId", it.id)
        }
        startActivity(intent)
    }

    private val onCategoryClick: (MainCategory) -> Unit = {
        searchTextView?.text = it.name
        quitSearchImageButton?.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.frame, ItemListFragment.newInstance ("CATEGORY", it.id, onItemClick)).commit()
    }

    private val onSearch: (String) -> Unit = {
        searchTextView?.text = it
        quitSearchImageButton?.visibility = View.VISIBLE
        supportFragmentManager.beginTransaction().replace(R.id.frame, ItemListFragment.newInstance ("TEXT", it, onItemClick)).commit()
    }
}

