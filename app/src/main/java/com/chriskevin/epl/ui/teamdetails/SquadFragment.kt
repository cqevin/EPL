package com.chriskevin.epl.ui.teamdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chriskevin.epl.R
import com.chriskevin.epl.databinding.FragmentSquadBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquadFragment : Fragment(R.layout.fragment_squad) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val args by navArgs<SquadFragmentArgs>()

        FragmentSquadBinding.bind(view).apply {
            recyclerView.apply {
                adapter = SquadAdapter().apply {
                    submitList(args.squad.toList())
                }
                setHasFixedSize(true)
            }
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}