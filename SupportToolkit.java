import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SupportToolkit {

    // Method to clear the DNS cache
    public static void clearDnsCache() {
        try {
            Process process;
            process = Runtime.getRuntime().exec("ipconfig /flushdns");
            process.waitFor();
            System.out.println("DNS cache cleared.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to clear DNS cache: " + e.getMessage());
        }
    }

    // Method to kill a process by its name
    public static void killProcess(String processName) {
        try {
            Process process = Runtime.getRuntime().exec("taskkill /f /im " + processName);
            process.waitFor();
            System.out.println("Process '" + processName + "' terminated.");
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to kill process: " + e.getMessage());
        }
    }

    // Method to check internet connectivity by pinging a specified address
    public static void checkInternet(String host) {
        try {
            Process process = Runtime.getRuntime().exec("ping -n 1 " + host);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean success = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Reply from")) {
                    success = true;
                    break;
                }
            }
            process.waitFor();
            if (success) {
                System.out.println("Internet is working (ping to " + host + " succeeded).");
            } else {
                System.out.println("Ping to " + host + " failed.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to ping " + host + ": " + e.getMessage());
        }
    }

    // Method to map a network drive
    public static void mapNetworkDrive(String driveLetter, String networkPath) {
        try {
            String command = String.format("net use %s: %s", driveLetter, networkPath);
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println("Drive mapped: " + driveLetter + ": -> " + networkPath);
        } catch (IOException | InterruptedException e) {
            System.out.println("Failed to map network drive: " + e.getMessage());
        }
    }


    // Main method that interacts with the user
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Clear DNS Cache
        System.out.print("Clear DNS cache? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            clearDnsCache();
        }

        // Kill Process
        System.out.print("Kill a process? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter process name (e.g., notepad.exe): ");
            String processName = scanner.nextLine().trim();
            killProcess(processName);
        }

        // Check Internet Connectivity
        System.out.print("Check internet connectivity? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter address to ping (e.g., 8.8.8.8 or google.com): ");
            String address = scanner.nextLine().trim();
            checkInternet(address);
        }

        // Map Network Drives
        System.out.print("Map network drives? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            while (true) {
                System.out.print("Enter drive letter (e.g., Z), or type 'done' to finish: ");
                String driveLetter = scanner.nextLine().trim();
                if (driveLetter.equalsIgnoreCase("done")) break;

                System.out.print("Enter network path (e.g., \\\\server\\share): ");
                String path = scanner.nextLine().trim();
                mapNetworkDrive(driveLetter, path);
            }
        }

        System.out.println("\nAll selected tasks completed. Goodbye!");
    }
}
