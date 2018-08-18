package com.adouae.toubib.web.managedBean;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.adouae.toubib.domain.dto.PasswordDto;
import com.adouae.toubib.domain.dto.UserDTO;
import com.adouae.toubib.error.UserNotFoundException;
import com.adouae.toubib.impl.persistence.entity.PasswordResetToken;
import com.adouae.toubib.impl.persistence.entity.User;
import com.adouae.toubib.itf.domain.service.ManageOrder;
import com.adouae.toubib.itf.domain.service.ManageUsers;
import com.adouae.toubib.security.LoginBean;

@ManagedBean
@Scope("session")
@Component
@PropertySource("classpath:META-INF/config/email.properties")
public class UserController {

	@Autowired
	private transient ManageUsers manageUser;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserData userData;

	@Autowired
	private ClientBean clientBean;
	
	@Autowired
	private OrderBean orderBean;
	
	@Autowired
	private transient ManageOrder manageOrder;

	public OrderBean getOrderBean() {
		return orderBean;
	}

	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}

	private String userEmail;
	private String contactMsg;

	public String getContactMsg() {
		return contactMsg;
	}

	public void setContactMsg(String contactMsg) {
		this.contactMsg = contactMsg;
	}

	private Boolean changePwdRendred = true;
	private Boolean forgotPwdRendred = true;
	private Boolean errorRendered = false;

	private String errorMsg = "";

	Locale locale;
	ResourceBundle bundle;

	@Autowired
	private LoginBean loginBean = null;

	@PostConstruct
	public void intit() {
		locale = new Locale(userData.getLocaleCode());
		bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);
		setForms(true, false, false);

		loginBean.logout();
	}

	private PasswordDto passwordDto = new PasswordDto();

	public String resetPassword() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (manageUser.getUserDtoByMail(userEmail) == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					bundle.getString("message.badEmail") + userEmail, errorMsg));
			context.getExternalContext().getFlash().setKeepMessages(true);
			return "/views/public/loginRegister/forgotPassword.xhtml";
		}
		UserDTO user = manageUser.getUserDtoByMail(userEmail);

		if (user == null) {
			throw new UserNotFoundException();
		}

		final String token = UUID.randomUUID().toString();

		manageUser.createPasswordResetTokenForUser(user, token);

		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));

		context.addMessage(null, new FacesMessage(bundle.getString("email.confirmSendMail"),
				bundle.getString("message.resetPasswordEmail")));
		context.getExternalContext().getFlash().setKeepMessages(true);

		return "/views/public/loginRegister/forgotPassword.xhtml";
	}

	public Boolean getErrorRendered() {
		return errorRendered;
	}

	public void setErrorRendered(Boolean errorRendered) {
		this.errorRendered = errorRendered;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, UserDTO user) {
		Locale locale = new Locale(userData.getLocaleCode());
		ResourceBundle bundle = ResourceBundle.getBundle("com.adouae.toubib.messages", locale);

		String url = contextPath + "/views/public/loginRegister/resetPassword.xhtml?id=" + user.getId() + "&token="
				+ token;
		String message1 = bundle.getString("email.hello") + user.getFirstName() + ", \n\n"
				+ bundle.getString("email.message1");
		String message2 = bundle.getString("email.message2");
		String message3 = bundle.getString("email.signature");

		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(user.getEmail());
		email.setSubject(bundle.getString("message.resetPassword"));
		email.setText(message1 + "\r\n" + url + "\r\n" + message2 + "\r\n" + message3);
		email.setFrom(env.getProperty("email.from"));
		return email;
	}
	
	//contact team toubib
	public void contactUs(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		SimpleMailMessage email = new SimpleMailMessage();

		email.setTo(env.getProperty("email.from"));
		email.setSubject("Contact Toubib Team");
		email.setText(contactMsg);
		email.setFrom(userEmail);
		mailSender.send(email);
		
		context.addMessage(null, new FacesMessage(bundle.getString("email.confirmReceiptMail"),""));

		//return "/views/public/components/home.xhtml?faces-redirect=true";
	}
	
	// showChangePasswordPage
	public String showChangePasswordPage(Long idUser, String token) {

		PasswordResetToken passToken;
		if (manageUser.getPasswordResetToken(token) == null) {
			errorMsg = bundle.getString("auth.message.invalidToken");
			setForms(true, false, true);
			return "/views/public/loginRegister/forgotPassword.xhtml";

		} else {
			passToken = manageUser.getPasswordResetToken(token);
		}

		User user = passToken.getUser();
		if (passToken == null || user.getId() != idUser) {
			errorMsg = bundle.getString("auth.message.invalidToken");
			setForms(true, false, true);

			return "/views/public/loginRegister/forgotPassword.xhtml?id=" + user.getId() + "&token=" + token;
		}

		Calendar cal = Calendar.getInstance();
		if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {

			setForms(true, false, true);
			return "/views/public/loginRegister/forgotPassword.xhtml?id=" + user.getId() + "&token=" + token;
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);

		setForms(false, true, false);

		// load doctor acount
		initDoctorAcount(manageUser.getUserDto(user.getEmail()));

		return "/views/public/loginRegister/resetPassword.xhtml?id=" + user.getId() + "&token=" + token;
	}

	public void initDoctorAcount(UserDTO doctorDto) {
		
		// load patients list
		clientBean.setClients(manageUser.getAllClients(doctorDto));
		clientBean.setDoctorDto(doctorDto);
		
		// Load consultation list
		//clientBean.setConsultationList(manageUser.getConsultation(clientBean.getSelectedClient(), doctorDto));   
		//clientBean.setConsultationListDate(manageUser.getConsultationDate(doctorDto, clientBean.getSelectedClient()));

		// Load Order List
//		clientBean.setOrderBean(orderBean);
//		orderBean.orderList = manageOrder.getOrder(doctorDto, clientBean.getSelectedClient());
//		orderBean.orderListDate = manageOrder.getOrderDate(loginBean.getCurrentUser(), clientBean.getSelectedClient());

		//orderBean.setSelectedClient(clientBean.getSelectedClient());
		// Payment
		//orderBean.historicalPayment = manageOrder.getPayments(clientBean.getSelectedClient(), loginBean.getCurrentUser());

//		// folder
//		folderBean.setSelectedClient(clientBean.getSelectedClient());
//
//		// schedule
//		listAppClt = manageSchedule.getAppointmentsClts(loginBean.getCurrentUser(), clientBean.getSelectedClient());

	}

	public ManageOrder getManageOrder() {
		return manageOrder;
	}

	public void setManageOrder(ManageOrder manageOrder) {
		this.manageOrder = manageOrder;
	}

	// change user password
	public String changeUserPassword() {

		// HttpServletRequest req = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext()
		// .getRequest();

		User userDto = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		final UserDTO user = manageUser.getUserDtoByMail(userDto.getEmail());
		if (!manageUser.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {

			throw new com.adouae.toubib.error.InvalidOldPasswordException();
		}
		manageUser.changeUserPassword(user, passwordDto.getNewPassword());

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", bundle.getString("message.updatePasswordSuc")));

		return "/views/secure/client/homeClient.xhtml?faces-redirect=true";
	}

	// save password
	public String savePassword() throws IOException {
		User userDto = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		final UserDTO user = manageUser.getUserDtoByMail(userDto.getEmail());
		manageUser.savePassword(user, passwordDto.getNewPassword());

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", bundle.getString("message.updatePasswordSuc")));

		// ExternalContext ec =
		// FacesContext.getCurrentInstance().getExternalContext();
		// ec.redirect(ec.getRequestContextPath() +
		// "/views/secure/client/homeClient.xhtml");
		return "/views/secure/client/homeClient.xhtml?faces-redirect=true";
	}

	// affichage vue
	private void setForms(Boolean forgotPwdRendred, Boolean changePwdRendred, Boolean errorRendered) {
		this.forgotPwdRendred = forgotPwdRendred;
		this.changePwdRendred = changePwdRendred;
		this.errorRendered = errorRendered;
	}

	// getters & setters

	public PasswordDto getPasswordDto() {
		return passwordDto;
	}

	public void setPasswordDto(PasswordDto passwordDto) {
		this.passwordDto = passwordDto;
	}

	public ManageUsers getManageUser() {
		return manageUser;
	}

	public void setManageUser(ManageUsers manageUser) {
		this.manageUser = manageUser;
	}

	public MessageSource getMessages() {
		return messages;
	}

	public void setMessages(MessageSource messages) {
		this.messages = messages;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public Boolean getChangePwdRendred() {
		return changePwdRendred;
	}

	public void setChangePwdRendred(Boolean changePwdRendred) {
		this.changePwdRendred = changePwdRendred;
	}

	public Boolean getForgotPwdRendred() {
		return forgotPwdRendred;
	}

	public void setForgotPwdRendred(Boolean forgotPwdRendred) {
		this.forgotPwdRendred = forgotPwdRendred;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public ClientBean getClientBean() {
		return clientBean;
	}

	public void setClientBean(ClientBean clientBean) {
		this.clientBean = clientBean;
	}

}
