package com.juliane.splyza.view.invite

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.juliane.splyza.R
import com.juliane.splyza.databinding.FragmentInviteBinding
import com.juliane.splyza.model.BRole
import com.juliane.splyza.model.QRLink
import com.juliane.splyza.model.Teams
import com.juliane.splyza.network.DataState
import com.juliane.splyza.view.home.HomeActivity


class InviteFragment : Fragment() {

    private lateinit var binding: FragmentInviteBinding
    private lateinit var teams: Teams
    private lateinit var qrLink: QRLink

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
                        findViewById<TextView>(R.id.tv_menu_title).text =
                            getString(R.string.app_name)
                        it.visibility = View.GONE
                        findNavController().navigate(R.id.gotoHome)
                    }
                }
            }

        }

        initObservers()
        (activity as HomeActivity).getVM().getTeams()
    }

    private fun initObservers() {
        (activity as HomeActivity).getVM().apply {
            dataState.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is DataState.Success -> {
                        updateLoaderState(false)
                        when (response.data.code()) {
                            200 -> {
                                response.data.body()?.apply {
                                    Gson().fromJson(string(), Teams::class.java)?.apply {
                                        teams = this
                                    }
                                    updateUI()
                                    setupInviteSpinner(
                                        teams.id,
                                        teams.plan.supporterLimit,
                                        teams.plan.supporterLimit - teams.members.supporters,
                                        teams.plan.memberLimit - teams.members.total
                                    )
                                }
                            }
                            else -> {
                                //TODO: Negative path not required, not mocked
                            }
                        }
                    }
                    is DataState.Loading -> {
                        updateLoaderState(true)
                    }
                    else -> {
                        updateLoaderState(false)
                            //TODO: Negative path not required, not mocked
                    }
                }
            }

            roleState.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is DataState.Success -> {
                        updateButtonLoadingState(true)
                        when (response.data.code()) {
                            200 -> {
                                response.data.body()?.apply {
                                    Gson().fromJson(string(), QRLink::class.java)?.apply { qrLink = this }
                                }
                            }
                            else -> {
                                //TODO: Negative path not required, not mocked
                            }
                        }
                    }
                    is DataState.Loading -> {
                        updateButtonLoadingState(false)
                    }
                    else -> {
                        updateButtonLoadingState(true)
                        //TODO: Negative path not required, not mocked
                    }
                }
            }
        }

        binding.apply {
            bCopyLink.setOnClickListener {
                val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText(
                    "label", when {
                        ::qrLink.isInitialized -> qrLink.url
                        else -> ""
                    }
                )
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(requireContext(), "Text Copied!", Toast.LENGTH_SHORT).show()
            }

            bShareQr.setOnClickListener {
                if(::qrLink.isInitialized) {
                    findNavController().navigate(
                        R.id.gotoQR,
                        bundleOf(getString(R.string.label_qr_link) to qrLink.url)
                    )
                    (activity as HomeActivity).supportActionBar?.apply {
                        customView.apply {
                            findViewById<TextView>(R.id.tv_menu_back).visibility = View.VISIBLE
                            findViewById<TextView>(R.id.tv_menu_title).text =
                                context.getString(R.string.label_my_qr_code)
                        }
                    }
                }else Toast.makeText(requireContext(), "QR Code not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI() {
        if (::teams.isInitialized) {
            binding.apply {
                if (teams.plan.supporterLimit < 1) {
                    binding.groupSupporters.visibility = View.GONE
                }
                tvCurrentMembersVal.text = teams.members.total.toString()
                tvCurrentSupportersVal.text = teams.members.supporters.toString()
                tvMembersLimitVal.text = teams.plan.memberLimit.toString()
                tvSupportersLimitVal.text = teams.plan.supporterLimit.toString()
            }

        }
    }

    private fun setupInviteSpinner(id: String, supporterLimit: Int, availSupporterSlot: Int, availMemberSlot: Int) {
        if (availSupporterSlot < 1 && availMemberSlot < 1 || availMemberSlot < 1 && supporterLimit < 1) {
            binding.apply {
                tvInvitePermissions.text = getString(R.string.label_invite_disabled)
                sInvitePermissions.visibility = View.GONE
            }
            return
        }

        val arrayAdapter: ArrayAdapter<String> = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            when (supporterLimit) {
                0 -> {
                    resources.getStringArray(R.array.invite_permissions_no_sup).asList()
                }
                else -> {
                    resources.getStringArray(R.array.invite_permissions).asList()
                }
            }
        ) {
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView

                if (availSupporterSlot < 1) {
                    if (position > 2) {
                        view.setTextColor(Color.LTGRAY)
                    }
                }

                if (availMemberSlot < 1) {
                    if (position < 3)
                        view.setTextColor(Color.LTGRAY)
                }

                if (position == binding.sInvitePermissions.selectedItemPosition) {
                    view.background = ColorDrawable(Color.parseColor("#F5F5F5"))
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                if (availMemberSlot < 1) {
                    if (position < 3)
                        return false
                }

                if (availSupporterSlot < 1) {
                    if (position > 2) {
                        return false
                    }
                }

                return true
            }
        }

        binding.sInvitePermissions.apply {
            adapter = arrayAdapter
            when {
                availSupporterSlot < 1 -> {
                    setSelection(0)
                }
                availMemberSlot < 1 -> {
                    setSelection(3)
                }
            }
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    (activity as HomeActivity).getVM().updateRole(id, BRole(resources.getStringArray(R.array.role_choice)[position]!!))
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }
    }

    private fun updateLoaderState(enabled: Boolean) {
        if (enabled) {
            Glide.with(requireContext())
                .load(R.drawable.gif_loading)
                .into(binding.ivLoading)


            binding.flLoading.visibility = View.VISIBLE
        } else {
            Glide.with(requireContext()).clear(binding.ivLoading)
            binding.flLoading.visibility = View.GONE
        }
    }

    private fun updateButtonLoadingState(enabled: Boolean) {
        binding.apply {
            bShareQr.isEnabled = enabled
            bCopyLink.isEnabled = enabled

            if (enabled) {
                bShareQr.alpha = 1.0f
                bCopyLink.alpha = 1.0f
            } else {
                bShareQr.alpha = 0.5f
                bCopyLink.alpha = 0.5f
            }
        }
    }
}