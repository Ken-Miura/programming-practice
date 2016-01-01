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
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

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
		panelConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(fieldTreeAndValueArea, panelConstraints);
		
		JPanel fieldArea = new JPanel();
		JScrollPane scrollableFieldArea = new JScrollPane(fieldArea);
		JPanel valueArea = new JPanel();
		valueArea.setLayout(new GridBagLayout());
		
		fieldTreeAndValueArea.setLeftComponent(scrollableFieldArea);
		fieldTreeAndValueArea.setRightComponent(valueArea);
		
		Hashtable<String, Field> firstHierarchy = new WrappedHashtable<>();
		Field[] fields = this.createdObjectType.getDeclaredFields();
		for (final Field f: fields) {
			f.setAccessible(true);
			try {
				firstHierarchy.put(f.toGenericString(), f);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
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
				if (f.getType().isPrimitive() || f.getType() == java.lang.String.class ) {
					if (f.getType() == char.class) {
						valueArea.add(new JButton("char"));
					} else if (f.getType() == int.class) {
						JButton test = new JButton("int");
						valueArea.add(test);
					} else if (f.getType() == java.lang.String.class) {
						valueArea.add(new JButton("String"));
					} 
				} else {
					System.out.println("not primitive");
				}
				fieldTreeAndValueArea.setResizeWeight(.8d);
				fieldTreeAndValueArea.revalidate();
			}
		});
		fieldArea.add(fieldTree);
	}

}
