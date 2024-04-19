package com.project.myBlog.controller;

import com.project.myBlog.dto.GithubProfileDto;
import com.project.myBlog.dto.OAuthDto;
import com.project.myBlog.entity.User;
import com.project.myBlog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${github.client-id}")
    private String clientId;

    @Value("${github.client-secret}")
    private String clientSecret;

    @Value("${github.password}")
    private String githubPassword;

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/auth/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "user/loginForm";
    }

    @GetMapping("/auth/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("user",new User());
        return "user/joinForm";
    }

    @PostMapping("/auth/joinProc")
    public String register(@ModelAttribute User user, Model model) {
        try {
            User createdUser = User.createUser(user, passwordEncoder);
            userService.register(createdUser);
        } catch (BadRequestException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "auth/joinForm";
        }
        return "redirect:/";
    }

    @GetMapping("/oauth/redirect")
    public String githubLogin(@RequestParam String code){
        RestTemplate restTemplate = new RestTemplate();
        log.debug("@@@@@@@@@@@@@@@ code :{}" ,code);
        ResponseEntity<OAuthDto> response = restTemplate.exchange("https://github.com/login/oauth/access_token",
                HttpMethod.POST,
                getAccessToken(code),
                OAuthDto.class);

        String accessToken = response.getBody().getAccessToken();
        log.debug("@@@@@@@@@@@@@@@ accessToken :{}" ,accessToken);
        return "redirect:/oauth/githubLogin/success?access_token="+accessToken;
    }

    private HttpEntity<MultiValueMap<String,String>> getAccessToken(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id",clientId);
        params.add("client_secret",clientSecret);
        params.add("code",code);

        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(params,headers);
    }

    @GetMapping("/oauth/githubLogin/success")
    public String githubLoginSuccess(@RequestParam String access_token, HttpServletRequest request) throws BadRequestException {
        log.debug("@@@@@@@@@@@@@@@ access_token :{}" ,access_token);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GithubProfileDto> response = restTemplate.exchange("https://api.github.com/user"
                , HttpMethod.GET
                , getUserInfo(access_token)
                , GithubProfileDto.class);

        GithubProfileDto githubUser = response.getBody();
        String email = githubUser.getEmail();
        if(null==email){
            email = githubUser.getName()+"@github.com";
        }
        log.debug("@@@@@@@@@@@@@@@ email :{}", email);
        User savedUser = userService.loginByGithubUserSave(email,githubPassword);
        log.debug("@@@@@@@@@@@@@@@ savedUser :{}", savedUser.getEmail());
        log.debug("@@@@@@@@@@@@@@@ githubPassword :{}", githubPassword);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(),
                        githubPassword
                )
        );
        log.debug("@@@@@@@@@@@@@@ authentication.getName() :{}", authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("@@@@@@@@@@@@@@ SecurityContextHolder :{}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "redirect:"+request.getHeader("Referer");
    }

    private HttpEntity<MultiValueMap<String,String>> getUserInfo(String access_token) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "token " + access_token);
        return new HttpEntity<>(requestHeaders);
    }

}
