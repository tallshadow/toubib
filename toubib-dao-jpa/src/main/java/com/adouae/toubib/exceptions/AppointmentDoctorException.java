package com.adouae.toubib.exceptions;

import java.io.Serializable;

public class AppointmentDoctorException extends RuntimeException implements Serializable {

	  private static final long serialVersionUID = 1L;
	  // champs privés
	  private int code = 0;

	  // constructeurs
	  public AppointmentDoctorException() {
	    super();
	  }

	  public AppointmentDoctorException(String message) {
	    super(message);
	  }

	  public AppointmentDoctorException(String message, Throwable cause) {
	    super(message, cause);
	  }

	  public AppointmentDoctorException(Throwable cause) {
	    super(cause);
	  }

	  public AppointmentDoctorException(String message, int code) {
	    super(message);
	    setCode(code);
	  }

	  public AppointmentDoctorException(Throwable cause, int code) {
	    super(cause);
	    setCode(code);
	  }

	  public AppointmentDoctorException(String message, Throwable cause, int code) {
	    super(message, cause);
	    setCode(code);
	  }

	  // getters - setters
	  public int getCode() {
	    return code;
	  }

	  public void setCode(int code) {
	    this.code = code;
	  }
	}

