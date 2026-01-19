package com.example.paymentService.Security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
@RequiredArgsConstructor
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
                    System.out.println(authHeader);

                    if(authHeader != null){
                        requestTemplate.header("Authorization", authHeader);
                        return;
                    }
                }
                String systemToken = jwtService.generateSystemToken();
                requestTemplate.header("Authorization", "Bearer " + systemToken);
                System.out.println("Generated System Token for Kafka Consumer: " + systemToken);
            }
        };
    }
}
