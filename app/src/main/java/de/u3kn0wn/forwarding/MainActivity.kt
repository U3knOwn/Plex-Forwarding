package de.u3kn0wn.forwarding

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleSharedIntent(intent)
        finish()
    }

    private fun handleSharedIntent(intent: Intent?) {
        if (intent != null && Intent.ACTION_SEND == intent.action && intent.type?.startsWith("text/") == true) {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
            if (sharedText != null) {
                val modifiedText = removeWhiteSpace(sharedText)
                val modifiedUrl = removeQueryStringParameterIfPresent(modifiedText)
                val url = Uri.parse(modifiedUrl)
                val openUrlIntent = Intent(Intent.ACTION_VIEW, url)
                if (openUrlIntent.resolveActivity(packageManager) != null) {
                    startActivity(openUrlIntent)
                    Log.d(localClassName, "Output: $modifiedUrl")
                }
            } else {
                Log.d(localClassName, "Text is null or empty")
            }
        }
    }

    private fun removeWhiteSpace(input: String): String {
        return input.replace("\\s+".toRegex(), "")
    }

    private fun removeQueryStringParameterIfPresent(url: String): String {
        val parametersToRemove = (0..9).map { "i=$it" }
        var modifiedUrl = url
        for (parameterToRemove in parametersToRemove) {
            modifiedUrl = modifiedUrl.replace("&$parameterToRemove", "")
            modifiedUrl = modifiedUrl.replace("?$parameterToRemove", "")
        }
        return modifiedUrl
    }
}
