/* Copyright (C) 2016 Ken Miura */
package refactor.interpret;

import java.awt.Dimension;
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

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = -5625858125672229888L;
	private static final int TOP_BOTTOM_MARGIN = 34;
	private static final int LEFT_RIGHT_MARGIN = 4;
	private static final int MARGIN = 3;
	
	private final Object instance;
	private final Hashtable<String, Field> fieldTable = new WrappedHashtable<String, Field>();
	
	private final GridBagConstraints componentConstrants = new GridBagConstraints();
	
	private final JButton methodDialogOpen = new JButton("メソッドを選択して実行する");
	private final JPanel fieldArea = new JPanel();
	private final JScrollPane scrollableComponent = new JScrollPane(fieldArea);
	private final JPanel valueArea = new JPanel();
	
	public FieldDialog (Object instance) {
		Objects.requireNonNull(instance, "instance must not be null.");
		this.instance = instance;
		setModal(true);
		setResizable(false);
		setLayout(new GridBagLayout());
		componentConstrants.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		
		Field[] fields = instance.getClass().getDeclaredFields();
		for (final Field field : fields) {
			field.setAccessible(true);
			fieldTable.put(field.toGenericString(), field);
		}
		
		methodDialogOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MethodDialog(instance).setVisible(true);
			}
		});
		
		componentConstrants.gridx = 0;
		componentConstrants.gridy = 0;
		componentConstrants.fill = GridBagConstraints.NONE;
		componentConstrants.anchor = GridBagConstraints.CENTER;
		add(methodDialogOpen, componentConstrants);
		
		JTree fieldTree = new JTree(fieldTable);
		fieldTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
//		fieldTree.addTreeSelectionListener(new TreeSelectionListener() {
//
//			@Override
//			public void valueChanged(TreeSelectionEvent evt) {
//				DefaultMutableTreeNode node = (DefaultMutableTreeNode) fieldTree.getLastSelectedPathComponent();
//				if (node == null) {
//					return;
//				}
//				valueArea.removeAll();
//				
//				Object nodeInfo = node.getUserObject();
//				Field f = fieldTable.get(nodeInfo);
//				setComponentsInValueArea(f);
//				
//				valueArea.revalidate();
//				Dimension d = getPreferredSize();
//				setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
//			}
//			
//		});
		fieldArea.add(fieldTree);
		
		componentConstrants.gridx = 0;
		componentConstrants.gridy = 1;
		componentConstrants.fill = GridBagConstraints.HORIZONTAL;
		componentConstrants.anchor = GridBagConstraints.CENTER;
		add(fieldTree, componentConstrants);
		
		Dimension d = getPreferredSize();
		setSize(LEFT_RIGHT_MARGIN + d.width, TOP_BOTTOM_MARGIN + d.height);
	}
	
	private void setComponentsInValueArea(Field f) {
		assert f != null;
		
	}	

	private static void setStaticFinalField(Field field, Object value) throws IllegalAccessException {
		Field modifiersField;

	    try {
	       modifiersField = Field.class.getDeclaredField("modifiers");
	    } catch (NoSuchFieldException e) {
	      throw new RuntimeException(e);
	    }
	
	    modifiersField.setAccessible(true);
	    int nonFinalModifiers = modifiersField.getInt(field) - java.lang.reflect.Modifier.FINAL;
	    modifiersField.setInt(field, nonFinalModifiers);
	
	    //noinspection UnnecessaryFullyQualifiedName,UseOfSunClasses
	    sun.reflect.FieldAccessor accessor = sun.reflect.ReflectionFactory.getReflectionFactory().newFieldAccessor(field, false);
	    accessor.set(null, value);
	}	
	
	private static final class WrappedHashtable<K, V> extends Hashtable<K, V> {

		/**
		 * Ver 1.0
		 */
		private static final long serialVersionUID = -3265845421824619850L;
		
		private static final Object nullObject = new Object(); 
		
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
}
