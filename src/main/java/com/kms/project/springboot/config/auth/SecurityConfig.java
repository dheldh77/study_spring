package com.kms.project.springboot.config.auth;

import com.kms.project.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 1. Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 2. csrf().disable().headers().frameOptions().disable()
        // - h2-console 화면을 사용하기 위해 해당 옵션들을 disable 처리

        // 3. authorizeRequests
        // - URL별 권한 관리를 설정하는 옵션의 시작점
        // - authorizeRequest가 선언되어야만 antMachers 옵션을 사용할 수 있음

        // 4. antMatchers
        // - 권한 관리 대상을 지정하는 옵션
        // - URL, HTTP 메소드별 관리가 가능
        // - "/"등 지정된 URL들은 permitAll() 옵션을 통해 전체 열람 권한을 주었음
        // - "/api/vi/**" 주소를 가진 API는 USER 권한을 가진 사람만 가능하도록 함

        // 5. anyRequest
        // - 설정된 값들 이외 나머지 URL들을 나타냄
        // - 여기서는 authenticated()을 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용하게 함
        // - 인증된 사용자 즉, 로그인한 사용자들을 이야기 함

        // 6. logout().logoutSuccessURL("/")
        // - 로그아웃 기능에 대한 여러 설정의 진입점
        // - 로그아웃 성공 시 / 주소로 이동

        // 7. oauth2Login
        // - OAuth2 로그인 기능에 대한 여러 설정의 진입점

        // 8. userInfoEndpoint
        // - OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당

        // 9. userService
        // - 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
        // - 리소스 서비(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**",
                            "/js/**", "/h2-console/**", "/profile").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.
                                                        USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
