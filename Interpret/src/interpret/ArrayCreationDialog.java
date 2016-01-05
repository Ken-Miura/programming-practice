/* Copyright (C) 2015 Ken Miura */
package interpret;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * @author Ken Miura
 *
 */
public final class ArrayCreationDialog extends JDialog {

	/**
	 * Ver 1.0
	 */
	private static final long serialVersionUID = 9070213267407540600L;
	private static final String TITLE = "配列生成に関する操作";
	private static final int WIDTH = 700;
	private static final int HEIGHT = 800;
	private static final int MARGIN = 7;
	
	private final Class<?> parent;
	
	private final JPanel classArea = new JPanel(new GridBagLayout());
	private final JPanel sizeArea = new JPanel(new GridBagLayout());
	
	private Class<?> searchResult = null;
	private Object createdArray = null;
		
	private final JLabel searchCaption = new JLabel("生成したい配列のクラスのバイナリ名を入力してください");
	private final JTextField searchClass = new JTextField("", 16);
	private final JButton searchButton = new JButton("検索");
	
	public ArrayCreationDialog (Class<?> clazz) {
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
		} else {
			
			sizeArea.removeAll();
			
			GridBagConstraints gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.fill = GridBagConstraints.HORIZONTAL;
			sizeArea.add(new JLabel(parent.getName() + "の配列を生成します"), gc);
			
			gc.gridx = 0;
			gc.gridy = 1;
			gc.fill = GridBagConstraints.HORIZONTAL;
			sizeArea.add(new JLabel("サイズを指定してください"), gc);
			
			SpinnerNumberModel intArrayModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
			JSpinner intArraySpinner = new JSpinner(intArrayModel);
			gc.gridx = 0;
			gc.gridy = 2;
			gc.fill = GridBagConstraints.HORIZONTAL;
			sizeArea.add(intArraySpinner, gc);
			
			JButton createAndCloseButton = new JButton ("配列を生成して閉じる");
			createAndCloseButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int size = (Integer) intArraySpinner.getValue();
					createdArray = Array.newInstance(parent, size);
					dispose();
				}
			});
			gc.gridx = 0;
			gc.gridy = 3;
			gc.fill = GridBagConstraints.HORIZONTAL;
			sizeArea.add(createAndCloseButton, gc);
			
			JButton createAndOpenButton = new JButton ("配列を生成して要素一覧を開く");
			createAndOpenButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int size = (Integer) intArraySpinner.getValue();
					createdArray = Array.newInstance(searchResult, size);
					new ArrayElementsDialog(createdArray).setVisible(true);
					dispose();
				}
			});
			gc.gridx = 1;
			gc.gridy = 3;
			gc.fill = GridBagConstraints.HORIZONTAL;
			sizeArea.add(createAndOpenButton, gc);
			
			sizeArea.revalidate();			
		}
		
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
				if (parent != null) {
					throw new AssertionError("parent != null");
				}
				String binaryName = searchClass.getText();
				
				try {
					
					if (binaryName.equals("boolean")) {
						setSearchResult(boolean.class);
					} else if (binaryName.equals("char")) {
						setSearchResult(char.class);
					} else if (binaryName.equals("byte")) {
						setSearchResult(byte.class);
					} else if (binaryName.equals("short")) {
						setSearchResult(short.class);
					} else if (binaryName.equals("int")) {
						setSearchResult(int.class);
					} else if (binaryName.equals("long")) {
						setSearchResult(long.class);
					} else if (binaryName.equals("float")) {
						setSearchResult(float.class);
					} else if (binaryName.equals("double")) {
						setSearchResult(double.class);
					} else {
						searchResult = Class.forName(binaryName);						
					}

					sizeArea.removeAll();
					
					GridBagConstraints gc = new GridBagConstraints();
					gc.gridx = 0;
					gc.gridy = 0;
					gc.fill = GridBagConstraints.HORIZONTAL;
					sizeArea.add(new JLabel(searchResult.getName() + "の配列を生成します"), gc);
					
					gc.gridx = 0;
					gc.gridy = 1;
					gc.fill = GridBagConstraints.HORIZONTAL;
					sizeArea.add(new JLabel("サイズを指定してください"), gc);
					
					SpinnerNumberModel intArrayModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
					JSpinner intArraySpinner = new JSpinner(intArrayModel);
					gc.gridx = 0;
					gc.gridy = 2;
					gc.fill = GridBagConstraints.HORIZONTAL;
					sizeArea.add(intArraySpinner, gc);
					
					JButton createAndCloseButton = new JButton ("配列を生成して閉じる");
					createAndCloseButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int size = (Integer) intArraySpinner.getValue();
							createdArray = Array.newInstance(searchResult, size);
							dispose();
						}
					});
					gc.gridx = 0;
					gc.gridy = 3;
					gc.fill = GridBagConstraints.HORIZONTAL;
					sizeArea.add(createAndCloseButton, gc);
					
					JButton createAndOpenButton = new JButton ("配列を生成して要素一覧を開く");
					createAndOpenButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							int size = (Integer) intArraySpinner.getValue();
							createdArray = Array.newInstance(searchResult, size);
							new ArrayElementsDialog(createdArray).setVisible(true);
							dispose();
						}
					});
					gc.gridx = 1;
					gc.gridy = 3;
					gc.fill = GridBagConstraints.HORIZONTAL;
					sizeArea.add(createAndOpenButton, gc);
					
					sizeArea.revalidate();
				} catch (ClassNotFoundException cnfe) {
					cnfe.printStackTrace();
					JOptionPane.showMessageDialog(null, "指定されたクラス(" + binaryName + ") が見つかりませんでした。", "クラス検索エラー", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		
		componetConstraintsForPanel.insets = new Insets(MARGIN, MARGIN, MARGIN, MARGIN);
		componetConstraintsForPanel.gridx = 0;
		componetConstraintsForPanel.gridy = 1;
		componetConstraintsForPanel.anchor = GridBagConstraints.NORTH;
		componetConstraintsForPanel.fill = GridBagConstraints.HORIZONTAL;
		add(sizeArea, componetConstraintsForPanel);
	}
	
	private void setSearchResult(Class<?> clazz) {
		this.searchResult = clazz;
	}

	public Object getCreatedArray() {
		return createdArray;
	}

	public void setCreatedArray(Object createdArray) {
		this.createdArray = createdArray;
	}
}
