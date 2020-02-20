package com.fengxuechao.seed.security.rbac.authentication;

import com.fengxuechao.seed.security.rbac.domain.Admin;
import com.fengxuechao.seed.security.rbac.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class RbacUserDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("表单登录用户名:" + username);
		Admin admin = adminRepository.findByUsername(username);
		admin.getUrls();
		return admin;
	}

}
