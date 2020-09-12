package com.siiberad.newsapi.view

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.http.SslError
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.webkit.SslErrorHandler
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.siiberad.newsapi.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.toolbar_webview.*

class WebViewActivity : AppCompatActivity() {

    private val inputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

    companion object {
        fun show(source: Activity, url: String?) {
            val intent = Intent(source, WebViewActivity::class.java).apply {
                putExtras(Bundle().apply {
                    putString("URL", url)
                })
            }
            source.startActivityForResult(intent, 911)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        supportActionBar?.hide()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        txt_toolbar.text = "WebView"
        backBtn.setOnClickListener { finish() }
        webView.settings.apply {
            allowFileAccess = true
            builtInZoomControls = false
            displayZoomControls = false
            domStorageEnabled = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedSslError(
                view: android.webkit.WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                try {
                    var message = "SSL Certificate error."
                    when (error!!.primaryError) {
                        SslError.SSL_UNTRUSTED -> message =
                            "The certificate authority is not trusted."
                        SslError.SSL_EXPIRED -> message = "The certificate has expired."
                        SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                        SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
                    }
                    message += " Do you want to continue anyway?"
                    if (handler != null) {
                        val alertDialog = AlertDialog.Builder(this@WebViewActivity).create()
                        alertDialog.setTitle("SSL Certificate Error")
                        alertDialog.setMessage(message)
                        alertDialog.setButton(
                            Dialog.BUTTON_POSITIVE,
                            "PROCEED"
                        ) { _, _ -> handler.proceed() }
                        alertDialog.setButton(
                            Dialog.BUTTON_NEGATIVE,
                            "CANCEL"
                        ) { _, _ -> handler.cancel() }
                        alertDialog.show()
                        return
                    } else {
                        super.onReceivedSslError(view, handler, error)
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    super.onReceivedSslError(view, handler, error)
                }
            }
        }
        val url = intent?.extras?.getString("URL").toString()
        webView.loadUrl(url)

        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}