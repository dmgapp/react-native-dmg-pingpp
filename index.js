/**
 * Created by Zix on 16/9/2.
 */

'use strict';

import { NativeModules, NativeEventEmitter } from 'react-native';

let nativeAPI = NativeModules.RNDMGPingPP;
const myModuleEvt = new NativeEventEmitter( nativeAPI )
let savedCallback = undefined;

myModuleEvt.addListener( 'Pingpp_Resp', resp => {
  const callback = savedCallback;
savedCallback = undefined;
callback && callback( resp );
} );

function waitForResponse() {
  return new Promise( ( resolve, reject ) => {

    // if ( savedCallback ) {
    //   savedCallback( '用户中途取消' );
    // }

    savedCallback = r => {
    savedCallback = undefined;

    const { result, errCode, errMsg } = r;

    if ( result && result === 'success' ) {
      resolve( result );
    } else if ( result && result === 'cancel' ) {
      resolve( result );
    } else {
      const err = new Error( errMsg );
      err.errCode = errCode;
      err.errMsg = errMsg;
      reject( err );
    }
  };
} );
}

export async function pay( charge ) {
  if ( typeof charge === 'string' ) {
    nativeAPI.pay( charge );
  } else {
    nativeAPI.pay( JSON.stringify( charge ) );
  }

  return await waitForResponse();
}