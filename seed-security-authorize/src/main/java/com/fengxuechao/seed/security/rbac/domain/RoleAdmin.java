package com.fengxuechao.seed.security.rbac.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色用户关系表
 * 
 * @author fengxuechao
 */
@Table(name = "role_users")
@Entity
public class RoleAdmin implements Serializable {

	private static final long serialVersionUID = -8633925383060456086L;

	/**
	 * 数据库表主键
	 */
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 审计日志，记录条目创建时间，自动赋值，不需要程序员手工赋值
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdTime;
	/**
	 * 角色
	 */
	@ManyToOne
	private Role role;
	/**
	 * 管理员
	 */
	@ManyToOne
	private Admin user;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
	/**
	 * @return the admin
	 */
	public Admin getUser() {
		return user;
	}
	/**
	 * @param admin the admin to set
	 */
	public void setUser(Admin admin) {
		this.user = admin;
	}
	
}
