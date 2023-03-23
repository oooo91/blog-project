package com.portfolio.postproject.service.user;

import com.portfolio.postproject.dto.user.OAuthAttributes;
import com.portfolio.postproject.dto.user.UserSessionDto;
import com.portfolio.postproject.entity.user.DiaryUser;
import com.portfolio.postproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //google

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() //sub (pk)
                .getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes(); //user info

        OAuthAttributes attr = OAuthAttributes.of(registrationId, userNameAttributeName, attributes); //OAuth 서비스에 종속적이지 않는 객체
        DiaryUser diaryUser = saveOrUpdate(attr);

        httpSession.setAttribute("diaryUser", new UserSessionDto(diaryUser));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(diaryUser.getUserRoles().getUserRole())),
                attributes,
                userNameAttributeName);
    }

    //소셜 로그인 시 기존 회원이 존재하면 날짜 업데이트, 아니면 새로 저장
    private DiaryUser saveOrUpdate(OAuthAttributes attributes) {
        Optional<DiaryUser> user = userRepository.findById(attributes.getUserId());

        if (!user.isPresent()) {
            return userRepository.save(attributes.toEntity());
        }

        DiaryUser diaryUser = user.get();
        diaryUser.setLoginAt(LocalDateTime.now());
        return userRepository.save(diaryUser);

    }
}
