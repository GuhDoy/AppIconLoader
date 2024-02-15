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
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import coil3.ImageLoader;
import coil3.SingletonImageLoader;
import coil3.request.ImageRequest;
import coil3.target.ImageViewTarget;

public class CoilAppListFragment extends AppListFragment {
    @NonNull
    public static CoilAppListFragment newInstance() {
        return new CoilAppListFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CoilInitializer.initializeCoil(requireContext().getApplicationContext());
    }

    @NonNull
    @Override
    protected AppListAdapter.IconLoader onCreateIconLoader() {
        return (imageView, packageInfo) -> {
            Context context = imageView.getContext();
            ImageLoader loader = SingletonImageLoader.get(context);
            loader.enqueue(new ImageRequest.Builder(context)
                    .data(packageInfo)
                    .target(new ImageViewTarget(imageView))
                    .build());
        };
    }
}
