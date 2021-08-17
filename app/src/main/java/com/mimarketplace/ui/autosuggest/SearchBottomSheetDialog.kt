package com.mimarketplace.ui.autosuggest

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.InflateException
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mimarketplace.R
import com.mimarketplace.domain.autosuggest.models.Suggest
import com.mimarketplace.ui.autosuggest.fragments.AutoSuggestListFragment
import java.util.*


class SearchBottomSheetDialog (private val onSelectListener: (String) -> Unit) : BottomSheetDialogFragment() {

    private var q: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog =  super.onCreateDialog(savedInstanceState)
        try {
            val newView = View.inflate(context, R.layout.bottom_sheet_search_layout, null)

            val linearLayout = newView.findViewById<LinearLayout>(R.id.root)
            val params = linearLayout?.layoutParams as LinearLayout.LayoutParams
            params.height = getScreenHeight()
            linearLayout.layoutParams = params

            val searchEditText = newView?.findViewById<EditText>(R.id.search_edit_text)
            searchEditText?.addTextChangedListener(textWatcher)

            searchEditText?.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    onSelectListener(q)
                    dismiss()
                    return@OnEditorActionListener true
                }
                false
            })

            dialog.setContentView(newView!!)
            val mBehavior = BottomSheetBehavior.from(newView?.parent as View)
            mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED

            searchEditText?.requestFocus()
            searchEditText?.showSoftKeyboard()

            val autoSuggestFragment = parentFragmentManager?.findFragmentById(R.id.auto_sugges_fragment) as AutoSuggestListFragment
            onSearch = autoSuggestFragment.onSearch
            autoSuggestFragment.onItemClick = onItemClick
        } catch (e: InflateException) {
            /* map is already there, just return view as it is */
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        //mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    lateinit var onSearch: (String) -> Unit

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int,
            count: Int
        ) {
            /* do nothing */
        }

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            /* do nothing */
        }

        override fun afterTextChanged(s: Editable) {
            q = s.toString().lowercase()
            onSearch(q)
        }
    }

    private val onItemClick: (Suggest) -> Unit = {
        onSelectListener(it.q)
        dismiss()

    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            parentFragmentManager.fragments.forEach {
                if (it is AutoSuggestListFragment)
                parentFragmentManager?.beginTransaction().remove(it).commit()
            }
        } catch (e: Error) {
            /* scape error */
        }

    }
}


// extension function to open soft keyboard programmatically
fun EditText.showSoftKeyboard(){
    (this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        //.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}