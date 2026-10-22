package view;

import controller.*;
import model.*;
import util.SessionManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class DashboardForm extends JFrame {

    private JPanel contentPanel;
    private JButton activeBtn;
    private static final Color SIDEBAR_BG   = new Color(15, 52, 45);
    private static final Color SIDEBAR_ACT  = new Color(255, 255, 255, 30);
    private static final Color ACCENT_GREEN = new Color(0, 180, 120);
    private static final Color TOP_BG       = new Color(10, 35, 60);

    public DashboardForm() {
        setTitle("Travel Club Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1440, 820);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1100, 650));
        initComponents();
    }

    private void initComponents() {
        boolean isAdmin = SessionManager.isAdmin();
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(TOP_BG);
// TOP BAR
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(TOP_BG);
        topBar.setPreferredSize(new Dimension(0, 56));
        topBar.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel logoArea = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 16));
        logoArea.setBackground(TOP_BG);
        JLabel dot = new JLabel("■");
        dot.setForeground(ACCENT_GREEN);
        dot.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel lblApp = new JLabel("Travel Club Management System");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblApp.setForeground(Color.WHITE);
        logoArea.add(dot); logoArea.add(lblApp);

        JPanel rightTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        rightTop.setBackground(TOP_BG);

        String roleTxt = isAdmin ? "ADMIN" : "MEMBER";
        Color  roleClr = isAdmin ? new Color(255, 200, 50) : ACCENT_GREEN;

        JLabel lblWelcome = new JLabel("Welcome, " + SessionManager.getCurrentUser().getFullName() + "   |");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblWelcome.setForeground(new Color(180, 200, 220));

        JLabel lblRole = new JLabel("■ " + roleTxt);
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRole.setForeground(roleClr);

        JButton btnLogout = new JButton("⏻  Logout");
        btnLogout.setBackground(new Color(255,255,255,0));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,80), 1, true));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.setOpaque(false);
        btnLogout.addActionListener(e -> { SessionManager.logout(); new LoginForm().setVisible(true); dispose(); });

        rightTop.add(lblWelcome); rightTop.add(lblRole); rightTop.add(btnLogout);
        topBar.add(logoArea, BorderLayout.WEST);
        topBar.add(rightTop, BorderLayout.EAST);

        //  SIDEBAR 
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(16, 0, 16, 0));

        JLabel lblRoleBadge = new JLabel("  " + (isAdmin ? "Administrator" : "Member"));
        lblRoleBadge.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRoleBadge.setForeground(ACCENT_GREEN);
        lblRoleBadge.setMaximumSize(new Dimension(220, 32));
        lblRoleBadge.setBorder(new EmptyBorder(0, 20, 12, 0));
        sidebar.add(lblRoleBadge);

        String[][] menu = isAdmin
            ? new String[][]{{"🏠","Dashboard"},{"👥","Members"},{"✈","Trips"},
                             {"📋","Bookings"},{"💳","Payments"},{"🔔","Notifications"},{"📊","Reports"}}
            : new String[][]{{"🏠","Dashboard"},{"✈","Trips"},
                             {"📋","Bookings"},{"💳","Payments"},{"🔔","Notifications"}};

        for (String[] item : menu) {
            JButton btn = makeSidebarBtn(item[0] + "   " + item[1]);
            sidebar.add(btn);
            sidebar.add(Box.createVerticalStrut(2));
            btn.addActionListener(e -> { setActiveBtn(btn); loadPanel(item[1]); });
        }

        // CONTENT 
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 244, 248));
        loadPanel("Dashboard");

        root.add(topBar, BorderLayout.NORTH);
        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);
        setContentPane(root);
    }

    private JButton makeSidebarBtn(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(220, 48));
        btn.setPreferredSize(new Dimension(220, 48));
        btn.setBackground(SIDEBAR_BG);
        btn.setForeground(new Color(180, 210, 200));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(0, 20, 0, 0));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (btn != activeBtn) btn.setBackground(new Color(255,255,255,20));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (btn != activeBtn) btn.setBackground(SIDEBAR_BG);
            }
        });
        return btn;
    }

    private void setActiveBtn(JButton btn) {
        if (activeBtn != null) {
            activeBtn.setBackground(SIDEBAR_BG);
            activeBtn.setForeground(new Color(180, 210, 200));
        }
        activeBtn = btn;
        btn.setBackground(new Color(255, 255, 255, 25));
        btn.setForeground(Color.WHITE);
    }

    private void loadPanel(String name) {
        contentPanel.removeAll();
        JPanel panel;
        switch (name) {
            case "Members":       panel = SessionManager.isAdmin() ? wrap(new UserModule()) : buildHome(); break;
            case "Trips":         panel = wrap(new TripModule()); break;
            case "Bookings":      panel = wrap(new BookingModule()); break;
            case "Payments":      panel = wrap(new PaymentModule()); break;
            case "Notifications": panel = wrap(new NotificationtModule()); break;
            case "Reports":       panel = SessionManager.isAdmin() ? new ReportPanel() : buildHome(); break;
            default:              panel = buildHome(); break;
        }
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel wrap(JFrame module) {
        JPanel w = new JPanel(new BorderLayout());
        w.setBackground(new Color(240, 244, 248));
        Container cp = module.getContentPane();
        module.setContentPane(new JPanel()); // detach before dispose
        w.add(cp, BorderLayout.CENTER);
        module.dispose();
        return w;
    }

       // HOME DASHBOARD
    private JPanel buildHome() {
        // Background image panel
        JPanel bg = new JPanel(new BorderLayout()) {
            private final BufferedImage img = loadImg("/images/dashboard_bg.jpeg");
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (img != null) g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                else { g.setColor(new Color(20, 60, 80)); g.fillRect(0, 0, getWidth(), getHeight()); }
            }
        };
        bg.setOpaque(true);

        // Scrollable content over the background
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Title
        JLabel lblDash = new JLabel("Dashboard");
        lblDash.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblDash.setForeground(Color.WHITE);
        lblDash.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Explore, book and enjoy your journey with us!");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(200, 220, 240));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSub.setBorder(new EmptyBorder(2, 0, 18, 0));

        content.add(lblDash);
        content.add(lblSub);

        // STAT CARDS
        boolean isAdmin = SessionManager.isAdmin();
        int myBookings = isAdmin ? BookingController.findAll().size()
                       : (int) BookingController.findAll().stream()
                           .filter(b -> b.getUser() != null &&
                                   b.getUser().getUserId() == SessionManager.getCurrentUser().getUserId())
                           .count();
        int upcomingTrips = TripController.findActiveTrips().size();

        // Members see their own total paid; admin sees total revenue
        double myPaid;
        String payLabel;
        if (isAdmin) {
            myPaid    = PaymentController.getTotalRevenue();
            payLabel  = "Total Revenue";
        } else {
            myPaid = PaymentController.findAll().stream()
                .filter(p -> p.getBooking() != null
                    && BookingController.findAll().stream()
                        .anyMatch(b -> b.getBookingId() == p.getBooking().getBookingId()
                            && b.getUser() != null
                            && b.getUser().getUserId() == SessionManager.getCurrentUser().getUserId()))
                .mapToDouble(p -> p.getAmount()).sum();
            payLabel = "My Total Paid";
        }

        List<Notification> allNotifs = isAdmin
                ? NotificationController.findAll()
                : NotificationController.findByUser(SessionManager.getCurrentUser().getUserId());
        int notifCount = allNotifs.size();

        JPanel statsRow = new JPanel(new GridLayout(1, 4, 14, 0));
        statsRow.setOpaque(false);
        statsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        statsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        statsRow.add(statCard("My Bookings",    String.valueOf(myBookings),
                "Total bookings",  new Color(66, 133, 244),  "📋"));
        statsRow.add(statCard("Upcoming Trips", String.valueOf(upcomingTrips),
                "Active trips",    new Color(52, 168, 83),   "✈"));
        statsRow.add(statCard(payLabel, "RWF " + String.format("%,.0f", myPaid),
                isAdmin ? "All payments" : "Amount paid", new Color(251, 188, 4), "💳"));
        statsRow.add(statCard("Notifications",  String.valueOf(notifCount),
                "Messages",        new Color(156, 39, 176),  "🔔"));

        content.add(statsRow);
        content.add(Box.createVerticalStrut(16));
    // MIDDLE ROW: Upcoming Trip + Recent Notifications
        JPanel midRow = new JPanel(new GridLayout(1, 2, 14, 0));
        midRow.setOpaque(false);
        midRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));
        midRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        midRow.add(buildUpcomingTripCard());
        midRow.add(buildNotificationsCard());
        content.add(midRow);
        content.add(Box.createVerticalStrut(16));

         // QUICK ACTIONS
        content.add(buildQuickActions());

        JScrollPane scroll = new JScrollPane(content);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        bg.add(scroll, BorderLayout.CENTER);
        return bg;
    }

    private JPanel statCard(String title, String value, String sub, Color iconBg, String icon) {
        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(new Color(255, 255, 255, 220));
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(16, 16, 16, 16)));

        // Icon box
        JLabel ico = new JLabel(icon, SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        ico.setForeground(Color.WHITE);
        ico.setOpaque(true);
        ico.setBackground(iconBg);
        ico.setPreferredSize(new Dimension(48, 48));
        ico.setBorder(new EmptyBorder(4, 4, 4, 4));

        JPanel icoWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        icoWrap.setOpaque(false);
        icoWrap.add(ico);

        // Text
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(100, 110, 120));

        JLabel lblVal = new JLabel(value);
        lblVal.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblVal.setForeground(new Color(20, 30, 50));

        JLabel lblSub = new JLabel("🗂 " + sub);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setForeground(new Color(140, 150, 160));

        JPanel txt = new JPanel();
        txt.setLayout(new BoxLayout(txt, BoxLayout.Y_AXIS));
        txt.setOpaque(false);
        txt.add(lblTitle);
        txt.add(lblVal);
        txt.add(lblSub);

        card.add(icoWrap, BorderLayout.WEST);
        card.add(txt, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildUpcomingTripCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255, 215));
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(16, 16, 12, 16)));

        JLabel title = new JLabel("\u2708  Upcoming Trip");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(new Color(20, 30, 50));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));

        List<Trip> trips = TripController.findActiveTrips();
        JPanel inner;
        if (!trips.isEmpty()) {
            Trip t = trips.get(0);
            inner = new JPanel(new BorderLayout(12, 0));
            inner.setOpaque(false);

            // Real image from resources, fallback to colored panel with emoji
            String[] imgPaths = {"/images/Akagera.jpeg","/images/Inyambo.jpeg", "/images/Gorilla.jpeg",
                                 "/images/kigaliBackground.jpeg", "/images/dashboard_bg.jpeg",
                                 "/images/Background1.jpeg"};
            BufferedImage tripImg = null;
            for (String p : imgPaths) {
                tripImg = loadImg(p);
                if (tripImg != null) break;
            }
            final BufferedImage finalImg = tripImg;
            JPanel imgPanel = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (finalImg != null)
                        g.drawImage(finalImg, 0, 0, getWidth(), getHeight(), this);
                    else {
                        g.setColor(new Color(180, 210, 240));
                        g.fillRect(0, 0, getWidth(), getHeight());
                        g.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
                        g.setColor(Color.WHITE);
                        g.drawString("\uD83C\uDF0D", 30, 70);
                    }
                }
            };
            imgPanel.setPreferredSize(new Dimension(140, 160));
            imgPanel.setBorder(new LineBorder(new Color(200, 210, 220), 1, true));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setOpaque(false);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            String dep  = t.getDepartureDate() != null ? sdf.format(t.getDepartureDate()) : "?";
            String ret  = t.getReturnDate()    != null ? sdf.format(t.getReturnDate())    : "?";
            String desc = (t.getDescription() != null && !t.getDescription().trim().isEmpty())
                        ? t.getDescription()
                        : "Discover the beauty of " + t.getDestination() + ", " + t.getCountry()
                          + ". This trip offers an unforgettable experience with stunning landscapes, "
                          + "rich culture, and exciting adventures. Book your seats now before they run out!";

            JLabel lName = new JLabel(t.getDestination() + (t.getCountry() != null ? ", " + t.getCountry() : ""));
            lName.setFont(new Font("Segoe UI", Font.BOLD, 15));
            lName.setForeground(new Color(15, 52, 45));

            JLabel lDates = new JLabel("\uD83D\uDCC5  " + dep + "  \u2192  " + ret);
            lDates.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lDates.setForeground(new Color(80, 90, 110));

            JLabel lPrice = new JLabel("\uD83D\uDCB0  RWF " + String.format("%,.0f", t.getPrice())
                + "  per seat   |   \uD83D\uDC64 " + t.getAvailableSlots() + " slots left");
            lPrice.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lPrice.setForeground(new Color(80, 90, 110));

            // Rich description — up to 5 lines wrapped
            JLabel lDesc = new JLabel("<html><div style='width:320px;color:#445;font-size:11px;line-height:1.5;'>"
                + desc + "</div></html>");
            lDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));

            JLabel lStatus = new JLabel("  " + t.getStatus() + "  ");
            lStatus.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lStatus.setForeground(new Color(0, 150, 80));
            lStatus.setBorder(new CompoundBorder(
                new LineBorder(new Color(0, 150, 80), 1, true),
                new EmptyBorder(2, 6, 2, 6)));
            lStatus.setOpaque(true);
            lStatus.setBackground(new Color(230, 255, 240));

            info.add(lName);
            info.add(Box.createVerticalStrut(4));
            info.add(lDates);
            info.add(Box.createVerticalStrut(3));
            info.add(lPrice);
            info.add(Box.createVerticalStrut(6));
            info.add(lDesc);
            info.add(Box.createVerticalStrut(8));
            info.add(lStatus);

            inner.add(imgPanel, BorderLayout.WEST);
            inner.add(info, BorderLayout.CENTER);
        } else {
            inner = new JPanel(new FlowLayout(FlowLayout.CENTER));
            inner.setOpaque(false);
            JLabel none = new JLabel("No upcoming trips available.");
            none.setFont(new Font("Segoe UI", Font.ITALIC, 13));
            none.setForeground(new Color(120, 130, 140));
            inner.add(none);
        }

        JButton btnView = makeActionBtn("View All Trips", ACCENT_GREEN);
        btnView.addActionListener(e -> loadPanel("Trips"));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(btnView);

        card.add(title, BorderLayout.NORTH);
        card.add(inner, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private JPanel buildNotificationsCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255, 215));
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(16, 16, 12, 16)));

        JLabel title = new JLabel("🔔  Recent Notifications");
        title.setFont(new Font("Segoe UI", Font.BOLD, 15));
        title.setForeground(new Color(20, 30, 50));
        title.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        List<Notification> notifs = SessionManager.isAdmin()
                ? NotificationController.findAll()
                : NotificationController.findByUser(SessionManager.getCurrentUser().getUserId());

        Color[] dotColors = {new Color(0,180,100), new Color(66,133,244),
                             new Color(251,188,4), new Color(156,39,176), new Color(120,120,120)};
        int max = Math.min(notifs.size(), 5);
        if (max == 0) {
            JLabel none = new JLabel("No notifications yet.");
            none.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            none.setForeground(new Color(140, 150, 160));
            list.add(none);
        }
        for (int i = 0; i < max; i++) {
            Notification n = notifs.get(i);
            list.add(notifRow(n, dotColors[i % dotColors.length]));
            if (i < max - 1) list.add(Box.createVerticalStrut(8));
        }

        JButton btnAll = makeActionBtn("View All Notifications", ACCENT_GREEN);
        btnAll.addActionListener(e -> loadPanel("Notifications"));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setOpaque(false);
        bottom.add(btnAll);

        card.add(title, BorderLayout.NORTH);
        card.add(list, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);
        return card;
    }

    private JPanel notifRow(Notification n, Color dotColor) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel dot = new JLabel("●");
        dot.setFont(new Font("Segoe UI", Font.BOLD, 18));
        dot.setForeground(dotColor);
        dot.setPreferredSize(new Dimension(24, 24));

        JLabel msg = new JLabel("<html><b>" + n.getType() + "</b><br>"
            + "<span style='color:#666;font-size:10px;'>"
            + (n.getMessage().length() > 55 ? n.getMessage().substring(0, 55) + "..." : n.getMessage())
            + "</span></html>");
        msg.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        JLabel time = new JLabel(n.getSentAt() != null ? sdf.format(n.getSentAt()) : "");
        time.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        time.setForeground(new Color(150, 160, 170));

        row.add(dot, BorderLayout.WEST);
        row.add(msg, BorderLayout.CENTER);
        row.add(time, BorderLayout.EAST);
        return row;
    }

    private JPanel buildQuickActions() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(255, 255, 255, 215));
        card.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(16, 20, 16, 20)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("⚡  Quick Actions");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(new Color(20, 30, 50));
        title.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel btns = new JPanel(new GridLayout(1, 4, 12, 0));
        btns.setOpaque(false);

        JButton b1 = quickActionBtn("✈  Browse Trips",    new Color(0, 180, 120));
        JButton b2 = quickActionBtn("📋  My Bookings",     new Color(66, 133, 244));
        JButton b3 = quickActionBtn("💳  Make Payment",    new Color(251, 140, 0));
        JButton b4 = quickActionBtn("👤  My Profile",      new Color(156, 39, 176));

        b1.addActionListener(e -> loadPanel("Trips"));
        b2.addActionListener(e -> loadPanel("Bookings"));
        b3.addActionListener(e -> loadPanel("Payments"));
        b4.addActionListener(e -> loadPanel("Members"));

        btns.add(b1); btns.add(b2); btns.add(b3); btns.add(b4);

        card.add(title, BorderLayout.NORTH);
        card.add(btns, BorderLayout.CENTER);
        return card;
    }

    private JButton quickActionBtn(String text, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setForeground(fg);
        b.setBackground(Color.WHITE);
        b.setBorder(new CompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(10, 8, 10, 8)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(new Color(245, 250, 255)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { b.setBackground(Color.WHITE); }
        });
        return b;
    }

    private JButton makeActionBtn(String text, Color fg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        b.setForeground(fg);
        b.setBackground(Color.WHITE);
        b.setBorder(new CompoundBorder(
            new LineBorder(fg, 1, true),
            new EmptyBorder(5, 14, 5, 14)));
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private static BufferedImage loadImg(String path) {
        try {
            InputStream is = DashboardForm.class.getResourceAsStream(path);
            if (is != null) return ImageIO.read(is);
        } catch (Exception ignored) {}
        return null;
    }
}
