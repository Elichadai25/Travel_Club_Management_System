package view;

import controller.NotificationController;
import model.Notification;
import model.User;
import rmi.ServiceConnector;
import util.BgPanel;
import util.SessionManager;
import util.UITheme;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NotificationtModule extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Message", "Type", "Read", "Sent At"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Color HDR_BG = new Color(15, 52, 45);

    private JTable table;
    private JLabel lblCount;
    private JPanel emptyState;

    public NotificationtModule() {
        setTitle("Notifications");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 620);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        BgPanel root = new BgPanel(new BorderLayout(),
            "/images/dashboard_bg.jpeg", new Color(230, 240, 250, 200));

        //  HEADER 
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HDR_BG);
        header.setPreferredSize(new Dimension(0, 54));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel lbl = new JLabel("🔔  Notifications");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);

        lblCount = new JLabel("  Loading...");
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblCount.setForeground(new Color(0, 220, 150));

        JButton btnBack = makeBtn("← Back", new Color(52, 152, 219), Color.WHITE);
        btnBack.addActionListener(e -> dispose());

        JPanel rightH = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightH.setBackground(HDR_BG);
        rightH.add(lblCount);
        rightH.add(btnBack);

        header.add(lbl, BorderLayout.WEST);
        header.add(rightH, BorderLayout.EAST);

        //  TABLE 
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(0, 180, 120, 60));
        table.setSelectionForeground(Color.BLACK);

        // Header styling
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(HDR_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setOpaque(true);

        // Color unread rows amber, read rows white/light
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (!sel) {
                    boolean isRead = "Yes".equals(tableModel.getValueAt(row, 3));
                    if (!isRead) {
                        setBackground(new Color(255, 248, 215));
                        setForeground(new Color(140, 80, 0));
                        setFont(new Font("Segoe UI", Font.BOLD, 13));
                    } else {
                        setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 252, 250));
                        setForeground(new Color(60, 70, 80));
                    }
                }
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // Empty state panel shown when no notifications
        emptyState = new JPanel(new GridBagLayout());
        emptyState.setBackground(Color.WHITE);
        JLabel emptyIcon = new JLabel("🔔");
        emptyIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        JLabel emptyMsg = new JLabel("<html><div style='text-align:center;'>"
            + "<b style='font-size:14px;color:#0f3420;'>No notifications yet</b><br>"
            + "<span style='color:#888;font-size:12px;'>Notifications appear here when you<br>"
            + "create a booking or make a payment.</span></div></html>");
        emptyMsg.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints ec = new GridBagConstraints();
        ec.gridx = 0; ec.gridy = 0; ec.insets = new Insets(0, 0, 10, 0);
        emptyState.add(emptyIcon, ec);
        ec.gridy = 1;
        emptyState.add(emptyMsg, ec);

        // Card that switches between table and empty state
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 220, 210), 1, true),
            new EmptyBorder(0, 0, 0, 0)));

        JPanel cardHeader = new JPanel(new BorderLayout());
        cardHeader.setBackground(Color.WHITE);
        cardHeader.setBorder(new EmptyBorder(14, 14, 10, 14));
        JLabel secTitle = new JLabel("Notification History");
        secTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        secTitle.setForeground(HDR_BG);
        cardHeader.add(secTitle, BorderLayout.WEST);

        JPanel tableArea = new JPanel(new CardLayout());
        tableArea.add(scroll, "TABLE");
        tableArea.add(emptyState, "EMPTY");

        tableCard.add(cardHeader, BorderLayout.NORTH);
        tableCard.add(tableArea, BorderLayout.CENTER);

        // ── BOTTOM BUTTONS ───────────────────────────────────────────────────
        JButton btnMarkRead = makeBtn("✔  Mark as Read",    new Color(0, 160, 90),   Color.WHITE);
        JButton btnMarkAll  = makeBtn("✔✔  Mark All Read",  new Color(100, 30, 160), Color.WHITE);
        JButton btnRefresh  = makeBtn("↻  Refresh",         new Color(41, 128, 185), Color.WHITE);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btns.setBackground(new Color(245, 248, 250));
        btns.setBorder(new MatteBorder(1, 0, 0, 0, new Color(200, 210, 220)));

        // Admin gets extra "Send Announcement" button
        if (SessionManager.isAdmin()) {
            JButton btnAnnounce = makeBtn("📢  Send Announcement", new Color(200, 100, 0), Color.WHITE);
            btnAnnounce.setPreferredSize(new Dimension(200, 36));
            btns.add(btnAnnounce);
            btnAnnounce.addActionListener(e -> showSendAnnouncementDialog(tableArea));
        }

        btns.add(btnMarkAll);
        btns.add(btnMarkRead);
        btns.add(btnRefresh);

        JPanel body = new JPanel(new BorderLayout());
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(14, 14, 0, 14));
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        root.add(btns, BorderLayout.SOUTH);
        setContentPane(root);

        //  ACTIONS 
        btnMarkRead.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { warn("Select a notification first."); return; }
            int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            if (NotificationController.markAsRead(id)) { loadTable(); }
        });

        btnMarkAll.addActionListener(e -> {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if ("No".equals(tableModel.getValueAt(i, 3)))
                    NotificationController.markAsRead(Integer.parseInt(tableModel.getValueAt(i, 0).toString()));
            }
            loadTable();
        });

        btnRefresh.addActionListener(e -> loadTable());
    }

    /** Admin dialog to send a trip announcement to all users */
    private void showSendAnnouncementDialog(JPanel tableArea) {
        JDialog dlg = new JDialog(this, "Send Trip Announcement", true);
        dlg.setSize(500, 320);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(20, 24, 10, 24));
        form.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL; g.gridx = 0;
        g.insets = new Insets(6, 0, 4, 0);

        JLabel lblType = new JLabel("Notification Type:");
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JComboBox<String> cmbType = new JComboBox<>(new String[]{"ANNOUNCEMENT", "TRIP_UPDATE", "REMINDER", "GENERAL"});
        cmbType.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblMsg = new JLabel("Message (sent to ALL users):");
        lblMsg.setFont(new Font("Segoe UI", Font.BOLD, 13));
        JTextArea txtMsg = new JTextArea(5, 30);
        txtMsg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtMsg.setLineWrap(true);
        txtMsg.setWrapStyleWord(true);
        txtMsg.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 210, 220), 1),
            new EmptyBorder(6, 8, 6, 8)));

        g.gridy = 0; form.add(lblType, g);
        g.gridy = 1; form.add(cmbType, g);
        g.gridy = 2; form.add(lblMsg, g);
        g.gridy = 3; form.add(new JScrollPane(txtMsg), g);

        JButton btnSend   = makeBtn("📢  Send to All", new Color(15, 52, 45), Color.WHITE);
        JButton btnCancel = makeBtn("Cancel",           new Color(150, 150, 150), Color.WHITE);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnRow.setBackground(Color.WHITE);
        btnRow.add(btnCancel); btnRow.add(btnSend);

        dlg.add(form, BorderLayout.CENTER);
        dlg.add(btnRow, BorderLayout.SOUTH);
        dlg.getContentPane().setBackground(Color.WHITE);

        btnCancel.addActionListener(e -> dlg.dispose());
        btnSend.addActionListener(e -> {
            String msg = txtMsg.getText().trim();
            if (msg.isEmpty()) { warn("Message cannot be empty."); return; }
            String type = cmbType.getSelectedItem().toString();
            try {
                List<model.User> users = rmi.ServiceConnector.getService().findAllUsers();
                int sent = 0;
                for (model.User u : users) {
                    Notification n = new Notification(msg, type, u);
                    ServiceConnector.getService().sendNotification(n);
                    sent++;
                }
                JOptionPane.showMessageDialog(dlg,
                    "Announcement sent to " + sent + " user(s)!",
                    "Sent", JOptionPane.INFORMATION_MESSAGE);
                dlg.dispose();
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dlg, "Failed: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dlg.setVisible(true);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        User current = SessionManager.getCurrentUser();

        List<Notification> list;
        if (current != null && current.getRole() != null && current.getRole().name().equals("ADMIN")) {
            list = NotificationController.findAll();
        } else if (current != null) {
            list = NotificationController.findByUser(current.getUserId());
        } else {
            list = NotificationController.findAll();
        }

        // Show/hide empty state
        Container tableArea = table.getParent().getParent().getParent(); // scroll -> viewport -> tableArea
        if (tableArea.getLayout() instanceof CardLayout) {
            ((CardLayout) tableArea.getLayout()).show(tableArea, list.isEmpty() ? "EMPTY" : "TABLE");
        }

        int unread = 0;
        for (Notification n : list) {
            if (!n.isRead()) unread++;
            tableModel.addRow(new Object[]{
                n.getNotificationId(),
                n.getMessage(),
                n.getType(),
                n.isRead() ? "Yes" : "No",
                n.getSentAt() != null ? SDF.format(n.getSentAt()) : ""
            });
        }
        lblCount.setText("  " + unread + " unread  |  " + list.size() + " total  ");
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
    }

    private JButton makeBtn(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBorderPainted(true);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(7, 16, 7, 16)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new NotificationtModule().setVisible(true));
    }
}
