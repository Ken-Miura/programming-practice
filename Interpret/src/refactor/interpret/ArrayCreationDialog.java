/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Array;

import javax.swing.JButton;
import javax.swing.JLabel;
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
	private final SpinnerNumberModel arraySizeModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);  
	private final JSpinner arraySizeSpinner = new JSpinner(arraySizeModel);
	private final JButton createAndCloceButton = new JButton("配列を生成して閉じる");
	private final JButton createAndOpenButton = new JButton("配列を生成して要素を確認する");
	
	public ArrayCreationDialog(Class<?> clazz) {
		super(clazz);
		setTitle(TITLE);
		
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		createAndCloceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (getSpecifiedClass() == null) {
					createAndSetInstance(getClassSeachPanel().getSearchResult());						
				} else {
					createAndSetInstance(getSpecifiedClass());	
				}
				dispose();
			}
		});

		createAndOpenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (getSpecifiedClass() == null) {
					createAndSetInstance(getClassSeachPanel().getSearchResult());						
				} else {
					createAndSetInstance(getSpecifiedClass());	
				}
				// element open　TODO
				dispose();
			}
		});

		
		Class<?> specifiedClass = getSpecifiedClass();
		if (specifiedClass != null) {
			addArrayCreationComponents(specifiedClass);
		} else {
			componentConstraints.gridx = 0;
			componentConstraints.gridy = 0;
			componentConstraints.fill = GridBagConstraints.HORIZONTAL;
			componentConstraints.anchor = GridBagConstraints.CENTER;
			add(getClassSeachPanel(), componentConstraints);
		}
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(ClassSeachPanel.SEARCH_RESULT_KEY)) {
			remove(arrayLabel);
			remove(arraySizeLabel);
			remove(arraySizeSpinner);
			remove(createAndCloceButton);
			remove(createAndOpenButton);
			
			addArrayCreationComponents((Class<?>) evt.getNewValue());
			
			revalidate();
			Dimension d = getPreferredSize();
			setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
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
	
	private void createAndSetInstance (Class<?> clazz) {
		assert clazz != null;
		setInstance(Array.newInstance(clazz, (Integer) arraySizeSpinner.getValue()));
	}

}
