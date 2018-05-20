package com.mx.entidades;

public class dept_emp {
	    private String emp_no;
	    private String dept_no;
	    private String from_date;
	    private String to_date;
	    
	    public dept_emp() {
			setDept_no("");
			setEmp_no("");
			setFrom_date("");
			setTo_date("");
		}
		public String getEmp_no() {
			return emp_no;
		}
		public String getDept_no() {
			return dept_no;
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
		public void setDept_no(String dept_no) {
			if(dept_no!=null)
				this.dept_no=dept_no.trim();
			else
			this.dept_no = "";
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
