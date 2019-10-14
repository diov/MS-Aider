package io.github.diov.msaider

import android.content.Intent
import android.net.Uri
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
        setContentView(R.layout.activity_aider)

        analysisIntent()
    }

    private fun analysisIntent() {
        val data = intent.data ?: return
        val order = data.schemeSpecificPart
        recruiter.recruit(order, RecruitType.MAX_LUCK) { outcome ->
            when (outcome) {
                is Outcome.Success -> {
                    val result = outcome.value
                    if (result.status == 1) {
                        val link = order.split("\n")[3]
                        val code = extractCode(link)
                        notifyMonsterStrike(code)
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

    private fun extractCode(link: String): String {
        val linkUri = Uri.parse(link)
        return linkUri.getQueryParameters("pass_code")[0]
    }

    private fun notifyMonsterStrike(code: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("$MONSTER_STRIKE_SCHEME$code")
        startActivity(intent)
    }
}
