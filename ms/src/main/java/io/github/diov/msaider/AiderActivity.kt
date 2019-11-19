package io.github.diov.msaider

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * MSAider
 *
 * Created by Dio_V on 2019-03-23.
 * Copyright © 2019 diov.github.io. All rights reserved.
 */

class AiderActivity : AppCompatActivity() {

    private val recruiter = GamewithRecruiter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent ?: return
        analysis(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent ?: return
        analysis(intent)
    }

    private fun analysis(intent: Intent) {
        val data = intent.data ?: return
        val order = data.schemeSpecificPart
        showRecruitFragment(order)
    }

    private fun showRecruitFragment(order: String) {
        RecruitFragment.newInstance(order, ::requestRecruit).show(supportFragmentManager)
    }

    private fun requestRecruit(fragment: RecruitFragment, order: String, type: RecruitType, count: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            when (val outcome = recruiter.recruit(order, type, count)) {
                is Outcome.Success -> {
                    copyToClipboard(order)
                    notifyMonsterStrike(outcome.value.intentUrl)
                    fragment.dismissAllowingStateLoss()
                }
                is Outcome.Failure -> {
                    outcome.exception.printStackTrace()
                }
            }
        }
    }

    private fun notifyMonsterStrike(url: String) {
        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        startActivity(intent)
    }
}
