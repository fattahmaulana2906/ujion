package com.example.ujion

import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.KeyEvent.KEYCODE_BACK
import android.R.id
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    internal var cari: EditText? = null
    internal lateinit var tombol_cari: Button
    internal lateinit var web_view: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cari = findViewById(R.id.input_cari)
        tombol_cari = findViewById(R.id.tombol_cari)
        web_view = findViewById(R.id.web_view)
        tombol_cari.setOnClickListener(this)
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = ujion()
    }

    override fun onClick(v: View) {
        when (v.getId()) {
            R.id.tombol_cari -> {
                val alamat = cari?.text.toString()
                cari_alamat(alamat)
            }
        }
    }

    private fun cari_alamat(alamat: String) {
        val str = alamat.toLowerCase()
        if (str.matches(".* .*".toRegex())) {
            web_view.loadUrl("https://www.google.com/search?q=$alamat")
        } else {
            if (str.matches(".*https://.*".toRegex()) || str.matches(".*http://.*".toRegex())) {
                web_view.loadUrl(alamat)
            }
            if (str.matches(".*..*".toRegex())) {
                web_view.loadUrl("https://$alamat")
            } else {
                web_view.loadUrl("https://www.google.com/search?q=$alamat")
            }
        }

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private inner class ujion : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            web_view.loadUrl(url)
            return true
        }


    }
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Apa kalian ingin Exit?")
            .setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { _, _ -> this@MainActivity.finish() })
            .setNegativeButton("No", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present. getMenuInflater().inflate(R.menu.menu_main, menu);
        return true
    }
}
