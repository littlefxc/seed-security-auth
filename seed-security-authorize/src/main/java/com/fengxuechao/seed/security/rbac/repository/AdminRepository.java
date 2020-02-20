package com.fengxuechao.seed.security.rbac.repository;

import com.fengxuechao.seed.security.rbac.domain.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends SeedRepository<Admin> {

	Admin findByUsername(String username);

}
