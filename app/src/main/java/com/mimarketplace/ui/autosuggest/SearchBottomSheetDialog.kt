package com.mimarketplace.ui.autosuggest

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.InflateException
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mimarketplace.R
import com.mimarketplace.domain.autosuggest.models.Suggest
import com.mimarketplace.ui.autosuggest.fragments.AutoSuggestListFragment
import java.util.*


class SearchBottomSheetDialog (private val onSelectListener: (String) -> Unit) : BottomSheetDialogFragment() {

    var q: String = ""

    private var cleanImageButton:AppCompatImageButton? = null
    private var searchEditText: EditText? = null

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

            //Establecer el componente (Fragment) que lista los resultados de la sugerencia
            val autoSuggestFragment = parentFragmentManager?.findFragmentById(R.id.auto_sugges_fragment) as AutoSuggestListFragment
            onSearch = autoSuggestFragment.onSearch
            autoSuggestFragment.onItemClick = onItemClick

            searchEditText = newView?.findViewById<EditText>(R.id.search_edit_text)
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
            searchEditText?.setText(q)
            searchEditText?.showSoftKeyboard()

            val backImageButton = newView?.findViewById<AppCompatImageButton>(R.id.back_ImageButton)
            backImageButton.setOnClickListener {
                dismiss()
            }

            cleanImageButton = newView?.findViewById<AppCompatImageButton>(R.id.clean_ImageButton)
            cleanImageButton?.setOnClickListener {
                searchEditText?.setText("")
            }
            if (q.isNotEmpty()) cleanImageButton?.visibility = View.VISIBLE
        } catch (e: InflateException) {
            /* map is already there, just return view as it is */
        }

        return dialog
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
            if (q.isEmpty()) {
                cleanImageButton?.visibility = View.GONE
            } else {
                cleanImageButton?.visibility = View.VISIBLE
            }
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
        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}