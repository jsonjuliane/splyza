package com.juliane.splyza.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.juliane.splyza.R
import com.juliane.splyza.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            binding.bInviteNow.setOnClickListener {
                findNavController().navigate(R.id.gotoInvite)
                (activity as HomeActivity).supportActionBar?.apply {
                    customView.apply {
                        findViewById<TextView>(R.id.tv_menu_back).visibility = View.VISIBLE
                        findViewById<TextView>(R.id.tv_menu_title).text = getString(R.string.label_invite_now)
                    }
                }
            }
        }
    }
}