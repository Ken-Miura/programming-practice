/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author Ken Miura
 *
 */
public final class InstanceCreationDialog extends CreationDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 6657680895410091655L;
	private static final String TITLE = "インスタンスの生成";
	private static final int MARGIN = 3;
	public static final String CONSTRUCTOR_CHANGE_KEY = "constructor_change";
	
	private final PropertyChangeSupport properChangeSupport;
	
	private final GridBagConstraints componentConstraints = new GridBagConstraints();
	private final JLabel constructoComboLabel = new JLabel("コンストラクタ一覧");
	private final JComboBox<Constructor<?>> constructorCombo = new JComboBox<>();
	private final JLabel parameterLabel = new JLabel("引数一覧");
	private final ParameterPanel parameterPanel = new ParameterPanel();
	private final JButton createAndCloceButton = new JButton("インスタンスを生成して閉じる");
	private final JButton createAndOpenButton = new JButton("インスタンスを生成してフィールドを確認する");
	
	public InstanceCreationDialog(Class<?> clazz) {
		super(clazz);
		setTitle(TITLE);
		
		componentConstraints.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		properChangeSupport = new PropertyChangeSupport(this);
		properChangeSupport.addPropertyChangeListener(parameterPanel);
		
		constructorCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Constructor<?> selectedConstructor = (Constructor<?>) constructorCombo.getSelectedItem();
				if (selectedConstructor == null) { // removeAllのとき
					return;
				}
				remove(parameterLabel);
				remove(parameterPanel);
				remove(createAndCloceButton);
				remove(createAndOpenButton);
				
				java.lang.reflect.Type[] parameters = selectedConstructor.getGenericParameterTypes();
				properChangeSupport.firePropertyChange(CONSTRUCTOR_CHANGE_KEY, null, parameters);
				
				componentConstraints.gridx = 0;
				componentConstraints.gridy = 3;
				componentConstraints.fill = GridBagConstraints.NONE;
				componentConstraints.anchor = GridBagConstraints.CENTER;
				add(parameterLabel, componentConstraints);
				
				componentConstraints.gridx = 0;
				componentConstraints.gridy = 4;
				componentConstraints.fill = GridBagConstraints.NONE;
				componentConstraints.anchor = GridBagConstraints.CENTER;
				add(parameterPanel, componentConstraints);
				
				componentConstraints.gridx = 0;
				componentConstraints.gridy = 5;
				componentConstraints.fill = GridBagConstraints.HORIZONTAL;
				componentConstraints.anchor = GridBagConstraints.CENTER;
				add(createAndCloceButton, componentConstraints);
				
				componentConstraints.gridx = 0;
				componentConstraints.gridy = 6;
				componentConstraints.fill = GridBagConstraints.HORIZONTAL;
				componentConstraints.anchor = GridBagConstraints.CENTER;
				add(createAndOpenButton, componentConstraints);
				
				Dimension d = getPreferredSize();
				setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
			}
		});
		
		createAndCloceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					createAndSetInstance((Constructor<?>)constructorCombo.getSelectedItem());
					dispose();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getClass()+") がスローされ、インスタンスの生成に失敗しました", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getTargetException().getClass()+") がスローされ、インスタンスの生成に失敗しました", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "入力エラー", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		createAndOpenButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				try {
					createAndSetInstance((Constructor<?>)constructorCombo.getSelectedItem());
					new FieldDialog (getInstance()).setVisible(true);
					dispose();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getClass()+") がスローされ、インスタンスの生成に失敗しました", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "例外 ("+e.getTargetException().getClass()+") がスローされ、インスタンスの生成に失敗しました", "インスタンス生成失敗", JOptionPane.ERROR_MESSAGE);
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "入力エラー", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		
		Class<?> specifiedClass = getSpecifiedClass();
		if (specifiedClass != null) {
			Constructor<?>[] constructors = specifiedClass.getDeclaredConstructors();
			addComboBox(constructors);
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
			remove(constructoComboLabel);
			constructorCombo.removeAllItems();
			remove(constructorCombo);
			
			Class<?> searchResult = (Class<?>) evt.getNewValue();
			addComboBox(searchResult.getDeclaredConstructors());
			
			revalidate();
			Dimension d = getPreferredSize();
			setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
		}
	}

	private void addComboBox (Constructor<?>[] constructors) {
		assert constructors != null;
		
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 1;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(constructoComboLabel, componentConstraints);
		
		for (Constructor<?> c: constructors) {
			if (c.getDeclaringClass() == Class.class) {
				continue;
			}
			c.setAccessible(true);
			constructorCombo.addItem(c);
		}
		componentConstraints.gridx = 0;
		componentConstraints.gridy = 2;
		componentConstraints.fill = GridBagConstraints.NONE;
		componentConstraints.anchor = GridBagConstraints.CENTER;
		add(constructorCombo, componentConstraints);
	}
	
	private void createAndSetInstance (Constructor<?> constructor) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidInputException {
		assert constructor != null;
		setInstance(constructor.newInstance(parameterPanel.getParameterValues()));
	}
}
