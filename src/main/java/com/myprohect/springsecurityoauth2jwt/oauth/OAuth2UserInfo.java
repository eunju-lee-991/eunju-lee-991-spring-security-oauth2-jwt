package com.myprohect.springsecurityoauth2jwt.oauth;


public interface OAuth2UserInfo {
    String getProviderId(); // naver, kakao의 primary key
    String getProvider(); // naver, kakao...
    String getEmail();
    String getName();
}
