package controller;

import model.EUserRole;
import model.User;
import rmi.ServiceConnector;
import util.SessionManager;
import view.DashboardForm;
import view.LoginForm;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AuthController {

    public static void handleLogin(String email, String password, JFrame parent) {
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Email and password are required.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(parent, "Please enter a valid email address.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            // Step 1: Verify credentials
            User user = ServiceConnector.getService().login(email, password);
            if (user == null || !user.isActive()) {
                JOptionPane.showMessageDialog(parent, "Invalid credentials or account inactive.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 2: Generate and send OTP
            String otp = ServiceConnector.getService().generateOTP(email);

            // Step 3: Inform user
            JOptionPane.showMessageDialog(parent,
                    "A 6-digit OTP has been sent to:\n" + email +
                    "\n\nPlease check your inbox and enter the code.",
                    "OTP Sent", JOptionPane.INFORMATION_MESSAGE);

            // Step 4: Ask user to enter OTP
            String entered = JOptionPane.showInputDialog(parent,
                    "Enter the 6-digit OTP sent to your email:",
                    "OTP Verification", JOptionPane.PLAIN_MESSAGE);

            if (entered == null || entered.trim().isEmpty()) {
                JOptionPane.showMessageDialog(parent, "OTP verification cancelled.",
                        "Cancelled", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Step 5: Verify OTP
            boolean valid = ServiceConnector.getService().verifyOTP(email, entered.trim());
            if (!valid) {
                JOptionPane.showMessageDialog(parent, "Invalid or expired OTP. Please try again.",
                        "OTP Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 6: OTP passed — open dashboard
            SessionManager.setCurrentUser(user);
            new DashboardForm().setVisible(true);
            parent.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Server error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void handleRegister(String fullName, String email, String phone,
            String password, String confirm, EUserRole role, JFrame parent) {
        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "All fields are required.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            JOptionPane.showMessageDialog(parent, "Please enter a valid email address.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(parent, "Password must be at least 6 characters.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(parent, "Passwords do not match.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            if (ServiceConnector.getService().emailExists(email)) {
                JOptionPane.showMessageDialog(parent, "Email already registered.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (ServiceConnector.getService().phoneExists(phone)) {
                JOptionPane.showMessageDialog(parent, "Phone number already registered.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            User user = new User(email, password, fullName, phone, role);
            User saved = ServiceConnector.getService().registerUser(user);
            if (saved != null) {
                JOptionPane.showMessageDialog(parent,
                        "Registration successful!\nWelcome, " + fullName + "!\nPlease login.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                new LoginForm().setVisible(true);
                parent.dispose();
            } else {
                JOptionPane.showMessageDialog(parent, "Registration failed. Please try again.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Server error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}