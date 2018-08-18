package com.adouae.toubib.web.managedBean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {

	private static final long serialVersionUID = 1L;
	// this managed property will read value from request parameter pageId
	@ManagedProperty(value = "#{param.pageId}")
	private String pageId;

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String moveToPage1() {
		return "login";
	}

	public String showPage() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */

			return "/views/secure/client/homeClient.xhtml?faces-redirect=true";

		} else if (pageId.equals("register")) {

			return "/views/public/loginRegister/register.xhtml?faces-redirect=true";

		} else if (pageId.equals("login")) {

			return "/views/public/loginRegister/login.xhtml?faces-redirect=true";

		} else if (pageId.equals("clients")) {

			return "/views/secure/client/clients.xhtml?faces-redirect=true";
			
		} else if (pageId.equals("forgotPassword")) {

			return "forgotPassword";

		} if (pageId.equals("contactUs")) {

			return "/views/public/components/contactUs.xhtml?faces-redirect=true";

		} else {

			return "/views/public/components/home.xhtml?faces-redirect=true";
		}
	}

}