/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

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
public final class InstanceCreationDialog extends JDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -2682389649709922342L;
	private static final String TITLE = "インスタンス生成に関する操作";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 800;
	private static final int MARGIN = 7;
	
	private final JPanel classArea = new JPanel(new GridBagLayout());
	private final JPanel constructorArea = new JPanel(new GridBagLayout());
	private final JPanel parameterArea = new JPanel(new GridBagLayout());
	
	private final Class<?> parent;
	
	private Class<?> searchResult = null;
	private Object createdInstance = null;
	
	private final JLabel searchCaption = new JLabel("検索したいクラスのバイナリ名を入力してください");
	private final JTextField searchClass = new JTextField();
	private final JButton searchButton = new JButton("検索");
	
	private final JLabel constructorCaption = new JLabel("コンストラクタ一覧");
	private Constructor<?>[] constructors = null;
	private final JComboBox<Constructor<?>> constructorsComboBox = new JComboBox<>();
	
	private final JLabel parameterCaptionLabel = new JLabel("引数一覧");
	
	private final List<? super JDialog> dialogList = new ArrayList<>();
	
	public InstanceCreationDialog (Class<?> clazz) {
		parent = clazz;
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		setModal(true);
		
		GridBagConstraints componetConstraintsForPanel = new GridBagConstraints();

		if (parent == null) {
			componetConstraintsForPanel.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
			componetConstraintsForPanel.gridx = 0;
			componetConstraintsForPanel.gridy = 0;
			componetConstraintsForPanel.anchor = GridBagConstraints.NORTH;
			componetConstraintsForPanel.fill = GridBagConstraints.HORIZONTAL;
			add(classArea, componetConstraintsForPanel);	
		}
		
		componetConstraintsForPanel.insets = new Insets(MARGIN, MARGIN * 3, MARGIN, MARGIN * 3);
		componetConstraintsForPanel.gridx = 0;
		componetConstraintsForPanel.gridy = 1;
		componetConstraintsForPanel.anchor = GridBagConstraints.NORTH;
		componetConstraintsForPanel.fill = GridBagConstraints.HORIZONTAL;
		add(constructorArea, componetConstraintsForPanel);
		
		componetConstraintsForPanel.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componetConstraintsForPanel.gridx = 0;
		componetConstraintsForPanel.gridy = 2;
		componetConstraintsForPanel.anchor = GridBagConstraints.NORTH;
		componetConstraintsForPanel.fill = GridBagConstraints.HORIZONTAL;
		add(parameterArea, componetConstraintsForPanel);
		
		GridBagConstraints componetConstraintsForClassArea = new GridBagConstraints();
		componetConstraintsForClassArea.gridx = 0;
		componetConstraintsForClassArea.gridy = 0;
		componetConstraintsForClassArea.anchor = GridBagConstraints.EAST;
		componetConstraintsForClassArea.fill = GridBagConstraints.HORIZONTAL;
		classArea.add(searchCaption, componetConstraintsForClassArea);
		
		componetConstraintsForClassArea.gridx = 0;
		componetConstraintsForClassArea.gridy = 1;
		componetConstraintsForClassArea.anchor = GridBagConstraints.EAST;
		componetConstraintsForClassArea.fill = GridBagConstraints.HORIZONTAL;
		classArea.add(searchClass, componetConstraintsForClassArea);
		
		componetConstraintsForClassArea.gridx = 1;
		componetConstraintsForClassArea.gridy = 1;
		componetConstraintsForClassArea.anchor = GridBagConstraints.EAST;
		componetConstraintsForClassArea.fill = GridBagConstraints.HORIZONTAL;
		classArea.add(searchButton, componetConstraintsForClassArea);
		
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (parent != null) {
					return;
				}
				String binaryName = searchClass.getText();
				try {
					searchResult = Class.forName(binaryName);
					constructors = searchResult.getDeclaredConstructors();
					constructorsComboBox.removeAllItems();
					for (Constructor<?> c: constructors) {
						if (c.getDeclaringClass() == Class.class) { // SecurityException回避のため
							continue;
						}
						c.setAccessible(true);
						constructorsComboBox.addItem(c);
					}
					constructorArea.revalidate();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
					JOptionPane.showMessageDialog(null, "指定されたクラス(" + binaryName + ") が見つかりませんでした。", "クラス検索エラー", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		GridBagConstraints componetConstraintsForConstructorArea = new GridBagConstraints();
		
		componetConstraintsForConstructorArea.gridx = 0;
		componetConstraintsForConstructorArea.gridy = 0;
		componetConstraintsForConstructorArea.anchor = GridBagConstraints.CENTER;
		componetConstraintsForConstructorArea.fill = GridBagConstraints.HORIZONTAL;
		constructorArea.add(constructorCaption, componetConstraintsForConstructorArea);
		
		componetConstraintsForConstructorArea.gridx = 0;
		componetConstraintsForConstructorArea.gridy = 1;
		componetConstraintsForConstructorArea.anchor = GridBagConstraints.CENTER;
		componetConstraintsForConstructorArea.fill = GridBagConstraints.HORIZONTAL;
		constructorArea.add(constructorsComboBox, componetConstraintsForConstructorArea);
		if (parent != null) {
			constructorsComboBox.removeAllItems();
			Constructor<?>[] constructors = parent.getDeclaredConstructors();
			for (Constructor<?> c: constructors) {
				if (c.getDeclaringClass() == Class.class) { // SecurityException回避のため
					continue;
				}
				c.setAccessible(true);
				constructorsComboBox.addItem(c);
			}
			constructorArea.revalidate();
		}
		
		constructorsComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parameterArea.removeAll();
				Constructor<?> selectedConstructor = (Constructor<?>) constructorsComboBox.getSelectedItem();
				if (selectedConstructor == null) { // removeAllのとき
					return;
				}
				dialogList.clear(); //　コンストラクタが変わるたびに削除
				java.lang.reflect.Type[] types = selectedConstructor.getGenericParameterTypes();
				
				GridBagConstraints componetConstraintsEachItem = new GridBagConstraints();
				componetConstraintsEachItem.gridx = 0;
				componetConstraintsEachItem.gridy = 0;
				componetConstraintsEachItem.anchor = GridBagConstraints.CENTER;
				componetConstraintsEachItem.fill = GridBagConstraints.HORIZONTAL;
				parameterArea.add(parameterCaptionLabel, componetConstraintsEachItem);
				
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
												
						JTextField stringTextField = new JTextField(16);
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
				JButton instanceCreationButton = new JButton("インスタンスを生成して閉じる");
				instanceCreationButton.addActionListener(new ActionListener() {
					
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
						
						Constructor<?> selectedConstructor = (Constructor<?>) constructorsComboBox.getSelectedItem();
						try {
							createdInstance = selectedConstructor.newInstance(args.toArray(new Object[0]));
							dispose();
						} catch (InstantiationException
								| IllegalAccessException
								| IllegalArgumentException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "インスタンス生成中に例外 ("+ ex.getClass().toString() +") が発生しました。", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
						} catch (InvocationTargetException ie) {
							JOptionPane.showMessageDialog(null, "インスタンス生成中に例外 ("+ ie.getCause().getClass().toString() +") が発生しました。", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				GridBagConstraints componetConstraintsForButton = new GridBagConstraints();
				componetConstraintsForButton.insets = new Insets(MARGIN * 3, MARGIN, MARGIN, MARGIN);
				componetConstraintsForButton.gridx = 0;
				componetConstraintsForButton.gridy = types.length+1;
				componetConstraintsForButton.anchor = GridBagConstraints.SOUTHEAST;
				parameterArea.add(instanceCreationButton, componetConstraintsForButton);

				JButton instanceCreationAndOpenFieldButton = new JButton("インスタンスを生成してフィールド一覧を開く");
				instanceCreationAndOpenFieldButton.addActionListener(new ActionListener() {
					
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
						
						Constructor<?> selectedConstructor = (Constructor<?>) constructorsComboBox.getSelectedItem();
						try {
							createdInstance = selectedConstructor.newInstance(args.toArray(new Object[0]));	
							// field open
							new FieldDialog (createdInstance).setVisible(true);
							dispose();
						} catch (InstantiationException
								| IllegalAccessException
								| IllegalArgumentException ex) {
							ex.printStackTrace();
							JOptionPane.showMessageDialog(null, "インスタンス生成中に例外 ("+ ex.getClass().toString() +") が発生しました。", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
						} catch (InvocationTargetException ie) {
							JOptionPane.showMessageDialog(null, "インスタンス生成中に例外 ("+ ie.getCause().getClass().toString() +") が発生しました。", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				GridBagConstraints componetConstraintsForButtonFieldOpen = new GridBagConstraints();
				componetConstraintsForButtonFieldOpen.insets = new Insets(MARGIN * 3, MARGIN, MARGIN, MARGIN);
				componetConstraintsForButtonFieldOpen.gridx = 1;
				componetConstraintsForButtonFieldOpen.gridy = types.length+1;
				componetConstraintsForButtonFieldOpen.anchor = GridBagConstraints.SOUTHEAST;
				parameterArea.add(instanceCreationAndOpenFieldButton, componetConstraintsForButtonFieldOpen);				
				
				parameterArea.revalidate();
			}
		});
	}

	public Object getCreatedInstance() {
		return createdInstance;
	}

	public void setCreatedInstance(Object createdInstance) {
		this.createdInstance = createdInstance;
	}

}
