package pl.sda.demoappjdbc.config;

public class DbConfiguration {

    private String driver;
    private String url;
    private String dbName;
    private String useSSL;
    private String timeZone;
    private String user;
    private String password;

    public DbConfiguration(String driver, String url, String dbName, String useSSL, String timeZone, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.dbName = dbName;
        this.useSSL = useSSL;
        this.timeZone = timeZone;
        this.user = user;
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getDbName() {
        return dbName;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUseSSL() {
        return useSSL;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
