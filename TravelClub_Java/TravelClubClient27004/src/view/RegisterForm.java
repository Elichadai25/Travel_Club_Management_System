package view;

import controller.AuthController;
import model.EUserRole;
import util.UITheme;
import java.awt.*;
import javax.swing.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;

public class RegisterForm extends JFrame {

    private JTextField txtFullName, txtEmail, txtPhone;
    private JPasswordField txtPassword, txtConfirm;
    private JComboBox<EUserRole> cmbRole;

    public RegisterForm() {
        setTitle("Travel Club — Create Account");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(500, 580);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Color.WHITE);

        // Header
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(UITheme.PRIMARY);
        header.setPreferredSize(new Dimension(0, 80));
        GridBagConstraints gh = new GridBagConstraints();
        gh.gridx = 0; gh.gridy = 0;
        JLabel ico = new JLabel("✈  Create Account");
        ico.setFont(new Font("Segoe UI", Font.BOLD, 22));
        ico.setForeground(Color.WHITE);
        header.add(ico, gh);
        gh.gridy = 1;
        JLabel sub = new JLabel("Join the Travel Club today");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(new Color(180, 210, 255));
        header.add(sub, gh);

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 40, 10, 40));
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(4, 0, 4, 0);
        g.gridx = 0;

        txtFullName = UITheme.styledField(); txtFullName.setPreferredSize(new Dimension(360, 36));
        txtEmail    = UITheme.styledField(); txtEmail.setPreferredSize(new Dimension(360, 36));
        txtPhone    = UITheme.styledField(); txtPhone.setPreferredSize(new Dimension(360, 36));
        txtPassword = UITheme.styledPassword(); txtPassword.setPreferredSize(new Dimension(360, 36));
        txtConfirm  = UITheme.styledPassword(); txtConfirm.setPreferredSize(new Dimension(360, 36));
        cmbRole     = new JComboBox<>(EUserRole.values());
        cmbRole.setFont(UITheme.FONT_BODY);
        cmbRole.setPreferredSize(new Dimension(360, 36));

        String[] labels = {"Full Name *", "Email Address *", "Phone Number *",
                           "Password *", "Confirm Password *", "Role"};
        JComponent[] fields = {txtFullName, txtEmail, txtPhone, txtPassword, txtConfirm, cmbRole};

        for (int i = 0; i < labels.length; i++) {
            g.gridy = i * 2;     form.add(UITheme.fieldLabel(labels[i]), g);
            g.gridy = i * 2 + 1; form.add(fields[i], g);
        }

        // Buttons
        JPanel btns = new JPanel(new GridLayout(1, 2, 10, 0));
        btns.setBackground(Color.WHITE);
        btns.setBorder(new EmptyBorder(16, 40, 20, 40));

        JButton btnRegister = UITheme.primaryButton("REGISTER");
        btnRegister.setPreferredSize(new Dimension(0, 40));
        btnRegister.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton btnBack = UITheme.secondaryButton("Back to Login");
        btnBack.setPreferredSize(new Dimension(0, 40));

        btns.add(btnRegister);
        btns.add(btnBack);

        root.add(header, BorderLayout.NORTH);
        root.add(new JScrollPane(form), BorderLayout.CENTER);
        root.add(btns, BorderLayout.SOUTH);
        setContentPane(root);

        btnRegister.addActionListener(e -> AuthController.handleRegister(
                txtFullName.getText().trim(), txtEmail.getText().trim(),
                txtPhone.getText().trim(), new String(txtPassword.getPassword()),
                new String(txtConfirm.getPassword()),
                (EUserRole) cmbRole.getSelectedItem(), this));

        btnBack.addActionListener(e -> { new LoginForm().setVisible(true); dispose(); });
    }
}
