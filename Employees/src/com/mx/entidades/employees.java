package com.mx.entidades;

public class employees {
	 private String emp_no;
	 private String  birth_date;
	 private String   first_name;
	 private String   last_name;
	 private String    gender;  
	 private String   hire_date;
	 
	 public employees() {
		setEmp_no("");
		setBirth_date("");
		setFirst_name("");
		setLast_name("");
		setGender("");
		setHire_date("");
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public String getFirst_name() {
		return first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public String getGender() {
		return gender;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setEmp_no(String emp_no) {
		if(emp_no!=null)
			this.emp_no=emp_no.trim();
			else
		this.emp_no = "";
	}
	public void setBirth_date(String birth_date) {
		if(birth_date!=null)
			this.birth_date=birth_date.trim();
			else
		this.birth_date = "";
	}
	public void setFirst_name(String first_name) {
		if(first_name!=null)
			this.first_name=first_name.trim();
			else
		this.first_name = "";
	}
	public void setLast_name(String last_name) {
		if(last_name!=null)
			this.last_name=last_name.trim();
			else
		this.last_name = "";
	}
	public void setGender(String gender) {
		if(gender!=null)
			this.gender=gender.trim();
			else
		this.gender = "";
	}
	public void setHire_date(String hire_date) {
		if(hire_date!=null)
			this.hire_date=hire_date.trim();
			else
		this.hire_date = "";
	}
	 
	 
	 
}
