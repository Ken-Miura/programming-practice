/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;


/**
 * @author Ken Miura
 *
 */
abstract class CreationDialog extends InstanceHoldingDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -2121890427136178457L;
	
	private final ClassSeachPanel classSeachPanel;
	
	public CreationDialog(Class<?> superClass) {
		classSeachPanel = ClassSeachPanel.createClassSeachPanel(this, superClass);
	}

	public final ClassSeachPanel getClassSeachPanel() {
		return classSeachPanel;
	}
}
