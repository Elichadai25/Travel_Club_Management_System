package view;

import controller.AuthController;
import util.UITheme;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginForm extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;

    public LoginForm() {
        setTitle("Travel Club Management System — Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(890, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new GridLayout(1, 2));

        // LEFT: Login Form (dark navy background)
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(new Color(10, 35, 60));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0; g.insets = new Insets(6, 20, 6, 20);

        JLabel ico = new JLabel("✈");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));
        ico.setForeground(new Color(0, 200, 140));
        ico.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Travel Club");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Management System");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sub.setForeground(new Color(160, 200, 240));

        JSeparator sep = new JSeparator();
        sep.setPreferredSize(new Dimension(200, 1));
        sep.setForeground(new Color(0, 200, 140));

        JLabel lblWelcome = new JLabel("Welcome Back!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);

        JLabel lblSub = new JLabel("Sign in to your account");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(new Color(160, 190, 220));
        lblSub.setBorder(new EmptyBorder(0, 0, 10, 0));

        txtEmail = UITheme.styledField();
        txtEmail.setPreferredSize(new Dimension(280, 38));
        txtEmail.setBackground(new Color(20, 50, 80));
        txtEmail.setForeground(Color.WHITE);
        txtEmail.setCaretColor(Color.WHITE);

        txtPassword = UITheme.styledPassword();
        txtPassword.setPreferredSize(new Dimension(280, 38));
        txtPassword.setBackground(new Color(20, 50, 80));
        txtPassword.setForeground(Color.WHITE);
        txtPassword.setCaretColor(Color.WHITE);

        JLabel lblEmail = new JLabel("Email Address");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEmail.setForeground(new Color(160, 200, 240));

        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPass.setForeground(new Color(160, 200, 240));

        JButton btnLogin = new JButton("SIGN IN");
        btnLogin.setBackground(new Color(0, 180, 120));
        btnLogin.setForeground(Color.GREEN);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setPreferredSize(new Dimension(280, 42));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { btnLogin.setBackground(new Color(0, 150, 100)); }
            public void mouseExited(java.awt.event.MouseEvent e)  { btnLogin.setBackground(new Color(0, 180, 120)); }
        });

        JButton btnRegister = new JButton("Don't have an account? Register");
        btnRegister.setBackground(new Color(10, 35, 60));
        btnRegister.setForeground(new Color(0, 200, 140));
        btnRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRegister.setBorderPainted(false);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        g.gridy = 0; left.add(ico, g);
        g.gridy = 1; left.add(title, g);
        g.gridy = 2; left.add(sub, g);
        g.gridy = 3; g.insets = new Insets(4, 20, 10, 20); left.add(sep, g);
        g.gridy = 4; g.insets = new Insets(4, 20, 2, 20); left.add(lblWelcome, g);
        g.gridy = 5; left.add(lblSub, g);
        g.gridy = 6; g.insets = new Insets(2, 20, 1, 20); left.add(lblEmail, g);
        g.gridy = 7; g.insets = new Insets(1, 20, 4, 20); left.add(txtEmail, g);
        g.gridy = 8; g.insets = new Insets(4, 20, 1, 20); left.add(lblPass, g);
        g.gridy = 9; g.insets = new Insets(1, 20, 4, 20); left.add(txtPassword, g);
        g.gridy = 10; g.insets = new Insets(12, 20, 4, 20); left.add(btnLogin, g);
        g.gridy = 11; g.insets = new Insets(0, 20, 0, 20); left.add(btnRegister, g);

        // RIGHT: Background image (Login_background.jpg) 
        JPanel right = new JPanel(new GridBagLayout()) {
            private final BufferedImage bg = loadImage("/images/Login_background.jpeg");
            @Override
            protected void paintComponent(Graphics g2) {
                super.paintComponent(g2);
                if (bg != null) {
                    g2.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                    // light overlay so it looks bright/warm
                    g2.setColor(new Color(255, 240, 200, 40));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        right.setBackground(new Color(200, 220, 240));

        // Tagline over the image
        GridBagConstraints gr = new GridBagConstraints();
        gr.gridx = 0; gr.gridy = 0; gr.insets = new Insets(0, 20, 8, 20);
        JLabel tagline = new JLabel(
            "<html><div style='text-align:center;color:white;'>" +
            "<b style='font-size:20px;'>Your Journey Starts Here</b><br>" +
            "<span style='font-size:16px;'>Explore the world with Travel Club</span>" +
            "</div></html>");
        tagline.setHorizontalAlignment(SwingConstants.CENTER);
        right.add(tagline, gr);

        root.add(left);
        root.add(right);
        setContentPane(root);

        btnLogin.addActionListener(e -> AuthController.handleLogin(
                txtEmail.getText().trim(), new String(txtPassword.getPassword()), this));
        btnRegister.addActionListener(e -> { new RegisterForm().setVisible(true); dispose(); });
        txtPassword.addActionListener(e -> btnLogin.doClick());
    }

    private static BufferedImage loadImage(String path) {
        try {
            java.io.InputStream is = LoginForm.class.getResourceAsStream(path);
            if (is != null) return ImageIO.read(is);
        } catch (Exception ignored) {}
        return null;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
