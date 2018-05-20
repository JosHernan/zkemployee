package com.vista.salaries;

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

import com.mx.entidades.employees;
import com.mx.entidades.salaries;

public class VistaSalaries {
	
	private salaries selectedItem;
	private ArrayList<salaries> showSalaries=new ArrayList<salaries>();
	private ArrayList<salaries> listSalaries=new ArrayList<salaries>();
	private ArrayList<employees>  showEmployees=new ArrayList<employees>();


	private Integer seletectItemIndex;
	private Connection conn = null;
	private ResultSet oRs = null;
	private Statement oSt = null;
	private String sSQL = null;
	private DataSource ds = null;
	public salaries getSelectedItem() {
		return selectedItem;
	}
	public ArrayList<salaries> getShowSalaries() {
		return showSalaries;
	}
	public void setSelectedItem(salaries selectedItem) {
		this.selectedItem = selectedItem;
	}
	public void setShowSalaries(ArrayList<salaries> showSalaries) {
		this.showSalaries = showSalaries;
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
		 String  salary;
		 String  from_date;
		 String  to_date;
		 salaries obj = null;
		
		/*variables de empleado*/
		 String   emp_no_empleado;
		 String   birth_date;
		 String   first_name;
		 String   last_name;
		 String   gender;  
		 String   hire_date;
		 employees objempleado=null;
		/*end variables de empleado*/
		 
		

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
			sSQL = "SELECT * FROM salaries";
			oRs = oSt.executeQuery(sSQL);
			if (oRs.next() == false) {
				// System.out.println("No se encontraron listado de datos");
				Messagebox.show("No se encontraro ningun registro:",
						"Informacion" + "INFO:",
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}

			do {
				obj = new salaries();
				emp_no = (oRs.getString("emp_no")) != null ? oRs
						.getString("emp_no") : "";
				obj.setEmp_no(emp_no);
				salary = (oRs.getString("salary")) != null ? oRs
						.getString("salary") : "";
				obj.setSalary(salary);
				from_date = (oRs.getString("from_date")) != null ? oRs
						.getString("from_date") : "";
				obj.setFrom_date(from_date);
				to_date = (oRs.getString("to_date")) != null ? oRs
						.getString("to_date") : "";
				obj.setTo_date(to_date);
				listSalaries.add(obj);
			} while (oRs.next());
			
			
			try {
				for (salaries titles_list : listSalaries) {
					 oSt = conn.createStatement();
				     sSQL = "SELECT * FROM employees WHERE emp_no='"+titles_list.getEmp_no().trim()+"'";
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
			
			
		
			
			
			
			
			
			if (listSalaries != null)
				for (salaries omsgFilms : listSalaries) {
					salaries objSalary= new salaries();
					objSalary.parser(omsgFilms,showEmployees);
					showSalaries.add(objSalary);
					objSalary = null;
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
			
		} finally {

			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			conn = null;
			obj = null;
			objempleado=null;
		

		}

	}
	
	

	
	@Command
	public void onAddNew() {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		map.put("showEmployees", showEmployees);

		Executions.createComponents("/salary/dialog-salary.zul", null,map);
	}
	
	@Command
	public void onEdit(@BindingParam("salariesRecord") salaries salary) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		this.selectedItem = salary;
		map.put("selectedRecord", salary);
		map.put("recordMode", "EDIT");
		map.put("showEmployees", showEmployees);
		setSeletectItemIndex(showSalaries.indexOf(selectedItem));
		Executions.createComponents(
				"/salary/dialog-edit-salary.zul", null, map);
	}
	

	@GlobalCommand("refreshList")
	@NotifyChange("showSalaries")
	public void refreshList(@BindingParam("selectedRecord") salaries salary,
			@BindingParam("recordMode") String recordMode) {
		if (recordMode.equals("NEW")) {
			showSalaries.add(salary);
		}

		if (recordMode.equals("EDIT")) {
			showSalaries.set(this.seletectItemIndex, salary);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onDelete(@BindingParam("salariesRecord") final salaries salary) {
		this.selectedItem = salary;
		String str = null;
		try {
			str = "¿Desea eliminar el  Salary \"" + salary.getSalary()
					+ "\"?";
			Messagebox.show(str, "Confirmar",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								return;
							} else if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								deleteApp(salary);
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

	public void deleteApp(salaries salary) {
		this.selectedItem = salary;
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
			 sSQL = "DELETE FROM salary WHERE emp_no='"+this.selectedItem.getEmp_no().trim()+"'";
			 oSt.executeUpdate(sSQL);
			 conn.commit();
			 showSalaries.remove(showSalaries.indexOf(selectedItem));
			Messagebox.show("Registros eliminado correctamente",
					"INFORMACIÓN", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils
					.postNotifyChange(null, null, VistaSalaries.this, "showSalaries");
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
