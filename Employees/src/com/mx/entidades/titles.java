package com.mx.entidades;

public class titles {
	private String emp_no;
    private String title;
    private String from_date;
    private String to_date;
	public String getEmp_no() {
		return emp_no;
	}
	public String getTitle() {
		return title;
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
	public void setTitle(String title) {
		if(title!=null)
			this.title=title.trim();
		else
		this.title = "";
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
