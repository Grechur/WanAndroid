package com.grechur.wanandroid.ui;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class WebViewActivity extends AppCompatActivity {

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
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


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
}