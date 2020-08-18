package com.grechur.wanandroid.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.grechur.wanandroid.R;
import com.grechur.wanandroid.utils.WatcherText;
import com.tencent.mm.opensdk.modelbiz.JumpToBizProfile;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JumpToWXActivity extends AppCompatActivity {

    @BindView(R.id.tv_jump)
    EditText tv_jump;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump_to_wx);
        ButterKnife.bind(this);
//        tv_jump.setFilters(new InputFilter[]{new CustomCoinNameFilter(this,10)});
        tv_jump.addTextChangedListener(new WatcherText(this,10,tv_jump));
    }

    @OnClick({R.id.tv_jump})
    public void onClick(View view){
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("标签随便写", tv_jump.getText());
        clipboardManager.setPrimaryClip(clipData);
//        getWechatApi();
        startWeibo("6420899149","");
    }

    /**
     * 跳转到微信
     */
    private void getWechatApi(){
//        try {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setComponent(cmp);
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            // TODO: handle exception
//            ToastUtils.show("检查到您手机没有安装微信，请安装后使用该功能");
//        }
        String appId = "wx363d70374dc306b0";//开发者平台ID
        IWXAPI api = WXAPIFactory.createWXAPI(this, appId, false);

        if (api.isWXAppInstalled()) {
            JumpToBizProfile.Req req = new JumpToBizProfile.Req();
            req.toUserName = "gh_696ba5faf351"; // 公众号原始ID
            req.extMsg = "";
            req.profileType = JumpToBizProfile.JUMP_TO_NORMAL_BIZ_PROFILE; // 普通公众号
            api.sendReq(req);
        }else{
            Toast.makeText(this, "微信未安装", Toast.LENGTH_SHORT).show();
        }

    }
    public void startWeibo(String id, String url) {
        if (isPackageInstalled(this,"com.sina.weibo")) {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.sina.weibo", "com.sina.weibo.page.ProfileInfoActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setComponent(cmp);
            intent.putExtra("uid", id);
            startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "微博未安装", Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null)
            return false;
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for(PackageInfo info : packageInfoList) {
            if (info.packageName.equals(packageName))
                return true;
        }
        return false;
    }
}
