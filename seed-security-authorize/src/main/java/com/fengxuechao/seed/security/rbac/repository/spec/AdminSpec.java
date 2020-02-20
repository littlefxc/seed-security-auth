package com.fengxuechao.seed.security.rbac.repository.spec;

import com.fengxuechao.seed.security.rbac.domain.Admin;
import com.fengxuechao.seed.security.rbac.dto.AdminCondition;
import com.fengxuechao.seed.security.rbac.repository.support.SeedSpecification;
import com.fengxuechao.seed.security.rbac.repository.support.QueryWraper;

/**
 * @author fengxuechao
 */
public class AdminSpec extends SeedSpecification<Admin, AdminCondition> {

    public AdminSpec(AdminCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWraper<Admin> queryWraper) {
        addLikeCondition(queryWraper, "username");
    }

}
