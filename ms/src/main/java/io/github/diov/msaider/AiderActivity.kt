package io.github.diov.msaider

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        recruiter.recruit(order, type, count) { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    val result = outcome.value
                    if (result.status == 1) {
                        fragment.dismissAllowingStateLoss()
                        notifyMonsterStrike(result.intentUrl)
                    } else {
                        Toast.makeText(this, result.message, Toast.LENGTH_LONG).show()
                    }
                }
                is Outcome.Failure -> {
                    Toast.makeText(this, R.string.recruit_failed, Toast.LENGTH_LONG).show()
                }
            }
        }
        copyToClipboard(order)
    }

    private fun notifyMonsterStrike(url: String) {
        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        startActivity(intent)
    }
}