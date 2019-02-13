/**
 * Copyright (c) 2019-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import <UIKit/UIKit.h>
#if __has_include(<React/RCTAssert.h>)
#import <React/RCTEventDispatcher.h>
#import <React/UIView+React.h>
#else
#import "RCTEventDispatcher.h"
#import "UIView+React.h"
#endif

@interface RCTLivePlayerView : UIView;
@property (strong, nonatomic) NSString *srcUrl;
@property (nonatomic) int bufferTime;
@property (nonatomic) int maxBufferTime;
@property (nonatomic) BOOL paused;
@property (strong, nonatomic) NSString *resizeMode;

@property(nonatomic, copy) RCTBubblingEventBlock onChange;

@end
