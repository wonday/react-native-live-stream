/**
 * Copyright (c) 2019-present, Wonday (@wonday.org)
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

import React, {Component} from 'react';
import {PropTypes} from 'prop-types';
import {requireNativeComponent, View, UIManager, findNodeHandle} from 'react-native';


var RCT_LIVE_PLAYER_REF = 'LivePlayer';

export default class LivePlayer extends Component {

    constructor(props) {
        super(props);
    }

    _onChange(event) {
    	if (event.nativeEvent.code==="status") {
			switch (event.nativeEvent.code) {
				//connecting
				case 1000:
					break;
				
				//connected
				case 1001:
					break;
				
				//play end
				case 1004:
					this.props.onEnd && this.props.onEnd();
					break;
			
				//cache loading
				case 1101:
					this.props.onLoading && this.props.onLoading();
					break;
				
				//cache loaded	
				case 1102:
					this.props.onLoad && this.props.onLoad({duration:0});
					break;
				default: {

				}
			}
		}
    }
    
    seek = (percent) => {
    
    }

    render() {
        return <RCTLivePlayer
            {...this.props}
            srcUrl = {(this.props.source&&this.props.source.uri)?this.props.source.uri:""}
            ref={RCT_LIVE_PLAYER_REF}
            onChange={(event) => {this._onChange(event)}}
        />;
    };
}

LivePlayer.name = RCT_LIVE_PLAYER_REF;
LivePlayer.propTypes = {
    source: PropTypes.object,
    bufferTime: PropTypes.number,
    maxBufferTime: PropTypes.number,
    paused: PropTypes.bool,
    resizeMode: PropTypes.oneOf(['contain', 'cover', 'stretch']),
    renderType: PropTypes.oneOf(['SURFACEVIEW', 'TEXTUREVIEW']),
    onLoading: PropTypes.func,
    onLoad: PropTypes.func,
    onEnd: PropTypes.func,
    ...View.propTypes
};

const RCTLivePlayer = requireNativeComponent('RCTLivePlayer', LivePlayer, {
    nativeOnly: {srcUrl: true, onChange: true}
});