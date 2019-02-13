/**
 * Copyright (c) 2019-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

#import "RCTLivePlayerView.h"
#import <NodeMediaClient/NodeMediaClient.h>

#if __has_include(<React/RCTAssert.h>)
#import <React/RCTBridgeModule.h>
#import <React/RCTEventDispatcher.h>
#import <React/UIView+React.h>
#import <React/RCTLog.h>
#else
#import "RCTBridgeModule.h"
#import "RCTEventDispatcher.h"
#import "UIView+React.h"
#import "RCTLog.h"
#endif


// output log both debug and release
#define RLog( s, ... ) NSLog( @"<%p %@:(%d)> %@", self, [[NSString stringWithUTF8String:__FILE__] lastPathComponent], __LINE__, [NSString stringWithFormat:(s), ##__VA_ARGS__] )

@interface RCTLivePlayerView()<NodePlayerDelegate>

@property (strong,nonatomic) NodePlayer *np;

@end

@implementation RCTLivePlayerView
{
    bool _canSendStatusEvent;
}

- (id)init {
    self = [super init];
    if(self) {
        _np = [[NodePlayer alloc] initWithPremium:@""];
        [_np setPlayerView:self];
        _srcUrl = nil;
        _canSendStatusEvent = NO;
        
        _np.nodePlayerDelegate = self;
    }
    return self;
}

- (void)dealloc{
    [_np stop];
}

- (void)setSrcUrl:(NSString *)srcUrl {
    _srcUrl = srcUrl;
    [_np setInputUrl:srcUrl];
}

-(void)setBufferTime:(int)bufferTime {
    _bufferTime = bufferTime;
    [_np setBufferTime:bufferTime];
}

-(void)setMaxBufferTime:(int)maxBufferTime {
    _maxBufferTime = maxBufferTime;
    [_np setMaxBufferTime:maxBufferTime];
}

-(void)setResizeMode:(NSString *)resizeMode {
    
    int mode = UIViewContentModeScaleToFill;
    if([resizeMode isEqualToString:@"contain"]) {
        mode = UIViewContentModeScaleAspectFit;
    } else if([resizeMode isEqualToString:@"cover"]) {
        mode = UIViewContentModeScaleAspectFill;
    }
    [_np setContentMode:mode];
}

- (void)setPaused:(BOOL)paused {
    _paused = paused;
}

- (void)setMuted:(BOOL)muted {
    [_np setAudioEnable:!muted];
}

- (void)didSetProps:(NSArray<NSString *> *)changedProps
{
    bool needStart = NO;
    
    if ([changedProps containsObject:@"srcUrl"]) {
        // switch srcUrl need stop old srcUrl then start new one
        [_np stop];
        needStart = YES;
    }
    
    if ([changedProps containsObject:@"resizeMode"]) {
        // after set resizeMode, not change immediately, so need stop then start...
        [_np stop:false];
        needStart = YES;
    }
    
    if ([changedProps containsObject:@"paused"]) {
        if (_paused) {
            _canSendStatusEvent = YES;
            if ([_np isLive]) {
                [_np stop:false];
            } else {
                [_np pause];
            }
        } else {
            needStart = YES;
        }
    }
    
    if (!_paused && needStart) {
        _canSendStatusEvent = NO;
        [_np start];
    }
}

- (void)onEventCallback:(nonnull id)sender event:(int)event msg:(nonnull NSString*)msg;
{
    if (_canSendStatusEvent) {
        if ((event=1004 && _np.isLive)) {
            // only live stream playing can send play end. user pause not send play end
            if (!_paused) {
                _onChange(@{ @"type": @"status", @"code": [NSNumber numberWithInt:event], @"msg": msg});
            }
        } else {
            _onChange(@{ @"type": @"status", @"code": [NSNumber numberWithInt:event], @"msg": msg});

        }
    }
}
@end
