package com.adouae.toubib.security;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adouae.toubib.impl.persistence.dao.UserDAO;
import com.adouae.toubib.impl.persistence.entity.Role;
 

@SuppressWarnings("deprecation")
@Service
@Transactional(readOnly=true)
@Component

public class CustomUserDetailsService implements UserDetailsService {
	
	@Inject
	private transient UserDAO userDao;
	private Role role;
	
	public CustomUserDetailsService() {
		super();
	}
	
	@Autowired
	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException{
		com.adouae.toubib.impl.persistence.entity.User user = userDao.getUser(email);
		
		boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        //Collection<GrantedAuthority> authoes = new ArrayList<GrantedAuthority>();
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		Iterator<Role> i=user.getRoles().iterator();
//		while(i.hasNext())
//			role=i.next();
		//authorities.add(new GrantedAuthorityImpl(role.getRoleName()));
		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
        return new User(
        	user.getEmail(),
        	user.getPassword(),
            enabled,
            accountNonExpired,
            credentialsNonExpired,
            accountNonLocked,
            authorities
        );
	    }
}