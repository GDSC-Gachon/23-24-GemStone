package com.mp.PLine.config.security.handler;

import com.mp.PLine.source.member.MemberRepository;
import com.mp.PLine.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
//    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Long userId = Long.parseLong(extractUsername(authentication)); // 인증 정보에서 Username(userId) 추출

        memberRepository.findById(userId)
                        .ifPresent(user -> {
                                user.updateRefreshToken(null);
                                memberRepository.saveAndFlush(user);
                                }
                        );
        String accessToken = jwtService.createAccessToken(userId);
//        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, null); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        log.info("로그인에 성공하였습니다. 유저ID : {}, AccessToken : {}", userId, accessToken);
//        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}