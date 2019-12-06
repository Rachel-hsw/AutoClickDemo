package com.example.autoclickdemo;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Iterator;
import java.util.List;

public class RobService extends AccessibilityService {
    @TargetApi(18)
    private void openPacket() {
        Log.i("------哈哈哈", "------哈哈哈");
        AccessibilityNodeInfo localAccessibilityNodeInfo1 = getRootInActiveWindow();
        if (localAccessibilityNodeInfo1 != null) {
            Log.i("------呵呵呵", "------呵呵呵");
            List localList1 = localAccessibilityNodeInfo1.findAccessibilityNodeInfosByViewId("com.ss.android.article.news:id/vs");
            localAccessibilityNodeInfo1.recycle();
            Iterator localIterator = localList1.iterator();
            while (localIterator.hasNext()) {
                AccessibilityNodeInfo localAccessibilityNodeInfo2 = (AccessibilityNodeInfo) localIterator.next();
                List localList2 = localAccessibilityNodeInfo2.findAccessibilityNodeInfosByViewId("com.ss.android.article.news:id/ba4");
                if ((localList2 != null) && (localList2.size() > 0)) {
                    Log.i("------视频播放按钮数量:", "" + localList2.size());
                    continue;
                }
                Log.i("------类型:", localAccessibilityNodeInfo2.getClassName().toString());
                Log.i("------父类类型:", localAccessibilityNodeInfo2.getParent().getClassName().toString());
                if ((!"android.widget.LinearLayout".equals(localAccessibilityNodeInfo2.getClassName().toString())) || (!"android.support.v7.widget.RecyclerView".equals(localAccessibilityNodeInfo2.getParent().getClassName().toString())))
                    continue;
                Log.i("------执行点击:", "------执行点击:");
                localAccessibilityNodeInfo2.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    public void onAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
        Log.i("------计算机放地上的", "------计算机放地上的");
        Toast.makeText(getApplicationContext(), "计算机放地上的", 1).show();
        //获取当前活动窗口中的根节点
        AccessibilityNodeInfo localAccessibilityNodeInfo = getRootInActiveWindow();
        if (localAccessibilityNodeInfo != null) {
            List localList = localAccessibilityNodeInfo.findAccessibilityNodeInfosByViewId("com.ss.android.article.news:id/a8f");
            if ((localList != null) && (localList.size() > 0)) {
                Log.i("------点击返回", "-----点击返回");
                try {
                    Thread.sleep(5000L);
                    //Action to go back
                    performGlobalAction(1);
                    return;
                } catch (InterruptedException localInterruptedException) {
                    localInterruptedException.printStackTrace();
                    return;
                }
            }
        }
        openPacket();
    }

    /**
     * com.ss.android.article.lite:id/lm
     * com.ss.android.article.lite:id/lw
     * 滑动
     * 滑动比例 0~20
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void slideVertical(final int next, final long nextTime, int startSlideRatio, int stopSlideRatio) {
        int screenHeight = ScreenUtils.getScreenHeight(getApplicationContext());
        int screenWidth = ScreenUtils.getScreenWidth(getApplicationContext());
        Log.i("Rachel", "屏幕：" + (screenHeight - (screenHeight / 10)) + "/" +
                (screenHeight - (screenHeight - (screenHeight / 10))) + "/" + screenWidth / 2);

        Path path = new Path();
        path.moveTo(400, 800);
        path.lineTo(400, 1400);
        GestureDescription.Builder builder = new GestureDescription.Builder();
        GestureDescription gestureDescription = builder
                .addStroke(new GestureDescription.
                        StrokeDescription(path,
                        200,
                        200))
                .build();

        dispatchGesture(gestureDescription, new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
                Toast.makeText(getApplicationContext(), "滑动结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
                Toast.makeText(getApplicationContext(), "滑动取消", Toast.LENGTH_SHORT).show();
            }
        }, null);
    }

    public void onInterrupt() {
    }

    protected void onServiceConnected() {
        super.onServiceConnected();
    }
}