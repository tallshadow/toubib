package com.adouae.toubib.tools;

public class Error{
	  
	  /** Creates a new instance of Erreur */
	  public Error() {
	  }
	  
	  // champ
	  private String classe;
	  private String message;

	  // constructeur
	  public Error(String classe, String message){
	    this.setClasse(classe);
	    this.message=message;
	  }
	  
	  // getters et setters

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	  public String getClasse() {
	    return classe;
	  }

	  public void setClasse(String classe) {
	    this.classe = classe;
	  }
	  
	  
	}
