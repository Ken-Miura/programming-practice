/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	
	private final List<Object> tempArgList = new ArrayList<>();
	
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
				String binaryName = searchClass.getText();
				try {
					searchResult = Class.forName(binaryName);
					constructors = searchResult.getDeclaredConstructors();
					constructorsComboBox.removeAllItems();
					for (Constructor<?> c: constructors) {
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
				c.setAccessible(true);
				constructorsComboBox.addItem(c);
			}
			constructorArea.revalidate();
		}
		
		constructorsComboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				parameterArea.removeAll();
				Constructor<?> selectedConstructor = (Constructor<?>) constructorsComboBox.getSelectedItem();
				if (selectedConstructor == null) { // removeAllのとき
					return;
				}
				java.lang.reflect.Type[] types = selectedConstructor.getGenericParameterTypes();
				
				GridBagConstraints componetConstraintsForBoolean = new GridBagConstraints();
				componetConstraintsForBoolean.gridx = 0;
				componetConstraintsForBoolean.gridy = 0;
				componetConstraintsForBoolean.anchor = GridBagConstraints.CENTER;
				componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
				parameterArea.add(parameterCaptionLabel, componetConstraintsForBoolean);
				
				for (int i=0; i<types.length; i++) {
					
					if (types[i] == null) {
						throw new AssertionError("types[i] cannot be null.");
					}
					
					if (types[i] == boolean.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("boolean : "), componetConstraintsForBoolean);
						
						JComboBox<Boolean> booleanComboBox = new JComboBox<>();
						booleanComboBox.addItem(Boolean.FALSE);
						booleanComboBox.addItem(Boolean.TRUE);
							
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(booleanComboBox, componetConstraintsForBoolean);
						
					} else if (types[i] == char.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("char : "), componetConstraintsForBoolean);
												
						SpinnerNumberModel charModel = new SpinnerNumberModel(0.0, 0.0, Character.MAX_VALUE, 1.0);  
						JSpinner charSpinner = new JSpinner(charModel);
						charSpinner.setName("charSpinner");	
						
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(charSpinner, componetConstraintsForBoolean);	
						
					} else if (types[i] == byte.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("byte : "), componetConstraintsForBoolean);
												
						SpinnerNumberModel byteModel = new SpinnerNumberModel(0.0, Byte.MIN_VALUE, Byte.MAX_VALUE, 1.0);  
						JSpinner byteSpinner = new JSpinner(byteModel);
						byteSpinner.setName("byteSpinner");
							
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(byteSpinner, componetConstraintsForBoolean);							
						
					} else if (types[i] == short.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("short : "), componetConstraintsForBoolean);
												
						SpinnerNumberModel shortModel = new SpinnerNumberModel(0.0, Short.MIN_VALUE, Short.MAX_VALUE, 1.0);  
						JSpinner shortSpinner = new JSpinner(shortModel);
						shortSpinner.setName("shortSpinner");
							
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(shortSpinner, componetConstraintsForBoolean);													
						
					} else if (types[i] == int.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("int : "), componetConstraintsForBoolean);
												
						SpinnerNumberModel intModel = new SpinnerNumberModel(0.0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1.0);  
						JSpinner intSpinner = new JSpinner(intModel);
						intSpinner.setName("intSpinner");
							
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(intSpinner, componetConstraintsForBoolean);																			
						
					} else if (types[i] == long.class) {
						
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("long : "), componetConstraintsForBoolean);
												
						SpinnerNumberModel longModel = new SpinnerNumberModel(0.0, Long.MIN_VALUE, Long.MAX_VALUE, 1.0);  
						JSpinner longSpinner = new JSpinner(longModel);
						longSpinner.setName("longSpinner");
							
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(longSpinner, componetConstraintsForBoolean);																									
						
					} else if (types[i] == float.class) {
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("float : "), componetConstraintsForBoolean);
												
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
						
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(floatTextField, componetConstraintsForBoolean);																									
					} else if (types[i] == double.class) {
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("double : "), componetConstraintsForBoolean);
												
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
						
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(doubleTextField, componetConstraintsForBoolean);																									
						
					} else if (types[i] == java.lang.String.class) {
						componetConstraintsForBoolean.gridx = 0;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(new JLabel("String : "), componetConstraintsForBoolean);
												
						JTextField stringTextField = new JTextField(16);
						stringTextField.setName("stringTextField");
						
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(stringTextField, componetConstraintsForBoolean);																									
					} else {
						Class<?> cls = null;
						if (types[i] instanceof Class<?>) {
							cls = (Class<?>) types[i];
						} else if (types[i] instanceof ParameterizedType) {
							cls = (Class<?>) ((ParameterizedType) types[i]).getRawType();
						} else {
							throw new Error("Unexpected non-class types[i]");	
						}
						
						if (cls.isArray()) {
							// TODO
						} else {
							componetConstraintsForBoolean.gridx = 0;
							componetConstraintsForBoolean.gridy = i+1;
							componetConstraintsForBoolean.anchor = GridBagConstraints.EAST;
							componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(new JLabel(cls.getName() + " : "), componetConstraintsForBoolean);
													
							JButton instanceCreationButton = new JButton("インスタンスを生成する");
							instanceCreationButton.setName(cls.getName());
							InstanceCreationDialog icd = new InstanceCreationDialog(cls);							
							instanceCreationButton.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									icd.setLocation(getLocation());
									icd.setVisible(true);
									tempArgList.add(icd.getCreatedInstance());
								}
							});
							
							componetConstraintsForBoolean.gridx = 1;
							componetConstraintsForBoolean.gridy = i+1;
							componetConstraintsForBoolean.anchor = GridBagConstraints.WEST;
							componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
							parameterArea.add(instanceCreationButton, componetConstraintsForBoolean);																									
						}
					}	
				}
				JButton instanceCreationButton = new JButton("インスタンスを生成して閉じる");
				instanceCreationButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Constructor<?> selectedConstructor = (Constructor<?>) constructorsComboBox.getSelectedItem();
						try {
							createdInstance = selectedConstructor.newInstance();
							dispose();
						} catch (InstantiationException
								| IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException ex) {
							ex.printStackTrace();
							// エラーメッセージ
						}
					}
				});
				GridBagConstraints componetConstraintsForButton = new GridBagConstraints();
				componetConstraintsForButton.insets = new Insets(MARGIN * 3, MARGIN, MARGIN, MARGIN);
				componetConstraintsForButton.gridx = 0;
				componetConstraintsForButton.gridy = types.length+1;
				componetConstraintsForButton.anchor = GridBagConstraints.SOUTHEAST;
				parameterArea.add(instanceCreationButton, componetConstraintsForButton);
				
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
