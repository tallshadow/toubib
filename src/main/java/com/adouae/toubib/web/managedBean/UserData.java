package com.adouae.toubib.web.managedBean;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.springframework.stereotype.Service;

@ManagedBean(name = "userData")
@RequestScoped
@Service
public class UserData {
	private String localeCode = "fr";
	private String name;

	private Map<String, Object> countries;

	public void countryLocaleCodeChanged(ValueChangeEvent e) {

		String newLocaleValue = e.getNewValue().toString();

		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(newLocaleValue)) {

				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());

			}
		}
	}

	public String actionMethod() {
		return "output";
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getCountries() {
		countries = new LinkedHashMap<String, Object>();
		countries.put("Arabic", new Locale("ar"));
		countries.put("English", Locale.ENGLISH);
		countries.put("French", Locale.FRENCH);
		return countries;
	}
	
	public String test(){
		return "cc";
	}
}