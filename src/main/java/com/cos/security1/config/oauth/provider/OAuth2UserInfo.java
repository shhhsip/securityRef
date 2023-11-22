package com.cos.security1.config.oauth.provider;

public interface OAuth2UserInfo {
	String getProviderId();
	String getProvider();
	String getEmail();
	String getName();
	
	// 네이버
	String getProfileImage();
}
