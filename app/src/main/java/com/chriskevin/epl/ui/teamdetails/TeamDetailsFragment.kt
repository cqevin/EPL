package com.chriskevin.epl.ui.teamdetails

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chriskevin.epl.R
import com.chriskevin.epl.databinding.FragmentTeamDetailsBinding
import com.chriskevin.epl.util.EventObserver
import com.google.android.material.progressindicator.BaseProgressIndicator.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment(R.layout.fragment_team_details) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<TeamDetailsViewModel>()
        var favoriteStatus = false

        FragmentTeamDetailsBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            this.viewModel = viewModel
            toolbar.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.add_to_favorite -> {
                            if (favoriteStatus) viewModel.deleteFavorite()
                            else viewModel.insertFavorite()
                            true
                        }
                        else -> false
                    }
                }
            }

            viewModel.details.observe(viewLifecycleOwner, { details ->
                details.teamSquad?.let { squad ->
                    squadButton.setOnClickListener {
                        val action =
                            TeamDetailsFragmentDirections.actionTeamDetailsFragmentToSquadFragment(
                                squad.toTypedArray()
                            )
                        findNavController().navigate(action)
                    }
                }
            })

            viewModel.showContent.observe(viewLifecycleOwner, EventObserver {
                if (!it) {
                    container.visibility = GONE
                    loading.apply {
                        visibility = VISIBLE
                        // xml attribute not work (fragment-ktx-1.3.0)
                        showAnimationBehavior = SHOW_INWARD
                        hideAnimationBehavior = HIDE_OUTWARD
                        show()
                    }
                } else {
                    container.apply {
                        alpha = 0f
                        visibility = VISIBLE
                        animate().alpha(1f).setListener(null)
                    }
                    loading.hide()
                }
            })

            viewModel.favoriteStatus.observe(viewLifecycleOwner, EventObserver {
                if (it) showSnack(container, R.string.added_to_favorite)
                else showSnack(container, R.string.removed_from_favorite)
                updateToolbar(toolbar, it)
                favoriteStatus = it
            })

            viewModel.getFavoriteTeam?.observe(viewLifecycleOwner, {
                it?.let {
                    updateToolbar(toolbar, true)
                    favoriteStatus = true
                }
            })
        }
    }

    private fun updateToolbar(toolbar: Toolbar, status: Boolean) {
        val favoriteItem = toolbar.menu.findItem(R.id.add_to_favorite)
        if (status) favoriteItem.setIcon(R.drawable.ic_star_yellow)
        else favoriteItem.setIcon(R.drawable.ic_star)
    }

    private fun showSnack(view: View, @StringRes resId: Int) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT)
            .setAction(R.string.dismiss) { }
            .show()
    }
}