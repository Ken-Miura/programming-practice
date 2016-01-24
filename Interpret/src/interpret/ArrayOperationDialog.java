/* Copyright (C) 2016 Ken Miura */
package interpret;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Array;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	private final JButton contentsButton = new JButton();
	private Component elementComponent = null;
	
	private final JPanel panel = new JPanel(new GridBagLayout());
	
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
		
		add(panel);	
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
		
		changeValueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					setValueIntoElement();
				} catch (InvalidInputException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "入力エラー", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				panel.removeAll();
				contentsButton.removeAll();
				
				addComponets();
				
				panel.revalidate();
				Dimension d = getPreferredSize();
				setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
				repaint();
			}

		});	
		
		contentsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = (Integer) arraySizeSpinner.getValue();
				Object o = Array.get(getInstance(), index);
				if (o == null) {
					JOptionPane.showMessageDialog(null, "参照値がnullです", "エラー", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (o.getClass().isArray()) {
					new ArrayOperationDialog(o).setVisible(true);	
				} else {
					new InstanceOperationDialog(o).setVisible(true);	
				}
			}
		});

	}
	
	private void addComponets() {
		componentConstraints.anchor = GridBagConstraints.CENTER;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;

		panel.add(new JLabel("要素型: " + getInstance().getClass().getComponentType()), componentConstraints);
		for (int i=0; i<length; i++) {
			componentConstraints.gridy = i+1;
			Object value = Array.get(getInstance(), i);
			if (value == null) {
				panel.add(new JLabel("要素番号: " + i + ", 値: null"), componentConstraints);	
			} else {
				panel.add(new JLabel("要素番号: " + i + ", 値: " + value), componentConstraints);	
			}
		}
		
		componentConstraints.gridy = length+1;
		panel.add(caption, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.gridy = length+2;
		panel.add(arraySizeSpinner, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.gridy = length+3;
		panel.add(inputCaption, componentConstraints);
		
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.gridy = length+4;
		addInputComponents();
		
		componentConstraints.gridy = length+5;
		panel.add(changeValueButton, componentConstraints);

		Class<?> componentType = getInstance().getClass().getComponentType();
		if (!(componentType.isPrimitive() || componentType == String.class)) {
			if (componentType.isArray()) {
				contentsButton.setText("配列要素を確認する");
			} else {
				contentsButton.setText("フィールドを確認する");	
			}
			componentConstraints.gridy = length+6;
			panel.add(contentsButton, componentConstraints);
		}
	}

	private void addInputComponents() {
		Class<?> componentType = getInstance().getClass().getComponentType();
		
		if (componentType == boolean.class) {			
			JComboBox<Boolean> combo = new JComboBox<>();
			combo.setName(BOOLEAN);
			combo.addItem(Boolean.TRUE);
			combo.addItem(Boolean.FALSE);
			panel.add(combo, componentConstraints);
			elementComponent = combo;
		} else if (componentType == char.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(CHAR);
			panel.add(text, componentConstraints);
			elementComponent = text;
		} else if (componentType == byte.class) {
			ByteSpinner byteSpinner = new ByteSpinner();
			byteSpinner.setName(BYTE);
			panel.add(byteSpinner, componentConstraints);
			elementComponent = byteSpinner;
		} else if (componentType == short.class) {
			ShortSpinner shortSpinner = new ShortSpinner();
			shortSpinner.setName(SHORT);
			panel.add(shortSpinner, componentConstraints);
			elementComponent = shortSpinner;
		} else if (componentType == int.class) {
			IntSpinner intSpinner = new IntSpinner();
			intSpinner.setName(INT);
			panel.add(intSpinner, componentConstraints);
			elementComponent = intSpinner;
		} else if (componentType == long.class) {
			LongSpinner longSpinner = new LongSpinner();
			longSpinner.setName(LONG);
			panel.add(longSpinner, componentConstraints);
			elementComponent = longSpinner;
		} else if (componentType == float.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(FLOAT);
			panel.add(text, componentConstraints);
			elementComponent = text;
		} else if (componentType == double.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(DOUBLE);
			panel.add(text, componentConstraints);
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
		panel.add(button, componentConstraints);
		elementComponent = instanceHoldingDialog;
	}
	
	@SuppressWarnings("unchecked") /* 正しい型にキャストできていることは明らか */ 
	private void setValueIntoElement() throws InvalidInputException {
		int index = (Integer) arraySizeSpinner.getValue();
		Object value = null;
		if (elementComponent.getName().equals(BOOLEAN)) {
			value = ((JComboBox<Boolean>)elementComponent).getSelectedItem();
		} else if (elementComponent.getName().equals(CHAR)) {
			String str = ((JTextField) elementComponent).getText();
			if (str.length() != 1) {
				throw new InvalidInputException("charには、サロゲートペアを必要としない1文字のみ指定してください");
			}
			value =  str.charAt(0);
		} else if (elementComponent.getName().equals(BYTE)) {
			value = ((ByteSpinner)elementComponent).getByte();
		} else if (elementComponent.getName().equals(SHORT)) {
			value = ((ShortSpinner)elementComponent).getShort();
		} else if (elementComponent.getName().equals(INT)) {
			value = ((IntSpinner)elementComponent).getInt();
		} else if (elementComponent.getName().equals(LONG)) {
			value = ((LongSpinner)elementComponent).getLong();
		} else if (elementComponent.getName().equals(FLOAT)) {
			float f = 0.0f;
			try {
				f = Float.parseFloat(((JTextField)elementComponent).getText());
			} catch (NumberFormatException e) {
				throw new InvalidInputException("floatの入力として不正な値が使用されています。");
			}
			value = f;
		} else if (elementComponent.getName().equals(DOUBLE)) {
			double d = 0.0;
			try {
				d = Double.parseDouble(((JTextField)elementComponent).getText());
			} catch (NumberFormatException e) {
				throw new InvalidInputException("doubleの入力として不正な値が使用されています。");
			}
			value = d;
		} else if (elementComponent.getName().equals(STRING)) {
			value = ((JTextField)elementComponent).getText();
		} else if (elementComponent.getName().equals(OBJECT)) {
			value = ((InstanceHoldingDialog)elementComponent).getInstance();
		} else {
			throw new AssertionError("not to be passed.");
		}
		Array.set(getInstance(), index, value);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// Do nothing
	}

}
