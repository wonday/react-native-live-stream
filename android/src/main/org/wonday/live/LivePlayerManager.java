/**
 * Copyright (c) 2017-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package org.wonday.live;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;

import org.wonday.live.RCTLivePlayerView;

public class LivePlayerViewManager extends SimpleViewManager<RCTLivePlayerView> {


    @Override
    public String getName() {
        return "RCTLivePlayer";
    }

    @Override
    protected RCTLivePlayerView createViewInstance(ThemedReactContext reactContext) {
        RCTLivePlayerView view = new RCTLivePlayerView(reactContext);
        return view;
    }

    @ReactProp(name = "srcUrl")
    public void setSrcUrl(RCTLivePlayerView view, @Nullable String srcUrl) {
        view.setSrcUrl(srcUrl);
    }

    @ReactProp(name = "bufferTime")
    public void setBufferTime(RCTLivePlayerView view, int bufferTime) {
        view.setBufferTime(bufferTime);
    }
    @ReactProp(name = "maxBufferTime")
    public void setMaxBufferTime(RCTLivePlayerView view, int maxBufferTime) {
        view.setMaxBufferTime(maxBufferTime);
    }

    @ReactProp(name = "resizeMode")
    public void setResizeMode(RCTLivePlayerView view, String resizeMode) {
        view.setResizeMode(resizeMode);
    }

    @ReactProp(name = "renderType")
    public void setRenderType(RCTLivePlayerView view, String renderType) {
        view.setRenderType(renderType);
    }

    @ReactProp(name = "paused")
    public void setPaused(RCTLivePlayerView view, Boolean paused) {
        view.setPaused(paused);
    }

    @ReactProp(name = "muted")
    public void setMuted(RCTLivePlayerView view, Boolean muted) {
        view.setMuted(muted);
    }

    @Override
    public void onAfterUpdateTransaction(RCTLivePlayerView view) {
        super.onAfterUpdateTransaction(view);
        view.updateView();
    }
}
