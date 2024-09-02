package orbisoftware.aquarius.dis_sim_map;

public class OSUtils {

    // Method to check if the operating system is Windows
    public static boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("win");
    }

    // Method to check if the operating system is Unix-like (Linux, macOS)
    public static boolean isUnix() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("nix") || osName.contains("nux") || osName.contains("mac");
    }
}