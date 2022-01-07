package com.tlw.storagemanagement.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "com.teleware.postgis")
public class PostgisProperties {
    private String host;
    private String port;
    private String user;
    private String pw;
    private String database;
    private String schema;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPw() {
        return pw;
    }

    public String getDatabase() {
        return database;
    }

    public String getSchema() {
        return schema;
    }
}
