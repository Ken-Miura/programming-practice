/* Copyright (C) 2015 Ken Miura */
package interpret.old;

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
	private static final int WIDTH = 800;
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
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, charCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel charModel = new SpinnerNumberModel(0, 0, Character.MAX_VALUE, 1);
			JSpinner charSpinner = new JSpinner(charModel);
			valueArea.add(charSpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					int k = (Integer) charSpinner.getValue();
					char newValue =  (char) k;
					charCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: char"), gc);
					for (int j=0; j<charCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + charCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
			
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, byteCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel byteModel = new SpinnerNumberModel(0, Byte.MIN_VALUE, Byte.MAX_VALUE, 1);
			JSpinner byteSpinner = new JSpinner(byteModel);
			valueArea.add(byteSpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					int k = (Integer) byteSpinner.getValue();
					byte newValue = (byte) k;
					byteCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: byte"), gc);
					for (int j=0; j<byteCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + byteCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, shortCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel shortModel = new SpinnerNumberModel(0, Short.MIN_VALUE, Short.MAX_VALUE, 1);
			JSpinner shortSpinner = new JSpinner(shortModel);
			valueArea.add(shortSpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					int k = (Integer) shortSpinner.getValue();
					short newValue = (short) k;
					shortCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: short"), gc);
					for (int j=0; j<shortCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + shortCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, intCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel intModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
			JSpinner intSpinner = new JSpinner(intModel);
			valueArea.add(intSpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					int newValue = (Integer) intSpinner.getValue();
					intCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: int"), gc);
					for (int j=0; j<intCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + intCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, longCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel longModel = new SpinnerNumberModel(0, Long.MIN_VALUE, Long.MAX_VALUE, 1);
			JSpinner longSpinner = new JSpinner(longModel);
			valueArea.add(longSpinner, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					double newValue = (Double) longSpinner.getValue();
					longCastedArray[i] = (long) newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: long"), gc);
					for (int j=0; j<longCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + longCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, floatCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JTextField t = new JTextField("0.0", 16);
			valueArea.add(t, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					float newValue = 0.0f;
					try {
						newValue = Float.parseFloat(t.getText());	
					} catch (NumberFormatException efe) {
						JOptionPane.showInputDialog(null, "不正な入力です。", "入力エラー", JOptionPane.ERROR_MESSAGE);
						return;
					}
					floatCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: float"), gc);
					for (int j=0; j<floatCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + floatCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);
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
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("要素番号: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 1;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			SpinnerNumberModel arrayModel = new SpinnerNumberModel(0, 0, doubleCastedArray.length-1, 1);
			JSpinner arraySpinner = new JSpinner(arrayModel);
			valueArea.add(arraySpinner, gcForValueArea);
			
			gcForValueArea.gridx = 0;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			valueArea.add(new JLabel("値: "), gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 2;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			JTextField t = new JTextField("0.0", 16);
			valueArea.add(t, gcForValueArea);
			
			gcForValueArea.gridx = 1;
			gcForValueArea.gridy = 0;
			gcForValueArea.fill = GridBagConstraints.HORIZONTAL;
			
			JButton button = new JButton("値を変更する");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int i = (Integer) arraySpinner.getValue();
					double newValue = 0.0;
					try {
						newValue = Double.parseDouble(t.getText());	
					} catch (NumberFormatException efe) {
						JOptionPane.showInputDialog(null, "不正な入力です。", "入力エラー", JOptionPane.ERROR_MESSAGE);
						return;
					}
					doubleCastedArray[i] = newValue;
					
					fieldArea.removeAll();
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					fieldArea.add(new JLabel("型: double"), gc);
					for (int j=0; j<doubleCastedArray.length; j++) {
						gc.gridy = j+1;
						fieldArea.add(new JLabel("要素番号:" + j + ", 値: " + doubleCastedArray[j]), gc);
					}
					fieldArea.revalidate();
				}
			});
			valueArea.add(button, gcForValueArea);			
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
