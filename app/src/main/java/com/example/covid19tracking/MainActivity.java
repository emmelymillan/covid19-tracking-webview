package com.example.covid19tracking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    WebView miVisorWeb;
    final String url = "https://covid19-tracking-em.herokuapp.com";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        miVisorWeb = (WebView) findViewById(R.id.visorWeb);

        final WebSettings ajustesVisorWeb = miVisorWeb.getSettings();
        ajustesVisorWeb.setDomStorageEnabled(true); // Permite localStorage
        ajustesVisorWeb.setJavaScriptEnabled(true); // Permite JavaScript

        if (savedInstanceState == null) {
            miVisorWeb.loadUrl(url);
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