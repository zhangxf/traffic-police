package org.trafficpolice.security.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUser<T> extends User {
	
	private static final long serialVersionUID = 8885697678459013367L;
	
	private T currentUser;
	
	public AuthUser(T currentUser, UserDetails user) {
		this(currentUser, user.getUsername(), user.getPassword(), true, true, true, true, user.getAuthorities());
	}
	
	public AuthUser(T currentUser, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		this(currentUser, username, password, true, true, true, true, authorities);
	}
	
	public AuthUser(T currentUser, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, true, true, true, true, authorities);
		this.currentUser = currentUser;
	}

	public T getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(T currentUser) {
		this.currentUser = currentUser;
	}
    
}
