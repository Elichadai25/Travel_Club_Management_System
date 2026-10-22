package util;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * UITheme - central design constants and helpers for a consistent modern UI.
 */
public class UITheme {

    //  Color Palette 
    public static final Color PRIMARY       = new Color(30, 60, 114);   // deep navy
    public static final Color PRIMARY_DARK  = new Color(18, 38, 74);    // darker navy
    public static final Color PRIMARY_LIGHT = new Color(41, 98, 180);   // medium blue
    public static final Color ACCENT        = new Color(0, 188, 140);   // teal green
    public static final Color DANGER        = new Color(192, 57, 43);   // red
    public static final Color WARNING       = new Color(243, 156, 18);  // amber
    public static final Color SUCCESS       = new Color(39, 174, 96);   // green
    public static final Color BG            = new Color(245, 247, 250); // light grey bg
    public static final Color CARD_BG       = Color.WHITE;
    public static final Color TEXT_MAIN     = new Color(30, 39, 46);
    public static final Color TEXT_MUTED    = new Color(127, 140, 141);
    public static final Color BORDER        = new Color(220, 225, 230);

    //  Fonts 
    public static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_BUTTON  = new Font("Segoe UI", Font.BOLD, 12);

    // ── Styled Components ────────────────────────────────────────────────────

    public static JTextField styledField() {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY);
        f.setPreferredSize(new Dimension(220, 34));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(4, 10, 4, 10)));
        return f;
    }

    public static JPasswordField styledPassword() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT_BODY);
        f.setPreferredSize(new Dimension(220, 34));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(4, 10, 4, 10)));
        return f;
    }

    public static JComboBox<?> styledCombo(Object[] items) {
        JComboBox<Object> c = new JComboBox<>(items);
        c.setFont(FONT_BODY);
        c.setPreferredSize(new Dimension(220, 34));
        c.setBackground(Color.WHITE);
        return c;
    }

    public static JButton primaryButton(String text) {
        return styledButton(text, PRIMARY, Color.WHITE);
    }

    public static JButton successButton(String text) {
        return styledButton(text, SUCCESS, Color.WHITE);
    }

    public static JButton warningButton(String text) {
        return styledButton(text, WARNING, Color.WHITE);
    }

    public static JButton dangerButton(String text) {
        return styledButton(text, DANGER, Color.WHITE);
    }

    public static JButton secondaryButton(String text) {
        return styledButton(text, new Color(189, 195, 199), TEXT_MAIN);
    }

    private static JButton styledButton(String text, Color bg, Color fg) {
        JButton b = new JButton(text);
        b.setFont(FONT_BUTTON);
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(110, 34));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            Color original = bg;
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(bg.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(original);
            }
        });
        return b;
    }

    public static JLabel sectionTitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_TITLE);
        l.setForeground(PRIMARY);
        l.setBorder(new EmptyBorder(0, 0, 10, 0));
        return l;
    }

    public static JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_HEADING);
        l.setForeground(TEXT_MAIN);
        return l;
    }

    /** Applies modern styling to a JTable */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(28);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(210, 228, 255));
        table.setSelectionForeground(TEXT_MAIN);
        table.setBackground(CARD_BG);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADING);
        header.setBackground(PRIMARY);
        header.setForeground(Color.BLACK);
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(BorderFactory.createEmptyBorder());

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                if (!sel) setBackground(row % 2 == 0 ? CARD_BG : new Color(248, 250, 253));
                return this;
            }
        });
    }

    /** Wraps content in a white card panel with rounded border */
    public static JPanel card(JComponent content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
            new EmptyBorder(16, 16, 16, 16)));
        card.add(content, BorderLayout.CENTER);
        return card;
    }
}
