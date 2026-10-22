package view;

import controller.UserController;
import model.EUserRole;
import model.User;
import util.BgPanel;
import util.UITheme;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class UserModule extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Full Name", "Email", "Phone", "Role", "Active"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };

    private JTextField txtId, txtName, txtEmail, txtPhone, txtPassword;
    private JComboBox<EUserRole> cmbRole;
    private JCheckBox chkActive;
    private JTable table;

    public UserModule() {
        setTitle("User Management");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 580);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        BgPanel root = new BgPanel(new BorderLayout());

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 52, 45));
        header.setPreferredSize(new Dimension(0, 52));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));
        JLabel lbl = new JLabel("👥  User Management");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        JButton btnBack = makeBtn("← Back", new Color(52,152,219), Color.black);
        btnBack.addActionListener(e -> dispose());
        header.add(lbl, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);

        // Form card
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(Color.blue);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER),
            new EmptyBorder(16, 20, 16, 20)));
        formCard.setPreferredSize(new Dimension(320, 0));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setBackground(Color.WHITE);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL; g.gridx = 0;
        g.insets = new Insets(4, 0, 4, 0);

        txtId       = UITheme.styledField(); txtId.setEditable(false);
        txtName     = UITheme.styledField();
        txtEmail    = UITheme.styledField();
        txtPhone    = UITheme.styledField();
        txtPassword = UITheme.styledField();
        cmbRole     = new JComboBox<>(EUserRole.values());
        cmbRole.setFont(UITheme.FONT_BODY);
        cmbRole.setPreferredSize(new Dimension(220, 34));
        chkActive   = new JCheckBox("Account Active", true);
        chkActive.setBackground(Color.WHITE);
        chkActive.setFont(UITheme.FONT_BODY);

        String[] labels = {"ID (auto)", "Full Name *", "Email *", "Phone *", "Password *", "Role"};
        JComponent[] flds = {txtId, txtName, txtEmail, txtPhone, txtPassword, cmbRole};
        for (int i = 0; i < labels.length; i++) {
            g.gridy = i * 2;     fields.add(UITheme.fieldLabel(labels[i]), g);
            g.gridy = i * 2 + 1; fields.add(flds[i], g);
        }
        g.gridy = labels.length * 2; fields.add(chkActive, g);

        JButton btnSave   = makeBtn("💾 Save",   new Color(0,180,100),  Color.black);
        JButton btnUpdate = makeBtn("✏ Update", new Color(230,140,0),   Color.black);
        JButton btnDelete = makeBtn("🗑 Delete", new Color(210,40,40),   Color.black);
        JButton btnClear  = makeBtn("✖ Clear",  new Color(90,90,90),    Color.black);

        JPanel btns = new JPanel(new GridLayout(2, 2, 8, 8));
        btns.setBackground(Color.WHITE);
        btns.setBorder(new EmptyBorder(14, 0, 0, 0));
        btns.add(btnSave); btns.add(btnUpdate);
        btns.add(btnDelete); btns.add(btnClear);

        JPanel inner = new JPanel(new BorderLayout());
        inner.setBackground(Color.WHITE);
        inner.add(UITheme.sectionTitle("User Details"), BorderLayout.NORTH);
        JScrollPane sp = new JScrollPane(fields);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Color.WHITE);
        inner.add(sp, BorderLayout.CENTER);
        inner.add(btns, BorderLayout.SOUTH);
        formCard.add(inner);

        // Table card
        //  User Form (dark navy background)
        JPanel JPanel = new JPanel(new GridBagLayout());
        setBackground(new Color(10, 35, 60));

        table = new JTable(tableModel);
        UITheme.styleTable(table);
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(new Color(7, 25, 30));
        tableCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.BORDER),
            new EmptyBorder(12, 12, 12, 12)));
        tableCard.add(UITheme.sectionTitle("All Members"), BorderLayout.NORTH);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout(12, 0));
        body.setBackground(UITheme.BG);
        body.setBorder(new EmptyBorder(12, 12, 12, 12));
        body.add(formCard, BorderLayout.WEST);
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        btnSave.addActionListener(e -> {
            if (txtName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()
                    || txtPhone.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields marked * are required.", "Validation", JOptionPane.WARNING_MESSAGE); return;
            }
            if (!txtEmail.getText().contains("@")) {
                JOptionPane.showMessageDialog(this, "Enter a valid email address.", "Validation", JOptionPane.WARNING_MESSAGE); return;
            }
            User u = new User(txtEmail.getText().trim(), txtPassword.getText().trim(),
                    txtName.getText().trim(), txtPhone.getText().trim(), (EUserRole) cmbRole.getSelectedItem());
            u.setActive(chkActive.isSelected());
            if (UserController.save(u)) {
                JOptionPane.showMessageDialog(this, "User saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Save failed. Email or phone may already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a user first.", "Validation", JOptionPane.WARNING_MESSAGE); return; }
            User u = new User(txtEmail.getText().trim(), txtPassword.getText().trim(),
                    txtName.getText().trim(), txtPhone.getText().trim(), (EUserRole) cmbRole.getSelectedItem());
            u.setUserId(Integer.parseInt(txtId.getText().trim()));
            u.setActive(chkActive.isSelected());
            if (UserController.update(u)) {
                JOptionPane.showMessageDialog(this, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a user first.", "Validation", JOptionPane.WARNING_MESSAGE); return; }
            if (JOptionPane.showConfirmDialog(this, "Delete this user?", "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            if (UserController.delete(Integer.parseInt(txtId.getText().trim()))) {
                JOptionPane.showMessageDialog(this, "User deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtName.setText(tableModel.getValueAt(row, 1).toString());
                txtEmail.setText(tableModel.getValueAt(row, 2).toString());
                txtPhone.setText(tableModel.getValueAt(row, 3).toString());
                cmbRole.setSelectedItem(EUserRole.valueOf(tableModel.getValueAt(row, 4).toString()));
                chkActive.setSelected(Boolean.parseBoolean(tableModel.getValueAt(row, 5).toString()));
            }
        });
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (User u : UserController.findAll())
            tableModel.addRow(new Object[]{u.getUserId(), u.getFullName(), u.getEmail(),
                u.getPhoneNumber(), u.getRole(), u.isActive()});
    }

    private void clearForm() {
        txtId.setText(""); txtName.setText(""); txtEmail.setText("");
        txtPhone.setText(""); txtPassword.setText("");
        cmbRole.setSelectedIndex(0); chkActive.setSelected(true); table.clearSelection();
    }

    private JButton makeBtn(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setBackground(bg); b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(6, 14, 6, 14));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new UserModule().setVisible(true));
    }
}
