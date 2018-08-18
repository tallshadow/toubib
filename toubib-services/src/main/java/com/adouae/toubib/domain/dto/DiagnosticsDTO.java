package com.adouae.toubib.domain.dto;

public class DiagnosticsDTO {
	private int id;
	private String diagnostics;
	
	public DiagnosticsDTO() {
		super();
	}
	
	
	public DiagnosticsDTO(int id, String diagnostics) {
		super();
		this.id = id;
		this.diagnostics = diagnostics;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(String diagnostics) {
		this.diagnostics = diagnostics;
	}	
	
}

