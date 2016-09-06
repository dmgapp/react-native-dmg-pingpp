#react-native-dmg-pingpp

#安装
    npm install -save https://github.com/DMGroup/react-native-dmg-pingpp
    
    rnpm link react-native-dmg-pingpp

#配置
- xcode打开iOS项目,使用pod安装Pingpp iOS SDK并进行相关配置。详见https://www.pingxx.com/docs/client/sdk/ios

#可能遇到的问题
- RNDMGPingPP.m中Pingpp.h文件找不到。
  处理办法: 在TARGETS - Bulid Settings - Hearder Search Paths中添加$(SRCROOT)/../../../ios/Pods/Pingpp/lib。
  Hearder Search Paths添加的值取决于Pingpp.h存放的路径,如果报错,请根据本地路径修改。
  