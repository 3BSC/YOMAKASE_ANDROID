package com.example.yomakase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.yomakase.databinding.ActivityKakaoAddressFindBinding

class KakaoAddressFindActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKakaoAddressFindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoAddressFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.addJavascriptInterface(BridgeInterface(), "Android")
        binding.webView.webViewClient = WebViewClientClass()
        binding.webView.loadUrl("https://searchadresskakaoapi.firebaseapp.com/")
    }

    inner class WebViewClientClass: WebViewClient(){
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view!!.loadUrl("javascript:sample2_execDaumPostcode();")
        }
    }

    inner class BridgeInterface {
        @JavascriptInterface
        fun processDATA(data: String){
            val intent = Intent()
            intent.putExtra("data", data)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}