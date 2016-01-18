/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Ken Miura
 *
 */
final class MethodPanel extends JPanel {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -3202204861218973533L;
	private static final int MARGIN = 3;
	static final String METHOD_CHANGE = "method_change";
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	
	private final Object instance;
	private final JLabel caption = new JLabel("メソッド一覧");
	private final JComboBox<Method> methodCombo = new JComboBox<>();
	private final JLabel parameterLabel = new JLabel("引数一覧");
	private final ParameterPanel parameterPanel = new ParameterPanel();
	private final JButton methodExecutionButton = new JButton("メソッドを実行する");
	private final JLabel resultLabel = new JLabel();
	private final PropertyChangeSupport notifier;
	
	private MethodPanel (Object instance, PropertyChangeListener listener) {
		super(new GridBagLayout());
		this.instance = Objects.requireNonNull(instance, "instance must not be null");
		Objects.requireNonNull(listener, "listener must not be null");
		notifier = new PropertyChangeSupport(this);
		notifier.addPropertyChangeListener(parameterPanel);
		notifier.addPropertyChangeListener(listener);
		
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		add (caption, componentConstraints);
		
		Method[] methods = mergeMethods(instance.getClass().getDeclaredMethods(), instance.getClass().getMethods());
		for (final Method method: methods) {
			method.setAccessible(true);
			methodCombo.addItem(method);
		}
		componentConstraints.gridy = 1;
		add (methodCombo, componentConstraints);
		methodCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Method selectedMethod = (Method) methodCombo.getSelectedItem();
				if (selectedMethod == null) { // removeAllのとき
					return;
				}
				remove(parameterLabel);
				remove(parameterPanel);
				remove(methodExecutionButton);
				
				java.lang.reflect.Type[] parameters = selectedMethod.getGenericParameterTypes();
				notifier.firePropertyChange(ParameterPanel.PARAMETER_CHANGE_KEY, null, parameters);
				
				componentConstraints.gridy = 2;
				add(parameterLabel, componentConstraints);
				
				componentConstraints.gridy = 3;
				add(parameterPanel, componentConstraints);
				
				componentConstraints.gridy = 4;
				add (methodExecutionButton, componentConstraints);
				
				revalidate();
				repaint();
				
				notifier.firePropertyChange(METHOD_CHANGE, null, null);
			}
		});
		
		methodExecutionButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				remove(resultLabel);
				
				Method selectedMethod = (Method) methodCombo.getSelectedItem();
				Object returnValue = null;
				
				try {
					returnValue = selectedMethod.invoke(MethodPanel.this.instance, parameterPanel.getParameterValues());
				} catch (IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getClass()+") がスローされ、メソッドの実行に失敗しました", "メソッド実行失敗", JOptionPane.ERROR_MESSAGE);
					revalidate();
					repaint();
					return;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getTargetException().getClass()+") がスローされ、メソッドの実行に失敗しました", "メソッド実行失敗", JOptionPane.ERROR_MESSAGE);
					revalidate();
					repaint();
					return;
				} catch (InvalidInputException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "入力エラー", JOptionPane.ERROR_MESSAGE);
					revalidate();
					repaint();
					return;
				}
				
				resultLabel.removeAll();
				if (returnValue == null) {
					resultLabel.setText("戻り値なし");
				} else {
					resultLabel.setText("型: " + returnValue.getClass().getName() + ", 返り値: " + returnValue);
				}
				
				componentConstraints.gridy = 5;
				add (resultLabel, componentConstraints);
				
				revalidate();
				repaint();
			}
		});
	}
	
	private static Method[] mergeMethods(Method[] methods1, Method[] methods2) {
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
	
	public static MethodPanel createMethodPanel (Object instance, PropertyChangeListener listener) {
		return new MethodPanel(instance, listener);
	}
}
