package com.vista.titles;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.mx.entidades.employees;
import com.mx.entidades.titles;


public class OperacionesTitle {
	@Wire("#titlesCRUD")
	private Window win;
	private titles selectedRecord;
	private String recordMode;
	private ArrayList<employees>  showEmployees=new ArrayList<employees>();
	private employees employee;
	private String ERROR = "";
	private boolean vacios = true;
	Connection conn;
	Statement oSt = null;
	private Date from_date = new Date();
	private Date to_date = new Date();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//-MM-dd HH:mm:ss.SSS
	@Wire
	private Combobox cmbemployee;
	public titles getSelectedRecord() {
		return selectedRecord;
	}
	public String getRecordMode() {
		return recordMode;
	}
	public ArrayList<employees> getShowEmployees() {
		return showEmployees;
	}

	public employees getEmployee() {
		return employee;
	}

	public void setSelectedRecord(titles selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}
	public void setShowEmployees(ArrayList<employees> showEmployees) {
		this.showEmployees = showEmployees;
	}
	
	public void setEmployee(employees employee) {
		this.employee = employee;
		this.selectedRecord.setEmp_name(this.employee.getFirst_name());
		this.selectedRecord.setEmp_no(this.employee.getEmp_no());
	}

	

	public Date getFrom_date() {
		return from_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	
	
	
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") titles obtenerdept_titles,
			@ExecutionArgParam("showEmployees") ArrayList<employees> showEmployees,
			@ExecutionArgParam("recordMode") String recordMode) throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		String cunvertCurrentfromdate;
		String cunvertCurrentodate;
		this.showEmployees=showEmployees;

		if (recordMode.equals("NEW")) {
			this.selectedRecord = new titles();

		}
		if (recordMode.equals("EDIT")) {
			this.selectedRecord = obtenerdept_titles;
			cunvertCurrentfromdate = this.selectedRecord.getFrom_date();
			cunvertCurrentodate=this.selectedRecord.getTo_date();
			 try {
					from_date = dateFormat.parse(cunvertCurrentfromdate);
					to_date=dateFormat.parse(cunvertCurrentodate);
					setFrom_date(from_date);
					setTo_date(to_date);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dateFormat = null;
					cunvertCurrentfromdate = null;
					cunvertCurrentodate=null;
				} finally {
					dateFormat = null;
					cunvertCurrentfromdate = null;
					cunvertCurrentodate=null;
				}
			
		}
	}
	
	
	@Command
	public void saveThis() {
		validateCmbs();
		if (vacios == true) {
			Messagebox.show("Campos vacios.", "Campos vacios", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		save();
	}
	
	public void save() {
		Map<String, Object> args = new HashMap<String, Object>();
		DataSource ds =null;
		String sSQL=null;
		String datefrom_date=null;
		String dateto_date=null;
		
		try {
			 datefrom_date = dateFormat.format(from_date);
			 dateto_date = dateFormat.format(to_date);
			selectedRecord.setFrom_date(datefrom_date);
			selectedRecord.setTo_date(dateto_date);
			try {
			    ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/MyDB");
				conn = ds.getConnection();

			} catch (SQLException err) {
				System.out.println("No se establecio conexión con la Base de Datos.");
						ds = null;
						conn = null;
				return;
				// TODO: handle exception
			}
			 oSt = conn.createStatement();
			 if(selectedRecord.getEmp_no().equals("")){
				 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
				}
			 
			 if(selectedRecord.getTitle().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"Title"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
		
			 
			 if(selectedRecord.getFrom_date().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"From Date"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 if(selectedRecord.getTo_date().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"To Date"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }


				sSQL = "INSERT INTO " +
				"titles " +
				"(emp_no, title,from_date,to_date)" +
			   " VALUES ('" 
			   + selectedRecord.getEmp_no().trim()+"','"
			   + selectedRecord.getTitle()+"','"
			   + selectedRecord.getFrom_date().trim()+"','"
			   +selectedRecord.getTo_date().trim()
			   +"')";
			   
				oSt.executeUpdate(sSQL);
				conn.commit();
		
			args.put("selectedRecord", this.selectedRecord);
			args.put("recordMode", this.recordMode);
			Messagebox.show("Insertado Correctamente", "INFORMACIÓN", Messagebox.OK,
					Messagebox.INFORMATION);
			BindUtils.postGlobalCommand(null, null, "refreshList", args);
			win.detach();
			if (oSt != null)
				oSt.close();
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
			
		}catch (SQLException err){
			System.out.println( "SQLException:"+err.getErrorCode() + ",Error" + err.getMessage());
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
		}  
		catch (Exception e) {
			Messagebox.show("Exception:" + e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
			
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
			
		} finally {
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
		
		}
		
	}
	
	
	
	@Command
	public void updateThis() throws ParseException {
		update();
	}
	
	@Command
	public void update() throws ParseException {
		Map<String, Object> args = new HashMap<String, Object>();
		DataSource ds =null;
		String sSQL=null;
	try{
		String valorfromdate = this.from_date.toString();
		String valortodate= this.to_date.toString();
		java.util.Date fechaFormateadafromdate = new java.util.Date(valorfromdate);
		java.util.Date fechatodate = new java.util.Date(valortodate);
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date datefromdate;
		Date datetodate;
        String dateStringfromdate="";
        String dateStringtodate="";
		datefromdate = (Date) formatter.parse(fechaFormateadafromdate.toString());
		datetodate = (Date) formatter.parse(fechatodate.toString());
		dateStringfromdate = formato.format(datefromdate);
		dateStringtodate = formato.format(datetodate);
		this.selectedRecord.setFrom_date(dateStringfromdate);
		this.selectedRecord.setTo_date(dateStringtodate);
		try {
		    ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/MyDB");
			conn = ds.getConnection();

		} catch (SQLException err) {
			System.out.println("No se establecio conexión con la Base de Datos.");
					ds = null;
					conn = null;
			return;
			// TODO: handle exception
		}
		 oSt = conn.createStatement();
		 if(selectedRecord.getEmp_no().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Emp_No"+"]","INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
			}
		 
		 if(selectedRecord.getTitle().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Title"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getFrom_date().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"From Date"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getTo_date().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"To_Date"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
	
		   sSQL = "UPDATE titles SET " +
		   		"title = '" +selectedRecord.getTitle().trim()+ "', " +
		   		"from_date = '" +selectedRecord.getFrom_date().trim()+ "', " +
		   		"to_date  = '" +selectedRecord.getTo_date().trim()+ 
		   		"'WHERE emp_no='"+selectedRecord.getEmp_no().trim()+"'";
	           oSt.executeUpdate(sSQL);
	           conn.commit();
			args.put("selectedRecord", this.selectedRecord);
			args.put("recordMode", this.recordMode);
			Messagebox.show("Actualizado Correctamente", "INFORMACIÓN", Messagebox.OK,
					Messagebox.INFORMATION);
			BindUtils.postGlobalCommand(null, null, "refreshList", args);
			win.detach();
			args = null;
			

			if (oSt != null)
				oSt.close();
		}catch (SQLException err){
			System.out.println( "SQLException:"+err.getErrorCode() + ",Error" + err.getMessage());
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
		} 
	
	   catch (Exception e) {
			Messagebox.show("Exception:" + e.getMessage(), "ERROR", Messagebox.OK, Messagebox.ERROR);
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
			
			
		} finally {
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
			
		}
	}
	
	
	
	@Command
	public void closeThis() {
		win.detach();	
	}
	
	@Command
	public void validateCmbs() {
		if (ERROR.equals(cmbemployee.getValue())) {
			vacios = true;
			return;
			// throw new WrongValueException(cmbEstatus, "Debe especificar un
			// valor.");
		} else {
			vacios = false;
		}
	}
	
	
}
