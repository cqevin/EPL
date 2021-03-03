package com.chriskevin.epl.ui.scorers

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chriskevin.epl.R
import com.chriskevin.epl.databinding.FragmentScorersBinding
import com.chriskevin.epl.ui.ThemeDialogFragment
import com.chriskevin.epl.util.AdapterListener
import com.chriskevin.epl.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScorersFragment : Fragment(R.layout.fragment_scorers), Toolbar.OnMenuItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<ScorersViewModel>()

        val scorersAdapter = ScorersAdapter(AdapterListener {
            val action = ScorersFragmentDirections.actionScorersFragmentToTeamDetails(it)
            findNavController().navigate(action)
        })

        FragmentScorersBinding.bind(view).apply {
            toolbar.setOnMenuItemClickListener(this@ScorersFragment)

            recyclerView.apply {
                adapter = scorersAdapter
                setHasFixedSize(true)
            }

            viewModel.listItem.observe(viewLifecycleOwner, {
                if (!it.isNullOrEmpty()) {
                    noDataText.visibility = View.GONE
                    scorersAdapter.submitList(it)
                } else {
                    noDataText.visibility = View.VISIBLE
                }
            })

            viewModel.error.observe(viewLifecycleOwner, EventObserver {
                Snackbar.make(coordinator, R.string.check_connection, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.dismiss) { }
                    .show()
            })
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.dark_mode) {
            ThemeDialogFragment().show(childFragmentManager, ThemeDialogFragment.TAG)
            true
        } else false
    }
}