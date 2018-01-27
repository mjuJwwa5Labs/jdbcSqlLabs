package pl.sda.demoappjdbc.config;

public class DbConfiguration {

    private String driver;
    private String url;
    private String dbName;
    private String user;
    private String password;
    private String useSSL;

    public DbConfiguration(String driver, String url, String dbName, String user, String password, String useSSL) {
        this.driver = driver;
        this.url = url;
        this.dbName = dbName;
        this.user = user;
        this.password = password;
        this.useSSL = useSSL;
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

    public String getUseSSL() {return useSSL; }
}
