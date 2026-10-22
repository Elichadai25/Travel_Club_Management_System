package view;

import controller.PaymentController;
import model.Booking;
import model.EPaymentMode;
import model.Payment;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PaymentPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Booking ID", "Amount", "Mode", "Status", "Ref", "Payment Date"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JTextField txtId, txtBookingId, txtAmount;
    private JComboBox<EPaymentMode> cmbMode;
    private JLabel lblRevenue;
    private JTable table;

    public PaymentPanel() {
        setLayout(new BorderLayout());
        buildUI();
        loadTable();
    }

    private void buildUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Payment Form"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6); g.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10); txtId.setEditable(false);
        txtBookingId = new JTextField(10); txtAmount = new JTextField(10);
        cmbMode = new JComboBox<>(EPaymentMode.values());
        lblRevenue = new JLabel("Total Revenue: $0.00");
        lblRevenue.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRevenue.setForeground(new Color(0, 128, 0));

        String[] labels = {"ID", "Booking ID", "Amount", "Payment Mode"};
        JComponent[] fields = {txtId, txtBookingId, txtAmount, cmbMode};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = i; form.add(new JLabel(labels[i]), g);
            g.gridx = 1; form.add(fields[i], g);
        }
        g.gridx = 0; g.gridy = labels.length; g.gridwidth = 2; form.add(lblRevenue, g);

        JButton btnSave = new JButton("Save Payment"); btnSave.setBackground(new Color(102, 255, 102));
        JButton btnRefresh = new JButton("Refresh");

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        btns.add(btnSave); btns.add(btnRefresh);

        JPanel left = new JPanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER); left.add(btns, BorderLayout.SOUTH);

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Payment History"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, scroll);
        split.setDividerLocation(300);
        add(split, BorderLayout.CENTER);

        btnSave.addActionListener(e -> {
            if (txtBookingId.getText().trim().isEmpty() || txtAmount.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Booking ID and Amount are required."); return;
            }
            try {
                Booking booking = new Booking();
                booking.setBookingId(Integer.parseInt(txtBookingId.getText().trim()));
                double amount = Double.parseDouble(txtAmount.getText().trim());
                Payment p = new Payment(amount, new java.util.Date(), (EPaymentMode) cmbMode.getSelectedItem(), booking);
                Payment saved = PaymentController.create(p);
                if (saved != null) {
                    JOptionPane.showMessageDialog(this, "Payment saved. Ref: " + saved.getTransactionRef());
                    loadTable(); clearForm();
                } else JOptionPane.showMessageDialog(this, "Payment failed.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener(e -> loadTable());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtBookingId.setText(tableModel.getValueAt(row, 1).toString());
                txtAmount.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Payment p : PaymentController.findAll()) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getBooking() != null ? p.getBooking().getBookingId() : "",
                p.getAmount(), p.getPaymentMode(), p.getStatus(),
                p.getTransactionRef(),
                p.getPaymentDate() != null ? SDF.format(p.getPaymentDate()) : ""
            });
        }
        lblRevenue.setText("Total Revenue: $" + String.format("%.2f", PaymentController.getTotalRevenue()));
    }

    private void clearForm() {
        txtId.setText(""); txtBookingId.setText(""); txtAmount.setText("");
        cmbMode.setSelectedIndex(0); table.clearSelection();
    }
}
