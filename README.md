# react-native-livestream
[![npm](https://img.shields.io/npm/v/react-native-live-stream.svg?style=flat-square)](https://www.npmjs.com/package/react-native-live-stream)

a react-native module for live stream play and publish

## ChangeLog

v1.0.0
1. initial release


## Installation
### Using npm

    npm install react-native-orientation-locker --save
    react-native link react-native-orientation-locker

### Using yard
    yard add react-native-orientation-locker
    react-native link react-native-orientation-locker

## After Installation
### Android
 no addition operation, you can use it now

### iOS
1. Add framework search path
In Xcode, project ```Build Settings``` find ```Framework search path```, 
add ```$(PROJECT_DIR)/../node_modules/react-native-aliyun-push/ios/libs``` to it.

2. Add framework
drag ```node_modules/react-native-aliyun-push/ios/libs/NodeMediaClient.framework``` to Xcode ```frameworks``` folder，select ```create folder references``` then OK.

## LivePlayer

### Feature
Protocol: RTMP/RTMPT/RTMPE/RTSP/HLS/HTTP(S)-FLV
Video codec: H.264, H.265,FLV, VP6, MPEG4
Audio codec: AAC, MP3, SPEEX, NELLYMOSER, ADPCM_SWF, G.711

### Usage

```
import {LivePlayer} from "react-native-live-stream";

<LivePlayer source={{uri:"rtmp://live.hkstv.hk.lxdns.com/live/hks"}}}
   ref={(ref) => {
       this.player = ref
   }}
   style={styles.video}
   paused={false}
   muted={false}
   bufferTime={300}
   maxBufferTime={1000}
   resizeMode={"contain"}
   onLoading={()=>{}}
   onLoad={()=>{}}
   onEnd={()=>{}}
/>
```
