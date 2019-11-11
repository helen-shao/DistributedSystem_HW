package Database;

import org.apache.commons.dbcp2.*;

public class DataSource {
  private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
  // local database ==============================================================
  //  private static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/SkiResortApplication?useTimezone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
//  private static final String DB_USER = "root";
//  private static final String DB_PWD = "3142578";
  // My RDS database ==============================================================
  private static final String DB_CONNECTION_URL = "jdbc:mysql://mycs6650.cau9xiw8ltnp.us-west-2.rds.amazonaws.com:3306/SkiResortApplication";
  private static final String DB_USER = "admin";
  private static final String DB_PWD = "3142578Syx#";
  // Yun RDS database ==============================================================
//  private static final String DB_USER = "bsds_yw";  // "root"
//  private static final String DB_PWD = "12345678";  // "1234"
//  private static final String DB_CONNECTION_URL = "jdbc:mysql://rds-bsds.czvijbc39hml.us-west-2.rds.amazonaws.com:3306/SkiResorts?useTimezone=true&serverTimezone=UTC&autoReconnect=true";

  private static DataSource ds;
  private BasicDataSource basicDS = new BasicDataSource();

  //private constructor
  private DataSource(){
    basicDS.setDriverClassName(DRIVER_CLASS);
    basicDS.setUrl(DB_CONNECTION_URL);
    basicDS.setUsername(DB_USER);
    basicDS.setPassword(DB_PWD);
//    basicDS.setConnectionProperties("ssl=true");
//    basicDS.setConnectionProperties("serverTimezone=UTC");
    // Parameters for connection pooling
    basicDS.setInitialSize(10);
    basicDS.setMaxTotal(10);
//    basicDS.setMaxOpenPreparedStatements(100);
  }

  /**
   *static method for getting instance.
   */
  public static DataSource getInstance(){
    if(ds == null){
      ds = new DataSource();
    }
    return ds;
  }

  public BasicDataSource getBasicDS() {
    return basicDS;
  }

  public void setBasicDS(BasicDataSource dataSource) {
    this.basicDS = basicDS;
  }
}