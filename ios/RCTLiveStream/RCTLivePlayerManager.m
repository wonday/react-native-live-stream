/**
 * Copyright (c) 2019-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <React/RCTViewManager.h>
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import "RCTLivePlayerView.h"

@interface RCTLivePlayerManager : RCTViewManager
@end

@implementation RCTLivePlayerManager
RCT_EXPORT_MODULE()

- (UIView *)view {
  return [[RCTLivePlayerView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(srcUrl, NSString)
RCT_EXPORT_VIEW_PROPERTY(bufferTime, int)
RCT_EXPORT_VIEW_PROPERTY(maxBufferTime, int)
RCT_EXPORT_VIEW_PROPERTY(paused, BOOL)
RCT_EXPORT_VIEW_PROPERTY(resizeMode, NSString)
RCT_EXPORT_VIEW_PROPERTY(onChange, RCTBubblingEventBlock);

@end
