import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SupportToolkitGUI extends JFrame {
    JCheckBox dnsCheck;
    JTextField processField, pingField, driveLetterField, networkPathField;
    JTextArea logArea;

    public SupportToolkitGUI() {
        setTitle("Support Toolkit");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new BackgroundPanel()); // Set custom panel with background
        setLayout(new BorderLayout());

        // Panel for inputs
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setOpaque(false);

        dnsCheck = new JCheckBox("Clear DNS Cache");
        dnsCheck.setOpaque(false);

        panel.add(new JLabel("Kill Process (e.g., notepad.exe):"));
        processField = new JTextField();
        panel.add(processField);

        panel.add(new JLabel("Ping Address (e.g., 8.8.8.8):"));
        pingField = new JTextField();
        panel.add(pingField);

        panel.add(new JLabel("Drive Letter (e.g., Z):"));
        driveLetterField = new JTextField();
        panel.add(driveLetterField);

        panel.add(new JLabel("Network Path (e.g., \\\\server\\share):"));
        networkPathField = new JTextField();
        panel.add(networkPathField);

        panel.add(dnsCheck);

        JButton runButton = new JButton("Run Selected Tasks");
        panel.add(runButton);

        add(panel, BorderLayout.NORTH);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);

        runButton.addActionListener(e -> runTasks());
    }

    private void runTasks() {
        logArea.setText(""); // clear output

        if (dnsCheck.isSelected()) {
            runCommand("ipconfig /flushdns", "DNS cache cleared.");
        }

        String process = processField.getText().trim();
        if (!process.isEmpty()) {
            runCommand("taskkill /f /im " + process, "Tried to kill " + process);
        }

        String ping = pingField.getText().trim();
        if (!ping.isEmpty()) {
            runCommand("ping -n 1 " + ping, "Pinged " + ping);
        }

        String driveLetter = driveLetterField.getText().trim();
        String path = networkPathField.getText().trim();
        if (!driveLetter.isEmpty() && !path.isEmpty()) {
            runCommand("net use " + driveLetter + ": " + path, "Mapped drive " + driveLetter + ": to " + path);
        }

        log("All selected tasks attempted.");
    }

    private void runCommand(String command, String successMessage) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log(line);
            }
            process.waitFor();
            log(successMessage + "\n");
        } catch (Exception e) {
            log("Error running command: " + e.getMessage() + "\n");
        }
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupportToolkitGUI().setVisible(true));
    }

    // Custom background panel
    class BackgroundPanel extends JPanel {
        private Image background;

        public BackgroundPanel() {
            try {
                background = ImageIO.read(new File("C:/Users/Yvesf/OneDrive/Desktop/dc-logo.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            setLayout(new BorderLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            // Draw background image
            if (background != null) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.07f));
                int x = (getWidth() - background.getWidth(null)) / 2;
                int y = (getHeight() - background.getHeight(null)) / 2;
                g2d.drawImage(background, x, y, this);
            }

            // Reset alpha for text
            g2d.setComposite(AlphaComposite.SrcOver);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 12));
            g2d.setColor(Color.DARK_GRAY);

            // Bottom-left text
            g2d.drawString("Dentalcorp 2025", 10, getHeight() - 10);

            // Bottom-right text
            String credit = "Created by Yves Ferland";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(credit);
            g2d.drawString(credit, getWidth() - textWidth - 10, getHeight() - 10);

            g2d.dispose();
        }
    }
}
