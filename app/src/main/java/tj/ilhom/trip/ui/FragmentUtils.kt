package tj.ilhom.trip.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest

suspend fun <T : Any, VH : RecyclerView.ViewHolder> PagingDataAdapter<T, VH>.collectLoadStates(
    centerProgress: ProgressBar?, pagingProgressBar: ProgressBar?, onError: (Snackbar?) -> Unit
) {
    loadStateFlow.collectLatest { states ->
        pagingProgressBar?.isVisible =
            states.append is LoadState.Loading && centerProgress?.isVisible == false
        if (!this.snapshot().isNullOrEmpty()) {
            centerProgress?.isVisible = false
        }
        when {
            states.append is LoadState.Error || states.refresh is LoadState.Error ->
                onError(noInternetSnackBar(pagingProgressBar) { this.retry() })
        }
    }
}

fun EditText.onSearch(callback: () -> Context) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val context = callback.invoke()
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(this.windowToken, 0)
            return@setOnEditorActionListener true
        }
        false
    }
}

fun noInternetSnackBar(view: View?, action: (View) -> Unit): Snackbar? {
    return view?.let { v ->
        Snackbar.make(v, "Нет подключения к интернету", Snackbar.LENGTH_INDEFINITE)
            .setAction("Повторить", action)
            .also { it.show() }
    }
}