package com.example.covid19tracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String ShowOrHideWebViewInitialUse = "show";
    WebView miVisorWeb;
    final String url = "https://covid19-tracking-em.herokuapp.com";
    private ProgressBar spinner;
    private ImageView imag;
    private TextView vig;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        miVisorWeb = (WebView) findViewById(R.id.visorWeb);
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        miVisorWeb.setWebViewClient(new CustomWebViewClient());
        imag = (ImageView) findViewById(R.id.ImageV);
        vig = (TextView) findViewById(R.id.vigilancia);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setDomStorageEnabled(true); // Permite localStorage
        ajustesVisorWeb.setJavaScriptEnabled(true); // Permite JavaScript

        if (savedInstanceState == null) {
            miVisorWeb.loadUrl(url);
        }
    }

    // This allows for a splash screen
    // (and hide elements once the page loads)
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            // only make it invisible the FIRST time the app is run
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(webview.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            ShowOrHideWebViewInitialUse = "hide";
            spinner.setVisibility(View.GONE);
            imag.setVisibility(View.GONE);
            vig.setVisibility(View.GONE);

            view.setVisibility(miVisorWeb.VISIBLE);
            super.onPageFinished(view, url);

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        miVisorWeb.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        miVisorWeb.restoreState(savedInstanceState);
    }

    // Impedir que el bot??n atr??s cierre la aplicaci??n
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView miVisorWeb;
        miVisorWeb = (WebView) findViewById(R.id.visorWeb);
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (miVisorWeb.canGoBack()) {
                    miVisorWeb.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}