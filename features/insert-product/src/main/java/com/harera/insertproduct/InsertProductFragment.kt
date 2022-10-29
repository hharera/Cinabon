package com.harera.insertproduct

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.harera.common.afterTextChanged
import com.harera.common.base.BaseFragment
import com.harera.common.utils.BitmapUtils
import com.harera.insertproduct.databinding.FragmentInsertProductBinding
import com.opensooq.supernova.gligar.GligarPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InsertProductFragment : BaseFragment() {
    companion object {
        private val IMAGE_REQ_CODE = 3004
    }

    private lateinit var bind: FragmentInsertProductBinding
    private val insertProductViewModel: InsertProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentInsertProductBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        insertProductViewModel.image.observe(viewLifecycleOwner) {
            bind.image.setImageBitmap(it)
        }

        bind.name.setText(
            insertProductViewModel.categoryName.value
        )

        insertProductViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(state = it)
        }

        insertProductViewModel.error.observe(viewLifecycleOwner) {
            handleError(exception = it)
        }

        insertProductViewModel.operationCompleted.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        insertProductViewModel.formState.observe(viewLifecycleOwner) {
//            bind.add.isEnabled = it.isValid

            if (it.categoryNameError != null) {
                bind.name.error = getString(it.categoryNameError)
            } else if (it.categoryImageError != null) {
                Toast.makeText(
                    context,
                    getString(it.categoryImageError),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupListeners() {
        bind.name.afterTextChanged {
            insertProductViewModel.setCategoryName(it)
        }

        bind.image.setOnClickListener {
            onImageClicked()
        }

//        bind.add.setOnClickListener {
//            bind.add.isEnabled = false
//            addCategoryViewModel.submitForm()
//        }
    }

    private fun onImageClicked() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .withFragment(this)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = BitmapUtils.convertImagePathToBitmap(data)
            imageBitmap?.let {
                insertProductViewModel.setImage(it)
            }
        }
    }
}