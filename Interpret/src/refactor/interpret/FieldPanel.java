/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	static final String FIELD_CHANGE = "field_change";
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

	private final PropertyChangeSupport notifier;
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	
	private final Object instance;
	private final JLabel caption = new JLabel("フィールド一覧");
	private final JComboBox<Field> fieldCombo = new JComboBox<>();
	private final JPanel displayAndInputValuePanel = new JPanel(new GridBagLayout());
	private final JButton modifyValueButton = new JButton("値を変更する");
	private Component fieldComponent = null;
	
	private FieldPanel (Object instance, PropertyChangeListener listener) {
		super (new GridBagLayout());
		this.instance = Objects.requireNonNull(instance, "instance must not be null");
		Objects.requireNonNull(listener, "listener must not be null");
		notifier = new PropertyChangeSupport(this);
		notifier.addPropertyChangeListener(listener);
		
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
		
		fieldCombo.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				displayAndInputValuePanel.removeAll();
				if (fieldCombo.getSelectedItem() == null) { // まだ何もフィールドが選択されてないときは何もしない
					return;
				}
				addValueAndComponents((Field) fieldCombo.getSelectedItem());
				revalidate();
				repaint();
				notifier.firePropertyChange(FIELD_CHANGE, null, null);
			}

		});
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 2;
		add(displayAndInputValuePanel, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 3;
		add(modifyValueButton, componentConstraints);
		
		modifyValueButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (fieldComponent == null) {
					return;
				}
				Field selectedField = (Field) fieldCombo.getSelectedItem();
				Object value = null;
				
				try {
					value = retrieveInputValue();
				} catch (InvalidInputException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "入力エラー", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					if (Modifier.isStatic(selectedField.getModifiers()) && Modifier.isFinal(selectedField.getModifiers())) {
						setStaticFinalField(selectedField, value);
					} else {
						selectedField.set(instance, value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new AssertionError("not to be passed here");
				}
				displayAndInputValuePanel.removeAll();
				addValueAndComponents(selectedField);
				FieldPanel.this.revalidate();
				FieldPanel.this.repaint();
				notifier.firePropertyChange(FIELD_CHANGE, null, null);
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
		displayAndInputValuePanel.add(new JLabel("型: " + type.getName() + ", "), componentConstraints);
		
		String currentValue = null;
		try {
			currentValue = selectedField.get(instance) == null ? "null" : selectedField.get(instance).toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			throw new AssertionError("not to be passed here");
		}
		componentConstraints.gridx = 1;
		componentConstraints.gridy = 0;
		componentConstraints.anchor = GridBagConstraints.WEST;
		displayAndInputValuePanel.add(new JLabel("現在値: " + currentValue), componentConstraints);		
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		
		if (type == boolean.class) {			
			JComboBox<Boolean> combo = new JComboBox<>();
			combo.setName(BOOLEAN);
			combo.addItem(Boolean.TRUE);
			combo.addItem(Boolean.FALSE);
			displayAndInputValuePanel.add(combo, componentConstraints);
			
			fieldComponent = combo;
		} else if (type == char.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(CHAR);
			displayAndInputValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == byte.class) {
			ByteSpinner byteSpinner = new ByteSpinner();
			byteSpinner.setName(BYTE);
			displayAndInputValuePanel.add(byteSpinner, componentConstraints);
			
			fieldComponent = byteSpinner;
		} else if (type == short.class) {
			ShortSpinner shortSpinner = new ShortSpinner();
			shortSpinner.setName(SHORT);
			displayAndInputValuePanel.add(shortSpinner, componentConstraints);
			
			fieldComponent = shortSpinner;
		} else if (type == int.class) {
			IntSpinner intSpinner = new IntSpinner();
			intSpinner.setName(INT);
			displayAndInputValuePanel.add(intSpinner, componentConstraints);
			
			fieldComponent = intSpinner;
		} else if (type == long.class) {
			LongSpinner longSpinner = new LongSpinner();
			longSpinner.setName(LONG);
			displayAndInputValuePanel.add(longSpinner, componentConstraints);
			
			fieldComponent = longSpinner;
		} else if (type == float.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(FLOAT);
			displayAndInputValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == double.class) {
			JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
			text.setName(DOUBLE);
			displayAndInputValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else if (type == String.class) {
			JTextField text = TextFieldUtil.createStringTextField();
			text.setName(STRING);
			displayAndInputValuePanel.add(text, componentConstraints);
			
			fieldComponent = text;
		} else {
			addInstanceCreationButton(type);
		}		
	}
	
	private void addInstanceCreationButton(Class<?> cls) {
		assert cls != null;
		final JButton button;
		final InstanceHoldingDialog instanceHoldingDialog;
		if (cls.isArray()) {
			button = new JButton("配列を生成する (生成しない場合はnullを使用)");
			instanceHoldingDialog = new ArrayCreationDialog(cls.getComponentType());
		} else {
			button = new JButton("インスタンスを生成する (生成しない場合はnullを使用)");
			instanceHoldingDialog = new InstanceCreationDialog(cls);
		}
		instanceHoldingDialog.setName(OBJECT);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				instanceHoldingDialog.setVisible(true);
			}
		});
		displayAndInputValuePanel.add(button, componentConstraints);
		fieldComponent = instanceHoldingDialog;
	}

	@SuppressWarnings("unchecked") /* 正しい型にキャストできていることは明らか */ 
	private Object retrieveInputValue() throws InvalidInputException {
		if (fieldComponent.getName().equals(BOOLEAN)) {
			return ((JComboBox<Boolean>)fieldComponent).getSelectedItem();
		} else if (fieldComponent.getName().equals(CHAR)) {
			String str = ((JTextField) fieldComponent).getText();
			if (str.length() != 1) {
				throw new InvalidInputException("charには、サロゲートペアを必要としない1文字のみ指定してください");
			}
			return str.charAt(0);
		} else if (fieldComponent.getName().equals(BYTE)) {
			return ((ByteSpinner)fieldComponent).getByte();
		} else if (fieldComponent.getName().equals(SHORT)) {
			return ((ShortSpinner)fieldComponent).getShort();
		} else if (fieldComponent.getName().equals(INT)) {
			return ((IntSpinner)fieldComponent).getInt();
		} else if (fieldComponent.getName().equals(LONG)) {
			return ((LongSpinner)fieldComponent).getLong();
		} else if (fieldComponent.getName().equals(FLOAT)) {
			float f = 0.0f;
			try {
				f = Float.parseFloat(((JTextField)fieldComponent).getText());
			} catch (NumberFormatException e) {
				throw new InvalidInputException("floatの入力として不正な値が使用されています。");
			}
			return f;
		} else if (fieldComponent.getName().equals(DOUBLE)) {
			double d = 0.0;
			try {
				d = Double.parseDouble(((JTextField)fieldComponent).getText());
			} catch (NumberFormatException e) {
				throw new InvalidInputException("doubleの入力として不正な値が使用されています。");
			}
			return d;
		} else if (fieldComponent.getName().equals(STRING)) {
			return ((JTextField)fieldComponent).getText();
		} else if (fieldComponent.getName().equals(OBJECT)) {
			return ((InstanceHoldingDialog)fieldComponent).getInstance();
		} else {
			throw new AssertionError("not to be passed.");
		}
	}
		
	private static void setStaticFinalField(Field field, Object value) throws IllegalAccessException {
		      Field modifiersField;

		      try {
		         modifiersField = Field.class.getDeclaredField("modifiers");
		      }
		      catch (NoSuchFieldException e) {
		         throw new RuntimeException(e);
		      }

		      modifiersField.setAccessible(true);
		      int modifiersBeforeRemovingFinal = modifiersField.getInt(field);
		      int nonFinalModifiers = modifiersField.getInt(field) - java.lang.reflect.Modifier.FINAL;
		      modifiersField.setInt(field, nonFinalModifiers);

		      //noinspection UnnecessaryFullyQualifiedName,UseOfSunClasses
		      sun.reflect.FieldAccessor accessor =
		         sun.reflect.ReflectionFactory.getReflectionFactory().newFieldAccessor(field, false);
		      accessor.set(null, value);
		      
		      modifiersField.setInt(field, modifiersBeforeRemovingFinal);
	}
	
	
	public static FieldPanel createFieldPanel(Object instance, PropertyChangeListener listener) {
		return new FieldPanel(instance, listener);
	}
}
