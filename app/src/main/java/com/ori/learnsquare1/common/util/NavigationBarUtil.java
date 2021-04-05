package com.ori.learnsquare1.common.util;

import android.app.Activity;
import android.view.View;

/**
 * 创建人: zhengpf
 * 修改时间: 2021/4/4 18:58
 * 类说明:
 */

public class NavigationBarUtil {

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideBottomNav(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /**
     * 重新显示导航栏和状态栏
     */
    public static void showBottomNav(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
    }


    /**
     *沉浸式全屏状态下，显示导航栏和状态栏
     */
    public static void showBottomNavOfImmersiveMode(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(0);
        decorView .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
