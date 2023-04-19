package com.jabin.vsynctracker;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.view.WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
import static android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

public class FloatingWindow {

       Window window;
       WindowManager.LayoutParams mWindowLayoutParams;
       WindowManager wms;

       Context context;

       public FloatingWindow(Context context) {
              this.context = context;
              wms = context.getSystemService(WindowManager.class);

              mWindowLayoutParams = getFloatingWindowParams();
              mWindowLayoutParams.setTitle("VsyncTracker");
              window = getFloatingWindow(context);
              window.setWindowManager(wms, null, null);
       }


       public void attachWindow() {
              View decorView = window.getDecorView();
              if (decorView.isAttachedToWindow()) {
                     return;
              }
              wms.addView(decorView, mWindowLayoutParams);
              decorView.requestApplyInsets();
       }


       public void removeWindow() {
              final View decorView = window.peekDecorView();
              if (decorView != null && decorView.isAttachedToWindow()) {
                     wms.removeViewImmediate(decorView);
              }
       }

       public void setContentView(View contentView) {
              window.setContentView(contentView);
       }
       /**
        * Sets up window params for a floating window
        */
       public static WindowManager.LayoutParams getFloatingWindowParams() {
              WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                      WRAP_CONTENT, WRAP_CONTENT, /* xpos */ 0, /* ypos */ 0, TYPE_APPLICATION_OVERLAY,
                              FLAG_NOT_FOCUSABLE
                              | FLAG_NOT_TOUCHABLE
                              | FLAG_ALT_FOCUSABLE_IM,
                      PixelFormat.TRANSLUCENT);
              params.gravity= Gravity.START|Gravity.TOP;
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                     params.preferredRefreshRate = 60;
              params.layoutInDisplayCutoutMode =
                      WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
                     params.setFitInsetsTypes(WindowInsets.Type.systemBars());
              }
              return params;
       }

       /**
        * Constructs a transparent floating window
        */
       public static Window getFloatingWindow(Context context) {
              Window window = new Dialog(context).getWindow();
              window.requestFeature(Window.FEATURE_NO_TITLE);
              window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
              window.setBackgroundDrawableResource(android.R.color.transparent);
              return window;
       }
}
