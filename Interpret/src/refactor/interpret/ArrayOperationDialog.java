/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Array;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
public final class ArrayOperationDialog extends InstanceHoldingDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 1513068164944039662L;
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

	private static final String TITLE = "配列要素の操作";
	private static final int MARGIN = 3;
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	private final JLabel caption = new JLabel("要素番号を指定して下さい");
	private final int length;
	private final JSpinner arraySizeSpinner;
	private final JLabel inputCaption = new JLabel("値を入力してください");
	private final JButton changeValueButton = new JButton("値を変更する");
	private Component elementComponent = null;
	
	public ArrayOperationDialog (Object instance) {
		Objects.requireNonNull(instance, "instance must not be null");
		if (!(instance.getClass().isArray())) {
			throw new IllegalArgumentException("instance is not array");
		}
		setInstance(instance);
		setTitle(TITLE);
		length = Array.getLength(instance);
		if (length != 0) {
			arraySizeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, length-1, 1));			
		} else {
			arraySizeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));			
		}
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		addComponets();
				
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
		
		changeValueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				removeAll();
				
				// TODO
				addComponets();
				
				revalidate();
				Dimension d = getPreferredSize();
				setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
				repaint();
			}
		});	
	}
	
	private void addComponets() {
		componentConstraints.anchor = GridBagConstraints.CENTER;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;

		add(new JLabel("要素型: " + getInstance().getClass().getComponentType()), componentConstraints);
		for (int i=0; i<length; i++) {
			componentConstraints.gridy = i+1;
			Object value = Array.get(getInstance(), i);
			if (value == null) {
				add(new JLabel("要素番号: " + i + ", 値: null"), componentConstraints);	
			} else {
				add(new JLabel("要素番号: " + i + ", 値: " + value), componentConstraints);	
			}
		}
		
		componentConstraints.gridy = length+1;
		add(caption, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.gridy = length+2;
		add(arraySizeSpinner, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.gridy = length+3;
		add(inputCaption, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.gridy = length+4;
		addInputComponents();
		
		componentConstraints.gridy = length+5;
		add(changeValueButton, componentConstraints);
	}

	private void addInputComponents() {
		Class<?> componentType = getInstance().getClass().getComponentType();
		
		if (componentType == boolean.class) {			
			JComboBox<Boolean> combo = new JComboBox<>();
			combo.setName(BOOLEAN);
			combo.addItem(Boolean.TRUE);
			combo.addItem(Boolean.FALSE);
			add(combo, componentConstraints);
			elementComponent = combo;
		} else if (componentType == char.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(CHAR);
			add(text, componentConstraints);
			elementComponent = text;
		} else if (componentType == byte.class) {
			ByteSpinner byteSpinner = new ByteSpinner();
			byteSpinner.setName(BYTE);
			add(byteSpinner, componentConstraints);
			elementComponent = byteSpinner;
		} else if (componentType == short.class) {
			ShortSpinner shortSpinner = new ShortSpinner();
			shortSpinner.setName(SHORT);
			add(shortSpinner, componentConstraints);
			elementComponent = shortSpinner;
		} else if (componentType == int.class) {
			IntSpinner intSpinner = new IntSpinner();
			intSpinner.setName(INT);
			add(intSpinner, componentConstraints);
			elementComponent = intSpinner;
		} else if (componentType == long.class) {
			LongSpinner longSpinner = new LongSpinner();
			longSpinner.setName(LONG);
			add(longSpinner, componentConstraints);
			elementComponent = longSpinner;
		} else if (componentType == float.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(FLOAT);
			add(text, componentConstraints);
			elementComponent = text;
		} else if (componentType == double.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(DOUBLE);
			add(text, componentConstraints);
			elementComponent = text;
		} else if (componentType == String.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(STRING);
			elementComponent = text;
		} else {
			addInstanceCreationButton(componentType);
		}		
	}

	private void addInstanceCreationButton(Class<?> componentType) {
		assert componentType != null;
		final JButton button;
		final InstanceHoldingDialog instanceHoldingDialog;
		if (componentType.isArray()) {
			button = new JButton("配列を生成する (生成しない場合はnullを使用)");
			instanceHoldingDialog = new ArrayCreationDialog(componentType.getComponentType());
		} else {
			button = new JButton("インスタンスを生成する (生成しない場合はnullを使用)");
			instanceHoldingDialog = new InstanceCreationDialog(componentType);
		}
		instanceHoldingDialog.setName(OBJECT);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instanceHoldingDialog.setVisible(true);
			}
		});
		add(button, componentConstraints);
		elementComponent = instanceHoldingDialog;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Do nothing
	}

}
