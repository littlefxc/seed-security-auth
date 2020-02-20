package com.fengxuechao.seed.security.rbac.repository;

import com.fengxuechao.seed.security.rbac.domain.Resource;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends SeedRepository<Resource> {

	Resource findByName(String name);

}
