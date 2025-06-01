package ir.kashan.raziazriazy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private int webViewPreviousState;
    private final int PAGE_STARTED = 0x1;
    private CoordinatorLayout rootView;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);
        rootView = (CoordinatorLayout) findViewById(R.id.root_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true);
            }
        }
        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.setWebViewClient(new GeoWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebChromeClient(new GeoWebChromeClient());
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        webView.loadUrl("https://raziazriazy.ir/");
    }

    public class GeoWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
        }
    }

    public class GeoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith(".pdf")) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(android.net.Uri.parse(url), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException e) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
                    startActivity(browserIntent);
                }
                return true;
            }
            return false;
        }

        Dialog loadingDialog = new Dialog(MainActivity.this);
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            webViewPreviousState = PAGE_STARTED;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (!isConnected()) {
                final Snackbar snackBar = Snackbar.make(rootView, "No Internet Connection ", Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("Enable Data", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);
                        webView.loadUrl("javascript:window.location.reload( true )");
                        snackBar.dismiss();
                    }
                });
                snackBar.show();
            }
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (webViewPreviousState == PAGE_STARTED) {
                if (null != loadingDialog) {
                    loadingDialog.dismiss();
                    loadingDialog = null;
                }
            }
        }
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != cm) {
            NetworkInfo info = cm.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        }
        return false;
    }

    @Override
	public void onBackPressed() {
        super.onBackPressed();
	}
}
