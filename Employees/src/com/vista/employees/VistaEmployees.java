package com.vista.employees;

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

public class VistaEmployees {
	//creamos el objeto de la clase employees
	private employees SelectedItem;
	//creamos el arreglo donde se almacenaran los registros
	private ArrayList<employees>  showEmployees=new ArrayList<employees>();
	private Integer selectedItemIndex;
	/* Conexion de base de datos  variables*/
	Connection con = null;
	Statement oSt = null;
	String sSQL = " ";
	ResultSet oRs;
	DataSource ds = null;
	/* fin conexion variables*/
	public employees getSelectedItem() {
		return SelectedItem;
	}
	public ArrayList<employees> getShowEmployees() {
		return showEmployees;
	}
	public void setSelectedItem(employees selectedItem) {
		SelectedItem = selectedItem;
	}
	public void setShowEmployees(ArrayList<employees> showEmployees) {
		this.showEmployees = showEmployees;
	}
	
	//Crear el AfterComposer
	/*Propósito: Marcador de anotación para identificar un método de ciclo de vida que se 
	invocará en doAfterCompose () de BindComposer .  
	Solo se permite un método @ anotado AfterCompose como máximo en una clase ViewModel. 
	Si establece la superclase del elemento de anotación como verdadera , 
	el método @ AfterCompose-anotado de la clase principal de ViewModel se invocará primero y 
	luego el secundario, y esta lógica se repetirá en superclase.
	Si una clase no tiene un método con @AfterCompose, no se llamará a ningún método (incluida la superclase). [1] .*/
	
	public Integer getSelectedItemIndex() {
		return selectedItemIndex;
	}
	public void setSelectedItemIndex(Integer selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
	}
	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW)Component view){
		/*Una forma de obtener componentes es pasar componentes como parámetros en 
		el enlace de comandos que hemos hablado anteriormente. 
		Otra es llamar a Selectors.wireComponents () . 
		De esta forma, puede conectar componentes con @Wire como lo hace en SelectorComposer . 
		Debe llamar a Selectors.wireComponents () en un método con @AfterCompose de la siguiente manera:*/
		Selectors.wireComponents(view, this, false);
		 String   emp_no;
		 String   birth_date;
		 String   first_name;
		 String   last_name;
		 String   gender;  
		 String   hire_date;
		 employees obj=null;
		 try {
			 try {
				 //conexion al datasource
				 ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/MyDB");
			con = ds.getConnection();
			} catch (Exception e) {
				Messagebox.show("No se establecio conexión"
						+ "Revise la configuracion del DS:", "Exception DataSource", Messagebox.OK,
						Messagebox.ERROR);
				ds = null;
				con = null;
				return;
			}
			 
			 oSt = con.createStatement();
		     sSQL = "SELECT * FROM employees";
				oRs = oSt.executeQuery(sSQL);
				if (oRs.next() == false) {
					// System.out.println("No se encontraron listado de datos");
					Messagebox.show("Informacion:",
							"No se encontraro ningun registro" + "INFO:",
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
				
				do{
					 obj=new employees();
					
					  emp_no= (oRs.getString("emp_no")) != null ? oRs
								.getString("emp_no") : "";
					  obj.setEmp_no(emp_no);
					  
					  birth_date= (oRs.getString("birth_date")) != null ? oRs
								.getString("birth_date") : "";
								obj.setBirth_date(birth_date);
					  first_name= (oRs.getString("first_name")) != null ? oRs
								.getString("first_name") : "";
								obj.setFirst_name(first_name);
					  last_name= (oRs.getString("last_name")) != null ? oRs
								.getString("last_name") : "";
								obj.setLast_name(last_name);
					  gender= (oRs.getString("gender")) != null ? oRs
								.getString("gender") : "";
								obj.setGender(gender);
					  hire_date= (oRs.getString("hire_date")) != null ? oRs
								.getString("hire_date") : "";
								obj.setHire_date(hire_date);
					  
					 showEmployees.add(obj); 
				}while(oRs.next());
				
				
				if (oRs != null)
					oRs.close();

				if (oSt != null)
					oSt.close();
			 
		}catch (SQLException err){
			System.out.println("Error SQLException:"+err.getErrorCode()+":"+err.getSQLState()+":"+err.getMessage());
			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			con = null;
			obj=null;
		} catch (Exception e) {
			Messagebox.show("Exception Error:" + e, "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			con = null;
			obj=null;
		}finally {

			ds = null;
			oRs = null;
			sSQL = null;
			oSt = null;
			con = null;
			obj=null;
		
		}
		
		 
		 
		
	}
	
	/*El comando se implementa como el método de ViewModel. 
	Como ViewModel es un POJO, para que el mecanismo de enlace de datos identifique qué método representa un comando, 
	los desarrolladores deben anotar el método con la anotación @Command proporcionada por ZK . Usaremos el término: 
	Método de comando para representar el método anotado especial de un ViewModel en la sección posterior.
	Estos métodos generalmente manipulan la propiedad de ViewModel, como eliminar un elemento. 
	Disparar el evento de un componente desencadena la ejecución del comando vinculado, que invoca el método de comando.
	Durante la ejecución del comando, el desarrollador también debe especificar qué propiedades 
	cambian para notificar a través de la anotación Java que describiremos en la sección posterior .*/
	
	
	@Command
	public void onAddNew() {

		final HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("selectedRecord", null);
		map.put("recordMode", "NEW");
		Executions.createComponents("/employees/dialog-employees.zul",
				null, map);
	}
	
	/*La ejecución proporciona una colección de métodos para permitirle crear componentes basados ​​en un documento ZUML, 
	como Execution.createComponents (String, Component, Map) , 
	Execution.createComponentsDirectly (String, String, Component, Map) y muchos otros. Además,
	Executions proporciona una colección similar de atajos para que no tenga que 
	recuperar primero la ejecución actual.*/

	
	
	
	/* @BindingParam
	 * Propósito: Indique al encuadernador que recupere este parámetro con la clave 
	especificada del argumento vinculante en el ZUL.

	La anotación se aplica al parámetro del método de comando. 
	Declara que el parámetro aplicado debe provenir del argumento vinculante escrito en el ZUL con la clave especificada.
	*/
	@GlobalCommand("refreshList")
	@NotifyChange("showEmployees")
	public void refreshList(@BindingParam("selectedRecord") employees employee,
			@BindingParam("recordMode") String recordMode) {
		if (recordMode.equals("NEW")) {
			showEmployees.add(employee);
		}

		if (recordMode.equals("EDIT")) {
			showEmployees.set(this.selectedItemIndex, employee);
		}
	}
	
	@Command
	public void onEdit(@BindingParam("employeeRecord") employees employee) {
		final HashMap<String, Object> map = new HashMap<String, Object>();
		this.SelectedItem = employee;
		map.put("selectedRecord", employee);
		map.put("recordMode", "EDIT");
		setSelectedItemIndex(showEmployees.indexOf(SelectedItem));
		Executions.createComponents(
				"/employees/dialog-employeesedit.zul", null, map);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void onDelete(@BindingParam("employeeRecord") final employees employee) {
		this.SelectedItem = employee;
		String str = null;
		try {
			str = "¿Desea eliminar el  employee \"" + employee.getFirst_name()
					+ "\"?";
			Messagebox.show(str, "Confirmar",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event event) throws Exception {
							if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
								return;
							} else if (((Integer) event.getData()).intValue() == Messagebox.OK) {
								deleteApp(employee);
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

	public void deleteApp(employees employee) {
		this.SelectedItem = employee;
		DataSource ds =null;
		String sSQL=null;
		try {
			try {
			    ds = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/MyDB");
			    con = ds.getConnection();

			} catch (SQLException err) {
				System.out.println("No se establecio conexión con la Base de Datos.");
						ds = null;
						con = null;
				return;
				// TODO: handle exception
			}
			 if(this.SelectedItem.getEmp_no().equals("")){
				 Messagebox.show("Campo Obligatorio", "INFORMACIÓN", Messagebox.OK,
							Messagebox.INFORMATION);
				
					return;
				}
			 oSt = con.createStatement();
			 sSQL = "DELETE FROM employees WHERE emp_no='"+this.SelectedItem.getEmp_no().trim()+"'";
			 oSt.executeUpdate(sSQL);
			 con.commit();
			 showEmployees.remove(showEmployees.indexOf(SelectedItem));
			Messagebox.show("Registros eliminado correctamente",
					"INFORMACIÓN", Messagebox.OK, Messagebox.INFORMATION);
			BindUtils
					.postNotifyChange(null, null, VistaEmployees.this, "showEmployees");
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
			con=null;
			oSt=null;
		} finally {
			sSQL=null;
			ds=null;
			con=null;
			oSt=null;
		}
	}

	
	

}
