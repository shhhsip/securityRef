package com.cos.security1.config.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cos.security1.model.User;

import lombok.Data;

/**
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
 * 로그인 진행이 완료되면 시큐리티 session을 만들어준다.
 * 오브젝트 타입 => Authentication 타입 객체
 * Authentication 안에 user 정보가 있어야 됨.
 * User오브젝트타입 => UserDetails 타입 객체
 * 
 * Security session => Authentication => UserDetails
 * 
 * @author 1700
 *
 */

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
	
	private User user; // 컴포지션
	private Map<String, Object> attributes;
	
	
	//일반 로그인
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// OAuth 로그인
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}
	
	// 해당 user의 권한을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 사이트에 1년동안 회원이 로그인을 안하면 휴먼계정이면?
		// 현재시간 - 로긴시간 ( 마지막 로그인 날짜가 있으면 가져와서 계산 )
		return true;
	}


	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
