/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zhanghai.android.appiconloader.sample;

import android.content.Context;
import android.content.pm.PackageInfo;

import androidx.annotation.NonNull;

import coil3.ComponentRegistry;
import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.reflect.KClass;
import me.zhanghai.android.appiconloader.coil.AppIconFetcher;
import me.zhanghai.android.appiconloader.coil.AppIconKeyer;

public class CoilInitializer {
    private static boolean sInitialized = false;
    @NonNull
    private static final Object sInitializedLock = new Object();

    private CoilInitializer() {}

    public static void initializeCoil(@NonNull Context context) {
        synchronized (sInitializedLock) {
            if (sInitialized) {
                return;
            }
            context = context.getApplicationContext();
            int iconSize = context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
            SingletonImageLoader.setSafe(new SingletonImageLoader.Factory() {
                @NonNull
                @Override
                public ImageLoader newImageLoader(@NonNull Context context) {
                    KClass<PackageInfo> packageInfoKClass =
                            JvmClassMappingKt.getKotlinClass(PackageInfo.class);
                    return new ImageLoader.Builder(context)
                            .components(new ComponentRegistry.Builder()
                                    .add(new AppIconKeyer(), packageInfoKClass)
                                    .add(new AppIconFetcher.Factory(iconSize, false, context),
                                            packageInfoKClass)
                                    .build())
                            .build();
                }
            });
            sInitialized = true;
        }
    }
}
