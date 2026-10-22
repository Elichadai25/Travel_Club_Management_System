package view;

import controller.NotificationController;
import model.Notification;
import util.SessionManager;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class NotificationPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Message", "Type", "Read", "Sent At"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JTable table;

    public NotificationPanel() {
        setLayout(new BorderLayout());
        buildUI();
        loadTable();
    }

    private void buildUI() {
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(24);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Notification History"));

        JButton btnMarkRead = new JButton("Mark as Read"); btnMarkRead.setBackground(new Color(102, 204, 255));
        JButton btnRefresh = new JButton("Refresh"); btnRefresh.setBackground(new Color(255, 255, 153));

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        btns.add(btnMarkRead); btns.add(btnRefresh);

        add(scroll, BorderLayout.CENTER);
        add(btns, BorderLayout.SOUTH);

        btnMarkRead.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this, "Select a notification first."); return; }
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (NotificationController.markAsRead(id)) { JOptionPane.showMessageDialog(this, "Marked as read."); loadTable(); }
        });

        btnRefresh.addActionListener(e -> loadTable());
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        model.User current = SessionManager.getCurrentUser();
        List<Notification> list = current != null
            ? NotificationController.findByUser(current.getUserId())
            : NotificationController.findAll();
        for (Notification n : list) {
            tableModel.addRow(new Object[]{
                n.getNotificationId(), n.getMessage(), n.getType(),
                n.isRead() ? "Yes" : "No",
                n.getSentAt() != null ? SDF.format(n.getSentAt()) : ""
            });
        }
    }
}
