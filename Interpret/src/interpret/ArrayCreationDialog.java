/* Copyright (C) 2016 Ken Miura */
package interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Array;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
public final class ArrayCreationDialog extends CreationDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -866011205103376442L;
	
	private static final String TITLE = "配列の生成";
	private static final int MARGIN = 3;
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	private final JLabel arrayLabel = new JLabel();
	private final JLabel arraySizeLabel = new JLabel("要素数を入力してください ");
	private final JSpinner arraySizeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
	private final JButton createAndCloceButton = new JButton("配列を生成して閉じる");
	private final JButton createAndOpenButton = new JButton("配列を生成して要素を操作する");
	private final ClassSearchPanel classSearchPanel;
	private Class<?> componentType = null;
	
	public ArrayCreationDialog(Class<?> clazz) {
		super(clazz);
		setTitle(TITLE);
		
		classSearchPanel = ClassSearchPanel.createClassSearchPanel(this, clazz);
		
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 0;
		add (classSearchPanel, componentConstraints);
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
		
		createAndCloceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					assert componentType != null; //　クラス検索が行われた後なのでこの時点でnullはあり得ない
					createAndSetArrayInstance(componentType);
					dispose();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getClass()+") がスローされ、配列の生成に失敗しました", "配列生成失敗", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		createAndOpenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					assert componentType != null; //　クラス検索が行われた後なのでこの時点でnullはあり得ない
					createAndSetArrayInstance(componentType);
					
					ArrayOperationDialog arrayOperationDialog = new ArrayOperationDialog(getInstance());
					arrayOperationDialog.setLocation(ArrayCreationDialog.this.getLocation());
					arrayOperationDialog.setVisible(true);
					
					dispose();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getClass()+") がスローされ、配列の生成に失敗しました", "配列生成失敗", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ClassSearchPanel.SEARCH_RESULT_KEY)) {
			remove(arrayLabel);
			remove(arraySizeLabel);
			remove(arraySizeSpinner);
			remove(createAndCloceButton);
			remove(createAndOpenButton);
			
			componentType = (Class<?>) evt.getNewValue();
			addArrayCreationComponents(componentType);
			
			revalidate();
			Dimension d = getPreferredSize();
			setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
			repaint();
		}
	}

	private void addArrayCreationComponents (Class<?> clazz) {
		assert clazz != null;
		
		arrayLabel.removeAll();
		arrayLabel.setText(clazz.getName() + "の配列を生成します");
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(arrayLabel, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 2;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(arraySizeLabel, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 3;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(arraySizeSpinner, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 4;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(createAndCloceButton, componentConstraints);
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 5;
		componentConstraints.fill = GridBagConstraints.HORIZONTAL;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(createAndOpenButton, componentConstraints);
	}
	
	private void createAndSetArrayInstance (Class<?> clazz) {
		assert clazz != null;
		setInstance(Array.newInstance(clazz, (Integer) arraySizeSpinner.getValue()));
	}

}
