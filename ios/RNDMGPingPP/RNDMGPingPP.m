//
//  RNDMGPingPP.m
//  RNDMGPingPP
//
//  Created by Zix on 16/9/2.
//  Copyright © 2016年 Zix. All rights reserved.
//

#import "RNDMGPingPP.h"
#import "Pingpp.h"
#import "RCTBridge.h"
#import "RCTEventDispatcher.h"

@implementation RNDMGPingPP

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

- (instancetype)init {
    self = [super init];
    
    if (self) {
        //    [self _autoGetScheme];
        
        [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(handleOpenURL:) name:@"RCTOpenURLNotification" object:nil];
    }
    
    return self;
}

- (void)dealloc {
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)handleOpenURL:(NSNotification *)note {
    NSDictionary *userInfo = note.userInfo;
    NSString *url = userInfo[@"url"];
    
    [Pingpp handleOpenURL:[NSURL URLWithString:url] withCompletion:^(NSString *result, PingppError *error) {
        [self onResult:result erorr:error];
    }];
}

- (NSArray<NSString *> *)supportedEvents {
    return @[@"Pingpp_Resp"];
}

RCT_EXPORT_METHOD(pay:(NSString *)charge) {
#ifdef DEBUG
    [Pingpp setDebugMode:YES];
#endif
    
    [Pingpp createPayment:charge
             appURLScheme:@"smartAlipay"
           withCompletion:^(NSString *result, PingppError *error) {
               [self onResult:result erorr:error];
           }];
    
    
}

- (void)onResult:(NSString *)result erorr:(PingppError *)error {
    NSMutableDictionary *body = @{}.mutableCopy;
    body[@"result"] = result;
    
    if (![result isEqualToString:@"success"]) {
        body[@"errCode"] = @(error.code);
        body[@"errMsg"] = [error getMsg];
    }
    
    [self sendEventWithName:@"Pingpp_Resp" body:body];
}

//- (void)_autoGetScheme {
//  if (gScheme.length > 0) {
//    return;
//  }
//
//  NSArray *list = [[[NSBundle mainBundle] infoDictionary] valueForKey:@"CFBundleURLTypes"];
//
//  for (NSDictionary *item in list) {
//    NSString *name = item[@"CFBundleURLName"];
//
//    if ([name isEqualToString:@"smartAlipay"]) {
//      NSArray *schemes = item[@"CFBundleURLSchemes"];
//
//      if (schemes.count > 0) {
//        gScheme = schemes[0];
//        break;
//      }
//    }
//  }
//}

@end
