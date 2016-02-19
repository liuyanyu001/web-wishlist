package com.wishlist.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wishlist.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class UserContext implements UserDetails {
	

	private User user;

	public UserContext(User user) {
		this.user = user;
		 
		if( user != null)
		{
			this.username = user.getLogin();
			this.password = user.getPassword();
			this.id = user.getId();
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public UserContext(String username) {
		this.username = username;
	}

	public UserContext(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}
	
	
    @JsonCreator
    public UserContext(@JsonProperty("username") final String username,
                          @JsonProperty("password") final String password) {
        this.setPassword(password);
        this.setUsername(username);

    }
	
	
	private String id;

	private String username;

	private String password;

	private long expires;

	private boolean accountExpired;

	private boolean accountLocked;

	private boolean credentialsExpired;

	private boolean accountEnabled;

	private String newPassword;

	private Set<UserAuthority> authorities;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	@JsonIgnore
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}

	// Use Roles as external API
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			roles.addAll(authorities.stream().map(UserRole::valueOf).collect(Collectors.toList()));
		}
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return !accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}
}
