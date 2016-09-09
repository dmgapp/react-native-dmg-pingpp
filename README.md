#react-native-dmg-pingpp

#安装
    npm i -S react-native-dmg-pingpp
    
    react-native link react-native-dmg-pingpp
    
    npm run configureDmgPingpp <yourURLScheme>
    
    例如:
    npm run configureDmgPingpp smartAlipay

#iOS
###配置
- xcode打开iOS项目,使用pod安装Pingpp iOS SDK并进行相关配置。
配置文档详见https://www.pingxx.com/docs/client/sdk/ios

    注意:
    1.配置文档中"接收并处理交易结果"部分的代码,已在npm run configureDmgPingpp时自动添加到AppDelegate.m文件中)。
    2.npm run configureDmgPingpp <yourURLScheme>中的yourURLScheme,就是配置文档中URL Types下添加的URL Schemes。

- 根据以上配置完后,用xcode中TARGETS栏,在 Info -> URL Types -> URL Schemes里配置的值
,替换RNDMGPingPP.xcodeproj -> RNDMGPingPP.m中的static NSString *gScheme = @"此处填写URL Schemes里配置的值"。

###使用
    import { pay } from 'react-native-dmg-pingpp';
    
    function payment( charge ) {
        // 调用支付方法
        pay( charge )
        .then(( response ) => {  // 支付成功或用户取消
          if ( response == 'cancel' ) {
            console.log('用户中途取消' );
          }
          
          // 此处根据返回结果,进行相应处理
          console.log('response', response );
        })
        .catch( ( error ) => {  // 支付出错
          console.log('error', error.errMsg );
        })
    }

###可能遇到的问题
- RNDMGPingPP.m中Pingpp.h文件找不到。
  处理办法: 在TARGETS - Bulid Settings - Hearder Search Paths中添加$(SRCROOT)/../../../ios/Pods/Pingpp/lib。
  Hearder Search Paths添加的值取决于Pingpp.h存放的路径,如果报错,请根据本地路径修改。

 
#Android