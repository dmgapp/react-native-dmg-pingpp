# react-native-dmg-pingpp

## 官方

官方版react-native组件已经发布 [查看官方版](https://coding.net/u/pingplusplus/p/pingpp-react-native/git)，官方包中ios存在问题

### 0.1.4
- 删除android/libs 改用 maven 管理pingpp依赖包


# 安装
    npm i -S react-native-dmg-pingpp
    
    react-native link react-native-dmg-pingpp
    
    npm run configureDmgPingpp <yourURLScheme>
    
    //例如:
    npm run configureDmgPingpp smartAlipay

# 配置
## iOS
- xcode打开iOS项目,使用pod安装Pingpp iOS SDK并进行相关配置。
- 配置文档详见https://www.pingxx.com/docs/client/sdk/ios

    注意:
    1.配置文档中"接收并处理交易结果"部分的代码,已在npm run configureDmgPingpp时自动添加到AppDelegate.m文件中)。
    2.npm run configureDmgPingpp <yourURLScheme>中的yourURLScheme,就是配置文档中URL Types下添加的URL Schemes。

### 可能遇到的问题

- RNDMGPingPP.m中Pingpp.h文件找不到。
  处理办法: 在TARGETS - Bulid Settings - Hearder Search Paths中添加$(SRCROOT)/../../../ios/Pods/Pingpp/lib。
  Hearder Search Paths添加的值取决于Pingpp.h存放的路径,如果报错,请根据本地路径修改。

 
## Android

在 ./android/app/src/main/AndroidManifest.xml 中加入

```xml

<!-- Ping++ SDK -->
<activity
    android:name="com.pingplusplus.android.PaymentActivity"
    android:configChanges="orientation|screenSize"
    android:launchMode="singleTop"
    android:theme="@android:style/Theme.Translucent.NoTitleBar" >
    
    <!--使用QQ钱包时，需要填写-->
    <intent-filter>
        <action android:name="android.intent.action.VIEW"/>

        <category android:name="android.intent.category.BROWSABLE"/>
        <category android:name="android.intent.category.DEFAULT"/>
        <!-- 填写规则:qwallet + APP_ID -->
        <data android:scheme="qwalletXXXXXXXX"/>
    </intent-filter>

</activity>

<!-- 微信支付 sdk ，也是 Ping++ sdk 调用入口 -->
<activity-alias
    android:name=".wxapi.WXPayEntryActivity"
    android:exported="true"
    android:targetActivity="com.pingplusplus.android.PaymentActivity" />
<!-- 支付宝 sdk -->
<activity
    android:name="com.alipay.sdk.app.H5PayActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:exported="false"
    android:screenOrientation="behind" >
</activity>
<activity
    android:name="com.alipay.sdk.auth.AuthActivity"
    android:configChanges="orientation|keyboardHidden|navigation"
    android:exported="false"
    android:screenOrientation="behind" >
</activity>

<!-- 银联支付 sdk -->
<activity
    android:name="com.unionpay.uppay.PayActivity"
    android:configChanges="orientation|keyboardHidden|navigation|screenSize" />

```


# 使用

```javascript
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
```