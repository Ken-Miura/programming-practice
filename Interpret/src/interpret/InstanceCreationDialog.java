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
import java.lang.reflect.ParameterizedType;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private final JLabel booleanLabel = new JLabel("boolean: ");
	private final JComboBox<Boolean> booleanComboBox = new JComboBox<>();
	{
		booleanComboBox.addItem(Boolean.FALSE);
		booleanComboBox.addItem(Boolean.TRUE);
	}
	
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
						componetConstraintsForBoolean.anchor = GridBagConstraints.CENTER;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(booleanLabel, componetConstraintsForBoolean);
						
						componetConstraintsForBoolean.gridx = 1;
						componetConstraintsForBoolean.gridy = i+1;
						componetConstraintsForBoolean.anchor = GridBagConstraints.CENTER;
						componetConstraintsForBoolean.fill = GridBagConstraints.HORIZONTAL;
						parameterArea.add(booleanComboBox, componetConstraintsForBoolean);
						
					} else if (types[i] == char.class) {
						
					} else if (types[i] == byte.class) {
						
					} else if (types[i] == short.class) {
						
					} else if (types[i] == int.class) {
						
					} else if (types[i] == long.class) {
						
					} else if (types[i] == float.class) {
						
					} else if (types[i] == double.class) {
						
					} else if (types[i] == java.lang.String.class) {
						
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
							
						} else {
							
						}
					}
					
				}
				parameterArea.revalidate();
			}
		});
	}

}
