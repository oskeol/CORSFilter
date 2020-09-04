package com.lhind.codebeamer.icingafacade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class CORSFilter implements Filter {

    @Value("${cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String requestOrigin = req.getHeader("Origin");

        List<Pattern> allowedOriginsRegex = new ArrayList<>();

        for(String origin: allowedOrigins){
            allowedOriginsRegex.add(Pattern.compile(origin));
        }

        if(requestOrigin != null){

            String result = allowedOriginsRegex.stream()
                    .filter(pattern -> pattern.matcher(requestOrigin).matches())
                    .map(pattern -> requestOrigin)
                    .findFirst()
                    .orElse(null);

            HttpServletResponse res = (HttpServletResponse) response;
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Origin", result);
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
            if (((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
                response.getWriter().println("ok");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
