package com.chriskevin.epl.favorite

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chriskevin.epl.di.FavoriteModuleDependency
import com.chriskevin.epl.favorite.databinding.FragmentFavoriteBinding
import com.chriskevin.epl.ui.ThemeDialogFragment
import com.chriskevin.epl.util.AdapterListener
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependency::class.java
                )
            )
            .build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by viewModels<FavoriteViewModel> { factory }

        FragmentFavoriteBinding.bind(view).apply {
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == com.chriskevin.epl.R.id.dark_mode) {
                    ThemeDialogFragment().show(childFragmentManager, ThemeDialogFragment.TAG)
                    true
                } else false
            }

            val favoriteAdapter = FavoriteAdapter(AdapterListener {
                val action = FavoriteFragmentDirections.actionFavoriteFragmentToTeamDetails(it)
                findNavController().navigate(action)
            })

            recyclerView.apply {
                adapter = favoriteAdapter
                setHasFixedSize(true)
            }

            viewModel.listItem.observe(viewLifecycleOwner, {
                if(!it.isNullOrEmpty()) emptyText.visibility = View.GONE
                else emptyText.visibility = View.VISIBLE
                favoriteAdapter.submitList(it)
            })
        }
    }
}