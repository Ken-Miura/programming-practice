/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
public final class MethodDialog extends JDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -8155938973225114166L;
	private static final String TITLE = "メソッドの実行";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 800;
	private static final int MARGIN = 7;
	
	private final Object createdObject;
	private final Class<?> createdObjectType;
	private final JComboBox<Method> methodComboBox;
	private final JPanel parameterArea = new JPanel(new GridBagLayout());
	private final List<JDialog> dialogList = new ArrayList<>();
	private final JPanel returnValueArea = new JPanel(new GridBagLayout());
	
	public MethodDialog (Object createdObject) {
		Objects.requireNonNull(createdObject, "createdObject must not be null.");
		this.createdObject = createdObject;
		this.createdObjectType = this.createdObject.getClass();
		
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		setModal(true);

		GridBagConstraints gc = new GridBagConstraints();
		
		JLabel methodCaption = new JLabel("メソッド一覧");
		gc.gridx = 0;
		gc.gridy = 0;
		add(methodCaption, gc);
		
		final Method[] methods = mergeMethods(this.createdObjectType.getDeclaredMethods(), this.createdObjectType.getMethods());
		methodComboBox = new JComboBox<>();
		for (final Method method: methods) {
			method.setAccessible(true);
			methodComboBox.addItem(method);
		}
		
		methodComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parameterArea.removeAll();
				Method selectedMethod = (Method) methodComboBox.getSelectedItem();
				if (selectedMethod == null) { // removeAllのとき
					return;
				}
				dialogList.clear(); //　コンストラクタが変わるたびに削除
				java.lang.reflect.Type[] types = selectedMethod.getGenericParameterTypes();
				
				GridBagConstraints componetConstraintsEachItem = new GridBagConstraints();
				componetConstraintsEachItem.gridx = 0;
				componetConstraintsEachItem.gridy = 0;
				componetConstraintsEachItem.anchor = GridBagConstraints.CENTER;
				componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
				parameterArea.add(new JLabel("引数一覧"), componetConstraintsEachItem);
				
				for (int i=0; i<types.length; i++) {
					
					if (types[i] == null) {
						throw new AssertionError("types[i] cannot be null.");
					}
					
					if (types[i] == boolean.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("boolean : "), componetConstraintsEachItem);
						
						JComboBox<Boolean> booleanComboBox = new JComboBox<>();
						booleanComboBox.addItem(Boolean.FALSE);
						booleanComboBox.addItem(Boolean.TRUE);
							
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(booleanComboBox, componetConstraintsEachItem);
						
					} else if (types[i] == char.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("char : "), componetConstraintsEachItem);
												
						SpinnerNumberModel charModel = new SpinnerNumberModel(0, 0, Character.MAX_VALUE, 1);  
						JSpinner charSpinner = new JSpinner(charModel);
						charSpinner.setName("charSpinner");	
						
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(charSpinner, componetConstraintsEachItem);	
						
					} else if (types[i] == byte.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("byte : "), componetConstraintsEachItem);
												
						SpinnerNumberModel byteModel = new SpinnerNumberModel(0, Byte.MIN_VALUE, Byte.MAX_VALUE, 1);  
						JSpinner byteSpinner = new JSpinner(byteModel);
						byteSpinner.setName("byteSpinner");
							
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(byteSpinner, componetConstraintsEachItem);							
						
					} else if (types[i] == short.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("short : "), componetConstraintsEachItem);
												
						SpinnerNumberModel shortModel = new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1);  
						JSpinner shortSpinner = new JSpinner(shortModel);
						shortSpinner.setName("shortSpinner");
							
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(shortSpinner, componetConstraintsEachItem);													
						
					} else if (types[i] == int.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("int : "), componetConstraintsEachItem);
												
						SpinnerNumberModel intModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);  
						JSpinner intSpinner = new JSpinner(intModel);
						intSpinner.setName("intSpinner");
							
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(intSpinner, componetConstraintsEachItem);																			
						
					} else if (types[i] == long.class) {
						
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("long : "), componetConstraintsEachItem);
												
						SpinnerNumberModel longModel = new SpinnerNumberModel(0, Long.MIN_VALUE, Long.MAX_VALUE, 1);  
						JSpinner longSpinner = new JSpinner(longModel);
						longSpinner.setName("longSpinner");
							
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(longSpinner, componetConstraintsEachItem);																									
						
					} else if (types[i] == float.class) {
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("float : "), componetConstraintsEachItem);
												
						JTextField floatTextField = new JTextField(16);
						floatTextField.setText("0.0");
						floatTextField.setName("floatTextField");
						floatTextField.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									Float.parseFloat(floatTextField.getText());									
								} catch (NumberFormatException nfe) {
									nfe.printStackTrace();
									floatTextField.setText("0.0");
								}
							}
						});
						
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(floatTextField, componetConstraintsEachItem);																									
					} else if (types[i] == double.class) {
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("double : "), componetConstraintsEachItem);
												
						JTextField doubleTextField = new JTextField(16);
						doubleTextField.setText("0.0");
						doubleTextField.setName("doubleTextField");
						doubleTextField.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									Double.parseDouble(doubleTextField.getText());									
								} catch (NumberFormatException nfe) {
									nfe.printStackTrace();
									doubleTextField.setText("0.0");
								}
							}
						});
						
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(doubleTextField, componetConstraintsEachItem);																									
						
					} else if (types[i] == java.lang.String.class) {
						componetConstraintsEachItem.gridx = 0;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("String : "), componetConstraintsEachItem);
												
						JTextField stringTextField = new JTextField("", 16);
						stringTextField.setName("stringTextField");
						
						componetConstraintsEachItem.gridx = 1;
						componetConstraintsEachItem.gridy = i+1;
						componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
						componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(stringTextField, componetConstraintsEachItem);																									
					} else {
						final Class<?> cls;
						if (types[i] instanceof Class<?>) {
							cls = (Class<?>) types[i];
						} else if (types[i] instanceof ParameterizedType) {
							cls = (Class<?>) ((ParameterizedType) types[i]).getRawType();
						} else {
							throw new Error("Unexpected non-class types[i]");	
						}
						
						if (cls.isArray()) {
							componetConstraintsEachItem.gridx = 0;
							componetConstraintsEachItem.gridy = i+1;
							componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
							componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(new JLabel(cls.getName() + " : "), componetConstraintsEachItem);
													
							JButton instanceCreationButton = new JButton("配列を生成する (生成しない場合はnullを利用する)");
							instanceCreationButton.setName("array");	
							instanceCreationButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO
								}
							});
							
							componetConstraintsEachItem.gridx = 1;
							componetConstraintsEachItem.gridy = i+1;
							componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
							componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(instanceCreationButton, componetConstraintsEachItem);
							
						} else {
							componetConstraintsEachItem.gridx = 0;
							componetConstraintsEachItem.gridy = i+1;
							componetConstraintsEachItem.anchor = GridBagConstraints.EAST;
							componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(new JLabel(cls.getName() + " : "), componetConstraintsEachItem);
													
							JButton instanceCreationButton = new JButton("インスタンスを生成する (生成しない場合はnullを利用する)");
							instanceCreationButton.setName("reference");	
							InstanceCreationDialog icd = new InstanceCreationDialog(cls);
							dialogList.add(icd);
							instanceCreationButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									icd.setLocation(getLocation());
									icd.setVisible(true);
								}
							});
							
							componetConstraintsEachItem.gridx = 1;
							componetConstraintsEachItem.gridy = i+1;
							componetConstraintsEachItem.anchor = GridBagConstraints.WEST;
							componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(instanceCreationButton, componetConstraintsEachItem);																									
						}
					}	
				}
				JButton executeMethodButton = new JButton("メソッドを実行する");
				executeMethodButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						List<Object> args = new ArrayList<>();
						
						Component[] components = parameterArea.getComponents();
						int referenceArgCount = 0;
						for (int i=0; i<components.length; i++) {
							if (components[i] instanceof JComboBox) {
								args.add(((JComboBox<?>)components[i]).getSelectedItem());
							} else if (components[i] instanceof JSpinner && components[i].getName().equals("charSpinner")) {
								int temp = (Integer) ((JSpinner)components[i]).getValue();
								args.add((char) temp);
							} else if (components[i] instanceof JSpinner && components[i].getName().equals("byteSpinner")) {
								int temp = (Integer)((JSpinner)components[i]).getValue();
								args.add((byte)temp);
							} else if (components[i] instanceof JSpinner && components[i].getName().equals("shortSpinner")) {
								int temp = (Integer) ((JSpinner)components[i]).getValue();
								args.add((short) temp);
							} else if (components[i] instanceof JSpinner && components[i].getName().equals("intSpinner")) {
								args.add(((JSpinner)components[i]).getValue());
							} else if (components[i] instanceof JSpinner && components[i].getName().equals("longSpinner")) {
								double temp = (Double) ((JSpinner)components[i]).getValue();
								args.add((long) temp);
							} else if (components[i] instanceof JTextField && components[i].getName().equals("floatTextField")) {
								args.add(Float.parseFloat(((JTextField)components[i]).getText()));
							} else if (components[i] instanceof JTextField && components[i].getName().equals("doubleTextField")) {
								args.add(Double.parseDouble(((JTextField)components[i]).getText()));
							} else if (components[i] instanceof JTextField && components[i].getName().equals("stringTextField")) {
								args.add(((JTextField)components[i]).getText());
							} else if (components[i] instanceof JButton && (components[i].getName() != null) && ( components[i].getName().equals("reference") || components[i].getName().equals("array"))) {
								JDialog dialog = (JDialog) dialogList.get(referenceArgCount);
								if (dialog instanceof InstanceCreationDialog) {
									args.add(((InstanceCreationDialog)dialog).getCreatedInstance());	
									referenceArgCount++;
								} else if (dialog instanceof ArrayCreationDialog) {
									// TODO
									referenceArgCount++;
								} else {
									throw new AssertionError("not to be passed.");
								}
							} else {
								//System.out.println("for debug");
							}
						}
						
						Method selectedMethod = (Method) methodComboBox.getSelectedItem();
						try {
							final Object returnValue = selectedMethod.invoke(MethodDialog.this.createdObject, args.toArray(new Object[0]));
							
							returnValueArea.removeAll();
							GridBagConstraints gc = new GridBagConstraints();
							gc.gridx = 0;
							gc.gridy = 0;
							gc.fill = GridBagConstraints.HORIZONTAL;
							returnValueArea.add(new JLabel("戻り値") ,gc);
							
							gc.gridy = 1;
							
							if (returnValue == null) {
								returnValueArea.add(new JLabel("戻り値なし"), gc);
							} else if (returnValue.getClass() == java.lang.Boolean.class) {
								returnValueArea.add(new JLabel("値: Boolean, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Character.class) {
								returnValueArea.add(new JLabel("型: Character, 値: " + returnValue), gc);							
							} else if (returnValue.getClass() == java.lang.Byte.class) {
								returnValueArea.add(new JLabel("型: Byte, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Short.class) {
								returnValueArea.add(new JLabel("型: Short, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Integer.class) {
								returnValueArea.add(new JLabel("型: Integer, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Long.class) {
								returnValueArea.add(new JLabel("型: Long,  値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Float.class) {
								returnValueArea.add(new JLabel("型: Float, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.Double.class) {
								returnValueArea.add(new JLabel("型: Double, 値: " + returnValue), gc);
							} else if (returnValue.getClass() == java.lang.String.class) {
								returnValueArea.add(new JLabel("型: String, 値: " + returnValue), gc);
							} else {
								if (returnValue.getClass().isArray()) {
									// TODO
								} else {
									returnValueArea.add(new JLabel("String型" + returnValue), gc);
									JButton button = new JButton("フィールド一覧を開く");
									button.addActionListener(new ActionListener() {
										
										@Override
										public void actionPerformed(ActionEvent e) {
											new FieldDialog(returnValue).setVisible(true);
										}
									});
									gc.gridy = 2;
									returnValueArea.add(button, gc);
								}
							}							
						} catch ( IllegalAccessException
								| IllegalArgumentException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "メソッド実行中に例外  ("+ ex.getClass().toString() +") が発生しました。", "メソッド実行失敗", JOptionPane.ERROR_MESSAGE);
						} catch (InvocationTargetException ie) {
							JOptionPane.showMessageDialog(null, "メソッド実行中に例外 ("+ ie.getCause().getClass().toString() +") が発生しました。", "メソッド実行失敗", JOptionPane.ERROR_MESSAGE);
						}
						returnValueArea.revalidate();
					}
				});
				GridBagConstraints componetConstraintsForButton = new GridBagConstraints();
				componetConstraintsForButton.insets = new Insets(MARGIN * 3, MARGIN, MARGIN, MARGIN);
				componetConstraintsForButton.gridx = 0;
				componetConstraintsForButton.gridy = types.length+1;
				componetConstraintsForButton.anchor = GridBagConstraints.SOUTHEAST;
				parameterArea.add(executeMethodButton, componetConstraintsForButton);
				
				parameterArea.revalidate();
			}
		});
		
		gc.gridy = 1;
		add(methodComboBox, gc);
		
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(parameterArea, gc);
		
		gc.gridy = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		add(returnValueArea, gc);
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
	
	// for only static method
//	public MethodDialog (Class<?> createdObjectType) {
//		Objects.requireNonNull(createdObjectType, "createdObjectType must not be null.");
//		this.createdObject = null;
//		this.createdObjectType = createdObjectType;
//	}
}
