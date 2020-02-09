package com.fengxuechao.seed.security.app.authentication.openid;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fengxuechao
 * @date 2020-02-09
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

	// @formatter:off
    @Setter @Getter
    private SocialUserDetailsService userDetailsService;

	@Setter @Getter
    private UsersConnectionRepository usersConnectionRepository;
	// @formatter:on

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        Set<String> providerUserIds = new HashSet<>();
        providerUserIds.add((String) authenticationToken.getPrincipal());
        Set<String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(), providerUserIds);

        if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        UserDetails user = userDetailsService.loadUserByUserId(userId);

        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
