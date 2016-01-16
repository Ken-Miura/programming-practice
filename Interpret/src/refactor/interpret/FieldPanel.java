/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Ken Miura
 *
 */
final class FieldPanel extends JPanel {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 6205910764952203551L;
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

	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	
	private final Object instance;
	private final JLabel caption = new JLabel("フィールド一覧");
	private final JComboBox<Field> fieldCombo = new JComboBox<>();
	private final JPanel displayAndModifyValuePanel = new JPanel(new GridBagLayout());
	private Component fieldComponent = null;
	//private final ActionListener 
	
	private FieldPanel (Object instance) {
		super (new GridBagLayout());
		this.instance = Objects.requireNonNull(instance, "instance must not be null");
		
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componentConstraints.anchor = GridBagConstraints.CENTER;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		Field[] fields = instance.getClass().getDeclaredFields();
		for (final Field f: fields) {
			f.setAccessible(true);
			fieldCombo.addItem(f);
		}
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		add(caption, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		add(fieldCombo, componentConstraints);
		
		fieldCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAndModifyValuePanel.removeAll();
				if (fieldCombo.getSelectedItem() == null) {
					return;
				}
				addValueAndComponents((Field) fieldCombo.getSelectedItem());
				revalidate();
			}

		});
	}

	private void addValueAndComponents(Field selectedField) {
		Class<?> type = selectedField.getType();
		fieldComponent = null;
		
		GridBagConstraints componentConstraints = new GridBagConstraints();
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		componentConstraints.anchor = GridBagConstraints.EAST;
		displayAndModifyValuePanel.add(new JLabel("型: " + type.getName() + ", 現在値: "), componentConstraints);
		
		String currentValue = null;
		try {
			currentValue = selectedField.get(instance).toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new AssertionError("not to be passed here");
		}
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.WEST;
		displayAndModifyValuePanel.add(new JLabel(currentValue), componentConstraints);		
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.EAST;
		displayAndModifyValuePanel.add(new JLabel(type.getName() + ": "), componentConstraints);
		
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.WEST;
		
		if (type == boolean.class) {			
			JComboBox<Boolean> combo = new JComboBox<>();
			combo.setName(BOOLEAN);
			combo.addItem(Boolean.TRUE);
			combo.addItem(Boolean.FALSE);
			displayAndModifyValuePanel.add(combo, componentConstraints);
			
			fieldComponent = combo;
		} else if (type == char.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(CHAR);
			displayAndModifyValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == byte.class) {
			ByteSpinner byteSpinner = new ByteSpinner();
			byteSpinner.setName(BYTE);
			displayAndModifyValuePanel.add(byteSpinner, componentConstraints);
			
			fieldComponent = byteSpinner;
		} else if (type == short.class) {
			ShortSpinner shortSpinner = new ShortSpinner();
			shortSpinner.setName(SHORT);
			displayAndModifyValuePanel.add(shortSpinner, componentConstraints);
			
			fieldComponent = shortSpinner;
		} else if (type == int.class) {
			IntSpinner intSpinner = new IntSpinner();
			intSpinner.setName(INT);
			displayAndModifyValuePanel.add(intSpinner, componentConstraints);
			
			fieldComponent = intSpinner;
		} else if (type == long.class) {
			LongSpinner longSpinner = new LongSpinner();
			longSpinner.setName(LONG);
			displayAndModifyValuePanel.add(longSpinner, componentConstraints);
			
			fieldComponent = longSpinner;
		} else if (type == float.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(FLOAT);
			displayAndModifyValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == double.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(DOUBLE);
			displayAndModifyValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == String.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(STRING);
			displayAndModifyValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else {
			addInstanceCreationButton(type);
		}		
	}
	
	private void addInstanceCreationButton(Class<?> cls) {
		assert cls != null;
		JButton button = null;
		if (cls.isArray()) {
			button = new JButton("配列を生成する (生成しない場合はnullを使用)");
		} else {
			button = new JButton("インスタンスを生成する (生成しない場合はnullを使用)");
		}
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				InstanceHoldingDialog instanceHoldingDialog = null;
				if (cls.isArray()) {
					instanceHoldingDialog = new ArrayCreationDialog(cls.getComponentType());
				} else {
					instanceHoldingDialog = new InstanceCreationDialog(cls);
				}
				assert instanceHoldingDialog != null;
				instanceHoldingDialog.setName(OBJECT);
				fieldComponent = instanceHoldingDialog;
				instanceHoldingDialog.setVisible(true);
			}
		});
		displayAndModifyValuePanel.add(button, componentConstraints);
	}

	
	public static FieldPanel createFieldPanel(Object instance) {
		return new FieldPanel(instance);
	}
}
