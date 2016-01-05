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
	
	private final Class<?> specifiedClass;
	private final ClassSeachPanel classSeachPanel = ClassSeachPanel.createClassSeachPanel(this);
	
	public CreationDialog(Class<?> specifiedClass) {
		this.specifiedClass = specifiedClass;
	}

	public final Class<?> getSpecifiedClass() {
		return specifiedClass;
	}

	public final ClassSeachPanel getClassSeachPanel() {
		return classSeachPanel;
	}
}
