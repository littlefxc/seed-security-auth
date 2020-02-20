package com.fengxuechao.seed.security.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fengxuechao
 */
public interface RbacService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
