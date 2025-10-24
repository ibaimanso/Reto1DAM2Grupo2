package ui.panels;

import javax.swing.JPanel;

import ui.Window;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HistoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Window window = null;
	
	private DefaultListModel<String> listModel   = null;
	private JList<String>            historyList = null;
	
	private JButton profileButton = null;
	private JButton backButton    = null;
	private JPanel  topBar        = null;
	private JLabel  title         = null;

	public HistoryPanel() {
		//this(null);
	}

	public HistoryPanel(Window window) {
		this.window = window;
		this.setLayout(new BorderLayout());

		topBar = new JPanel(new BorderLayout());
		profileButton = new JButton("Perfil");
		profileButton.setPreferredSize(new Dimension(100, 30));
		profileButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (window != null) {
					window.showPanel(Window.LOGIN_PANEL);
				}
			}
		});
		topBar.add(profileButton, BorderLayout.WEST);

		backButton = new JButton("Volver");
		backButton.setPreferredSize(new Dimension(100, 30));
		backButton.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				if (window != null) {
					window.showPanel(Window.WORKOUT_PANEL);
				}
			}
		});
		topBar.add(backButton, BorderLayout.EAST);

		title = new JLabel("Historial de Workouts", SwingConstants.CENTER);
		topBar.add(title, BorderLayout.CENTER);

		this.add(topBar, BorderLayout.NORTH);

		this.listModel = new DefaultListModel<>();
		this.historyList = new JList<>(listModel);
		this.historyList.setVisibleRowCount(10);
		JScrollPane scroll = new JScrollPane(historyList);
		this.add(scroll, BorderLayout.CENTER);

		this.setPreferredSize(new Dimension(600, 500));
		this.setVisible(true);
	}

	
	public void setHistory(List<String> history) {
		this.listModel.clear();
		if (history == null || history.isEmpty()) {
			this.listModel.addElement("No hay workouts completados aún");
			return;
		}
		for (String item : history) {
			this.listModel.addElement(item);
		}
	}

}