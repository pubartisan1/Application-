package com.amelle.neo3;
import android.app.*;import android.os.*;import android.webkit.*;import android.content.pm.PackageManager;import android.Manifest;import android.graphics.Color;
public class MainActivity extends Activity{
 WebView web;
 @Override public void onCreate(Bundle b){super.onCreate(b);if(Build.VERSION.SDK_INT>=23&&checkSelfPermission(Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED)requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},7);web=new WebView(this);web.setBackgroundColor(Color.rgb(5,9,18));WebSettings s=web.getSettings();s.setJavaScriptEnabled(true);s.setDomStorageEnabled(true);s.setMediaPlaybackRequiresUserGesture(false);web.setWebViewClient(new WebViewClient());web.setWebChromeClient(new WebChromeClient(){@Override public void onPermissionRequest(PermissionRequest r){runOnUiThread(()->r.grant(r.getResources()));}});web.loadUrl("file:///android_asset/index.html");setContentView(web);}
 @Override public void onBackPressed(){if(web.canGoBack())web.goBack();else super.onBackPressed();}
}