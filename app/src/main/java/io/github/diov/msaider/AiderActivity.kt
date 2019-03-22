package io.github.diov.msaider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AiderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aider)

        analysisIntent()
    }

    private fun analysisIntent() {
        val data = intent.data ?: return
        val specificPart = data.schemeSpecificPart.split("\n")
        val link = specificPart[3]
        copyToClipboard(link)
        val code = extractCode(link)
        notifyMonsterStrike(code)
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
