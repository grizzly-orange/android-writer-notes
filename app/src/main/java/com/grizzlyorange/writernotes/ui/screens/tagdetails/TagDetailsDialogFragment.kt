package com.grizzlyorange.writernotes.ui.screens.tagdetails

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentTagDetailsBinding

class TagDetailsDialogFragment : DialogFragment() {

    private val tagDetailsVM: TagDetailsViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val binding = FragmentTagDetailsBinding.inflate(
                requireActivity().layoutInflater
            )

            binding.tagDetailsVM = tagDetailsVM
            binding.lifecycleOwner = this

            val builder = AlertDialog.Builder(it)
                .setView(binding.root)
                .setPositiveButton(R.string.buttonSaveTag, null)

            val dialog = builder.create()

            dialog.setOnShowListener {
                val btSave = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                btSave.setOnClickListener {
                    val tagSaved = tagDetailsVM.saveCurrentTag()
                    if (tagSaved) {
                        dialog.dismiss()
                    }
                }
            }

            dialog

        } ?: throw IllegalStateException("Activity cannot be null")
    }
}