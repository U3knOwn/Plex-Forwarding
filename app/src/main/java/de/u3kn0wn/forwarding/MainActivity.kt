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
                val url = Uri.parse(sharedText)
                val openUrlIntent = Intent(Intent.ACTION_VIEW, url)
                if (openUrlIntent.resolveActivity(packageManager) != null) {
                    startActivity(openUrlIntent)
                    Log.d(localClassName, "Output: $sharedText")
                }
            } else {
                Log.d(localClassName, "Text is null or empty")
            }
        }
    }
}
