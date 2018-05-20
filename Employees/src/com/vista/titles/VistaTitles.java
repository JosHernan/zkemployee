package com.vista.titles;

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
import com.mx.entidades.titles;


public class VistaTitles {
	
	private titles selectedItem;
	private ArrayList<titles> showTitles=new ArrayList<titles>();
	private ArrayList<titles> listatitles=new ArrayList<titles>();
	private ArrayList<employees>  showEmployees=new ArrayList<employees>();


	private Integer seletectItemIndex;
	private Connection conn = null;
	private ResultSet oRs = null;
	private Statement oSt = null;
	private String sSQL = null;
	private DataSource ds = null;
	public titles getSelectedItem() {
		return selectedItem;
	}
	public ArrayList<titles> getShowTitles() {
		return showTitles;
	}
	public void setSelectedItem(titles selectedItem) {
		this.selectedItem = selectedItem;
	}
	public void setShowDeptManager(ArrayList<titles> showTitles) {
		this.showTitles = showTitles;
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
		 String  title;
		 String  from_date;
		 String  to_date;
		 titles obj = null;
		
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
			sSQL = "SELECT * FROM titles";
			oRs = oSt.executeQuery(sSQL);
			if (oRs.next() == false) {
				// System.out.println("No se encontraron listado de datos");
				Messagebox.show("No se encontraro ningun registro:",
						"Informacion" + "INFO:",
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}

			do {
				obj = new titles();
				emp_no = (oRs.getString("emp_no")) != null ? oRs
						.getString("emp_no") : "";
				obj.setEmp_no(emp_no);
				title = (oRs.getString("title")) != null ? oRs
						.getString("title") : "";
				obj.setTitle(title);
				from_date = (oRs.getString("from_date")) != null ? oRs
						.getString("from_date") : "";
				obj.setFrom_date(from_date);
				to_date = (oRs.getString("to_date")) != null ? oRs
						.getString("to_date") : "";
				obj.setTo_date(to_date);
				listatitles.add(obj);
			} while (oRs.next());
			
			
			try {
				for (titles titles_list : listatitles) {
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
			
			
		
			
			
			
			
			
			if (listatitles != null)
				for (titles omsgFilms : listatitles) {
					titles oDept= new titles();
					oDept.parser(omsgFilms,showEmployees);
					showTitles.add(oDept);
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

		Executions.createComponents("/dept_manager/dialog-dept-manager.zul", null,map);
	}
	
	@Command
	public void onEdit(@BindingParam("titlesRecord") titles dept_man) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		this.selectedItem = dept_man;
		map.put("selectedRecord", dept_man);
		map.put("recordMode", "EDIT");
		map.put("showEmployees", showEmployees);
		setSeletectItemIndex(showTitles.indexOf(selectedItem));
		Executions.createComponents(
				"/titles/dialog-edit-titles.zul", null, map);
	}
	

	@GlobalCommand("refreshList")
	@NotifyChange("showTitles")
	public void refreshList(@BindingParam("selectedRecord") titles dept_mag,
			@BindingParam("recordMode") String recordMode) {
		if (recordMode.equals("NEW")) {
			showTitles.add(dept_mag);
		}

		if (recordMode.equals("EDIT")) {
			showTitles.set(this.seletectItemIndex, dept_mag);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onDelete(@BindingParam("titlesRecord") final titles titles) {
		this.selectedItem = titles;
		String str = null;
		try {
			str = "¿Desea eliminar el  Titulo \"" + titles.getTitle()
					+ "\"?";
			Messagebox.show(str, "Confirmar",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								return;
							} else if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								deleteApp(titles);
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

	public void deleteApp(titles dept) {
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
			 sSQL = "DELETE FROM titles WHERE emp_no='"+this.selectedItem.getEmp_no().trim()+"'";
			 oSt.executeUpdate(sSQL);
			 conn.commit();
			 showTitles.remove(showTitles.indexOf(selectedItem));
			Messagebox.show("Registros eliminado correctamente",
					"INFORMACIÓN", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils
					.postNotifyChange(null, null, VistaTitles.this, "showTitles");
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
