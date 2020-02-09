package com.fengxuechao.seed.security.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author fengxuechao
 * @date 2019-09-10
 */
@Slf4j
@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户信息
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名:{}", username);
        String encodePassword = passwordEncoder.encode("123456");
        log.debug("表单登录密码:{}", encodePassword);
        return buildUser(username, encodePassword);
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId the user ID used to lookup the user details
     * @return the SocialUserDetails requested
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户名:{}", userId);
        String encodePassword = passwordEncoder.encode("123456");
        log.debug("社交登录密码:{}", encodePassword);
        return buildUser(userId, encodePassword);
    }

    private SocialUser buildUser(String userId, String encodePassword) {
        return new SocialUser(userId, encodePassword,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin, ROLE_USER"));
    }
}
