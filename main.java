public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // DNS Flush
    System.out.print("Clear DNS cache? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        SupportToolkit.clearDnsCache();
    }

    // Kill Process
    System.out.print("Kill a process? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        System.out.print("Enter process name (e.g., notepad.exe): ");
        String processName = scanner.nextLine().trim();
        SupportToolkit.killProcess(processName);
    }

    // Internet Connectivity
    System.out.print("Check internet connectivity? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        System.out.print("Enter address to ping (e.g., 8.8.8.8 or google.com): ");
        String address = scanner.nextLine().trim();
        SupportToolkit.checkInternet(address);
    }

    // Map Drives
    System.out.print("Map network drives? (y/n): ");
    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
        while (true) {
            System.out.print("Enter drive letter (e.g., Z), or type 'done' to finish: ");
            String driveLetter = scanner.nextLine().trim();
            if (driveLetter.equalsIgnoreCase("done")) break;

            System.out.print("Enter network path (e.g., \\\\server\\share): ");
            String path = scanner.nextLine().trim();
            SupportToolkit.mapNetworkDrive(driveLetter, path);


        }
    }

    System.out.println("\nAll selected tasks completed. Goodbye!");
}

