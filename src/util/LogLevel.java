package util;


public enum LogLevel {
    SUCCESS(50, "Success"), ERROR(40, "Error"), WARNING(30, "Warning"), INFO(20, "Info"), VERBOSE(10, "Verbose"),
    DEBUG(0, "Debug");

    private int level;
    private String title;

    LogLevel(int level, String title) {
        this.level = level;
        this.title = title;
    }

    public int getLevel() {
        return this.level;
    }

    public String getTitle() {
        return this.title;
    }
}
