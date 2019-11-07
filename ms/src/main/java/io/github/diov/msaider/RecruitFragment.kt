package io.github.diov.msaider

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_recruit.*

/**
 * MSAider
 *
 * Created by Dio_V on 2019-10-17.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class RecruitFragment : BottomSheetDialogFragment() {

    private val order by lazy { requireNotNull(requireArguments().getString(ORDER_KEY)) }
    private var type = RecruitType.MAX_LUCK
    private var count = 3
    private lateinit var callback: RecruitCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recruit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recruitTitle.text = order.split("\n")[2]
        recruitTypeGroup.setOnCheckedChangeListener { group, id ->
            group.actAsRadioGroup()
            type = when (id) {
                R.id.maxLuckButton -> RecruitType.MAX_LUCK
                R.id.onlyCorrectButton -> RecruitType.ONLY_CORRECT
                R.id.anyoneButton -> RecruitType.ANYONE
                else -> RecruitType.MAX_LUCK
            }
        }
        recruitCountGroup.setOnCheckedChangeListener { group, id ->
            group.actAsRadioGroup()
            count = when (id) {
                R.id.recruitOneButton -> 1
                R.id.recruitTwoButton -> 2
                R.id.recruitThreeButton -> 3
                else -> 3
            }
        }
        recruitCopyButton.setOnClickListener {
            AiderApp.app.copyToClipboard(order)
        }
        recruitConfirmButton.setOnClickListener {
            callback(this, order, type, count)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        activity?.finish()
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    companion object {
        private const val TAG = "RecruitFragment"
        private const val ORDER_KEY = "order"

        fun newInstance(order: String, callback: RecruitCallback): RecruitFragment =
            RecruitFragment().apply {
                arguments = bundleOf(ORDER_KEY to order)
                this.callback = callback
            }
    }
}

typealias RecruitCallback = (fragment: RecruitFragment, order: String, type: RecruitType, count: Int) -> Unit
