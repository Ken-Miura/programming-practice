/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;


/**
 * @author Ken Miura
 *
 */
public final class ArrayElementsDialog extends JDialog {

	public final Object createdArray;
	public final Class<?> createdArrayComponentType;
	
	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -1808111276382615056L;
	private static final String TITLE = "配列の操作";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 800;

	private final JPanel fieldArea = new JPanel(new GridBagLayout());
	private final JScrollPane scrollableFieldArea = new JScrollPane(fieldArea);	
	private final JPanel valueArea = new JPanel(new GridBagLayout());
	
	public ArrayElementsDialog (Object createdArray) {
		Objects.requireNonNull(createdArray, "createdArray must not be null.");
		this.createdArray = createdArray;
		if (!(this.createdArray.getClass().isArray())) {
			throw new AssertionError("not array");
		}
		this.createdArrayComponentType = this.createdArray.getClass().getComponentType();
		
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new GridBagLayout());
		setModal(true);

		GridBagConstraints panelConstraints = new GridBagConstraints();

		panelConstraints.gridx = 0;
		panelConstraints.gridy = 0;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		add(scrollableFieldArea, panelConstraints);
		
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		
		add(valueArea, panelConstraints);
		
		GridBagConstraints gcForValueArea = new GridBagConstraints();
		gcForValueArea.gridx = 0;
		gcForValueArea.gridy = 0;
		gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
		
		valueArea.add(new JLabel("操作したい要素の番号と値を指定してください"), gcForValueArea);
		
		if (this.createdArrayComponentType == boolean.class) {
			boolean[] booleanCastedArray = (boolean[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: boolean"), gc);
			for (int i=0; i<booleanCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + booleanCastedArray[i]), gc);
			}
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, booleanCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JComboBox<Boolean> combo = new JComboBox<>();
			combo.addItem(Boolean.TRUE);
			combo.addItem(Boolean.FALSE);
			valueArea.add(combo, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					boolean newValue = (Boolean) combo.getSelectedItem();
					booleanCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: boolean"), gc);
					for (int j=0; j<booleanCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + booleanCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
			
		} else if (this.createdArrayComponentType == char.class) {
			char[] charCastedArray = (char[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: char"), gc);
			for (int i=0; i<charCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + charCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == byte.class) {
			byte[] byteCastedArray = (byte[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: byte"), gc);
			for (int i=0; i<byteCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + byteCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == short.class) {
			short[] shortCastedArray = (short[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: short"), gc);
			for (int i=0; i<shortCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + shortCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == int.class) {
			int[] intCastedArray = (int[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: int"), gc);
			for (int i=0; i<intCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + intCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == long.class) {
			long[] longCastedArray = (long[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: long"), gc);
			for (int i=0; i<longCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + longCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == float.class) {
			float[] floatCastedArray = (float[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: float"), gc);
			for (int i=0; i<floatCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + floatCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == double.class) {
			double[] doubleCastedArray = (double[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: double"), gc);
			for (int i=0; i<doubleCastedArray.length; i++) {
				gc.gridy = i+1;
				fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + doubleCastedArray[i]), gc);
			}
		} else if (this.createdArrayComponentType == java.lang.String.class) {
			java.lang.String[] stringCastedArray = (java.lang.String[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: String"), gc);
			for (int i=0; i<stringCastedArray.length; i++) {
				gc.gridy = i+1;
				if (stringCastedArray[i] == null) {
					fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
				} else {
					fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + stringCastedArray[i]), gc);	
				}
			}
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, stringCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JTextField t = new JTextField("", 16);
			valueArea.add(t, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					String newValue = t.getText();
					stringCastedArray[n] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: String"), gc);
					for (int i=0; i<stringCastedArray.length; i++) {
						gc.gridy = i+1;
						if (stringCastedArray[i] == null) {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
						} else {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + stringCastedArray[i]), gc);	
						}
					}					
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
			
			gcForValueArea.gridx = 2;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JButton nullButton = new JButton("nullを設定");
			nullButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					stringCastedArray[n] = null;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: String"), gc);
					for (int i=0; i<stringCastedArray.length; i++) {
						gc.gridy = i+1;
						if (stringCastedArray[i] == null) {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
						} else {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + stringCastedArray[i]), gc);	
						}
					}					
					fieldArea.revalidate();
				}
			});
			valueArea.add(nullButton, gcForValueArea);
			
			gcForValueArea.gridx = 3;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JButton buttonField = new JButton("フィールド一覧");
			buttonField.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					if (stringCastedArray[n] == null) {
						JOptionPane.showMessageDialog(null, "選択した要素はnullのためフィールド一覧を開けません", "選択エラー", JOptionPane.ERROR_MESSAGE);
					} else {
						new FieldDialog(stringCastedArray[n]).setVisible(true);;
					}
				}
			});
			valueArea.add(buttonField, gcForValueArea);

		} else {
			Object[] objectCastedArray = (Object[]) this.createdArray;
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			fieldArea.add(new JLabel("型: " + ArrayElementsDialog.this.createdArrayComponentType.getName()), gc);
			for (int i=0; i<objectCastedArray.length; i++) {
				gc.gridy = i+1;
				if (objectCastedArray[i] == null) {
					fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
				} else {
					fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + objectCastedArray[i]), gc);	
				}
			}
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, objectCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JButton button = new JButton("インスタンスを生成");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					InstanceCreationDialog icd = new InstanceCreationDialog(createdArrayComponentType);
					icd.setVisible(true);
					objectCastedArray[n] = icd.getCreatedInstance();
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: String"), gc);
					for (int i=0; i<objectCastedArray.length; i++) {
						gc.gridy = i+1;
						if (objectCastedArray[i] == null) {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
						} else {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + objectCastedArray[i]), gc);	
						}
					}					
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);

			gcForValueArea.gridx = 2;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JButton nullButton = new JButton("nullを設定");
			nullButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					objectCastedArray[n] = null;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: String"), gc);
					for (int i=0; i<objectCastedArray.length; i++) {
						gc.gridy = i+1;
						if (objectCastedArray[i] == null) {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: null"), gc);					
						} else {
							fieldArea.add(new JLabel("要素番号:" + i + ", 値: " + objectCastedArray[i]), gc);	
						}
					}					
					fieldArea.revalidate();
				}
			});
			valueArea.add(nullButton, gcForValueArea);
		
			gcForValueArea.gridx = 3;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;			
			JButton buttonField = new JButton("フィールド一覧");
			buttonField.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int n = (Integer) arraySpinner.getValue();
					if (objectCastedArray[n] == null) {
						JOptionPane.showMessageDialog(null, "選択した要素はnullのためフィールド一覧を開けません", "選択エラー", JOptionPane.ERROR_MESSAGE);
					} else {
						new FieldDialog(objectCastedArray[n]).setVisible(true);
					}
				}
			});
			valueArea.add(buttonField, gcForValueArea);

		}
	}
}
