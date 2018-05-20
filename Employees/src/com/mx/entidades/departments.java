package com.mx.entidades;

public class departments {
	 private String dept_no;
	 private String   dept_name;
	 
	 public departments() {
		setDept_name("");
		setDept_no("");
	}
	 
	public String getDept_no() {
		return dept_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_no(String dept_no) {
		if(dept_no!=null)
			this.dept_no=dept_no.trim();
		else
		this.dept_no = "";
	}
	public void setDept_name(String dept_name) {
		if(dept_name!=null)
			this.dept_name=dept_name.trim();
		else
		this.dept_name = "";
	}
	 
	 
}
