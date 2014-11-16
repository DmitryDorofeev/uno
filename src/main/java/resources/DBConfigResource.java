package resources;

/**
 * Created by alexey on 16.11.2014.
 */
public class DBConfigResource implements Resource {
    private String dialect;
    private String driver;
    private String url;
    private String username;
    private String password;
    private String show_sql;
    private String hbm2ddl;

    public DBConfigResource() {
    }

    public DBConfigResource(String dialect, String driver, String url,
                     String username, String password, String show_sql, String hbm2ddl) {
        setDialect(dialect);
        setDriver(driver);
        setUrl(url);
        setUsername(username);
        setPassword(password);
        setShow_sql(show_sql);
        setHbm2ddl(hbm2ddl);
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShow_sql() {
        return show_sql;
    }

    public void setShow_sql(String show_sql) {
        this.show_sql = show_sql;
    }

    public String getHbm2ddl() {
        return hbm2ddl;
    }

    public void setHbm2ddl(String hbm2ddl) {
        this.hbm2ddl = hbm2ddl;
    }
}
