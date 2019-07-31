package com.grechur.wanandroid.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.base.WanApplication;
import com.grechur.wanandroid.utils.Constant;
import com.grechur.wanandroid.view.WebLoadView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.web_load_view)
    WebLoadView web_load_view;
    @BindView(R.id.wv_web)
    WebView webView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private Unbinder unbind;
    private String mUrl = "";
    private String mTitle = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        unbind = ButterKnife.bind(this);

        mUrl = getIntent().getStringExtra(Constant.INTENT_URL);
        mTitle = getIntent().getStringExtra(Constant.INTENT_TITLE);

        title.setText(mTitle);

        Drawable drawable = getResources().getDrawable(R.mipmap.arrow_left);
        iv_back.setImageDrawable(drawable);
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webView.loadUrl(mUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                web_load_view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                web_load_view.setVisibility(View.GONE);
                web_load_view.cancleAnimal();

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        registerNetTypeChangeObserver();
    }

    @OnClick({R.id.iv_back})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        unbind.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void registerNetTypeChangeObserver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        WanApplication.getAppContext().registerReceiver(receiver, filter);
        WanApplication.getAppContext().registerReceiver(receiver, filter);
    }

    private static BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("MainActivity","接收到了");
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo info = cm.getActiveNetworkInfo();
                if (info == null || !info.isAvailable()) {
                    return;
                }

            }
        }
    };
}
