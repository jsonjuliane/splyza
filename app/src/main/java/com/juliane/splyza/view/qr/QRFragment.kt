package com.juliane.splyza.view.qr

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.juliane.splyza.R
import com.juliane.splyza.databinding.FragmentQRBinding
import com.juliane.splyza.view.home.HomeActivity


class QRFragment : Fragment() {
    private lateinit var binding: FragmentQRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQRBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (::binding.isInitialized) {
            (activity as HomeActivity).supportActionBar?.apply {
                customView.apply {
                    findViewById<TextView>(R.id.tv_menu_back).setOnClickListener {
                        findViewById<TextView>(R.id.tv_menu_title).text =
                            context.getString(R.string.label_invite_now)
                        findNavController().navigate(R.id.gotoInvite)
                    }
                }
            }
        }

        arguments?.getString(getString(R.string.label_qr_link))?.apply {
            binding.ivQrCode.setImageBitmap(getQrCodeBitmap(this))
        }
    }

    private fun getQrCodeBitmap(content: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}