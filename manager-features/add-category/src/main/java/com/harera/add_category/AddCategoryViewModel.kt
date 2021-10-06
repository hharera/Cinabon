package com.harera.add_category

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Tasks
import com.harera.common.utils.Validity
import com.harera.managehyper.ui.add_category.CategoryFormState
import com.harera.model.modelset.Category
import com.harera.repository.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _categoryName: MutableLiveData<String> = MutableLiveData()
    val categoryName: LiveData<String> = _categoryName

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _imageUrl: MutableLiveData<String> = MutableLiveData()
    val imageUrl: LiveData<String> = _imageUrl

    private val _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _operationCompleted: MutableLiveData<Boolean> = MutableLiveData()
    val operationCompleted: LiveData<Boolean> = _operationCompleted

    private val _formState: MutableLiveData<CategoryFormState> = MutableLiveData()
    val formState: LiveData<CategoryFormState> = _formState

    private val _category: MutableLiveData<Category> = MutableLiveData()
    val category: LiveData<Category> = _category

    fun setCategoryName(name: String) {
        _categoryName.value = name
        checkFormValidity()
    }

    fun setImage(imageBitmap: Bitmap) {
        _image.value = imageBitmap
        checkFormValidity()
    }

    private fun checkFormValidity() {
        if (categoryName.value == null) {
            _formState.value =
                CategoryFormState(categoryNameError = R.string.empty_category_name_error)
        } else if (!Validity.checkCategoryName(categoryName.value!!)) {
            _formState.value = CategoryFormState(categoryNameError = R.string.wrong_category_name)
        } else if (image.value == null) {
            _formState.value =
                CategoryFormState(categoryImageError = R.string.empty_image_error)
        } else {
            _formState.value =
                CategoryFormState(isValid = true)
        }
    }

    fun submitForm() {
        viewModelScope.launch (Dispatchers.IO) {
            if(uploadCategoryImage()) {
                if (encapsulateForm()) {
                    addCategory(category = category.value!!)
                }
            }
        }
    }

    private fun addCategory(category: Category) {
        _loading.value = true
        categoryRepository.addCategory(
            category = category
        ).addOnSuccessListener {
            _operationCompleted.value = true
            _loading.value = false
        }.addOnFailureListener {
            _error.value = it
            _loading.value = false
            _operationCompleted.value = false
        }
    }

    private suspend fun encapsulateForm(): Boolean {
        _loading.value = true

        return viewModelScope.async(Dispatchers.IO) {
            _category.value = Category(
                categoryName = categoryName.value!!,
                categoryImagerUrl = imageUrl.value!!,
            )
            true
        }.await()
    }

    private suspend fun uploadCategoryImage(): Boolean {
        return viewModelScope.async(Dispatchers.IO) {
            val uploadTask = categoryRepository.uploadCategoryImage(
                categoryName = categoryName.value!!,
                imageBitmap = image.value!!,
            )
            val result = Tasks.await(uploadTask)

            val urlTask = result.storage.downloadUrl
            val urlResult = Tasks.await(urlTask)
            _imageUrl.value = urlResult.toString()

            uploadTask.isSuccessful
        }.await()
    }
}