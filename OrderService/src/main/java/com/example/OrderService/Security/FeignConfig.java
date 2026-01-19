package com.example.OrderService.Security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignConfig {
    private final JwtValidationService jwtService;

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if(attributes != null){
                    HttpServletRequest request = attributes.getRequest();
                    String authHeader = request.getHeader("Authorization");
                    log.info(authHeader);

                    if(authHeader != null){
                        requestTemplate.header("Authorization", authHeader);
                        return;
                    }
                }
                String systemToken = jwtService.generateSystemToken();
                requestTemplate.header("Authorization", "Bearer " + systemToken);
                log.info("Generated System Token for Kafka Consumer: " + systemToken);
            }
        };
    }
}
