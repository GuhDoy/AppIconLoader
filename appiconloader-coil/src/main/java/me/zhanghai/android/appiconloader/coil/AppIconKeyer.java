package me.zhanghai.android.appiconloader.coil;

import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import coil3.key.Keyer;
import coil3.request.Options;
import me.zhanghai.android.appiconloader.AppIconLoader;

public class AppIconKeyer implements Keyer<PackageInfo> {
    @Nullable
    @Override
    public String key(@NonNull PackageInfo packageInfo, @NonNull Options options) {
        return AppIconLoader.getIconKey(packageInfo, options.getContext());
    }
}
