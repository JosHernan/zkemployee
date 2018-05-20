package com.mx.entidades;

public class salaries {
	private String emp_no;
	private String salary;
	private String from_date;
	private String  to_date;
	public salaries() {
		setEmp_no("");
		setFrom_date("");
		setSalary("");
		setTo_date("");
	}
	public String getEmp_no() {
		return emp_no;
	}
	public String getSalary() {
		return salary;
	}
	public String getFrom_date() {
		return from_date;
	}
	public String getTo_date() {
		return to_date;
	}
	public void setEmp_no(String emp_no) {
		if(emp_no!=null)
			this.emp_no=emp_no.trim();
		else
		this.emp_no = "";
	}
	public void setSalary(String salary) {
		if(salary!=null)
			this.salary=salary.trim();
		else
		this.salary = "";
	}
	public void setFrom_date(String from_date) {
		if(from_date!=null)
			this.from_date=from_date.trim();
		else
		this.from_date = "";
	}
	public void setTo_date(String to_date) {
		if(to_date!=null)
			this.to_date=to_date.trim();
		else
		this.to_date = "";
	}
	 
	
	 
}
