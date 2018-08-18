package com.adouae.toubib.web.managedBean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;


@Named(value = "templateBean")
@SessionScoped
public class TemplateBean implements Serializable {


	private static final long serialVersionUID = 1L;
	private String page;
	private static Map<String,Object> countries;
	private String localeCode ;
	
	static{
		countries = new LinkedHashMap<String,Object>();
		countries.put("Arabic", new Locale("ar"));
		countries.put("French", Locale.FRANCE); //label, value
		countries.put("English", Locale.ENGLISH);
	}

	public TemplateBean() {
	}

	public Map<String, Object> getCountriesInMap() {
		return countries;
	}
	
	
	public static Map<String, Object> getCountries() {
		return countries;
	}

	public static void setCountries(Map<String, Object> countries) {
		TemplateBean.countries = countries;
	}

	public String getLocaleCode() {
		return localeCode;
	}

	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}


	@PostConstruct
	public void init() {
		this.page = "schedule"; // Ensure that default is been set.
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
	//value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e) {
		
		String newLocaleValue = e.getNewValue().toString();
		
		//loop country map to compare the locale code
                for (Map.Entry<String, Object> entry : countries.entrySet()) {
        
        	   if(entry.getValue().toString().equals(newLocaleValue)){
        		
        		FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());
        		
        	  }
               }
	}
}
