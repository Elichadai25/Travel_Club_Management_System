package view;

import controller.TripController;
import model.Trip;
import util.BgPanel;
import util.UITheme;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class TripModule extends JFrame {

    private final DefaultTableModel tableModel = new DefaultTableModel(
        new String[]{"ID", "Destination", "Country", "Price (RWF)", "Slots", "Status", "Departure"}, 0) {
        public boolean isCellEditable(int r, int c) { 
            return false;
        }
    
        };
    private static final SimpleDateFormat DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

    private JTextField txtId, txtDest, txtCountry, txtPrice, txtSlots, txtDep, txtRet, txtDesc;
    private JTable table;

    public TripModule() {
        setTitle("Trip Management");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1150, 680);
        setLocationRelativeTo(null);
        initComponents();
        loadTable();
    }

    private void initComponents() {
        BgPanel root = new BgPanel(new BorderLayout());

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 52, 45));
        header.setPreferredSize(new Dimension(0, 52));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));
        JLabel lblTitle = new JLabel("✈  Trip Management");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        JButton btnBack = makeBtn("← Back", new Color(52, 152, 219), Color.BLACK);
        btnBack.addActionListener(e -> dispose());
        header.add(lblTitle, BorderLayout.WEST);
        header.add(btnBack, BorderLayout.EAST);

        // FORM: 2-column grid so all fields fit without scroll
        JPanel formCard = new JPanel(new BorderLayout());
        formCard.setBackground (Color.BLUE);
        formCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200,220,210), 1, true),
            new EmptyBorder(12, 16, 12, 16)));
        formCard.setPreferredSize(new Dimension(420, 0));

        JPanel grid = new JPanel(new GridBagLayout());
        grid.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(2, 4, 2, 4);

        txtId      = UITheme.styledField(); txtId.setEditable(false);
        txtId.setBackground(new Color(235, 235, 235));
        txtDest    = UITheme.styledField();
        txtCountry = UITheme.styledField();
        txtPrice   = UITheme.styledField();
        txtSlots   = UITheme.styledField();
        txtDep     = UITheme.styledField(); txtDep.setToolTipText("yyyy-MM-dd");
        txtRet     = UITheme.styledField(); txtRet.setToolTipText("yyyy-MM-dd");
        txtDesc    = UITheme.styledField();

        // Row 0: ID | Destination
        addRow(grid, g, 0, "Trip ID (auto)", txtId, "Destination *", txtDest);
        // Row 1: Country | Price
        addRow(grid, g, 1, "Country *", txtCountry, "Price (RWF) *", txtPrice);
        // Row 2: Slots | Departure
        addRow(grid, g, 2, "Available Slots *", txtSlots, "Departure Date * (yyyy-MM-dd)", txtDep);
        // Row 3: Return | Description
        addRow(grid, g, 3, "Return Date * (yyyy-MM-dd)", txtRet, "Description", txtDesc);

        // Buttons row
        JButton btnSave   = makeBtn("💾 Save",   new Color(0, 170, 90),  Color.BLUE);
        JButton btnUpdate = makeBtn("✏ Update", new Color(230, 140, 0),  Color.BLUE);
        JButton btnDelete = makeBtn("🗑 Delete", new Color(210, 40, 40),  Color.BLUE);
        JButton btnClear  = makeBtn("✖ Clear",  new Color(90, 90, 90),   Color.BLUE);

        JPanel btns = new JPanel(new GridLayout(1, 4, 8, 0));
        btns.setOpaque(false);
        btns.setBorder(new EmptyBorder(10, 0, 0, 0));
        btns.add(btnSave); btns.add(btnUpdate); btns.add(btnDelete); btns.add(btnClear);

        JPanel formInner = new JPanel(new BorderLayout(0, 8));
        formInner.setOpaque(false);
        JLabel sec = UITheme.sectionTitle("Trip Details");
        formInner.add(sec, BorderLayout.NORTH);
        formInner.add(grid, BorderLayout.CENTER);
        formInner.add(btns, BorderLayout.SOUTH);
        formCard.add(formInner);

        // TABLE 
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(new Color(10, 25, 30));
        tableCard.setBorder(new CompoundBorder(
            new LineBorder(new Color(200,220,210), 1, true),
            new EmptyBorder(12, 12, 12, 12)));
        tableCard.add(UITheme.sectionTitle("All Trips"), BorderLayout.NORTH);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel body = new JPanel(new BorderLayout(12, 0));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(10, 10, 10, 10));
        body.add(formCard, BorderLayout.WEST);
        body.add(tableCard, BorderLayout.CENTER);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);
        setContentPane(root);

        // ACTIONS
        btnSave.addActionListener(e -> saveTrip());
        btnUpdate.addActionListener(e -> updateTrip());
        btnDelete.addActionListener(e -> deleteTrip());
        btnClear.addActionListener(e -> clearForm());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() >= 0) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtDest.setText(tableModel.getValueAt(row, 1).toString());
                txtCountry.setText(tableModel.getValueAt(row, 2).toString());
                txtPrice.setText(tableModel.getValueAt(row, 3).toString());
                txtSlots.setText(tableModel.getValueAt(row, 4).toString());
                txtDep.setText(tableModel.getValueAt(row, 6).toString());
            }
        });
    }

    /** Adds a label+field pair in 2 columns per row */
    private void addRow(JPanel p, GridBagConstraints g, int row,
                        String lbl1, JTextField f1, String lbl2, JTextField f2) {
        g.gridy = row * 2;
        g.gridx = 0; g.weightx = 0.5; p.add(UITheme.fieldLabel(lbl1), g);
        g.gridx = 1; g.weightx = 0.5; p.add(UITheme.fieldLabel(lbl2), g);
        g.gridy = row * 2 + 1;
        g.gridx = 0; p.add(f1, g);
        g.gridx = 1; p.add(f2, g);
    }

    private void saveTrip() {
        // Technical validations — all required fields
        if (txtDest.getText().trim().isEmpty())    { 
            warn("Destination is required.");           
            return; 
        }
        if (txtCountry.getText().trim().isEmpty()) { 
            warn("Country is required.");                  
            return; 
        }
        if (txtPrice.getText().trim().isEmpty())   { 
            warn("Price is required.");                 
            return; 
        }
        if (txtSlots.getText().trim().isEmpty())   { 
            warn("Available Slots is required.");         
            return;
        }
        if (txtDep.getText().trim().isEmpty())     { 
            warn("Departure Date is required (yyyy-MM-dd)."); 
            return;
        }
        if (txtRet.getText().trim().isEmpty())     { 
            warn("Return Date is required (yyyy-MM-dd).");
            return; 
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            int    slots = Integer.parseInt(txtSlots.getText().trim());
            java.util.Date dep = DATE_FMT.parse(txtDep.getText().trim());
            java.util.Date ret = DATE_FMT.parse(txtRet.getText().trim());

            // Business validations
            if (price <= 0)      {
                warn("Price must be greater than 0.");           
                return; }
            if (slots <= 1)      {
                warn("Available Slots must be at least 1.");         
                return; }
            if (!ret.after(dep)) {  
                warn("Return Date must be after Departure Date.");    
                return; }

            Trip trip = new Trip(txtDest.getText().trim(), txtCountry.getText().trim(),
                    dep, ret, price, slots, txtDesc.getText().trim());

            Trip saved = TripController.saveAndReturn(trip);
            if (saved != null) {
                JOptionPane.showMessageDialog(this,
                    "Trip saved successfully!  ID: " + saved.getTripId(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable();
                clearForm();
                txtId.setText(String.valueOf(saved.getTripId()));
            }
            // error dialog already shown by TripController.saveAndReturn if null

        } catch (NumberFormatException ex) {
            warn("Price and Slots must be valid numbers.");
        } catch (ParseException ex) {
            warn("Date format must be yyyy-MM-dd  (e.g. 2026-07-20).");
        }
    }

    private void updateTrip() {
        if (txtId.getText().trim().isEmpty()) { warn("Select a trip from the table first."); return; }
        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            int    slots = Integer.parseInt(txtSlots.getText().trim());
            if (price <= 0) { warn("Price must be greater than 0."); return; }
            if (slots < 0)  { warn("Slots cannot be negative.");     return; }

            Trip trip = new Trip();
            trip.setTripId(Integer.parseInt(txtId.getText().trim()));
            trip.setDestination(txtDest.getText().trim());
            trip.setCountry(txtCountry.getText().trim());
            trip.setPrice(price);
            trip.setAvailableSlots(slots);
            if (!txtDep.getText().trim().isEmpty()) trip.setDepartureDate(DATE_FMT.parse(txtDep.getText().trim()));
            if (!txtRet.getText().trim().isEmpty()) trip.setReturnDate(DATE_FMT.parse(txtRet.getText().trim()));
            trip.setDescription(txtDesc.getText().trim());

            if (TripController.update(trip)) {
                JOptionPane.showMessageDialog(this, "Trip updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadTable(); clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) { warn("Invalid input: " + ex.getMessage()); }
    }

    private void deleteTrip() {
        if (txtId.getText().trim().isEmpty()) {
            warn("Select a trip from the table first."); 
            return; 
        }
        if (JOptionPane.showConfirmDialog(this, "Delete this trip? This cannot be undone.",
                "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) return;
        if (TripController.delete(Integer.parseInt(txtId.getText().trim()))) {
            JOptionPane.showMessageDialog(this, "Trip deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadTable(); clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Trip t : TripController.findAll()) {
            tableModel.addRow(new Object[]{
                t.getTripId(), t.getDestination(), t.getCountry(),
                String.format("%.0f", t.getPrice()),
                t.getAvailableSlots(), t.getStatus(),
                t.getDepartureDate() != null ? DATE_FMT.format(t.getDepartureDate()) : ""
            });
        }
    }

    private void clearForm() {
        txtId.setText(""); txtDest.setText(""); txtCountry.setText("");
        txtPrice.setText(""); txtSlots.setText(""); txtDep.setText("");
        txtRet.setText(""); txtDesc.setText(""); table.clearSelection();
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
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new TripModule().setVisible(true));
    }
}
