package com.portfolio.postproject.user.dto;

import com.portfolio.postproject.user.entity.DiaryUser;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

	private final DiaryUser diaryUser;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(() -> diaryUser.getUserRoles().getUserRole());
		return collection;
	}

	public String getName() {
		return diaryUser.getId();
	}

	@Override
	public String getPassword() {
		return diaryUser.getUserPwd();
	}

	@Override
	public String getUsername() {
		return diaryUser.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
