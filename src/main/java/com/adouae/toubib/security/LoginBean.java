package com.adouae.toubib.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.impl.persistence.dao.UserDAO;
import com.adouae.toubib.impl.persistence.entity.User;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.web.managedBean.UserData;

@ManagedBean(name = "loginBean")
//@SessionScoped
@Scope("session")
@Component
@Service
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	String password;
	String email;

	@Inject
	private transient UserDAO userDao;

//	@Autowired
//	private OrderBean orderBean;
//
//	@Autowired
//	private ClientBean clientBean;

//	@Autowired
//	private transient ManageSchedule manageSchedule;

	@Autowired
	private ManageUsers dao;

	@Autowired
	UserData userData;
	
	UserDTO userDto;

	public ManageUsers getDao() {
		return dao;
	}

	public void setDao(ManageUsers dao) {
		this.dao = dao;
	}

	public UserDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	public LoginBean() {
		this.userDto = new UserDTO();

	}

	

	@Autowired(required = true)
	private transient ManageUsers manageUser;

	public ManageUsers getManageUser() {
		return manageUser;
	}

	public void setManageUser(ManageUsers manageUser) {
		this.manageUser = manageUser;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}

	@Inject
	@ManagedProperty(value = "#{authenticationManager}")
	private AuthenticationManager authenticationManager;

	@Autowired
	private transient ManageUsers manageClient;

	public ManageUsers getManageClient() {
		return manageClient;
	}

	public void setManageClient(ManageUsers manageClient) {
		this.manageClient = manageClient;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String login() throws IOException, AccountStatusException {
		
		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
		

		try {
			
			Authentication request = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
			Authentication result = authenticationManager.authenticate(request);

			SecurityContextHolder.getContext().setAuthentication(result);

			HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			// Collection<SimpleGrantedAuthority> authorities =
			// (Collection<SimpleGrantedAuthority>) SecurityContextHolder
			// .getContext().getAuthentication().getAuthorities();
			//
			// for (GrantedAuthority auth : authorities) {
			// if (auth.getAuthority().equals("ROLE_USER"))
			// return "homeClient";
			// }
			
			if (req.isUserInRole("ROLE_USER")) {

//				orderBean.setClients(manageClient.getAllClients(getCurrentUser()));
//				orderBean.setDoctorDto(getCurrentUser());
//				clientBean.setClients(manageClient.getAllClients(getCurrentUser()));
//				clientBean.setDoctorDto(getCurrentUser());
				
				userDto = new UserDTO();

				return "/views/secure/client/homeClient.xhtml?faces-redirect=true";
			}

		} catch (AuthenticationException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("loginFailed"), ""));

			return "/views/public/loginRegister/login.xhtml?faces-redirect=true";
		}

		userDto = new UserDTO();
		return "/views/public/components/home.xhtml?faces-redirect=true";
	}

	public boolean getLoggedin() {
		HttpServletRequest req2 = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (req2.isUserInRole("ROLE_USER"))
			return true;
		else
			return false;
	}

	// get user name
	public String getUserName() {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		if (manageUser.getUserDto(req.getUserPrincipal().getName()).getLastName() != null) {
			return manageUser.getUserDto(req.getUserPrincipal().getName()).getLastName();
		}else if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User userDto =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return userDto.getLastName();
		}

		return "";

	}

	public String logout() {
		SecurityContextHolder.clearContext();
		SecurityContextHolder.getContext().setAuthentication(null);
		return "/views/public/components/home.xhtml?faces-redirect=true";
	}

	public UserDTO getCurrentUser() {
		// HttpServletRequest req = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getRequest();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			String currentUserName = authentication.getName();
			return manageUser.getUserDto(currentUserName);
		}
		return null;

	}

//	public OrderBean getOrderBean() {
//		return orderBean;
//	}

//	public void setOrderBean(OrderBean orderBean) {
//		this.orderBean = orderBean;
//	}
//
//	public ClientBean getClientBean() {
//		return clientBean;
//	}
//
//	public void setClientBean(ClientBean clientBean) {
//		this.clientBean = clientBean;
//	}
//
//	public ManageSchedule getManageSchedule() {
//		return manageSchedule;
//	}
//
//	public void setManageSchedule(ManageSchedule manageSchedule) {
//		this.manageSchedule = manageSchedule;
//	}

}
