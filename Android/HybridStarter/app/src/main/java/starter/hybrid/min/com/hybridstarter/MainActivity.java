package starter.hybrid.min.com.hybridstarter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.min.hybrid.library.bridge.Bridge;
import com.min.hybrid.library.util.L;
import com.min.hybrid.library.view.HybridWebView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private String mUrl = "http://10.10.13.117:8080/";

    private HybridWebView mWebView;
    private Bridge mBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = findViewById(R.id.wv);
        mBridge = new Bridge.Builder().build();
        mBridge.configureWebView(mWebView);
        mBridge.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                L.d(TAG, "onPageFinished=%s", url);
            }
        });
    }

}
