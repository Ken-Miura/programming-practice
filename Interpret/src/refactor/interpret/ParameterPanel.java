/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Ken Miura
 *
 */
final class ParameterPanel extends JPanel implements PropertyChangeListener {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -3970068781988466673L;
	private static final int MARGIN = 3;
	// コンポーネントの区別のために利用
	private static final String BOOLEAN = "boolean";
	private static final String CHAR = "char";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";
	private static final String STRING = "String"; // String型だけ他の参照型と区別。その方が使いやすいだろうから。
	private static final String OBJECT = "Object";	
	
	private final List<? super Component> parameterComponents = new ArrayList<>();
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	
	public ParameterPanel() {
		super(new GridBagLayout());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!(evt.getNewValue() instanceof Type[])) {
			throw new AssertionError("not to be passed.");
		}
		Type[] parameters = (Type[]) evt.getNewValue(); 
		
		parameterComponents.clear();
		removeAll();
		
		setParametersIntoPanel(parameters);
		
		revalidate();
	}

	@SuppressWarnings("unchecked") /* setParametersIntoPanelから正しい型にキャストできていることは明らか */ 
	public Object[] getParameterValues () throws InvalidInputException {
		List<Object> parameterValues = new ArrayList<>();
		
		int length = parameterComponents.size();
		for (int i=0; i<length; i++) {
			Component c = (Component) parameterComponents.get(i);
			if (c.getName().equals(BOOLEAN)) {
				parameterValues.add(((JComboBox<Boolean>)c).getSelectedItem());
			} else if (c.getName().equals(CHAR)) {
				String str = ((JTextField) c).getText();
				if (str.length() != 1) {
					throw new InvalidInputException("charには、サロゲートペアを必要としない1文字のみ指定してください");
				}
			} else if (c.getName().equals(BYTE)) {
				parameterValues.add(((ByteSpinner)c).getByte());
			} else if (c.getName().equals(SHORT)) {
				parameterValues.add(((ShortSpinner)c).getShort());
			} else if (c.getName().equals(INT)) {
				parameterValues.add(((IntSpinner)c).getInt());
			} else if (c.getName().equals(LONG)) {
				parameterValues.add(((LongSpinner)c).getLong());
			} else if (c.getName().equals(FLOAT)) {
				float f = 0.0f;
				try {
					f = Float.parseFloat(((JTextField)c).getText());
				} catch (NumberFormatException e) {
					throw new InvalidInputException("floatの入力として不正な値が使用されています。");
				}
				parameterValues.add(f);
			} else if (c.getName().equals(DOUBLE)) {
				double d = 0.0;
				try {
					d = Double.parseDouble(((JTextField)c).getText());
				} catch (NumberFormatException e) {
					throw new InvalidInputException("doubleの入力として不正な値が使用されています。");
				}
				parameterValues.add(d);
			} else if (c.getName().equals(STRING)) {
				parameterValues.add(((JTextField)c).getText());
			} else if (c.getName().equals(OBJECT)) {
				parameterValues.add(((InstanceHoldingDialog)c).getInstance());
			} else {
				throw new AssertionError("not to be passed.");
			}
		}
		if (parameterValues.size() != parameterComponents.size()) {
			throw new AssertionError("not to be pass.");
		}
		return parameterValues.toArray(new Object[0]);
	}
	
	private void setParametersIntoPanel(Type[] parameters) {
		assert parameters != null;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		for (int i=0; i<parameters.length; i++) {
			componentConstraints.gridy = i;
			if (parameters[i] == boolean.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("boolean: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				JComboBox<Boolean> combo = new JComboBox<>();
				combo.setName(BOOLEAN);
				combo.addItem(Boolean.TRUE);
				combo.addItem(Boolean.FALSE);
				add(combo, componentConstraints);
				
				parameterComponents.add(combo);
			} else if (parameters[i] == char.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("char: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				JTextField text = TextFieldUtil.createStringTextField();
				text.setName(CHAR);
				add(text, componentConstraints);
				
				parameterComponents.add(text);
			} else if (parameters[i] == byte.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("byte: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				ByteSpinner byteSpinner = new ByteSpinner();
				byteSpinner.setName(BYTE);
				add(byteSpinner, componentConstraints);
				
				parameterComponents.add(byteSpinner);
			} else if (parameters[i] == short.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("short: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				ShortSpinner shortSpinner = new ShortSpinner();
				shortSpinner.setName(SHORT);
				add(shortSpinner, componentConstraints);
				
				parameterComponents.add(shortSpinner);
			} else if (parameters[i] == int.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("int: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				IntSpinner intSpinner = new IntSpinner();
				intSpinner.setName(INT);
				add(intSpinner, componentConstraints);
				
				parameterComponents.add(intSpinner);
			} else if (parameters[i] == long.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("long: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				LongSpinner longSpinner = new LongSpinner();
				longSpinner.setName(LONG);
				add(longSpinner, componentConstraints);
				
				parameterComponents.add(longSpinner);
			} else if (parameters[i] == float.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("float: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
				text.setName(FLOAT);
				add(text, componentConstraints);
				
				parameterComponents.add(text);
			} else if (parameters[i] == double.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("double: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
				text.setName(DOUBLE);
				add(text, componentConstraints);
				
				parameterComponents.add(text);
			} else if (parameters[i] == String.class) {
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel("String: "), componentConstraints);
				
				componentConstraints.gridx = 1;
				componentConstraints.anchor = GridBagConstraints.WEST;
				JTextField text = TextFieldUtil.createStringTextField();
				text.setName(STRING);
				add(text, componentConstraints);
				
				parameterComponents.add(text);
			} else {
				final Class<?> cls;
				if (parameters[i] instanceof Class<?>) {
					cls = (Class<?>) parameters[i];
				} else if (parameters[i] instanceof ParameterizedType) {
					cls = (Class<?>) ((ParameterizedType) parameters[i]).getRawType();
				} else {
					throw new Error("Unexpected non-class parameters["+i+"]");	
				}
				componentConstraints.gridx = 0;
				componentConstraints.anchor = GridBagConstraints.EAST;
				add(new JLabel(cls.getName() + ": "), componentConstraints);
				
				setInstanceCreationButton(cls);
			}
		}
	}

	private void setInstanceCreationButton(Class<?> cls) {
		assert cls != null;
		componentConstraints.gridx = 1;
		componentConstraints.anchor = GridBagConstraints.WEST;
		final InstanceHoldingDialog instanceHoldingDialog;
		JButton button = null;
		if (cls.isArray()) {
			instanceHoldingDialog = new ArrayCreationDialog(cls.getComponentType());
			button = new JButton("配列を生成する (生成しない場合はnullを使用)");
		} else {
			instanceHoldingDialog = new InstanceCreationDialog(cls);
			button = new JButton("インスタンスを生成する (生成しない場合はnullを使用)");
		}
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instanceHoldingDialog.setVisible(true);
			}
		});
		add(button, componentConstraints);
		assert instanceHoldingDialog != null;
		instanceHoldingDialog.setName(OBJECT);
		parameterComponents.add(instanceHoldingDialog);
	}
}
