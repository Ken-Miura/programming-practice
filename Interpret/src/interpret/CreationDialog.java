/* Copyright (C) 2016 Ken Miura */
package interpret;


/**
 * @author Ken Miura
 *
 */
abstract class CreationDialog extends InstanceHoldingDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -2121890427136178457L;
	
	private final ClassSearchPanel classSeachPanel;
	
	public CreationDialog(Class<?> superClass) {
		classSeachPanel = ClassSearchPanel.createClassSearchPanel(this, superClass);
	}

	public final ClassSearchPanel getClassSeachPanel() {
		return classSeachPanel;
	}
}
