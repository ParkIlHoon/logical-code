package com.example.springsecuritystudy.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher requestMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
         this.orRequestMatcher = new OrRequestMatcher(pathToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList()));
         this.requestMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        return !orRequestMatcher.matches(httpServletRequest) && requestMatcher.matches(httpServletRequest);
    }
}
