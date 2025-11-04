package ui.panels;

import javax.swing.JPanel;

import ui.Window;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import controllers.ControllerFactory;

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

	/**
	 * Carga el historial de workouts del usuario actual
	 */
	public void loadHistory() {
		this.listModel.clear();
		
		if (window == null || window.getUserLogin() == null) {
			this.listModel.addElement("No hay usuario logueado");
			return;
		}
		
		try {
			List<String> history = ControllerFactory.getInstance()
				.getHistoryController()
				.getFormattedHistory(window.getUserLogin());
			
			if (history == null || history.isEmpty()) {
				this.listModel.addElement("No hay workouts completados aún");
			} else {
				for (String item : history) {
					this.listModel.addElement(item);
				}
			}
		} catch (Exception ex) {
			this.listModel.addElement("Error al cargar historial: " + ex.getMessage());
			JOptionPane.showMessageDialog(
				this,
				"No se ha podido cargar el historial",
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
		}
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