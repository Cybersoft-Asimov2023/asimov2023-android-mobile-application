package com.example.android.asimov2023.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.example.android.asimov2023.R
import com.example.android.asimov2023.ui.auth.SignInActivity

class TermsAndConditionsActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_conditions)
        var previousScrollPos = 0
        val webView = findViewById<WebView>(R.id.wv_TermsCond)
        val btnScroll = findViewById<Button>(R.id.bt_tobottom)
        val btnReturn = findViewById<Button>(R.id.bt_back)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        val url = "https://jcayllahuag.github.io/Terms-Conditions"
        webView.loadUrl(url)

        webView.setOnScrollChangeListener{ _, _, scrollY, _, _ ->
            if(scrollY < previousScrollPos)
                btnScroll.visibility = View.VISIBLE

            previousScrollPos = scrollY
        }

        btnScroll.setOnClickListener {
            btnScroll.visibility = View.GONE
            webView.pageDown(true)
        }
        btnReturn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}