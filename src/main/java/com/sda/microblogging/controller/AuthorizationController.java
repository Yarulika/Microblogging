package com.sda.microblogging.controller;

import com.sda.microblogging.exception.IllegalParameterValueException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * @author Ahmad Hamouda
 */
@RestController
@Api(tags = "Authorization")
public class AuthorizationController {

    @Autowired
    private DataSource dataSource;

    @DeleteMapping(path = "/logout/")
    @ApiOperation(value = "Revoke user token.", notes = "If you used this method to logout user, you will need to authorize the swagger again [first press authorize button then logout from the popup and then authorize].")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful response."),
            @ApiResponse(code = 400, message = "Illegal or missing parameter value."),
            @ApiResponse(code = 401, message = "Unauthorized.")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "User's token.", required = true, dataType = "string", paramType = "header"),
            @ApiImplicitParam(name = "authorization-refresh", value = "User's refresh token.", required = true, dataType = "string", paramType = "header")
    })
    @ResponseStatus(code = NO_CONTENT)
    public void revokeToken(HttpServletRequest request) {
        TokenStore tokenStore = new JdbcTokenStore(dataSource);
        String token = request.getHeader("authorization");
        String refreshToken = request.getHeader("authorization-refresh");
        if (token != null && refreshToken != null && token.startsWith("Bearer")) {
            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token.split(" ")[1]);
            OAuth2RefreshToken oAuth2RefreshToken = tokenStore.readRefreshToken(refreshToken);
            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (oAuth2AccessToken != null) {
                tokenStore.removeAccessToken(oAuth2AccessToken);
                tokenStore.removeRefreshToken(oAuth2RefreshToken);
            }
        } else {
            throw new IllegalParameterValueException("Missing tokens");
        }
    }

}
