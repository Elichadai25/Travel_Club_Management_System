 package view;

import controller.TripController;
import model.Trip;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TripPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Destination", "Country", "Price", "Slots", "Status", "Departure Date"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private static final SimpleDateFormat SDF      = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FMT  = new SimpleDateFormat("yyyy-MM-dd");
    private JTextField txtId, txtDest, txtCountry, txtPrice, txtSlots, txtDep, txtRet, txtDesc, txtSearch;
    private JTable table;

    public TripPanel() {
        setLayout(new BorderLayout());
        buildUI();
        loadTable();
    }

    private void buildUI() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Trip Form"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 6, 4, 6); g.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(10); txtId.setEditable(false);
        txtDest = new JTextField(15); txtCountry = new JTextField(15);
        txtPrice = new JTextField(10); txtSlots = new JTextField(10);
        txtDep = new JTextField(12); txtRet = new JTextField(12);
        txtDesc = new JTextField(15);

        String[] labels = {"ID", "Destination", "Country", "Price", "Slots", "Departure (yyyy-MM-dd)", "Return (yyyy-MM-dd)", "Description"};
        JComponent[] fields = {txtId, txtDest, txtCountry, txtPrice, txtSlots, txtDep, txtRet, txtDesc};
        for (int i = 0; i < labels.length; i++) {
            g.gridx = 0; g.gridy = i; form.add(new JLabel(labels[i]), g);
            g.gridx = 1; form.add(fields[i], g);
        }

        JButton btnSave = new JButton("Save"); btnSave.setBackground(new Color(102, 255, 102));
        JButton btnUpdate = new JButton("Update"); btnUpdate.setBackground(new Color(255, 255, 153));
        JButton btnDelete = new JButton("Delete"); btnDelete.setBackground(new Color(255, 80, 80)); btnDelete.setForeground(Color.WHITE);
        JButton btnClear = new JButton("Clear");

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        btns.add(btnSave); btns.add(btnUpdate); btns.add(btnDelete); btns.add(btnClear);

        JPanel left = new JPanel(new BorderLayout());
        left.add(form, BorderLayout.CENTER); left.add(btns, BorderLayout.SOUTH);

        // Search bar above the table
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search"); btnSearch.setBackground(new Color(41, 128, 185)); btnSearch.setForeground(Color.WHITE);
        JButton btnShowAll = new JButton("Show All"); btnShowAll.setBackground(new Color(127, 140, 141)); btnShowAll.setForeground(Color.WHITE);
        JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        searchBar.add(new JLabel("Search Destination:"));
        searchBar.add(txtSearch);
        searchBar.add(btnSearch);
        searchBar.add(btnShowAll);

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Trips"));

        JPanel right = new JPanel(new BorderLayout());
        right.add(searchBar, BorderLayout.NORTH);
        right.add(scroll, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        split.setDividerLocation(330);
        add(split, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (keyword.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a destination to search."); return; }
            tableModel.setRowCount(0);
            for (Trip t : TripController.search(keyword)) {
                tableModel.addRow(new Object[]{
                    t.getTripId(), t.getDestination(), t.getCountry(),
                    t.getPrice(), t.getAvailableSlots(), t.getStatus(),
                    t.getDepartureDate() != null ? DATE_FMT.format(t.getDepartureDate()) : ""
                });
            }
            if (tableModel.getRowCount() == 0)
                JOptionPane.showMessageDialog(this, "No trips found for: " + keyword);
        });

        btnShowAll.addActionListener(e -> { txtSearch.setText(""); loadTable(); });

        btnSave.addActionListener(e -> {
            if (txtDest.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Destination and Price are required."); return;
            }
            try {
                double price = Double.parseDouble(txtPrice.getText().trim());
                int slots = txtSlots.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSlots.getText().trim());
                java.util.Date dep = txtDep.getText().trim().isEmpty() ? new java.util.Date() : DATE_FMT.parse(txtDep.getText().trim());
                java.util.Date ret = txtRet.getText().trim().isEmpty() ? new java.util.Date() : DATE_FMT.parse(txtRet.getText().trim());
                Trip trip = new Trip(txtDest.getText().trim(), txtCountry.getText().trim(), dep, ret, price, slots, txtDesc.getText().trim());
                if (TripController.save(trip)) { JOptionPane.showMessageDialog(this, "Trip saved."); loadTable(); clearForm(); }
                else JOptionPane.showMessageDialog(this, "Save failed.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a trip first."); return; }
            try {
                Trip trip = new Trip();
                trip.setTripId(Integer.parseInt(txtId.getText().trim()));
                trip.setDestination(txtDest.getText().trim());
                trip.setCountry(txtCountry.getText().trim());
                trip.setPrice(Double.parseDouble(txtPrice.getText().trim()));
                trip.setAvailableSlots(txtSlots.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtSlots.getText().trim()));
                if (!txtDep.getText().trim().isEmpty()) trip.setDepartureDate(DATE_FMT.parse(txtDep.getText().trim()));
                if (!txtRet.getText().trim().isEmpty()) trip.setReturnDate(DATE_FMT.parse(txtRet.getText().trim()));
                trip.setDescription(txtDesc.getText().trim());
                if (TripController.update(trip)) { JOptionPane.showMessageDialog(this, "Trip updated."); loadTable(); clearForm(); }
                else JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) { JOptionPane.showMessageDialog(this, "Select a trip first."); return; }
            if (JOptionPane.showConfirmDialog(this, "Delete this trip?") != JOptionPane.YES_OPTION) return;
            if (TripController.delete(Integer.parseInt(txtId.getText().trim()))) { JOptionPane.showMessageDialog(this, "Trip deleted."); loadTable(); clearForm(); }
            else JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        });

        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtDest.setText(tableModel.getValueAt(row, 1).toString());
                txtCountry.setText(tableModel.getValueAt(row, 2).toString());
                txtPrice.setText(tableModel.getValueAt(row, 3).toString());
                txtSlots.setText(tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Trip t : TripController.findAll()) {
            tableModel.addRow(new Object[]{
                t.getTripId(), t.getDestination(), t.getCountry(),
                t.getPrice(), t.getAvailableSlots(), t.getStatus(),
                t.getDepartureDate() != null ? DATE_FMT.format(t.getDepartureDate()) : ""
            });
        }
    }

    private void clearForm() {
        txtId.setText(""); txtDest.setText(""); txtCountry.setText("");
        txtPrice.setText(""); txtSlots.setText(""); txtDep.setText("");
        txtRet.setText(""); txtDesc.setText(""); table.clearSelection();
    }
}
