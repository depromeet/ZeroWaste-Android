package com.depromeet.zerowaste.feature.mission.certificate

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.*
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.databinding.FragmentMissionCertBinding
import com.depromeet.zerowaste.databinding.ItemMissionCertImgBinding
import com.depromeet.zerowaste.databinding.ItemMissionCertLevelBinding
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import java.text.SimpleDateFormat
import java.util.*

class MissionCertFragment: BaseFragment<FragmentMissionCertBinding>(R.layout.fragment_mission_cert) {

    companion object {
        private var certUriList: List<Uri> = ArrayList()

        fun startMissionCert(fragment: BaseFragment<*>, navDirections: NavDirections) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    fragment.requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) -> getPhotos(fragment, navDirections)
                else -> {
                    fragment.permissionCheck(Manifest.permission.READ_EXTERNAL_STORAGE) {
                        if(it) {
                            getPhotos(fragment, navDirections)
                        }
                    }
                }
            }
        }

        private fun getPhotos(fragment: BaseFragment<*>, navDirections: NavDirections) {
            TedImagePicker.with(fragment.requireContext())
                .mediaType(MediaType.IMAGE)
                .max(3, fragment.getString(R.string.mission_cert_max_img, "3"))
                .buttonBackground(R.drawable.selector_button)
                .buttonTextColor(R.color.white)
                .backButton(R.drawable.ic_back)
                .errorListener {
                    fragment.showToast(it.message)
                }
                .startMultiImage {
                    certUriList = it
                    fragment.findNavController().navigate(navDirections)
                }
        }
    }

    private val viewModel: MissionCertViewModel by viewModels()

    private val imageListAdapter =  BaseRecycleAdapter(R.layout.item_mission_cert_img) { item: Uri, bind: ItemMissionCertImgBinding, _ -> bind.uri = item }
    private val levelAdapter = BaseRecycleAdapter(R.layout.item_mission_cert_level)
    { item: LevelItem, bind: ItemMissionCertLevelBinding, position: Int ->
        loadTxtMissionDifficulty(bind.itemMissionCertLevelTxt, item.difficulty)
        if(item.selected) {
            loadImageMissionDifficulty(bind.itemMissionCertLevelImg, item.difficulty)
            bind.itemMissionCertLevelTxt.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        } else {
            loadImageMissionDifficultyOff(bind.itemMissionCertLevelImg, item.difficulty)
            bind.itemMissionCertLevelTxt.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_2, null))
        }
        bind.root.setOnClickListener { levelClick(position) }
    }

    override fun init() {
        binding.fragment = this
        initImgList()
        initTitle()
        initLevel()
        initNextCheck()
    }

    private fun initTitle() {
        val nowDate = Date()
        val year = resources.getString(R.string.year)
        val month = resources.getString(R.string.month)
        val date = resources.getString(R.string.date)
        val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy${year}MM${month}dd${date}", resources.configuration.locales[0])
        } else {
            SimpleDateFormat("yyyy${year}MM${month}dd${date}", resources.configuration.locale)
        }
        binding.missionCertTitle.text = format.format(nowDate)
    }

    private fun initImgList() {
        binding.missionCertImgs.offscreenPageLimit = 3

        val pageMarginPx = dpToPx(requireContext(), 9F)
        val pagerWidth = resources.displayMetrics.widthPixels - dpToPx(requireContext(), 36F)
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.missionCertImgs.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        imageListAdapter.setData(certUriList)
        binding.missionCertImgs.adapter = imageListAdapter
        binding.missionCertIndicator.setViewPager2(binding.missionCertImgs)
    }

    private fun initLevel() {
        val levels = ArrayList<LevelItem>()
        Difficulty.values().reversedArray().forEach {
            levels.add(LevelItem(it, false))
        }
        binding.missionCertLevelList.layoutManager = genLayoutManager(requireContext(), spanCount = Difficulty.values().size)
        levelAdapter.setData(levels)
        binding.missionCertLevelList.adapter = levelAdapter
    }

    private fun initNextCheck() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.missionCertAfterEdit.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.missionCertAfterEdit.setRawInputType(InputType.TYPE_CLASS_TEXT)
        binding.missionCertAfterEdit.setOnEditorActionListener { v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                true
            } else false
        }
        binding.missionCertAfterEdit.addTextChangedListener(object: TextWatcher {
            var preString = ""
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                preString = s.toString()
            }
            override fun afterTextChanged(s: Editable?) {
                s?.also {
                    if(binding.missionCertAfterEdit.lineCount > 3) {
                        binding.missionCertAfterEdit.setText(preString)
                        binding.missionCertAfterEdit.setSelection(binding.missionCertAfterEdit.length())
                        viewModel.editTxt(preString)
                    } else {
                        viewModel.editTxt(it.toString())
                    }
                }
            }
        })

        viewModel.selectedDifficulty.observe(this) {
            if(viewModel.editedTxt.value.isNullOrEmpty()) {
                binding.missionCertNext.isEnabled = false
                binding.missionCertNext.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_2, null))
            } else {
                binding.missionCertNext.isEnabled = true
                binding.missionCertNext.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            }
        }
        viewModel.editedTxt.observe(this) {
            if(viewModel.selectedDifficulty.value == null || it.isEmpty()) {
                binding.missionCertNext.isEnabled = false
                binding.missionCertNext.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_2, null))
            } else {
                binding.missionCertNext.isEnabled = true
                binding.missionCertNext.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            }
        }
    }

    private fun levelClick(position: Int) {
        levelAdapter.getItems().forEachIndexed { i, item ->
            item.selected = if(i == position) {
                viewModel.selectDifficulty(item.difficulty)
                true
            } else false
        }
        levelAdapter.notifyDataSetChanged()
    }

    fun nextClick() {
        findNavController().navigate(MissionCertFragmentDirections.actionMissionCertFragmentToMissionDoneFragment())
    }

    fun backClick() {
        findNavController().popBackStack()
    }

    private data class LevelItem (val difficulty: Difficulty, var selected: Boolean)

}