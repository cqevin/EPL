package com.chriskevin.epl.ui.standings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chriskevin.epl.R
import com.chriskevin.epl.databinding.FragmentStandingsBinding
import com.chriskevin.epl.ui.ThemeDialogFragment
import com.chriskevin.epl.util.AdapterListener
import com.chriskevin.epl.util.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StandingsFragment : Fragment(R.layout.fragment_standings), Toolbar.OnMenuItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<StandingsViewModel>()

        val standingsAdapter = StandingsAdapter(AdapterListener { teamId ->
            val action = StandingsFragmentDirections.actionStandingsFragmentToTeamDetails(teamId)
            findNavController().navigate(action)
        })

        FragmentStandingsBinding.bind(view).apply {
            toolbar.setOnMenuItemClickListener(this@StandingsFragment)
            recyclerView.apply {
                setHasFixedSize(true)
                adapter = standingsAdapter
            }
            viewModel.listItem.observe(viewLifecycleOwner, {
                if (!it.isNullOrEmpty()) {
                    noDataText.visibility = View.GONE
                    standingsAdapter.addHeaderAndSubmitList(it)
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