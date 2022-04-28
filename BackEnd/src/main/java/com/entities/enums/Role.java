package com.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN, COMPANY, EMPLOYEE;
	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}
}
