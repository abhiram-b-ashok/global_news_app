package com.example.newsapplication.ui.info_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.newsapplication.databinding.FragmentDialogInfoBinding


class InfoDialogFragment(
    val message: String
):DialogFragment() {

    private lateinit var binding: FragmentDialogInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogInfoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        initViews()
    }

    private fun initViews() = binding.apply {
        textInfo.text = message
        btnOk.setOnClickListener {
            dismiss()
        }
    }
}

 fun Fragment.showInfoDialog(msg: String){
    val dialog = InfoDialogFragment(msg)
    dialog.show(childFragmentManager,"InfoDialogFragment")
}