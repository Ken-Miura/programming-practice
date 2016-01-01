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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;

/**
 * @author Ken Miura
 *
 */
public final class FieldDialog extends JDialog {

	private static class WrappedHashtable<K, V> extends Hashtable<K, V> {

		private static final Object nullObject = new Object(); 
		
		/**
		 * Ver 1.0
		 */
		private static final long serialVersionUID = -8373646536497435476L;
		
		@SuppressWarnings("unchecked")
		@Override
		public synchronized V put(K key, V value) {
			Objects.requireNonNull(key, "key must not be null.");
			if (value == null) {
				return super.put(key, (V)nullObject);
			}
			return super.put(key, value);
		}
		
		@Override
		public synchronized V get(Object key) {
			Objects.requireNonNull(key, "key must not be null.");
			if (super.get(key) == nullObject) {
				return null;
			}
			return super.get(key);
		}
		
	}
	
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

	private final JPanel methodButtonArea = new JPanel();
	private final JSplitPane fieldTreeAndValueArea = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	
	
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
		add(methodButtonArea, panelConstraints);

		JButton medhodDialogButton = new JButton("メソッドを選択して実行する");
		medhodDialogButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		methodButtonArea.add(medhodDialogButton, "CENTER");
		
		panelConstraints.insets = new Insets(0, 0, 0, 0);
		panelConstraints.gridx = 0;
		panelConstraints.gridy = 1;
		panelConstraints.anchor = GridBagConstraints.NORTH;
		panelConstraints.fill = GridBagConstraints.BOTH;
		add(fieldTreeAndValueArea, panelConstraints);
		
		JPanel fieldArea = new JPanel();
		JScrollPane scrollableFieldArea = new JScrollPane(fieldArea);
		JPanel valueArea = new JPanel();
		
		fieldTreeAndValueArea.setLeftComponent(scrollableFieldArea);
		fieldTreeAndValueArea.setRightComponent(valueArea);
		
		Hashtable<String, Object> firstHierarchy = new WrappedHashtable<>();
		Field[] fields = this.createdObjectType.getDeclaredFields();
		addNodes(firstHierarchy, fields);
		
		fieldArea.add(new JTree(firstHierarchy));
	}


	private void addNodes(Hashtable<String, Object> hierarchy, Field[] fields) {
		for (Field f : fields) {
			f.setAccessible(true);
			if (f.getType().isPrimitive()) {
				try {
					hierarchy.put(f.toGenericString(), f.get(createdObject));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} else {
				//Hashtable<String, Object> nextHierarchy = new Hashtable<>();
				//addNodes(nextHierarchy, f.getClass().getDeclaredFields());
				//hierarchy.put(f.toGenericString(), nextHierarchy);
				try {
					if (f.get(createdObject) == null) {
						
					} else {
						hierarchy.put(f.toGenericString(), f.get(createdObject));	
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
