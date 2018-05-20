package com.navbar;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Include;
import java.util.ArrayList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkmax.zul.Nav;
import org.zkoss.zkmax.zul.Navbar;
import org.zkoss.zkmax.zul.Navitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;

public class NavController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;
	@Wire
	Div sidebarContainer;
	@Wire
	Navbar navBar;
	// CSUserIP user;
	String usuario;

	@Wire
	private Navbar sidebar;

	@Wire
	private Include mainInclude;

	@Wire
	private boolean visible;

	@Listen("onSelect = #sidebar")
	@Init
	public void doSelect() {
		Navitem item = sidebar.getSelectedItem();
		String label = item.getLabel().trim();

		if ("Employees".equals(label)) {
			mainInclude.setSrc("/employees/employees.zul");

		} else if ("Departments".equals(label)) {
			mainInclude.setSrc("/departments/departments.zul");
		} else if ("Rescue the Baby".equals(label)) {
			mainInclude.setSrc("baby.zul");
		} else if ("Aplicaciones".equals(label)) {
			mainInclude.setSrc("Security/laplicaciones.zul");
		} else if ("Perfiles".equals(label)) {
			mainInclude.setSrc("/catalogos/perfiles/listProfiles.zul");
		} else if ("Usuarios".equals(label)) {
			mainInclude.setSrc("/catalogos/usuarios/listUsers.zul");
		} else if ("Operaciones".equals(label)) {
			mainInclude.setSrc("/catalogos/operaciones/listOperations.zul");
		} else if ("Perfil Operacion".equals(label)) {
			mainInclude
					.setSrc("/catalogos/perfiloperacion/listProfileOperation.zul");
		} else if ("Say Hi to the Soldier".equals(label)) {
			mainInclude.setSrc("hi.zul");
		} else if ("Parametros Inventario".equals(label)) {
			visible = true;
			mainInclude.setSrc("/Catalogos/parametrosInventario.zul");

		} else if ("Imagenes Predisenadas".equals(label)) {
			mainInclude.setSrc("/Catalogos/imagenes.zul");
		} else {
			visible = false;
			mainInclude.setSrc("");
			mainInclude.setSrc("none.zul");
		}
	}

}
