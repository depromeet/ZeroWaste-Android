package com.depromeet.zerowaste.feature.mission.certificate

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depromeet.zerowaste.R
import com.depromeet.zerowaste.comm.*
import com.depromeet.zerowaste.data.Difficulty
import com.depromeet.zerowaste.databinding.FragmentMissionCertBinding
import com.depromeet.zerowaste.databinding.ItemCertImgBinding
import com.depromeet.zerowaste.databinding.ItemCertLevelBinding
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import java.text.SimpleDateFormat
import java.util.*

class MissionCertFragment: BaseFragment<FragmentMissionCertBinding>(R.layout.fragment_mission_cert) {

    private val viewModel: MissionCertViewModel by viewModels()

    private val imageListAdapter =  BaseRecycleAdapter(R.layout.item_cert_img) { item: Uri, bind: ItemCertImgBinding, _ -> bind.uri = item }
    private val levelAdapter = BaseRecycleAdapter(R.layout.item_cert_level)
    { item: LevelItem, bind: ItemCertLevelBinding, position: Int ->
        loadTxtMissionDifficulty(bind.itemCertLevelTxt, item.difficulty)
        if(item.selected) {
            loadImageMissionDifficulty(bind.itemCertLevelImg, item.difficulty)
            bind.itemCertLevelTxt.setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
        } else {
            loadImageMissionDifficultyOff(bind.itemCertLevelImg, item.difficulty)
            bind.itemCertLevelTxt.setTextColor(ResourcesCompat.getColor(resources, R.color.gray_2, null))
        }
        bind.root.setOnClickListener { levelClick(position) }
    }

    override fun init() {
        checkPermission()
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

    private fun initImgList(imgs: List<Uri>) {
        binding.missionCertImgs.offscreenPageLimit = 3

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.default_margin)
        val pagerWidth = resources.displayMetrics.widthPixels
        val screenWidth = resources.displayMetrics.widthPixels
        val offsetPx = screenWidth - pageMarginPx - pagerWidth

        binding.missionCertImgs.setPageTransformer { page, position ->
            page.translationX = position * -offsetPx
        }

        imageListAdapter.setData(imgs)
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
        binding.missionCertAfterEdit.setOnEditorActionListener { _, actionId, _ ->
            actionId == EditorInfo.IME_ACTION_DONE
        }
        binding.missionCertAfterEdit.addTextChangedListener {
            it?.also {
                viewModel.editTxt(it.toString())
            }
        }
        binding.missionCertNext.setOnClickListener {
            Log.e("ttt", "tttt")
        }

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

    private fun checkPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> getPhotos()
            else -> {
                registerForActivityResult(ActivityResultContracts.RequestPermission())
                {
                    if(it) {
                        getPhotos()
                    } else {
                        showToast("권한이 없어용")
                        findNavController().popBackStack()
                    }
                }.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun getPhotos() {
        TedImagePicker.with(requireContext())
            .mediaType(MediaType.IMAGE)
            .max(3, R.string.mission_cert_max_img)
            .buttonBackground(R.drawable.selector_button)
            .buttonTextColor(R.color.white)
            .backButton(R.drawable.ic_back)
            .errorListener {
                showToast(it.message)
                findNavController().popBackStack()
            }
            .startMultiImage {
                initImgList(it)
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

    private data class LevelItem (val difficulty: Difficulty, var selected: Boolean)

}