package com.shvelo.qslidehider;

import android.widget.LinearLayout;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;


public class QSlideHider implements IXposedHookInitPackageResources {

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        resparam.res.hookLayout("com.android.systemui", "layout", "floating_launcher", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                LinearLayout panel = (LinearLayout) liparam.view.findViewById(
                        liparam.res.getIdentifier("floating_launcher_base", "id", "com.android.systemui"));
                panel.setVisibility(LinearLayout.GONE);
                panel.getLayoutParams().height = 0;
            }
        });
    }
}
