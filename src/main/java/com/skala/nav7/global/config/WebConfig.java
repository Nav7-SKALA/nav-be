package com.skala.nav7.global.config;

import com.skala.nav7.global.auth.jwt.resolver.MemberEntityResolver;
import com.skala.nav7.global.auth.jwt.resolver.MemberIdResolver;
import com.skala.nav7.global.auth.jwt.resolver.ProfileEntityResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final MemberEntityResolver memberEntityResolver;
    private final MemberIdResolver memberIdResolver;
    private final ProfileEntityResolver profileEntityResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdResolver);
        resolvers.add(memberEntityResolver);
        resolvers.add(profileEntityResolver);
    }

}
