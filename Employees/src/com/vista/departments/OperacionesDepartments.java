package com.vista.departments;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;
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
import com.mx.entidades.departments;

public class OperacionesDepartments {
	@Wire("#departmentsCRUD")
	private Window win;
	private departments selectedRecord;
	private String recordMode;
	Connection conn;
	Statement oSt = null;
	public departments getSelectedRecord() {
		return selectedRecord;
	}
	public String getRecordMode() {
		return recordMode;
	}
	public void setSelectedRecord(departments selectedRecord) {
		this.selectedRecord = selectedRecord;
	}
	public void setRecordMode(String recordMode) {
		this.recordMode = recordMode;
	}
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("selectedRecord") departments obtenerDepartments,
			@ExecutionArgParam("recordMode") String recordMode) throws CloneNotSupportedException {
		
		  Selectors.wireComponents(view, this, false);
		  setRecordMode(recordMode);
		  if (recordMode.equals("NEW")) {
				this.selectedRecord = new departments();

			}
			if (recordMode.equals("EDIT")) {
				this.selectedRecord = obtenerDepartments;
				
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


public void save() {
	Map<String, Object> args = new HashMap<String, Object>();
	DataSource ds =null;
	String sSQL=null;
	try {
		
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
		String dept_no=dbSigDep(conn);
		selectedRecord.setDept_no(dept_no);
		 if(selectedRecord.getDept_no().equals("")){
			 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
			}
		 
		 if(selectedRecord.getDept_name().equals("")){
			 Messagebox.show("Campo Obligatorio:["+"Dept Name"+"]", "INFORMACIÓN", Messagebox.OK,
						Messagebox.INFORMATION);
			
				return;
		 }
		 
		
		 
	

			sSQL = "INSERT INTO " +
			"departments " +
			"(dept_no, dept_name)" +
		   " VALUES ('" 
		   + selectedRecord.getDept_no().trim()
		   
		   +"','"+selectedRecord.getDept_name().trim()
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
public String dbSigDep(Connection ConCur){
	 Statement oSt = null;
	 ResultSet oRs = null;
	 String sSQL= "";
	 String clave="";
	 String format="";
	 try{
		 sSQL ="SELECT MAX(dept_no) Valor FROM departments";	 	 
		 oSt = ConCur.createStatement();
		 oRs = oSt.executeQuery(sSQL);
	
		 if(oRs.next()){
			 clave=oRs.getString(1);
			 String dept_no=clave.substring(1,4);
			 int sig=Integer.parseInt(dept_no);
			 sig++;
			 clave=" "+ sig++;
			 format="d0"+clave.trim();
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
	 if(selectedRecord.getDept_no().equals("")){
		 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
					Messagebox.INFORMATION);
		
			return;
		}
	 
	 if(selectedRecord.getDept_name().equals("")){
		 Messagebox.show("Campo Obligatorio:["+"First_Name"+"]", "INFORMACIÓN", Messagebox.OK,
					Messagebox.INFORMATION);
		
			return;
	 }
	 
	
	   sSQL = "UPDATE departments SET " +
	   		"dept_name  = '" +selectedRecord.getDept_name().trim()+ 
	   		"'WHERE dept_no='"+selectedRecord.getDept_no().trim()+"'";
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

}
