package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import service.FileService;

import model.Group;

import br.edu.fateczl.ObjectList;

public class SearchGroupController implements ActionListener {

	private DefaultComboBoxModel<String> comboBoxList;
	private JTextField searchBox;
	
	public SearchGroupController(JTextField searchBox) {
		this.searchBox = searchBox;
	}
	
	public JTextField getSearchBox() {
		return searchBox;
	}

	public void setSearchBox(JTextField searchBox) {
		this.searchBox = searchBox;
	}

	public DefaultComboBoxModel<String> comboBoxList() {
		return comboBoxList;
	}

	public void setComboBox(DefaultComboBoxModel<String> comboBoxList) {
		this.comboBoxList = comboBoxList;
	}

	private FileService fileCtrl = new FileService();
	private String groupsData;
	private ObjectList groups;
	
	private void loadData() {
		try {
			groupsData = fileCtrl.readData("Groups");
			String[] GroupsByLine = groupsData.split("\\r\\n");
			String line[];
			groups = new ObjectList();
			
			int groupsSize = GroupsByLine.length;
			Group g;
			String code, professor, area, tema, students;
			for (int i = 0; i < groupsSize; i++) {
				line = GroupsByLine[i].split(";");
				code 		= line[0];
				professor 	= line[1];
				area 		= line[2];
				tema 		= line[3];
				students 	= line[4];
				g = new Group(code, professor, area, tema, students);
				
				try {
					groups.addLast(g);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void searchGroup() {
		loadData();
		int groupsSize = groups.size();
		String code = this.searchBox.getText();
		Group g;
		ObjectList groupsByCode = new ObjectList();
		
		for (int i = 0; i < groupsSize; i++) {
			try {
				g = (Group) groups.get(i);
				if (g.getCode().substring(0, code.length()).contains(code)) {
					groupsByCode.addLast(g);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		int lSize = groupsByCode.size();
		comboBoxList.removeAllElements();
		for (int i = 0; i < lSize; i++) {
			try {
				g = (Group) groupsByCode.get(i);
				comboBoxList.addElement(g.getCode());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.contains("Pesquisar")) {
			this.searchGroup();
		}
		
	}

}
