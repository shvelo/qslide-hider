package com.shvelo.qslidehider;

import android.widget.LinearLayout;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;


public class QSlideHider implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        XposedBridge.log("QslideHider: Initialized");
    }

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        XposedBridge.log("QslideHider: HandleInitPackageResources");

        resparam.res.hookLayout("com.android.systemui", "layout", "floating_launcher", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                XposedBridge.log("QslideHider: Hiding QSlide bar in floating_launcher");

                LinearLayout panel = (LinearLayout) liparam.view.findViewById(
                        liparam.res.getIdentifier("floating_launcher_base", "id", "com.android.systemui"));
                panel.setVisibility(LinearLayout.GONE);
                panel.getLayoutParams().height = 0;
            }
        });

        resparam.res.hookLayout("com.android.systemui", "layout", "status_bar_expanded", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                XposedBridge.log("QslideHider: Hiding QSlide bar in status_bar_expanded");

                LinearLayout panel = (LinearLayout) liparam.view.findViewById(
                        liparam.res.getIdentifier("floating_launcher_base", "id", "com.android.systemui"));
                panel.setVisibility(LinearLayout.GONE);
                panel.getLayoutParams().height = 0;
            }
        });
    }
}
