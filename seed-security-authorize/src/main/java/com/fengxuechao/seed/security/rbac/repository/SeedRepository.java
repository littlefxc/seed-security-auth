package com.fengxuechao.seed.security.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SeedRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
