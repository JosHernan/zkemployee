package com.vista.employees;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import com.mx.entidades.employees;


public class OperacionesEmployees {
	@Wire("#employeesCRUD")
	private Window win;
	private employees selectedRecord;
	private String recordMode;
	Connection conn;
	Statement oSt = null;
	private Date birth_date = new Date();
	private Date hire_date = new Date();
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//-MM-dd HH:mm:ss.SSS
	public Date getBirth_date() {
		return birth_date;
	}
	public Date getHire_date() {
		return hire_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	public employees getSelectedRecord() {
		return selectedRecord;
	}
	public String getRecordMode() {
		return recordMode;
	}
	public void setSelectedRecord(employees selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") employees obtenerEmployees,
			@ExecutionArgParam("recordMode") String recordMode) throws CloneNotSupportedException {
		Selectors.wireComponents(view, this, false);
		setRecordMode(recordMode);
		String cunvertCurrentDateBirth;
		String cunvertCurrentDateHire;
		if (recordMode.equals("NEW")) {
			this.selectedRecord = new employees();

		}
		if (recordMode.equals("EDIT")) {
			this.selectedRecord = obtenerEmployees;
			cunvertCurrentDateBirth = this.selectedRecord.getBirth_date();
			cunvertCurrentDateHire=this.selectedRecord.getHire_date();
			 try {
					birth_date = dateFormat.parse(cunvertCurrentDateBirth);
					hire_date=dateFormat.parse(cunvertCurrentDateHire);
					setBirth_date(birth_date);
					setHire_date(hire_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					dateFormat = null;
					cunvertCurrentDateBirth = null;
					cunvertCurrentDateHire=null;
				} finally {
					dateFormat = null;
					cunvertCurrentDateBirth = null;
					cunvertCurrentDateHire=null;
				}
			
		}
		
	}
	
	
	@Command
	public void closeThis() {
		win.detach();	
	}
	
	
	@Command
	public void saveThis() {
		
		save();
	}
	@Command
	public void save() {
		Map<String, Object> args = new HashMap<String, Object>();
		DataSource ds =null;
		String sSQL=null;
		String datebirthString=null;
		String datehireString=null;
		try {
			 datebirthString = dateFormat.format(birth_date);
			 datehireString = dateFormat.format(hire_date);
			selectedRecord.setBirth_date(datebirthString);
			selectedRecord.setHire_date(datehireString);
			
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
			String idemp=dbSigEmp(conn);
			selectedRecord.setEmp_no(idemp);
			 if(selectedRecord.getEmp_no().equals("")){
				 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
				}
			 
			 if(selectedRecord.getFirst_name().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"First_Name"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
			 if(selectedRecord.getBirth_date().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"Birth Date"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
			 if(selectedRecord.getLast_name().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"Last Name"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
			 if(selectedRecord.getGender().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"Gender"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
			 if(selectedRecord.getHire_date().equals("")){
				 Messagebox.show("Campo Obligatorio:["+"Hire Date"+"]", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
			 }
			 
			 
		

				sSQL = "INSERT INTO " +
				"employees " +
				"(emp_no, birth_date,first_name,last_name,gender,hire_date)" +
			   " VALUES ('" 
			   + idemp
			   +"','"+selectedRecord.getBirth_date().trim() 
			   +"','"+selectedRecord.getFirst_name().trim() 
			   +"','"+selectedRecord.getLast_name().trim() 
			   +"','"+selectedRecord.getGender().trim() 
			   +"','"+selectedRecord.getHire_date().trim()
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
			datebirthString=null;
			datehireString=null;
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
			datebirthString=null;
			datehireString=null;
		} finally {
			args = null;
			ds = null;
			conn = null;
			oSt=null;
			sSQL=null;
			datebirthString=null;
			datehireString=null;
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
		String valorbirth = this.birth_date.toString();
		String valorhire= this.hire_date.toString();
		java.util.Date fechaFormateada = new java.util.Date(valorbirth);
		java.util.Date fechahire = new java.util.Date(valorhire);
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date datebirth;
		Date datehire;
        String dateStringbirth="";
        String dateStringhire="";
		datebirth = (Date) formatter.parse(fechaFormateada.toString());
		datehire = (Date) formatter.parse(fechahire.toString());
		dateStringbirth = formato.format(datebirth);
		dateStringhire = formato.format(datehire);
		this.selectedRecord.setBirth_date(dateStringbirth);
		this.selectedRecord.setHire_date(dateStringhire);
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
		 
		 if(selectedRecord.getFirst_name().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"First_Name"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getBirth_date().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Birth Date"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getLast_name().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Last Name"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getGender().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Gender"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		 if(selectedRecord.getHire_date().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Hire Date"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		   sSQL = "UPDATE employees SET " +
		   		"birth_date = '" +selectedRecord.getBirth_date().trim()+ "', " +
		   		"first_name = '" +selectedRecord.getFirst_name().trim()+ "', " +
		   		"last_name = '" +selectedRecord.getLast_name().trim()+ "', " +
		   		"gender = '" +selectedRecord.getGender().trim()+ "', " +
		   		"hire_date  = '" +selectedRecord.getHire_date().trim()+ 
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
	
	

	public String dbSigEmp(Connection ConCur){
		 Statement oSt = null;
		 ResultSet oRs = null;
		 String sSQL= "";
		 String clave="";
		 String format="";
		 try{
			 sSQL ="SELECT MAX(emp_no) Valor FROM employees";	 	 
			 oSt = ConCur.createStatement();
			 oRs = oSt.executeQuery(sSQL);
		
			 if(oRs.next()){
				 clave=oRs.getString(1);
				 int sig=Integer.parseInt(clave);
				 sig++;
				 clave=" "+ sig++;
				 format=clave;
			 }
			 
			 if (oSt != null) {oSt.close();oSt = null;}
			 if (oRs != null) {oRs.close(); oRs = null;}
		 }catch(SQLException err){
		
			 
			 oSt = null;
			 oRs = null;
			 sSQL=null;
		 }catch(Exception err){
			
			 oRs = null;
			 oSt = null;
			 sSQL=null;
		 }finally{
			 oSt = null;
			 oRs =null;
			 sSQL=null;
		 }
		 return format;
}
}
