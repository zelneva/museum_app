package dev.android.museum.adapter.selection

import android.support.v7.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.selection.SelectionTracker
import dev.android.museum.R
import dev.android.museum.presenters.common.ShowpieceSelectorPresenter

class ActionModeController(private val tracker: SelectionTracker<*>,
                           val presneter: ShowpieceSelectorPresenter,
                           var list: List<String>, var id: String) : ActionMode.Callback {

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        mode.menuInflater.inflate(R.menu.action_menu, menu)
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        tracker.clearSelection()
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = true

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_clear -> {
            mode.finish()
            true
        }

        R.id.action_select -> {
            presneter.addShowpieces(list, id)
            mode.finish()
            true
        }
        else -> false
    }


}