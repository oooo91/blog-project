package com.portfolio.postproject.user.service.login;

import com.portfolio.postproject.user.dto.oauth.OAuthAttributes;
import com.portfolio.postproject.user.dto.oauth.UserSessionDTO;
import com.portfolio.postproject.user.entity.DiaryUser;
import com.portfolio.postproject.user.enums.UserRole;
import com.portfolio.postproject.user.repository.DiaryUserRepository;
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

    private final DiaryUserRepository diaryUserRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //userRequest를 통해 OAuth 서비스에서 가져온 유저 정보를 담고 있는 OAuth2User를 가져온다.
        //어떤 OAuth 서비스인지(github인지 google인지 naver인지) 정보를 가져온다.
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //pk값 가져온다. (구글의 기본 코드는 "sub")
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        //유저의 정보를 map 형태로 꺼내준다.
        Map<String, Object> attributes = oAuth2User.getAttributes();

        //OAuth 서비스에 종속적이지 않는 객체를 얻는다.
        OAuthAttributes attr = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        //db 저장
        DiaryUser diaryUser = saveOrUpdate(attr);

        //세션 정보를 저장하는 직렬화된 dto 클래스
        httpSession.setAttribute("diaryUser", new UserSessionDTO(diaryUser));

        //권한 부여
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(UserRole.SOCIAL.toString())),
                attributes,
                userNameAttributeName);
    }

    //소셜 로그인 시 기존 회원이 존재하면 날짜 업데이트, 아니면 새로 저장
    private DiaryUser saveOrUpdate(OAuthAttributes attributes) {
        Optional<DiaryUser> user = diaryUserRepository.findById(attributes.getUserId());

        if (!user.isPresent()) {
            return diaryUserRepository.save(attributes.toEntity());
        }

        DiaryUser diaryUser = user.get();
        diaryUser.setLoginAt(LocalDateTime.now());
        return diaryUserRepository.save(diaryUser);

    }
}
