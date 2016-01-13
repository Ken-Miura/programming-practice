///* Copyright (C) 2016 Ken Miura */
//package refactor.interpret;
//
//import java.awt.Component;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.lang.reflect.Field;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import javax.swing.JButton;
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
///**
// * @author Ken Miura
// *
// */
//final class FieldPanel extends JPanel {
//
//	/**
//	 * Ver 1.0
//	 */
//	private static final long serialVersionUID = 6205910764952203551L;
//	private static final int MARGIN = 3;
//	// コンポーネントの区別のために利用
//	private static final String BOOLEAN = "boolean";
//	private static final String CHAR = "char";
//	private static final String BYTE = "byte";
//	private static final String SHORT = "short";
//	private static final String INT = "int";
//	private static final String LONG = "long";
//	private static final String FLOAT = "float";
//	private static final String DOUBLE = "double";
//	private static final String STRING = "String"; // String型だけ他の参照型と区別。その方が使いやすいだろうから。
//	private static final String OBJECT = "Object";	
//
//	private final GridBagConstraints componentConstraints = new GridBagConstraints();
//	
//	private final Object instance;
//	private final JLabel caption = new JLabel("フィールド一覧");
//	private final JComboBox<Field> fieldCombo = new JComboBox<>();
//	private Component fieldComponent = null;
//	//private final ActionListener 
//	
//	private FieldPanel (Object instance) {
//		super (new GridBagLayout());
//		this.instance = Objects.requireNonNull(instance, "instance must not be null");
//		
//		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
//		componentConstraints.anchor = GridBagConstraints.CENTER;
//		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
//		
//		Field[] fields = instance.getClass().getDeclaredFields();
//		for (final Field f: fields) {
//			f.setAccessible(true);
//			fieldCombo.addItem(f);
//		}
//	}
//	
//	public static FieldPanel createFieldPanel(Object instance) {
//		return new FieldPanel(instance);
//	}
//	
//	@SuppressWarnings("unchecked") /*  */ 
//	public Object getFieldValue () throws InvalidInputException {
//		
//			if (fieldComponent.getName().equals(BOOLEAN)) {
//				return ((JComboBox<Boolean>)fieldComponent).getSelectedItem();
//			} else if (fieldComponent.getName().equals(CHAR)) {
//				String str = ((JTextField) fieldComponent).getText();
//				if (str.length() != 1) {
//					throw new InvalidInputException("charには、サロゲートペアを必要としない1文字のみ指定してください");
//				}
//				return str.charAt(0);
//			} else if (fieldComponent.getName().equals(BYTE)) {
//				return (((ByteSpinner)fieldComponent).getByte());
//			} else if (c.getName().equals(SHORT)) {
//				parameterValues.add(((ShortSpinner)c).getShort());
//			} else if (c.getName().equals(INT)) {
//				parameterValues.add(((IntSpinner)c).getInt());
//			} else if (c.getName().equals(LONG)) {
//				parameterValues.add(((LongSpinner)c).getLong());
//			} else if (c.getName().equals(FLOAT)) {
//				float f = 0.0f;
//				try {
//					f = Float.parseFloat(((JTextField)c).getText());
//				} catch (NumberFormatException e) {
//					throw new InvalidInputException("floatの入力として不正な値が使用されています。");
//				}
//				parameterValues.add(f);
//			} else if (c.getName().equals(DOUBLE)) {
//				double d = 0.0;
//				try {
//					d = Double.parseDouble(((JTextField)c).getText());
//				} catch (NumberFormatException e) {
//					throw new InvalidInputException("doubleの入力として不正な値が使用されています。");
//				}
//				parameterValues.add(d);
//			} else if (c.getName().equals(STRING)) {
//				parameterValues.add(((JTextField)c).getText());
//			} else if (c.getName().equals(OBJECT)) {
//				parameterValues.add(((InstanceHoldingDialog)c).getInstance());
//			} else {
//				throw new AssertionError("not to be passed.");
//			}
//	}
//	
//	private void addParametersOntoPanel(Class<?> fieldType) {
//		assert fieldType != null;
//		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
//		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
//			componentConstraints.gridy = i;
//			if (fieldType == boolean.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("boolean: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				JComboBox<Boolean> combo = new JComboBox<>();
//				combo.setName(BOOLEAN);
//				combo.addItem(Boolean.TRUE);
//				combo.addItem(Boolean.FALSE);
//				add(combo, componentConstraints);
//				
//				fieldComponent = combo;
//			} else if (parameters[i] == char.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("char: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				JTextField text = TextFieldUtil.createStringTextField();
//				text.setName(CHAR);
//				add(text, componentConstraints);
//				
//				fieldComponent.add(text);
//			} else if (parameters[i] == byte.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("byte: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				ByteSpinner byteSpinner = new ByteSpinner();
//				byteSpinner.setName(BYTE);
//				add(byteSpinner, componentConstraints);
//				
//				fieldComponent.add(byteSpinner);
//			} else if (parameters[i] == short.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("short: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				ShortSpinner shortSpinner = new ShortSpinner();
//				shortSpinner.setName(SHORT);
//				add(shortSpinner, componentConstraints);
//				
//				fieldComponent.add(shortSpinner);
//			} else if (parameters[i] == int.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("int: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				IntSpinner intSpinner = new IntSpinner();
//				intSpinner.setName(INT);
//				add(intSpinner, componentConstraints);
//				
//				fieldComponent.add(intSpinner);
//			} else if (parameters[i] == long.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("long: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				LongSpinner longSpinner = new LongSpinner();
//				longSpinner.setName(LONG);
//				add(longSpinner, componentConstraints);
//				
//				fieldComponent.add(longSpinner);
//			} else if (parameters[i] == float.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("float: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
//				text.setName(FLOAT);
//				add(text, componentConstraints);
//				
//				fieldComponent.add(text);
//			} else if (parameters[i] == double.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("double: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				JTextField text = TextFieldUtil.createFloatingPointNumberTextField();
//				text.setName(DOUBLE);
//				add(text, componentConstraints);
//				
//				fieldComponent.add(text);
//			} else if (parameters[i] == String.class) {
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel("String: "), componentConstraints);
//				
//				componentConstraints.gridx = 1;
//				componentConstraints.anchor = GridBagConstraints.WEST;
//				JTextField text = TextFieldUtil.createStringTextField();
//				text.setName(STRING);
//				add(text, componentConstraints);
//				
//				fieldComponent.add(text);
//			} else {
//				final Class<?> cls;
//				if (parameters[i] instanceof Class<?>) {
//					cls = (Class<?>) parameters[i];
//				} else if (parameters[i] instanceof ParameterizedType) {
//					cls = (Class<?>) ((ParameterizedType) parameters[i]).getRawType();
//				} else {
//					throw new Error("Unexpected non-class parameters["+i+"]");	
//				}
//				componentConstraints.gridx = 0;
//				componentConstraints.anchor = GridBagConstraints.EAST;
//				add(new JLabel(cls.getName() + ": "), componentConstraints);
//				
//				setInstanceCreationButton(cls);
//			}
//	}
//
//	private void setInstanceCreationButton(Class<?> cls) {
//		assert cls != null;
//		componentConstraints.gridx = 1;
//		componentConstraints.anchor = GridBagConstraints.WEST;
//		JButton button = null;
//		if (cls.isArray()) {
//			button = new JButton("配列を生成する (生成しない場合はnullを使用)");
//		} else {
//			button = new JButton("インスタンスを生成する (生成しない場合はnullを使用)");
//		}
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				InstanceHoldingDialog instanceHoldingDialog = null;
//				if (cls.isArray()) {
//					instanceHoldingDialog = new ArrayCreationDialog(cls.getComponentType());
//				} else {
//					instanceHoldingDialog = new InstanceCreationDialog(cls);
//				}
//				assert instanceHoldingDialog != null;
//				instanceHoldingDialog.setName(OBJECT);
//				fieldComponent.add(instanceHoldingDialog);
//				instanceHoldingDialog.setVisible(true);
//			}
//		});
//		add(button, componentConstraints);
//	}
//
//}
