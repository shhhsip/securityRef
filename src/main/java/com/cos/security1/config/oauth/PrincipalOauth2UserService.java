package com.cos.security1.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	// 구글 후처리 함수( userRequest 데이터에 대한 )
	// 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다 
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		/*
		 * log.info("userRequest.getClientRegistration() = {}",
		 * userRequest.getClientRegistration()); // registrationId로 어떤 Oauth로 로그인했는지 확인
		 * 가능 log.info("userRequest.getAccessToken().getTokenValue() = {}",
		 * userRequest.getAccessToken().getTokenValue());
		 * log.info("userRequest.getAccessToken().getClientName() = {}",
		 * userRequest.getClientRegistration().getClientName());
		 * log.info("super.loadUser(userRequest) = {}", super.loadUser(userRequest));
		 */
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		/*
		 * 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(Oauth-Client라이브러리) -> AccessToken 요청
		 * userRequest 정보 -> 회원프로필 get (loadUser)
		 */
		log.info("getAttributes : {}", oAuth2User.getAttributes());
		
		String provider = userRequest.getClientRegistration().getClientName();
		String providerId = oAuth2User.getAttribute("sub");
		String username = provider+"_"+providerId; // google_29382481oi24~~~
		String email = oAuth2User.getAttribute("email");
		String password = bCryptPasswordEncoder.encode("SNS로그인");
		String role = "ROLE_USER";
		
		User userEntity = userRepository.findByUsername(username);
		if(userEntity == null) {
			userEntity = User.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.provider(provider)
					.provicerId(providerId)
					.build();
			userRepository.save(userEntity);
		}
		
		return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
	}


}
