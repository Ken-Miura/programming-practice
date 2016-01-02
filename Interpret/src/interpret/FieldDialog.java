/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author Ken Miura
 *
 */
public final class FieldDialog extends JDialog {
	
	private final Object createdObject;
	private final Class<?> createdObjectType;
	
	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 7379465369448475393L;
	private static final String TITLE = "フィールドの操作";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 800;
	private static final int MARGIN = 7;

	private final JPanel methodExecutionButtonArea = new JPanel();
	private final JPanel fieldArea = new JPanel();
	private final JScrollPane scrollableFieldArea = new JScrollPane(fieldArea);	
	private final JPanel valueArea = new JPanel(new GridBagLayout());
	
	
	public FieldDialog(Object createdObject){
		Objects.requireNonNull(createdObject, "createdObject must not be null.");
		this.createdObject = createdObject;
		this.createdObjectType = this.createdObject.getClass();
		
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		setModal(true);
		
		GridBagConstraints panelConstraints = new GridBagConstraints();
		
		panelConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 0;
		panelConstraints.anchor = GridBagConstraints.NORTH;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(methodExecutionButtonArea, panelConstraints);

		JButton medhodDialogButton = new JButton("メソッドを選択して実行する");
		medhodDialogButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MethodDialog(FieldDialog.this.createdObject).setVisible(true);
			}
		});
		methodExecutionButtonArea.add(medhodDialogButton);
		
		Hashtable<String, Field> firstHierarchy = new WrappedHashtable<>();
		Field[] fields = this.createdObjectType.getDeclaredFields();
		for (final Field f: fields) {
			f.setAccessible(true);
			try {
				firstHierarchy.put(f.toGenericString(), f);
			} catch (IllegalArgumentException iae) {
				iae.printStackTrace();
			}
		}
		JTree fieldTree = new JTree(firstHierarchy);
		fieldTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		fieldTree.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) fieldTree.getLastSelectedPathComponent();
				if (node == null) {
					return;
				}
				valueArea.removeAll();
				Object nodeInfo = node.getUserObject();
				Field f = firstHierarchy.get(nodeInfo);
				JLabel currentValue = new JLabel();
				if (f.getType().isPrimitive() || f.getType() == java.lang.String.class ) {
					if (f.getType() == char.class) {
						try {
							currentValue.setText("型: char, 値: " + f.getChar(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						SpinnerNumberModel charModel = new SpinnerNumberModel(0, 0, Character.MAX_VALUE, 1);  
						JSpinner charSpinner = new JSpinner(charModel);
						valueArea.add(charSpinner);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int i = (Integer) charSpinner.getValue();
								try {
									f.setAccessible(true);
									f.setChar(createdObject, (char)i);
									currentValue.setText("型: char, 値: " + f.getChar(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);						
					} else if (f.getType() == byte.class) { 
						try {
							currentValue.setText("型: byte, 値: " + f.getByte(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						SpinnerNumberModel byteModel = new SpinnerNumberModel(0, Byte.MIN_VALUE, Byte.MAX_VALUE, 1);  
						JSpinner byteSpinner = new JSpinner(byteModel);
						valueArea.add(byteSpinner);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int i = (Integer) byteSpinner.getValue();
								try {
									f.setAccessible(true);
									f.setByte(createdObject, (byte)i);
									currentValue.setText("型: byte, 値: " + f.getByte(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);						
					} else if (f.getType() == short.class) { 
						try {
							currentValue.setText("型: short, 値: " + f.getShort(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						SpinnerNumberModel shortModel = new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1);  
						JSpinner shortSpinner = new JSpinner(shortModel);
						valueArea.add(shortSpinner);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int i = (Integer) shortSpinner.getValue();
								try {
									f.setAccessible(true);
									f.setShort(createdObject, (short)i);
									currentValue.setText("型: short, 値: " + f.getShort(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);						
					} else if (f.getType() == int.class) {
						try {
							currentValue.setText("型: int, 値: " + f.getInt(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						SpinnerNumberModel intModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);  
						JSpinner intSpinner = new JSpinner(intModel);
						valueArea.add(intSpinner);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								int i = (Integer) intSpinner.getValue();
								try {
									f.setAccessible(true);
									f.setInt(createdObject, i);
									currentValue.setText("型: int, 値: " + f.getInt(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);
					} else if (f.getType() == long.class) {
						try {
							currentValue.setText("型: long, 値: " + f.getLong(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						SpinnerNumberModel longModel = new SpinnerNumberModel(0, Long.MIN_VALUE, Long.MAX_VALUE, 1);  
						JSpinner longSpinner = new JSpinner(longModel);
						valueArea.add(longSpinner);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								double d = (Double) longSpinner.getValue();
								try {
									f.setAccessible(true);
									f.setLong(createdObject, (long)d);
									currentValue.setText("型: long, 値: " + f.getLong(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);
					} else if (f.getType() == float.class) {
						try {
							currentValue.setText("型: float, 値: " + f.getFloat(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						JTextField floatText = new JTextField("0.0", 10);
						floatText.setHorizontalAlignment(JTextField.RIGHT);
						valueArea.add(floatText);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								String floatString = floatText.getText();
								final float floatValue;
								try {
									floatValue = Float.parseFloat(floatString);									
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(null, floatString + "はfloat型として不正な入力です", "入力エラー", JOptionPane.ERROR_MESSAGE);
									return;
								}

								try {
									f.setAccessible(true);
									f.setFloat(createdObject, floatValue);
									currentValue.setText("型: float, 値: " + f.getFloat(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);						
					} else if (f.getType() == double.class) {
						try {
							currentValue.setText("型: double, 値: " + f.getDouble(createdObject));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						valueArea.add(currentValue);
						
						JTextField doubleText = new JTextField("0.0", 10);
						doubleText.setHorizontalAlignment(JTextField.RIGHT);
						valueArea.add(doubleText);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								String doubleString = doubleText.getText();
								final double doubleValue;
								try {
									doubleValue = Double.parseDouble(doubleString);									
								} catch (NumberFormatException e1) {
									JOptionPane.showMessageDialog(null, doubleString + "はdouble型として不正な入力です", "入力エラー", JOptionPane.ERROR_MESSAGE);
									return;
								}

								try {
									f.setAccessible(true);
									f.setDouble(createdObject, doubleValue);
									currentValue.setText("型: double, 値: " + f.getDouble(createdObject));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						valueArea.add(button);
					} else if (f.getType() == java.lang.String.class) {
						GridBagConstraints gc = new GridBagConstraints();
						final String str;
						try {
							str = (String)f.get(createdObject);
							currentValue.setText("型: String, 値: " + (str == null ? "null" : str));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						gc.gridx = 0;
						gc.gridy = 0;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(currentValue, gc);
						
						JTextField text = new JTextField("", 10);
						valueArea.add(text);
						
						JButton button = new JButton("値を変更する");
						button.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								String strText = text.getText();
								try {
									f.setAccessible(true);
									f.set(createdObject, strText);
									String str = (String)f.get(createdObject);
									currentValue.setText("型: String, 値: " + (str == null ? "null" : str));
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						gc.gridx = 0;
						gc.gridy = 1;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(button, gc);	
						
						JButton nullButton = new JButton("nullに変更する");
						nullButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									f.setAccessible(true);
									f.set(createdObject, null);
									currentValue.setText("型: String, 値: null");
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});	
						gc.gridx = 1;
						gc.gridy = 1;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(nullButton, gc);	
					} 
				} else {
					if (f.getType().isArray()) {
						// TODO
					} else {
						GridBagConstraints gc = new GridBagConstraints();
						
						final Object object;
						try {
							object = f.get(createdObject);
							currentValue.setText("型: "+ f.getType().getName() +", 値: " + (object == null ? "null" : object));
						} catch (IllegalArgumentException
								| IllegalAccessException e1) {
							e1.printStackTrace();
						}
						gc.gridx = 0;
						gc.gridy = 0;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(currentValue, gc);
						
						JButton fieldButton = new JButton("フィールド一覧を表示する");
						fieldButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									final Object o = f.get(createdObject);
									if (o == null) {
										JOptionPane.showMessageDialog(null, "参照値がnullです", "入力エラー", JOptionPane.ERROR_MESSAGE);
									} else {
										new FieldDialog(o).setVisible(true);	
									}
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
							}
						});
						gc.gridx = 0;
						gc.gridy = 1;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(fieldButton, gc);
						
						JButton creationButton = new JButton("新しくインスタンスを生成してフィールドを変更する");
						creationButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								InstanceCreationDialog icd = new InstanceCreationDialog(f.getType());
								icd.setVisible(true);
								Object newObject = icd.getCreatedInstance();
								if (newObject == null) {
									JOptionPane.showMessageDialog(null, "インスタンスの生成に失敗しました", "インスタンス生成エラー", JOptionPane.ERROR_MESSAGE);
									return;
								}
								f.setAccessible(true);
								try {
									f.set(FieldDialog.this.createdObject, newObject);
									currentValue.setText("型: "+ f.getType().getName() +", 値: " + newObject);
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});
						gc.gridx = 1;
						gc.gridy = 1;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(creationButton, gc);
						
						JButton nullButton = new JButton("フィールドをnullに変更する");
						nullButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								f.setAccessible(true);
								try {
									f.set(FieldDialog.this.createdObject, null);
									currentValue.setText("型: "+ f.getType().getName() +", 値: null");
								} catch (IllegalArgumentException
										| IllegalAccessException e1) {
									e1.printStackTrace();
								}
								valueArea.revalidate();
							}
						});
						gc.gridx = 1;
						gc.gridy = 2;
						gc.fill = GridBagConstraints.HORIZONTAL;
						valueArea.add(nullButton, gc);
					}
				}
				revalidate();
				repaint();
			}
		});
		
		fieldArea.add(fieldTree);
		
		panelConstraints.insets = new Insets(0, 0, 0, 0);
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.anchor = GridBagConstraints.NORTH;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(scrollableFieldArea, panelConstraints);		
		
		panelConstraints.insets = new Insets(0, 0, 0, 0);
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 2;
		panelConstraints.anchor = GridBagConstraints.SOUTH;
		panelConstraints.fill = GridBagConstraints.BOTH;
		add(valueArea, panelConstraints);
	}

}
