package com.example.automation2.util;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/*** Created: 8/12/2021 --- 7:48 PM --- ALPHANULL ***/
// Prepare JDBC connection for connecting to MySQL database
// Properties are all set in (dbMy) file in dir properties

@Component("utilJDBC")
public class DatabaseJDBC {

    private static Connection _Connection;

    @NonNull
    public static Connection getConnection() {
        if(_Connection != null) {
            return _Connection;
        } else {
            try {
                Properties properties = new Properties();
                InputStream stream = DatabaseJDBC.class.getClassLoader()
                        .getResourceAsStream("properties/dbMy.properties");
                properties.load(stream);
                Map<String,String> map = new HashMap<>();
                map.put("driver",properties.getProperty("driver"));
                map.put("url"   ,properties.getProperty("url" ));
                map.put("user"  ,properties.getProperty("user"));
                map.put("pass"  ,properties.getProperty("pass"));
                Class.forName(map.get("driver").trim());
                _Connection = DriverManager.getConnection
                        (map.get("url"),map.get("user"),map.get("pass"));
            } catch(SQLException | IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        } return _Connection;
    }

    @SuppressWarnings("unused")
    public static void setConnection(final Connection _Connection) {
        DatabaseJDBC._Connection = _Connection;
    }
}


