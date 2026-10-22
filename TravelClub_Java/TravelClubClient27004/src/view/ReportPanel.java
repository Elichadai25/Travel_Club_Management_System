package view;

import controller.BookingController;
import controller.PaymentController;
import controller.TripController;
import controller.UserController;
import model.Booking;
import model.Payment;
import util.ExportUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends JPanel {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Color HEADER_BG  = new Color(15, 52, 45);
    private static final Color BTN_BAR_BG = new Color(255, 255, 255, 240);
    private static final Color TBL_HDR_BG = new Color(15, 52, 45);

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblStatus;
    private BufferedImage bgImage;

    public ReportPanel() {
        setLayout(new BorderLayout());
        setOpaque(true);
        bgImage = loadImg("/images/dashboard_bg.jpeg");
        buildUI();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getWidth(), getHeight());
        } else {
            g.setColor(new Color(20, 50, 70));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void buildUI() {
        // ── HEADER ──────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setPreferredSize(new Dimension(0, 54));
        header.setBorder(new EmptyBorder(0, 20, 0, 20));
        header.setOpaque(true);

        JLabel lblTitle = new JLabel("📊  Reports & Export");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        header.add(lblTitle, BorderLayout.WEST);

        // ── BUTTON BAR ───────────────────────────────────────────────────────
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 10));
        top.setBackground(new Color(245, 248, 250));
        top.setOpaque(true);
        top.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, new Color(15, 52, 45)),
            new EmptyBorder(6, 10, 6, 10)));

        JButton btnUsers    = makeBtn("👥  Members",  new Color(41, 128, 185));
        JButton btnTrips    = makeBtn("✈  Trips",     new Color(39, 174, 96));
        JButton btnBookings = makeBtn("📋  Bookings", new Color(200, 120, 0));
        JButton btnPayments = makeBtn("💳  Payments", new Color(192, 57, 43));

        JSeparator vsep = new JSeparator(JSeparator.VERTICAL);
        vsep.setPreferredSize(new Dimension(1, 30));
        vsep.setForeground(new Color(180, 190, 200));

        JLabel lblExp = new JLabel("  Export:");
        lblExp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblExp.setForeground(new Color(15, 52, 45));

        JButton btnCSV  = makeBtn("⬇ CSV",  new Color(44, 62, 80));
        JButton btnHTML = makeBtn("⬇ HTML", new Color(44, 62, 80));
        JButton btnPDF  = makeBtn("⬇ PDF",  new Color(44, 62, 80));

        top.add(btnUsers); top.add(btnTrips); top.add(btnBookings); top.add(btnPayments);
        top.add(vsep); top.add(lblExp);
        top.add(btnCSV); top.add(btnHTML); top.add(btnPDF);

        // ── TABLE ────────────────────────────────────────────────────────────
        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0, 180, 120, 80));
        table.setSelectionForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        // Header: dark green bg, WHITE bold text
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(TBL_HDR_BG);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 38));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder());

        // Alternating rows + left padding
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 12, 0, 12));
                setFont(new Font("Segoe UI", Font.PLAIN, 13));
                if (!sel) {
                    setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 250, 248));
                    setForeground(new Color(30, 40, 50));
                }
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // Semi-transparent white card around table
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(Color.WHITE);
        tableCard.setOpaque(true);
        tableCard.setBorder(new EmptyBorder(0, 0, 0, 0));
        tableCard.add(top, BorderLayout.NORTH);
        tableCard.add(scroll, BorderLayout.CENTER);

        // ── STATUS BAR ───────────────────────────────────────────────────────
        lblStatus = new JLabel("  Select a report to generate.");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(new Color(15, 52, 45));
        lblStatus.setBorder(new EmptyBorder(8, 14, 8, 14));
        lblStatus.setOpaque(true);

        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(0, 0, 0, 0));
        body.add(tableCard, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        // ── ACTIONS ──────────────────────────────────────────────────────────
        btnUsers.addActionListener(e    -> loadUsersReport());
        btnTrips.addActionListener(e    -> loadTripsReport());
        btnBookings.addActionListener(e -> loadBookingsReport());
        btnPayments.addActionListener(e -> loadPaymentsReport());

        btnCSV.addActionListener(e -> {
            if (table.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Generate a report first."); return; }
            ExportUtil.exportToCSV(table, lblStatus.getText().trim());
        });
        btnHTML.addActionListener(e -> {
            if (table.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Generate a report first."); return; }
            ExportUtil.exportToHTML(table, lblStatus.getText().trim());
        });
        btnPDF.addActionListener(e -> {
            if (table.getRowCount() == 0) { JOptionPane.showMessageDialog(this, "Generate a report first."); return; }
            JOptionPane.showMessageDialog(this,
                "PDF export: use Export HTML and print to PDF from your browser.",
                "PDF Export", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void loadUsersReport() {
        tableModel.setRowCount(0); tableModel.setColumnCount(0);
        for (String c : new String[]{"ID","Full Name","Email","Phone","Role","Active"}) tableModel.addColumn(c);
        UserController.findAll().forEach(u -> tableModel.addRow(new Object[]{
            u.getUserId(), u.getFullName(), u.getEmail(),
            u.getPhoneNumber(), u.getRole(), u.isActive() ? "Yes" : "No"
        }));
        lblStatus.setText("  Members Report — " + tableModel.getRowCount() + " records");
    }

    private void loadTripsReport() {
        tableModel.setRowCount(0); tableModel.setColumnCount(0);
        for (String c : new String[]{"ID","Destination","Country","Price (RWF)","Slots","Status","Departure"}) tableModel.addColumn(c);
        TripController.findAll().forEach(t -> tableModel.addRow(new Object[]{
            t.getTripId(), t.getDestination(), t.getCountry(),
            String.format("%.0f", t.getPrice()), t.getAvailableSlots(), t.getStatus(),
            t.getDepartureDate() != null ? new SimpleDateFormat("yyyy-MM-dd").format(t.getDepartureDate()) : ""
        }));
        lblStatus.setText("  Trips Report — " + tableModel.getRowCount() + " records");
    }

    private void loadBookingsReport() {
        tableModel.setRowCount(0); tableModel.setColumnCount(0);
        for (String c : new String[]{"ID","Member","Trip","Seats","Total (RWF)","Status","Booking Date"}) tableModel.addColumn(c);
        for (Booking b : BookingController.findAll())
            tableModel.addRow(new Object[]{
                b.getBookingId(),
                b.getUser() != null ? b.getUser().getFullName() : "",
                b.getTrip() != null ? b.getTrip().getDestination() : "",
                b.getNumSeats(), String.format("%.0f", b.getTotalAmount()), b.getStatus(),
                b.getBookingDate() != null ? SDF.format(b.getBookingDate()) : ""
            });
        lblStatus.setText("  Bookings Report — " + tableModel.getRowCount() + " records");
    }

    private void loadPaymentsReport() {
        tableModel.setRowCount(0); tableModel.setColumnCount(0);
        for (String c : new String[]{"ID","Booking ID","Amount (RWF)","Mode","Status","Reference","Payment Date"}) tableModel.addColumn(c);
        for (Payment p : PaymentController.findAll())
            tableModel.addRow(new Object[]{
                p.getPaymentId(),
                p.getBooking() != null ? p.getBooking().getBookingId() : "",
                String.format("%.0f", p.getAmount()),
                p.getPaymentMode(), p.getStatus(), p.getTransactionRef(),
                p.getPaymentDate() != null ? SDF.format(p.getPaymentDate()) : ""
            });
        double rev = PaymentController.getTotalRevenue();
        lblStatus.setText("  Payments Report — " + tableModel.getRowCount()
            + " records  |  Total Revenue: RWF " + String.format("%.0f", rev));
    }

    private JButton makeBtn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setBorderPainted(true);
        b.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg.darker(), 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)));
        b.setPreferredSize(new Dimension(120, 34));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(bg.darker()); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    private static BufferedImage loadImg(String path) {
        try {
            InputStream is = ReportPanel.class.getResourceAsStream(path);
            if (is != null) return ImageIO.read(is);
        } catch (Exception ignored) {}
        return null;
    }
}
