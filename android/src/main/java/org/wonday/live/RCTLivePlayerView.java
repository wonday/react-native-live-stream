/**
 * Copyright (c) 2017-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package org.wonday.live;

import java.util.ArrayList;
import android.util.AttributeSet;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import cn.nodemedia.NodePlayer;
import cn.nodemedia.NodePlayerDelegate;
import cn.nodemedia.NodePlayerView;

public class RCTLivePlayerView extends NodePlayerView implements LifecycleEventListener {
    private NodePlayer mNodePlayer;
    private Boolean isPaused = true;
    private ArrayList mChangedProps = new ArrayList<String>
    private Boolean canSendStatusEvent = false;

    public RCTLivePlayerView(ThemedReactContext context) {
        super(context);
        context.addLifecycleEventListener(this);
        mNodePlayer = new NodePlayer(context, "");
        mNodePlayer.setPlayerView(this);
        mNodePlayer.setNodePlayerDelegate(new NodePlayerDelegate() {
            @Override
            public void onEventCallback(NodePlayer nodePlayer, int i, String s) {
                if (canSendStatusEvent) {
                    WritableMap event = Arguments.createMap();
                    event.putString("type", "status");
                    event.putInt("code", i);
                    event.putString("message", s);
                    ReactContext reactContext = (ReactContext) getContext();
                    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                            getId(),
                            "topChange",
                            event);
                }
            }
        });
    }

    public void setSrcUrl(String srcUrl) {
        mChangedProps.add("srcUrl");
        mNodePlayer.setInputUrl(srcUrl);
    }

    public void setBufferTime(int bufferTime) {
        mChangedProps.add("bufferTime");
        mNodePlayer.setBufferTime(bufferTime);
    }

    public void setMaxBufferTime(int maxBufferTime) {
        mChangedProps.add("maxBufferTime");
        mNodePlayer.setMaxBufferTime(maxBufferTime);
    }

    public void setResizeMode(String resizeMode) {
        mChangedProps.add("resizeMode");
        // default resizeMode="stretch"
        String smode = "ScaleToFill";
        if (resizeMode.equals("contain")) {
            smode = "ScaleAspectFit";
        } else if (resizeMode.equals("cover")) {
            smode = "ScaleAspectFill";
        }
        NodePlayerView.UIViewContentMode mode = NodePlayerView.UIViewContentMode.valueOf(smode);
        parent.setUIViewContentMode(mode);
    }

    public void setRenderType(String renderType) {
        mChangedProps.add("renderType");
        NodePlayerView.RenderType type =  NodePlayerView.RenderType.valueOf(renderType);
        parent.setRenderType(type);
    }

    public void setPaused(bool paused) {
        mChangedProps.add("paused");
        isPaused = paused;
    }

    public void setMuted(bool muted) {
        mChangedProps.add("muted");
        mNodePlayer.setAudioEnable(!muted);
    }

    public void updateView() {
        bool needStart = false;

        if (mChangedProps.contains("srcUrl")) {
            // switch srcUrl need stop old srcUrl then start new one
            mNodePlayer.stop();
            needStart = true;
        }

        if (mChangedProps.contains("resizeMode")) {
            // after set resizeMode, not change immediately, so need stop then start...
            mNodePlayer.stop();
            needStart = true;
        }

        if (mChangedProps.contains("paused")) {
            if (isPaused) {
                _canSendStatusEvent = YES;
                if (mNodePlayer.isLive()) {
                    mNodePlayer.stop();
                } else {
                    mNodePlayer.pause();
                }
            } else {
                needStart = true;
            }
        }

        if (!isPaused && needStart) {
            _canSendStatusEvent = false;
            mNodePlayer.start();
        }
        
        mChangedProps.clear();
    }

    @Override
    public void onHostResume() {

    }

    @Override
    public void onHostPause() {

    }

    @Override
    public void onHostDestroy() {
        mNodePlayer.stop();
        mNodePlayer.release();
    }
}
