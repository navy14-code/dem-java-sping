//package edu.poly.demo_api.core;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//@Component
//public class DBConnection {
//
//    @Autowired
//    private final HikariDataSource dataSource;
//
//    public DBConnection(DataSource dataSource) {
//        if (!(dataSource instanceof HikariDataSource)) {
//            throw new IllegalStateException("DataSource khong phai HikariCP");
//        }
//        this.dataSource = (HikariDataSource) dataSource;
//    }
//
//    public Connection getConnection() throws SQLException {
//        return dataSource.getConnection();
//    }
//
//}
