import org.junit.rules.ExternalResource;
import org.sql2o.*;
import spark.Spark;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBandsQuery = "DROP TABLE bands;";
      String deleteVenuesQuery = "DROP TABLE venues;";
      String deleteVenuesBandsQuery = "DROP TABLE venues_bands;";
      String createBandsQuery = "CREATE TABLE bands (id serial PRIMARY KEY, name varchar, description varchar, genre varchar);";
      String createVenuesQuery = "CREATE TABLE venues (id serial PRIMARY KEY, name varchar, location varchar, minor boolean);";
      String createVenuesBandsQuery = " CREATE TABLE venues_bands (id serial PRIMARY KEY, venue_id int, band_id int);";
      con.createQuery(deleteBandsQuery).executeUpdate();
      con.createQuery(deleteVenuesQuery).executeUpdate();
      con.createQuery(deleteVenuesBandsQuery).executeUpdate();
      con.createQuery(createBandsQuery).executeUpdate();
      con.createQuery(createVenuesQuery).executeUpdate();
      con.createQuery(createVenuesBandsQuery).executeUpdate();
    }
  }

}
