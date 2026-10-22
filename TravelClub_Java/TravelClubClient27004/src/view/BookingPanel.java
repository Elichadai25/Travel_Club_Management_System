package view;

import controller.BookingController;
import controller.TripController;
import model.Booking;
import model.Trip;
import model.User;
import util.SessionManager;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookingPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "User", "Trip", "Seats", "Total", "Status", "Booking Date"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private JTextField txtId, txtTripId, txtSeats;
    private JComboBox<String> cmbStatus;
    private JTable table;

    public BookingPanel() {
        setLayout(new BorderLayout());
        buildUI();
        loadTable();
    }

    private void buildUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Booking Form"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6); g.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10); txtId.setEditable(false);
        txtTripId = new JTextField(10); txtSeats = new JTextField(10);
        cmbStatus = new JComboBox<>(new String[]{"PENDING", "CONFIRMED", "CANCELLED", "COMPLETED"});

        String[] labels = {"ID", "Trip ID", "Seats", "Status"};
        JComponent[] fields = {txtId, txtTripId, txtSeats, cmbStatus};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = i; form.add(new JLabel(labels[i]), g);
            g.gridx = 1; form.add(fields[i], g);
        }

        JButton btnBook = new JButton("Book"); btnBook.setBackground(new Color(102, 255, 102));
        JButton btnCancel = new JButton("Cancel"); btnCancel.setBackground(new Color(255, 80, 80)); btnCancel.setForeground(Color.WHITE);
        JButton btnDelete = new JButton("Delete"); btnDelete.setBackground(new Color(200, 50, 50)); btnDelete.setForeground(Color.WHITE);
        JButton btnClear = new JButton("Clear");

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        btns.add(btnBook); btns.add(btnCancel); btns.add(btnDelete); btns.add(btnClear);

        JPanel left = new JPanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER); left.add(btns, BorderLayout.SOUTH);

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Bookings"));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, scroll);
        split.setDividerLocation(280);
        add(split, BorderLayout.CENTER);

        btnBook.addActionListener(e -> {
            if (txtTripId.getText().trim().isEmpty() || txtSeats.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Trip ID and Seats are required."); return;
            }
            try {
                User current = SessionManager.getCurrentUser();
                if (current == null) { JOptionPane.showMessageDialog(this, "Not logged in."); return; }
                Trip trip = TripController.findById(Integer.parseInt(txtTripId.getText().trim()));
                if (trip == null) { JOptionPane.showMessageDialog(this, "Trip not found."); return; }
                Booking b = new Booking(new java.util.Date(), Integer.parseInt(txtSeats.getText().trim()), current, trip);
                if (BookingController.create(b) != null) { JOptionPane.showMessageDialog(this, "Booking created."); loadTable(); clearForm(); }
                else JOptionPane.showMessageDialog(this, "Booking failed.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a booking first."); return; }
            if (BookingController.cancel(Integer.parseInt(txtId.getText().trim()))) { JOptionPane.showMessageDialog(this, "Booking cancelled."); loadTable(); clearForm(); }
            else JOptionPane.showMessageDialog(this, "Cancel failed.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a booking first."); return; }
            if (JOptionPane.showConfirmDialog(this, "Delete this booking?") != JOptionPane.YES_OPTION) return;
            if (BookingController.delete(Integer.parseInt(txtId.getText().trim()))) { JOptionPane.showMessageDialog(this, "Booking deleted."); loadTable(); clearForm(); }
            else JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                cmbStatus.setSelectedItem(tableModel.getValueAt(row, 5).toString());
            }
        });
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Booking b : BookingController.findAll()) {
            tableModel.addRow(new Object[]{
                b.getBookingId(),
                b.getUser() != null ? b.getUser().getFullName() : "",
                b.getTrip() != null ? b.getTrip().getDestination(): "",
                b.getNumSeats(), b.getTotalAmount(), b.getStatus(),
                b.getBookingDate() != null ? SDF.format(b.getBookingDate()) : ""
            });
        }
    }

    private void clearForm() {
        txtId.setText(""); txtTripId.setText(""); txtSeats.setText("");
        cmbStatus.setSelectedIndex(0); table.clearSelection();
    }
}
