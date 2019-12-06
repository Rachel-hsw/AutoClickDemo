package com.example.autoclickdemo;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //com.snap.vseries
    private final static String packageName="com.ss.android.article.news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (serviceIsRunning()) {
            Toast.makeText(this, "无障碍服务已经开启", Toast.LENGTH_SHORT).show();
            openApp();
        } else {
            startAccessibilityService();
        }
    }

    void openApp() {
        if (!isInstallAppForPackageName(this, packageName)) {
            Toast.makeText(this, "请先安装头条", Toast.LENGTH_SHORT).show();
        } else {
            startAPP(packageName);
        }
    }

    /**
     * 启动一个app
     */
    public void startAPP(String appPackageName) {
        try {
            Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "头条APP打开失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageName 包名
     */
    public static boolean isInstallAppForPackageName(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();

        for (int i = 0; i < packageInfoList.size(); i++) {
            String packName = packageInfoList.get(i).packageName;
            packageNames.add(packName);
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断自己的应用的AccessibilityService是否在运行
     */
    private boolean serviceIsRunning() {
        ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services;
        if (am != null) {
            services = am.getRunningServices(Short.MAX_VALUE);
            for (ActivityManager.RunningServiceInfo info : services) {
                if (info.service.getClassName().equals(getPackageName() + ".RobService")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 前往设置界面开启服务
     */
    private void startAccessibilityService() {
        new AlertDialog.Builder(this)
                .setTitle("开启辅助功能")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("使用此项功能需要您开启辅助功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 隐式调用系统设置界面
                        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                        startActivity(intent);
                    }
                }).create().show();
    }
}
