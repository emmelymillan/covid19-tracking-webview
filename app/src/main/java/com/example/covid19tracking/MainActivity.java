package com.example.covid19tracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
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
    private ProgressBar progressBar;
    private ImageView imag;
    private TextView vig;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        miVisorWeb = (WebView) findViewById(R.id.visorWeb);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);
        miVisorWeb.setWebViewClient(new CustomWebViewClient());
        imag = (ImageView) findViewById(R.id.ImageV);
        vig = (TextView) findViewById(R.id.vigilancia);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setDomStorageEnabled(true); // Permite localStorage
        ajustesVisorWeb.setJavaScriptEnabled(true); // Permite JavaScript



        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (savedInstanceState == null) {
                miVisorWeb.loadUrl(url);
            }
        } else {
            vig.setText("Dispositivo sin internet");
            progressBar.setVisibility(View.GONE);
            imag.setImageResource(R.drawable.ic_signal_wifi_connected_no_internet_4_24);
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
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ShowOrHideWebViewInitialUse = "hide";
                    progressBar.setVisibility(View.GONE);
                    imag.setVisibility(View.GONE);
                    vig.setVisibility(View.GONE);
                    view.setVisibility(miVisorWeb.VISIBLE);
                }
            }, 2500);

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

    // Impedir que el botón atrás cierre la aplicación
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