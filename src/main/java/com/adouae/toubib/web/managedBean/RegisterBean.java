package com.adouae.toubib.web.managedBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.itf.domain.service.ManageUsers;


@SuppressWarnings("deprecation")
@ManagedBean(name="registerBean")
@RequestScoped
@Component
public class RegisterBean {
	
	UserDTO userDto;
	
	@Autowired
	private transient ManageUsers manageUser;

	@Autowired
	private AuthenticationManager authenticationManager ;
	
	//@Autowired
	//private LoginBean loginBean ;
	
	public RegisterBean() {
		this.userDto = new UserDTO() ;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public UserDTO getUser() {
		return userDto;
	}

	public void setUser(UserDTO user) {
		this.userDto = user;
	}

	public ManageUsers getManageUser() {
		return manageUser;
	}

	public void setManageUser(ManageUsers manageUser) {
		this.manageUser = manageUser;
	}

	public String addUser() throws IOException,AccountStatusException{
		
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
			if(!manageUser.checkUser(userDto.getEmail())){
				
				manageUser.addUser(userDto, 0);
				FacesContext.getCurrentInstance().addMessage(null,
						 new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Votre Inscription est effectuée !"));
				
				initUserDto();
				
				 return "/views/public/loginRegister/login.xhtml?faces-redirect=true";
				
			}
			FacesContext.getCurrentInstance().addMessage(null,
					 new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", "Cette adresse e-mail est déjà utilisée!"));
			return "/views/public/loginRegister/register.xhtml?faces-redirect=true";
		
	}
	
	
	public void authenticateUserAndSetSession(UserDTO user, HttpServletRequest request) {
        String username = user.getEmail();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
	
	
	 public void autoLogin(UserDTO userDto) {
		 
		 Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		 		 
		 Authentication request = new UsernamePasswordAuthenticationToken(
					userDto.getEmail(), userDto.getPassword(), authorities);
		 
		 try{
			Authentication result = authenticationManager.authenticate(request);
			
			SecurityContextHolder.getContext().setAuthentication(result);
			//HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();		
		 } catch(Exception e){
	         e.printStackTrace();
		 }
		
	    }
	
	    public String doAutoLogin(String username, String password,HttpServletRequest request) {
	
	        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

	        Authentication auth = authenticationManager.authenticate(token);

	        if (!(auth instanceof AnonymousAuthenticationToken)) {
			    /* The user is logged in :) */
			    return "/views/secure/client/homeClient.xhtml?faces-redirect=true";
			}
	        // Place the new Authentication object in the security context.
	        SecurityContextHolder.getContext().setAuthentication(auth);

	        //this step is import, otherwise the new login is not in session which is required by Spring Security
	        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

	        return "/views/secure/client/homeClient.xhtml?faces-redirect=true";
	    }
	 
	public void initUserDto(){
//		userDto.setEmail(null);
//		userDto.setPassword(null);
		userDto = new UserDTO();	
	}
	
	
}
