package io.github.diov.msaider

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.naver.line.android.R
import jp.naver.line.android.databinding.FragmentRecruitBinding

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-17.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class RecruitFragment : BottomSheetDialogFragment() {

    interface RecruitDelegate {
        fun recruit(order: String, type: RecruitType, count: Int)
        fun canceled()
    }

    private val order by lazy { requireNotNull(requireArguments().getString(ORDER_KEY)) }
    private var type = RecruitType.MAX_LUCK
    private var count = 3
    private lateinit var delegate: RecruitDelegate
    private var _binding: FragmentRecruitBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate = activity as? RecruitDelegate ?: error("Activity should implement RecruitDelegate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecruitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recruitTitle.text = order.split("\n")[2]
        binding.recruitTypeGroup.setOnCheckedChangeListener { group, id ->
            group.actAsRadioGroup()
            type = when (id) {
                R.id.maxLuckButton -> RecruitType.MAX_LUCK
                R.id.onlyCorrectButton -> RecruitType.ONLY_CORRECT
                R.id.anyoneButton -> RecruitType.ANYONE
                else -> RecruitType.MAX_LUCK
            }
        }
        binding.recruitCountGroup.setOnCheckedChangeListener { group, id ->
            group.actAsRadioGroup()
            count = when (id) {
                R.id.recruitOneButton -> 1
                R.id.recruitTwoButton -> 2
                R.id.recruitThreeButton -> 3
                else -> 3
            }
        }
        binding.recruitCopyButton.setOnClickListener {
            AiderApp.app.copyToClipboard(order)
        }
        binding.recruitConfirmButton.setOnClickListener {
            delegate.recruit(order, type, count)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        delegate.canceled()
        super.onDismiss(dialog)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    companion object {
        private const val TAG = "RecruitFragment"
        private const val ORDER_KEY = "order"

        fun newInstance(order: String): RecruitFragment =
            RecruitFragment().apply {
                arguments = bundleOf(ORDER_KEY to order)
            }
    }
}
