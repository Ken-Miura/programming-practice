/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Ken Miura
 *
 */
public final class MethodDialog extends InstanceHoldingDialog {
	
	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -4938110775654178305L;
	private static final String TITLE = "メソッドの実行";
	private static final int MARGIN = 3;
	private final Class<?> instanceType;
	
	private final PropertyChangeSupport properChangeSupport;
	private final GridBagConstraints componentConstraints = new GridBagConstraints();

	private final JLabel caption = new JLabel("メソッド一覧");
	private final JComboBox<Method> methodCombo = new JComboBox<>();
	private final JLabel paramCaption = new JLabel("引数一覧");
	private final ParameterPanel parameterPanel = new ParameterPanel();
	private final JButton methodExecutionButton = new JButton("メソッドを実行する");
	private final JLabel resultLabel = new JLabel();
	
	public MethodDialog (Object instance) {
		Objects.requireNonNull(instance, "instance must not be null.");
		setInstance(instance);
		this.instanceType = instance.getClass();
		
		setTitle(TITLE);
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		properChangeSupport = new PropertyChangeSupport(this);
		properChangeSupport.addPropertyChangeListener(parameterPanel);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(caption, componentConstraints);
		
		Method[] methods = mergeMethods(this.instanceType.getDeclaredMethods(), this.instanceType.getMethods());
		for (final Method method: methods) {
			method.setAccessible(true);
			methodCombo.addItem(method);
		}
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(methodCombo, componentConstraints);
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
		
		methodCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Method selectedMethod = (Method) methodCombo.getSelectedItem();
				if (selectedMethod == null) { // removeAllのとき
					return;
				}
				remove(paramCaption);
				remove(parameterPanel);
				remove(methodExecutionButton);
				remove(resultLabel);
				
				java.lang.reflect.Type[] parameters = selectedMethod.getGenericParameterTypes();
				//properChangeSupport.firePropertyChange(ParameterPanel.PARAMETER_CHANGE_KEY, null, parameters);
				PropertyChangeEvent evt = new PropertyChangeEvent(this, ParameterPanel.PARAMETER_CHANGE_KEY, null, parameters);
				parameterPanel.propertyChange(evt);
				
				addParameterAndExecutionButton();
				
				//parameterPanel.revalidate();
				Dimension d = getPreferredSize();
				setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
			}
		});
		
		methodExecutionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}

	// for static method, non instance
	//public MethodDialog (Class<?> instanceType) {
	//	this.instanceType = Objects.requireNonNull(instanceType, "instanceType must not be null.");
	//}
	
	private Method[] mergeMethods(Method[] methods1, Method[] methods2) {
		List<Method> tempMembers = new ArrayList<>();
		found: 
		for (Method m1 : methods1) {
			for (Method m2 : methods2) {
				if (m1.equals(m2)) {
					break found;
				}
			}
			tempMembers.add(m1);
		}
		List<Method> mergedMembers = new ArrayList<>(tempMembers);
		for (Method m: methods2) {
			mergedMembers.add(m);
		}
		return mergedMembers.toArray(new Method[0]);
	}	
	
	private void addParameterAndExecutionButton() {
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 2;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(paramCaption, componentConstraints);		
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 3;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(parameterPanel, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 4;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(methodExecutionButton, componentConstraints);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Do nothing
	}

	
}
