package com.vista.dept_manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;
import com.mx.entidades.departments;
import com.mx.entidades.dept_manager;
import com.mx.entidades.employees;
import com.vista.employees.VistaEmployees;

public class VistaDeptManager {
	
	private dept_manager selectedItem;
	private ArrayList<dept_manager> showDeptManager=new ArrayList<dept_manager>();
	private ArrayList<dept_manager> listaDeptManager=new ArrayList<dept_manager>();
	private ArrayList<employees>  showEmployees=new ArrayList<employees>();
	private ArrayList<departments> showDepartments = new ArrayList<departments>();

	private Integer seletectItemIndex;
	private Connection conn = null;
	private ResultSet oRs = null;
	private Statement oSt = null;
	private String sSQL = null;
	private DataSource ds = null;
	public dept_manager getSelectedItem() {
		return selectedItem;
	}
	public ArrayList<dept_manager> getShowDeptManager() {
		return showDeptManager;
	}
	public void setSelectedItem(dept_manager selectedItem) {
		this.selectedItem = selectedItem;
	}
	public void setShowDeptManager(ArrayList<dept_manager> showDeptManager) {
		this.showDeptManager = showDeptManager;
	}
	public Integer getSeletectItemIndex() {
		return seletectItemIndex;
	}
	public void setSeletectItemIndex(Integer seletectItemIndex) {
		this.seletectItemIndex = seletectItemIndex;
	}
	
	
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		 String  emp_no;
		 String  dept_no;
		 String  from_date;
		 String  to_date;
		dept_manager obj = null;
		
		/*variables de empleado*/
		 String   emp_no_empleado;
		 String   birth_date;
		 String   first_name;
		 String   last_name;
		 String   gender;  
		 String   hire_date;
		 employees objempleado=null;
		/*end variables de empleado*/
		 
		 /*varianles de departamento*/
		    String dept_no_departamento = null;
			String dept_name = null;
			departments objdepartamento = null;
		 /*end variables de departamento*/

		try {

			try {
				// conexion al datasource
				ds = (DataSource) new InitialContext()
						.lookup("java:comp/env/jdbc/MyDB");
				conn = ds.getConnection();
			} catch (Exception e) {
				Messagebox
						.show("No se establecio conexión"
								+ "Revise la configuracion del DS:",
								"Exception DataSource", Messagebox.OK,
								Messagebox.ERROR);
				ds = null;
				conn = null;
				return;
			}
			
		
			
			
			
			

			oSt = conn.createStatement();
			sSQL = "SELECT * FROM dept_manager";
			oRs = oSt.executeQuery(sSQL);
			if (oRs.next() == false) {
				// System.out.println("No se encontraron listado de datos");
				Messagebox.show("No se encontraro ningun registro:",
						"Informacion" + "INFO:",
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}

			do {
				obj = new dept_manager();
				emp_no = (oRs.getString("emp_no")) != null ? oRs
						.getString("emp_no") : "";
				obj.setEmp_no(emp_no);
				dept_no = (oRs.getString("dept_no")) != null ? oRs
						.getString("dept_no") : "";
				obj.setDept_no(dept_no);
				from_date = (oRs.getString("from_date")) != null ? oRs
						.getString("from_date") : "";
				obj.setFrom_date(from_date);
				to_date = (oRs.getString("to_date")) != null ? oRs
						.getString("to_date") : "";
				obj.setTo_date(to_date);
				listaDeptManager.add(obj);
			} while (oRs.next());
			
			
			try {
				for (dept_manager dept_manager : listaDeptManager) {
					 oSt = conn.createStatement();
				     sSQL = "SELECT * FROM employees WHERE emp_no='"+dept_manager.getEmp_no().trim()+"'";
						oRs = oSt.executeQuery(sSQL);
						if (oRs.next() == false) {
							// System.out.println("No se encontraron listado de datos");
							Messagebox.show("Informacion:",
									"No se encontraro ningun registro" + "INFO:",
									Messagebox.OK, Messagebox.INFORMATION);
							return;
						}
						
						do{
							objempleado=new employees();
							
							emp_no_empleado= (oRs.getString("emp_no")) != null ? oRs
										.getString("emp_no") : "";
										objempleado.setEmp_no(emp_no_empleado);
							  
							  birth_date= (oRs.getString("birth_date")) != null ? oRs
										.getString("birth_date") : "";
										objempleado.setBirth_date(birth_date);
							  first_name= (oRs.getString("first_name")) != null ? oRs
										.getString("first_name") : "";
										objempleado.setFirst_name(first_name);
							  last_name= (oRs.getString("last_name")) != null ? oRs
										.getString("last_name") : "";
										objempleado.setLast_name(last_name);
							  gender= (oRs.getString("gender")) != null ? oRs
										.getString("gender") : "";
										objempleado.setGender(gender);
							  hire_date= (oRs.getString("hire_date")) != null ? oRs
										.getString("hire_date") : "";
										objempleado.setHire_date(hire_date);
							  
							 showEmployees.add(objempleado); 
						}while(oRs.next());
						
						
						if (oRs != null)
							oRs.close();

						if (oSt != null)
							oSt.close();
				}
				
				
				
			} catch (Exception e) {
			System.out.println("Error Empleados:"+e.getMessage());
			}
			
			
			try {
			
			  /*Metodo que solamente obtiene la informacion que pertenece al Id de dept_Manager*/
				//for (dept_manager dept_manager : listaDeptManager) {
					oSt = conn.createStatement();
					//sSQL = "SELECT* FROM departments WHERE dept_no='"+dept_manager.getDept_no().trim()+"'";
					sSQL = "SELECT* FROM departments";
					oRs = oSt.executeQuery(sSQL);
					if (oRs.next() == false) {
						// System.out.println("No se encontraron listado de datos");
						Messagebox.show("No se encontraro ningun registro:",
								"Informacion" + "INFO:",
								Messagebox.OK, Messagebox.INFORMATION);
						return;
					}

					do {
						objdepartamento = new departments();
						dept_no_departamento = (oRs.getString("dept_no")) != null ? oRs
								.getString("dept_no") : "";
								objdepartamento.setDept_no(dept_no_departamento);
						dept_name = (oRs.getString("dept_name")) != null ? oRs
								.getString("dept_name") : "";
								objdepartamento.setDept_name(dept_name);
						showDepartments.add(objdepartamento);
					} while (oRs.next());

					if (oRs != null)
						oRs.close();

					if (oSt != null)
						oSt.close();
					
			//	}
				
				
			} catch (Exception e) {
				System.out.println("Error departamentos:"+e.getMessage());
			}
			
			
			
			
			
			if (listaDeptManager != null)
				for (dept_manager omsgFilms : listaDeptManager) {
					dept_manager oDept= new dept_manager();
					oDept.parser(omsgFilms,showEmployees,showDepartments);
					showDeptManager.add(oDept);
					oDept = null;
				}
			
			

			if (oRs != null)
				oRs.close();

			if (oSt != null)
				oSt.close();

		} catch (SQLException err) {
			System.out.println("Error SQLException:" + err.getErrorCode() + ":"
					+ err.getSQLState() + ":" + err.getMessage());
			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			conn = null;
			obj = null;
			objempleado=null;
			objdepartamento=null;
		}

		catch (Exception e) {
			Messagebox.show("Exception Error:" + e, "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			conn = null;
			obj = null;
			objempleado=null;
			objdepartamento=null;
		} finally {

			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			conn = null;
			obj = null;
			objempleado=null;
			objdepartamento=null;

		}

	}
	
	

	
	@Command
	public void onAddNew() {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		map.put("showEmployees", showEmployees);
		map.put("showDepartments", showDepartments);
		Executions.createComponents("/dept_manager/dialog-dept-manager.zul", null,map);
	}
	
	@Command
	public void onEdit(@BindingParam("dept_managerRecord") dept_manager dept_man) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		this.selectedItem = dept_man;
		map.put("selectedRecord", dept_man);
		map.put("recordMode", "EDIT");
		map.put("showEmployees", showEmployees);
		map.put("showDepartments", showDepartments);
		setSeletectItemIndex(showDeptManager.indexOf(selectedItem));
		Executions.createComponents(
				"/dept_manager/dialog-editdept-manager.zul", null, map);
	}
	

	@GlobalCommand("refreshList")
	@NotifyChange("showDeptManager")
	public void refreshList(@BindingParam("selectedRecord") dept_manager dept_mag,
			@BindingParam("recordMode") String recordMode) {
		if (recordMode.equals("NEW")) {
			showDeptManager.add(dept_mag);
		}

		if (recordMode.equals("EDIT")) {
			showDeptManager.set(this.seletectItemIndex, dept_mag);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onDelete(@BindingParam("dept_managerRecord") final dept_manager dept_manager) {
		this.selectedItem = dept_manager;
		String str = null;
		try {
			str = "¿Desea eliminar el  Empleado \"" + dept_manager.getName_dept()
					+ "\"?";
			Messagebox.show(str, "Confirmar",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								return;
							} else if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								deleteApp(dept_manager);
							}
						}
					});
			str = null;

		} catch (Exception e) {
			str = null;
		} finally {
			str = null;
		}

	}

	public void deleteApp(dept_manager dept) {
		this.selectedItem = dept;
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
			 if(this.selectedItem.getEmp_no().equals("")){
				 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
				}
			 oSt = conn.createStatement();
			 sSQL = "DELETE FROM dept_manager WHERE emp_no='"+this.selectedItem.getEmp_no().trim()+"'";
			 oSt.executeUpdate(sSQL);
			 conn.commit();
			 showDeptManager.remove(showDeptManager.indexOf(selectedItem));
			Messagebox.show("Registros eliminado correctamente",
					"INFORMACIÓN", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils
					.postNotifyChange(null, null, VistaDeptManager.this, "showDeptManager");
			if (oRs != null)
				oRs.close();

			if (oSt != null)
				oSt.close();
		
		}catch(SQLException err){
			
			System.out.println( "SQLException:"+err.getErrorCode() + ",Error" + err.getMessage());
			 oSt = null;
			 oRs = null;
			 sSQL=null;
		 } catch (Exception e) {
			System.out.println("Exception-->"+e.getMessage());
			sSQL=null;
			ds=null;
			conn=null;
			oSt=null;
		} finally {
			sSQL=null;
			ds=null;
			conn=null;
			oSt=null;
		}
	}

	

}
