package com.juliane.splyza.view.invite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.juliane.splyza.R
import com.juliane.splyza.databinding.FragmentInviteBinding
import com.juliane.splyza.model.Teams
import com.juliane.splyza.network.DataState
import com.juliane.splyza.view.home.HomeActivity

class InviteFragment : Fragment() {

    private lateinit var binding: FragmentInviteBinding
    private lateinit var teams: Teams

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInviteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            (activity as HomeActivity).supportActionBar?.apply {
                customView.apply {
                    findViewById<TextView>(R.id.tv_menu_back).setOnClickListener {
                        findViewById<TextView>(R.id.tv_menu_title).text = getString(R.string.app_name)
                        it.visibility = View.GONE
                        findNavController().navigate(R.id.gotoHome)
                    }
                }
            }
        }

        getInitialData()
    }

    private fun getInitialData() {
        (activity as HomeActivity).getVM().apply {
            dataState.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is DataState.Success -> {
                        when (response.data.code()) {
                            200 -> {
                                response.data.body()?.apply {
                                    teams = Gson().fromJson(string(), Teams::class.java)
                                    updateUI()
                                }
                            }
                            else -> {

                            }
                        }
                    }
                    is DataState.Loading -> {

                    }
                    else -> {

                    }
                }
            }
            getTeams()
        }
    }

    private fun updateUI() {
        if (::teams.isInitialized) {
//            if(teams.plan.supporterLimit < 1){
//            }
        }
    }
}