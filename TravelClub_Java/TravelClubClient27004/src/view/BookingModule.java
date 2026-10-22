package view;

import controller.BookingController;
import controller.PaymentController;
import controller.TripController;
import model.Booking;
import model.EBookingStatus;
import model.Payment;
import model.Trip;
import model.User;
import util.BgPanel;
import util.SessionManager;
import util.UITheme;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BookingModule extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Member", "Trip", "Seats", "Total (RWF)", "Paid (RWF)", "Balance", "Status", "Payment", "Date"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    private JTextField txtId, txtTripId, txtSeats;
    private JComboBox<EBookingStatus> cmbStatus;
    private JTable table;

    public BookingModule() {
        setTitle("Booking Management");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 620);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        BgPanel root = new BgPanel(new BorderLayout());

        //  HEADER 
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 52, 45));
        header.setPreferredSize(new Dimension(0, 52));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));
        JLabel lbl = new JLabel("📋  Booking Management");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(Color.WHITE);
        JButton btnBack = makeBtn("← Back", new Color(52, 152, 219), Color.WHITE);
        btnBack.addActionListener(e -> dispose());
        header.add(lbl, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);

        //  FORM CARD 
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground(new Color(255, 255, 255, 230));
        formCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 220, 210), 1, true),
            new EmptyBorder(16, 20, 16, 20)));
        formCard.setPreferredSize(new Dimension(300, 0));

        JPanel fields = new JPanel(new GridBagLayout());
        fields.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL; g.gridx = 0;
        g.insets = new Insets(4, 0, 4, 0);

        txtId     = UITheme.styledField(); txtId.setEditable(false);
        txtTripId = UITheme.styledField();
        txtSeats  = UITheme.styledField();
        cmbStatus = new JComboBox<>(EBookingStatus.values());
        cmbStatus.setFont(UITheme.FONT_BODY);
        cmbStatus.setPreferredSize(new Dimension(220, 34));

        String[] labels = {"Booking ID (auto)", "Trip ID *", "Number of Seats *", "Status"};
        JComponent[] flds = {txtId, txtTripId, txtSeats, cmbStatus};
        for (int i = 0; i < labels.length; i++) {
            g.gridy = i * 2;     fields.add(UITheme.fieldLabel(labels[i]), g);
            g.gridy = i * 2 + 1; fields.add(flds[i], g);
        }

        JButton btnBook   = makeBtn("✈ Book Trip", new Color(0, 180, 100),  Color.WHITE);
        JButton btnCancel = makeBtn("✖ Cancel",    new Color(230, 140, 0),  Color.WHITE);
        JButton btnDelete = makeBtn("🗑 Delete",   new Color(210, 40, 40),  Color.WHITE);
        JButton btnClear  = makeBtn("↺ Clear",     new Color(90, 90, 90),   Color.WHITE);

        JPanel btns = new JPanel(new GridLayout(2, 2, 8, 8));
        btns.setOpaque(false);
        btns.setBorder(new EmptyBorder(14, 0, 8, 0));
        btns.add(btnBook); btns.add(btnCancel);
        btns.add(btnDelete); btns.add(btnClear);

        //  STATUS LEGEND 
        JPanel legend = new JPanel(new GridLayout(4, 1, 2, 2));
        legend.setOpaque(false);
        legend.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 220, 210), 1, true),
            new EmptyBorder(8, 10, 8, 10)));
        legend.add(legendRow("PENDING",   new Color(255, 200, 50),  "Booked, awaiting payment"));
        legend.add(legendRow("CONFIRMED", new Color(0, 180, 100),   "Payment received, seat secured"));
        legend.add(legendRow("CANCELLED", new Color(210, 40, 40),   "Booking was cancelled"));
        legend.add(legendRow("COMPLETED", new Color(66, 133, 244),  "Trip completed successfully"));

        JPanel inner = new JPanel(new BorderLayout());
        inner.setOpaque(false);
        inner.add(UITheme.sectionTitle("New Booking"), BorderLayout.NORTH);
        inner.add(fields, BorderLayout.CENTER);
        JPanel southForm = new JPanel(new BorderLayout());
        southForm.setOpaque(false);
        southForm.add(btns, BorderLayout.NORTH);
        southForm.add(legend, BorderLayout.CENTER);
        inner.add(southForm, BorderLayout.SOUTH);
        formCard.add(inner);

        // ── TABLE CARD ───────────────────────────────────────────────────────
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (!sel) {
                    String status = tableModel.getValueAt(row, 7) != null
                            ? tableModel.getValueAt(row, 7).toString() : "";
                    switch (status) {
                        case "PENDING":   setBackground(new Color(255, 253, 230)); break;
                        case "CONFIRMED": setBackground(new Color(230, 255, 240)); break;
                        case "CANCELLED": setBackground(new Color(255, 235, 235)); break;
                        case "COMPLETED": setBackground(new Color(230, 240, 255)); break;
                        default: setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 252, 250));
                    }
                    setForeground(new Color(30, 40, 50));
                }
                return this;
            }
        });

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(new Color(255, 255, 255, 230));
        tableCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 220, 210), 1, true),
            new EmptyBorder(12, 12, 12, 12)));
        tableCard.add(UITheme.sectionTitle("Booking History"), BorderLayout.NORTH);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout(12, 0));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(12, 12, 12, 12));
        body.add(formCard, BorderLayout.WEST);
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        //  ACTIONS 
        btnBook.addActionListener(e -> {
            if (txtTripId.getText().trim().isEmpty() || txtSeats.getText().trim().isEmpty()) {
                warn("Trip ID and Seats are required."); return;
            }
            try {
                int seats = Integer.parseInt(txtSeats.getText().trim());
                if (seats <= 0) { warn("Seats must be at least 1."); return; }
                User current = SessionManager.getCurrentUser();
                if (current == null) { warn("Not logged in."); return; }
                Trip trip = TripController.findById(Integer.parseInt(txtTripId.getText().trim()));
                if (trip == null) { warn("Trip not found. Make sure the server is running and the Trip ID exists."); return; }
                Booking b = new Booking(new java.util.Date(), seats, current, trip);
                Booking saved = BookingController.create(b);
                if (saved != null) {
                    JOptionPane.showMessageDialog(this,
                        "Booking created!\nID: " + saved.getBookingId()
                        + "\nTrip: " + trip.getDestination()
                        + "\nSeats: " + seats
                        + "\nTotal Due: RWF " + String.format("%,.0f", saved.getTotalAmount())
                        + "\n\nStatus: PENDING — please make payment to confirm.",
                        "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
                    loadTable(); clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Booking failed. Check available slots.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) { warn("Trip ID and Seats must be numbers."); }
              catch (Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnCancel.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { warn("Select a booking first."); return; }
            if (BookingController.cancel(Integer.parseInt(txtId.getText().trim()))) {
                JOptionPane.showMessageDialog(this, "Booking cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else JOptionPane.showMessageDialog(this, "Cancel failed.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { warn("Select a booking first."); return; }
            if (JOptionPane.showConfirmDialog(this, "Delete this booking?", "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            if (BookingController.delete(Integer.parseInt(txtId.getText().trim()))) {
                JOptionPane.showMessageDialog(this, "Booking deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                cmbStatus.setSelectedItem(EBookingStatus.valueOf(tableModel.getValueAt(row, 7).toString()));
            }
        });
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        boolean isAdmin = SessionManager.isAdmin();
        java.util.List<Booking> bookings = isAdmin
            ? BookingController.findAll()
            : BookingController.findByUser(SessionManager.getCurrentUser().getUserId());
        for (Booking b : bookings) {
            double total  = b.getTotalAmount();
            Payment pay   = PaymentController.findByBooking(b.getBookingId());
            double paid   = (pay != null) ? pay.getAmount() : 0.0;
            double balance = total - paid;
            String payStatus;
            if (paid <= 0)          payStatus = "UNPAID";
            else if (paid >= total) payStatus = "FULLY PAID";
            else                    payStatus = "PARTIAL";

            tableModel.addRow(new Object[]{
                b.getBookingId(),
                b.getUser() != null ? b.getUser().getFullName() : "",
                b.getTrip() != null ? b.getTrip().getDestination() : "",
                b.getNumSeats(),
                String.format("%.0f", total),
                String.format("%.0f", paid),
                balance > 0 ? String.format("%.0f", balance) : "—",
                b.getStatus(),
                payStatus,
                b.getBookingDate() != null ? SDF.format(b.getBookingDate()) : ""
            });
        }
    }

    private JPanel legendRow(String status, Color color, String desc) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 2));
        row.setOpaque(false);
        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Segoe UI", Font.BOLD, 14));
        dot.setForeground(color);
        JLabel txt = new JLabel("<html><b>" + status + "</b> — " + desc + "</html>");
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txt.setForeground(new Color(40, 50, 60));
        row.add(dot); row.add(txt);
        return row;
    }

    private void clearForm() {
        txtId.setText(""); txtTripId.setText(""); txtSeats.setText("");
        cmbStatus.setSelectedIndex(0); table.clearSelection();
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
        SwingUtilities.invokeLater(() -> new BookingModule().setVisible(true));
    }
}
