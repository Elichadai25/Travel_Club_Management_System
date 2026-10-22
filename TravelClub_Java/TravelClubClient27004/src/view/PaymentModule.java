package view;

import controller.PaymentController;
import model.Booking;
import model.EPaymentMode;
import model.Payment;
import util.BgPanel;
import util.UITheme;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class PaymentModule extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Booking ID", "Amount (RWF)", "Mode", "Status", "Ref", "Date"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private JTextField txtId, txtBookingId, txtAmount;
    private JComboBox<EPaymentMode> cmbMode;
    private JLabel lblRevenue;
    private JTable table;

    public PaymentModule() {
        setTitle("Payment Management");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 540);
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
        JLabel lbl = new JLabel("💳  Payment Management");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        lblRevenue = new JLabel("Total Revenue: $0.00");
        lblRevenue.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRevenue.setForeground(new Color(0, 220, 150));
        JButton btnBack = makeBtn("← Back", new Color(52,152,219), Color.BLACK);
        btnBack.addActionListener(e -> dispose());
        JPanel rightH = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightH.setBackground(new Color(15, 52, 45));
        rightH.add(lblRevenue); rightH.add(btnBack);
        header.add(lbl, BorderLayout.WEST);
        header.add(rightH, BorderLayout.EAST);

        // Form card
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(Color.BLUE);
        formCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200,220,210), 1, true),
            new EmptyBorder(16, 20, 16, 20)));
        formCard.setPreferredSize(new Dimension(300, 0));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL; g.gridx = 0;
        g.insets = new Insets(4, 0, 4, 0);

        txtId        = UITheme.styledField(); txtId.setEditable(false);
        txtBookingId = UITheme.styledField();
        txtAmount    = UITheme.styledField();
        cmbMode      = new JComboBox<>(EPaymentMode.values());
        cmbMode.setFont(UITheme.FONT_BODY);
        cmbMode.setPreferredSize(new Dimension(220, 34));

        String[] labels = {"Payment ID (auto)", "Booking ID *", "Amount *", "Payment Mode"};
        JComponent[] flds = {txtId, txtBookingId, txtAmount, cmbMode};
        for (int i = 0; i < labels.length; i++) {
            g.gridy = i * 2;     fields.add(UITheme.fieldLabel(labels[i]), g);
            g.gridy = i * 2 + 1; fields.add(flds[i], g);
        }

        JButton btnSave    = makeBtn("💾 Save Payment", new Color(0,180,100),  Color.BLACK);
        JButton btnRefresh = makeBtn("↻ Refresh",       new Color(52,152,219), Color.BLACK);
        JButton btnClear   = makeBtn("✖ Clear",         new Color(90,90,90),   Color.BLACK);

        JPanel btns = new JPanel(new GridLayout(1, 3, 8, 0));
        btns.setOpaque(false);
        btns.setBorder(new EmptyBorder(14, 0, 0, 0));
        btns.add(btnSave); btns.add(btnRefresh); btns.add(btnClear);

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.add(UITheme.sectionTitle("Record Payment"), BorderLayout.NORTH);
        inner.add(fields, BorderLayout.CENTER);
        inner.add(btns, BorderLayout.SOUTH);
        formCard.add(inner);

        // Table card
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.lightGray);
        tableCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200,220,210), 1, true),
            new EmptyBorder(12, 12, 12, 12)));
        tableCard.add(UITheme.sectionTitle("Payment History"), BorderLayout.NORTH);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout(12, 0));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(12, 12, 12, 12));
        body.add(formCard, BorderLayout.WEST);
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        btnSave.addActionListener(e -> {
            if (txtBookingId.getText().trim().isEmpty() || txtAmount.getText().trim().isEmpty()) {
                warn("Booking ID and Amount are required."); return;
            }
            try {
                int bookingId = Integer.parseInt(txtBookingId.getText().trim());
                double amount = Double.parseDouble(txtAmount.getText().trim());
                if (amount <= 0) { warn("Amount must be greater than 0."); return; }

                // Validate booking belongs to current user (for members)
                if (!util.SessionManager.isAdmin()) {
                    controller.BookingController.findByUser(util.SessionManager.getCurrentUser().getUserId())
                        .stream().filter(b -> b.getBookingId() == bookingId).findFirst()
                        .orElseThrow(() -> new Exception("Booking #" + bookingId + " does not belong to you."));
                }

                Booking booking = new Booking();
                booking.setBookingId(bookingId);
                Payment p = new Payment(amount, new java.util.Date(), (EPaymentMode) cmbMode.getSelectedItem(), booking);
                Payment saved = PaymentController.create(p);
                if (saved != null) {
                    JOptionPane.showMessageDialog(this, "Payment saved!\nRef: " + saved.getTransactionRef(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadTable(); clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Payment failed. Booking may already be paid or not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) { warn("Amount and Booking ID must be valid numbers."); }
              catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnRefresh.addActionListener(e -> loadTable());
        btnClear.addActionListener(e -> clearForm());

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
        boolean isAdmin = util.SessionManager.isAdmin();
        java.util.List<Payment> payments;
        if (isAdmin) {
            payments = PaymentController.findAll();
        } else {
            // filter client-side: only payments whose booking belongs to current user
            int uid = util.SessionManager.getCurrentUser().getUserId();
            payments = PaymentController.findAll().stream()
                .filter(p -> p.getBooking() != null
                    && p.getBooking().getUser() != null
                    && p.getBooking().getUser().getUserId() == uid)
                .collect(java.util.stream.Collectors.toList());
        }
        for (Payment p : payments) {
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getBooking() != null ? p.getBooking().getBookingId() : "",
                String.format("%.0f", p.getAmount()),
                p.getPaymentMode(), p.getStatus(),
                p.getTransactionRef(),
                p.getPaymentDate() != null ? SDF.format(p.getPaymentDate()) : ""
            });
        }
        lblRevenue.setText("  Revenue: RWF " + String.format("%,.0f", PaymentController.getTotalRevenue()) + "  ");
    }

    private void clearForm() {
        txtId.setText(""); txtBookingId.setText(""); txtAmount.setText("");
        cmbMode.setSelectedIndex(0); table.clearSelection();
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new PaymentModule().setVisible(true));
    }
}
