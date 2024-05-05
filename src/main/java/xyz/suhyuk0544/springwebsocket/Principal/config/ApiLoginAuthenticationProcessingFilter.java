package xyz.suhyuk0544.springwebsocket.Principal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

@Slf4j
public class ApiLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    protected ApiLoginAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    protected ApiLoginAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    protected ApiLoginAuthenticationProcessingFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl, authenticationManager);
    }

    protected ApiLoginAuthenticationProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager) {
        super(requiresAuthenticationRequestMatcher, authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        JSONObject jsonObject = makeJSONObject(request);

        log.info("login : {}",jsonObject.getString("email"));

        super.successfulAuthentication(request, response, chain, authResult);

    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//
//        JSONObject jsonObject = makeJSONObject(request);
//
//        log.info("login fail : {}",jsonObject.getString("email"));

//        super.unsuccessfulAuthentication(request, response, failed);
//    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (!request.getMethod().equals(HttpMethod.POST.toString()))
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());

        JSONObject jsonObject = makeJSONObject(request);

        String email = jsonObject.getString("email");
        email = (email != null) ? email : "";
        String password = jsonObject.getString("password");
        password = (password != null) ? password : "";



        return this.getAuthenticationManager().authenticate(UsernamePasswordAuthenticationToken.unauthenticated(email,password));
    }


    private JSONObject makeJSONObject(HttpServletRequest request) throws AuthenticationException, IOException {
        return new JSONObject(new JSONTokener(request.getReader()));
    }


    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {

        super.setFilterProcessesUrl(filterProcessesUrl);
    }
}
