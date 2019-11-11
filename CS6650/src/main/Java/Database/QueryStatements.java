package Database;

import Model.LiftRideQuery;
import java.sql.*;
import java.util.List;

public class QueryStatements {

  private static final String LIFTRIDE_TABLE = "LiftRides";
  private static final String INSERT_QUERY_LIFTRIDE = "INSERT IGNORE INTO " + LIFTRIDE_TABLE
          + " (resortID, seasonID, dayID, SkierID, liftID, time, vertical)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";

  private static final String SKIER_VERTICAL_TABLE = "SkierVerticals";
  private static final String INSERT_QUERY_SKIER_VERTICAL = "INSERT IGNORE INTO " + SKIER_VERTICAL_TABLE
          + " (resortID, seasonID, dayID, SkierID, vertical)" + " VALUES (?, ?, ?, ?, ?)"
          + " ON DUPLICATE KEY UPDATE vertical=" + "VALUES(vertical) + vertical";

  private static final String GET_QUERY_SUM_VERTICAL = "SELECT SUM(vertical) FROM " + LIFTRIDE_TABLE
          + "WHERE resortID=?" + " AND seasonID=?" + " AND dayID=?" + " AND skierID=?";

  private static void prepareStatementPrimaryKey(PreparedStatement preparedStatement, LiftRideQuery liftRideQuery) throws SQLException {
    preparedStatement.setString(1, liftRideQuery.getResortID());
    preparedStatement.setString(2, liftRideQuery.getSeasonID());
    preparedStatement.setString(3, liftRideQuery.getDayID());
    preparedStatement.setString(4, liftRideQuery.getSkierID());
  }
  private static void prepareStatementSetLiftRide(PreparedStatement preparedStatement, LiftRideQuery liftRideQuery) throws SQLException {
    preparedStatement.setString(5, liftRideQuery.getLiftID());
  }
  private static void closeConnection(Connection connection) throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
  private static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
    if (preparedStatement != null) {
      preparedStatement.close();
    }
  }
  public static void insertIntoLiftRides(LiftRideQuery liftRideQuery)
      throws SQLException {
    Connection connection = DataSource.getInstance().getBasicDS().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_LIFTRIDE);
    prepareStatementPrimaryKey(preparedStatement, liftRideQuery);
    prepareStatementSetLiftRide(preparedStatement, liftRideQuery);
    preparedStatement.setString(6, liftRideQuery.getTime());
    preparedStatement.setString(7, liftRideQuery.getVertical());
    preparedStatement.execute();
    closePreparedStatement(preparedStatement);
    closeConnection(connection);
  }
  public static void insertIntoSkierVerticals(LiftRideQuery liftRideQuery)
      throws SQLException {
    // If record exists in SkierVerticals table, update the total vertical. Otherwise insert new record.
    Connection connection = DataSource.getInstance().getBasicDS().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_SKIER_VERTICAL);
    prepareStatementPrimaryKey(preparedStatement, liftRideQuery);
    prepareStatementSetLiftRide(preparedStatement, liftRideQuery);
    preparedStatement.execute();
  }

  public static ResultSet getSumOfVerticals(LiftRideQuery liftRideQuery)
      throws SQLException {
    Connection connection = DataSource.getInstance().getBasicDS().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(GET_QUERY_SUM_VERTICAL);
    prepareStatementPrimaryKey(preparedStatement, liftRideQuery);
    ResultSet resultSet = preparedStatement.executeQuery();
    closePreparedStatement(preparedStatement);
    closeConnection(connection);
    return resultSet;
  }

  public static void batchPostLiftRides(List<LiftRideQuery> liftRideQueries) throws SQLException {
    Connection connection = DataSource.getInstance().getBasicDS().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_LIFTRIDE);
    for (LiftRideQuery liftRideQuery: liftRideQueries) {
      prepareStatementPrimaryKey(preparedStatement, liftRideQuery);
      prepareStatementSetLiftRide(preparedStatement, liftRideQuery);
      preparedStatement.setString(6, liftRideQuery.getTime());
      preparedStatement.setString(7, liftRideQuery.getVertical());
      preparedStatement.addBatch();
    }
    preparedStatement.executeBatch();
    closePreparedStatement(preparedStatement);
    closeConnection(connection);
  }

  public static void batchPostSkierVerticals(List<LiftRideQuery> liftRideQueries) throws SQLException {
    Connection connection = DataSource.getInstance().getBasicDS().getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_SKIER_VERTICAL);
    for (LiftRideQuery liftRideQuery: liftRideQueries) {
      prepareStatementPrimaryKey(preparedStatement, liftRideQuery);
      prepareStatementSetLiftRide(preparedStatement, liftRideQuery);
      preparedStatement.addBatch();
    }
    preparedStatement.executeBatch();
    closePreparedStatement(preparedStatement);
    closeConnection(connection);
  }
}
