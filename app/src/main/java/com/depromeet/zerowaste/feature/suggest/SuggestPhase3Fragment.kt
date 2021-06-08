package com.depromeet.zerowaste.feature.suggest

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.BaseFragment
import com.depromeet.zerowaste.databinding.FragmentMissionSuggestPhase3Binding
import com.depromeet.zerowaste.feature.mission.certificate.MissionCertFragment
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType

class SuggestPhase3Fragment: BaseFragment<FragmentMissionSuggestPhase3Binding>(R.layout.fragment_mission_suggest_phase3) {

    private val viewModel: SuggestViewModel by viewModels({requireParentFragment()})

    override fun init() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.missionSuggestPhase3Edit.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.missionSuggestPhase3Edit.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.missionSuggestPhase3Edit.setOnEditorActionListener { v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else false
        }
        binding.missionSuggestPhase3Edit.addTextChangedListener(object: TextWatcher {
            var preString = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                preString = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if(binding.missionSuggestPhase3Edit.lineCount > 3) {
                        binding.missionSuggestPhase3Edit.setText(preString)
                        binding.missionSuggestPhase3Edit.setSelection(binding.missionSuggestPhase3Edit.length())
                        viewModel.setContents(preString)
                    } else {
                        viewModel.setContents(it.toString())
                    }
                }
            }
        })

        binding.missionSuggestPhase3Photo.setOnClickListener {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> getPhotos()
                else -> {
                    permissionCheck(Manifest.permission.READ_EXTERNAL_STORAGE) {
                        if(it) { getPhotos() }
                    }
                }
            }
        }
    }

    private fun getPhotos() {
        TedImagePicker.with(requireContext())
            .mediaType(MediaType.IMAGE)
            .buttonBackground(R.drawable.selector_button)
            .buttonTextColor(R.color.white)
            .backButton(R.drawable.ic_back)
            .errorListener {
                showToast(it.message)
            }
            .start {
                viewModel.setImgUri(it)
            }
    }
}