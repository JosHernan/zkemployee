package com.vista.departments;

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

public class ViewsDepartments {
	private departments selectedItem;
	private ArrayList<departments> showDepartments = new ArrayList<departments>();
	private Integer selectedItemIndex;
	private Connection conn = null;
	private ResultSet oRs = null;
	private Statement oSt = null;
	private String sSQL = null;
	private DataSource ds = null;

	public departments getSelectedItem() {
		return selectedItem;
	}

	public ArrayList<departments> getShowDepartments() {
		return showDepartments;
	}

	public Integer getSelectedItemIndex() {
		return selectedItemIndex;
	}

	public void setSelectedItem(departments selectedItem) {
		this.selectedItem = selectedItem;
	}

	public void setShowDepartments(ArrayList<departments> showDepartments) {
		this.showDepartments = showDepartments;
	}

	public void setSelectedItemIndex(Integer selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		String dept_no = null;
		String dept_name = null;
		departments obj = null;

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
			sSQL = "SELECT * FROM departments";
			oRs = oSt.executeQuery(sSQL);
			if (oRs.next() == false) {
				// System.out.println("No se encontraron listado de datos");
				Messagebox.show("No se encontraro ningun registro:",
						"Informacion" + "INFO:",
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}

			do {
				obj = new departments();
				dept_no = (oRs.getString("dept_no")) != null ? oRs
						.getString("dept_no") : "";
				obj.setDept_no(dept_no);
				dept_name = (oRs.getString("dept_name")) != null ? oRs
						.getString("dept_name") : "";
				obj.setDept_name(dept_name);
				showDepartments.add(obj);
			} while (oRs.next());

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
		} finally {

			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			conn = null;
			obj = null;

		}

	}
	
	@Command
	public void onAddNew() {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		Executions.createComponents("/departments/dialog-departments.zul",
				null, map);
	}
	
	@GlobalCommand("refreshList")
	@NotifyChange("showDepartments")
	public void refreshList(@BindingParam("selectedRecord") departments department,
			@BindingParam("recordMode") String recordMode) {
		if (recordMode.equals("NEW")) {
			showDepartments.add(department);
		}

		if (recordMode.equals("EDIT")) {
			showDepartments.set(this.selectedItemIndex, department);
		}
	}
	
	@Command
	public void onEdit(@BindingParam("departmentRecord") departments department) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		this.selectedItem = department;
		map.put("selectedRecord", department);
		map.put("recordMode", "EDIT");
		setSelectedItemIndex(showDepartments.indexOf(selectedItem));
		Executions.createComponents(
				"/departments/dialog-departmentsedit.zul", null, map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onDelete(@BindingParam("departmentRecord") final departments department) {
		this.selectedItem = department;
		String str = null;
		try {
			str = "¿Desea eliminar el  departments \"" + department.getDept_no()
					+ "\"?";
			Messagebox.show(str, "Confirmar",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								return;
							} else if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								deleteApp(department);
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

	public void deleteApp(departments department) {
		this.selectedItem = department;
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
			 if(this.selectedItem.getDept_no().equals("")){
				 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
				}
			 oSt = conn.createStatement();
			 sSQL = "DELETE FROM departments WHERE dept_no='"+this.selectedItem.getDept_no().trim()+"'";
			 oSt.executeUpdate(sSQL);
			 conn.commit();
			 showDepartments.remove(showDepartments.indexOf(selectedItem));
			Messagebox.show("Registros eliminado correctamente",
					"INFORMACIÓN", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils
					.postNotifyChange(null, null, ViewsDepartments.this, "showDepartments");
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
