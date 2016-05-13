import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Venue {
  private int id;
  private String name;
  private String location;
  private boolean minor;

  public Venue(String name, String location, boolean minor) {
    this.name = name;
    this.location = location;
    this.minor = minor;
  }

  public String getName() {
    return name;
  }

  public boolean getMinor() {
    return minor;
  }

  public String getLocation() {
    return location;
  }

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
      this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues(name, location, minor) VALUES (:name, :location, :minor)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("location", this.location)
      .addParameter("minor", this.minor)
      .executeUpdate()
      .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where id=:id";
      Venue venue = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

//   public void delete() {
//   try(Connection con = DB.sql2o.open()) {
//     String deleteQuery = "DELETE FROM venues WHERE id = :id;";
//       con.createQuery(deleteQuery)
//         .addParameter("id", this.getId())
//         .executeUpdate();
//
//     String joinDeleteQuery = "DELETE FROM venues_bands WHERE venue_id = :venueId";
//       con.createQuery(joinDeleteQuery)
//         .addParameter("venueId", this.getId())
//         .executeUpdate();
//   }
// }

  // public void addBand(Band band) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
  //     con.createQuery(sql)
  //     .addParameter("venue_id", this.getId())
  //     .addParameter("band_id", band.getId())
  //     .executeUpdate();
  //   }
  // }


  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM venues_bands WHERE venue_id = :venue_id";
      List<Integer> bandIds = con.createQuery(joinQuery)
      .addParameter("venue_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer bandId : bandIds) {
        String bandQuery = "Select * From bands WHERE id = :bandId ORDER BY duedate ASC";
        Band band = con.createQuery(bandQuery)
        .addParameter("bandId", bandId)
        .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }

}
