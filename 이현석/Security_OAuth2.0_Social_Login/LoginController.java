package com.mp.PLine.source.login;

import com.mp.PLine.config.BaseException;
import com.mp.PLine.config.BaseResponse;
import com.mp.PLine.config.BaseResponseStatus;
import com.mp.PLine.source.member.MemberRepository;
import com.mp.PLine.source.member.MemberService;
import com.mp.PLine.source.member.dto.req.PostMemberReq;
import com.mp.PLine.source.member.dto.res.PostMemberRes;
import com.mp.PLine.utils.JwtService;
import com.mp.PLine.utils.Validation;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Slf4j
@RestController
public class LoginController {
    private final JwtService jwtService;
    private final MemberService memberService;
        /**
     * Login with kakao API
     * compare kakaoId using AccessToken
     * [POST} /kakao/sign-in
     */
    @ApiOperation("카카오 로그인 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "K-ACCESS-TOKEN", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2004, message = "ACCESS TOKEN을 입력해주세요."),
            @ApiResponse(code = 2028, message = "존재하지 않는 유저입니다.")
    })
    @GetMapping("/kakao")
    public BaseResponse<PostMemberRes> login(HttpServletRequest request) throws BaseException {
        log.info("hi");
//        String kakaoAccessToken = jwtService.extractAccessToken(request)
//                .orElseThrow(() -> new BaseException(BaseResponseStatus.EMPTY_ACCESS_TOKEN));
//        return memberService.findMember(Long.valueOf(kakaoAccessToken));
        return null;
    }

    @PostMapping("/kakao")
    public BaseResponse<PostMemberRes> signUp(HttpServletRequest request,
                                              @RequestBody PostMemberReq postMemberReq) throws BaseException {
        String kakaoAccessToken = jwtService.extractAccessToken(request)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.EMPTY_ACCESS_TOKEN));
        return memberService.signUp(Long.valueOf(kakaoAccessToken));
    }

}
