import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Band {
  private int id;
  private String name;
  private String description;
  private String genre;

  public Band(String name, String description, String genre) {
    this.name = name;
    this.description = description;
    this.genre = genre;
  }

  public String getName() {
    return name;
  }

  public String getGenre() {
    return genre;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands ORDER BY duedate ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getDescription().equals(newBand.getDescription()) &&
      this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(name, description, genre) VALUES (:name, :description, :genre)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("description", this.description)
      .addParameter("genre", this.genre)
      .executeUpdate()
      .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands where id=:id";
      Band band = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String newDescription, String newDuedate, String newGenre) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name, description = :description, genre = :genre WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", newName)
      .addParameter("description", newDescription)
      .addParameter("genre", newGenre)
      .addParameter("id", this.id)
      .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM bands WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM venues_bands WHERE band_id = :bandId";
        con.createQuery(joinDeleteQuery)
          .addParameter("bandId", this.getId())
          .executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
      con.createQuery(sql)
      .addParameter("venue_id", venue.getId())
      .addParameter("band_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT venue_id FROM venues_bands WHERE band_id = :band_id";
      List<Integer> venueIds = con.createQuery(joinQuery)
      .addParameter("band_id", this.getId())
      .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();

      for (Integer venueId : venueIds) {
        String bandQuery = "Select * From venues WHERE id = :venueId";
        Venue venue = con.createQuery(bandQuery)
        .addParameter("venueId", venueId)
        .executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }
      return venues;
    }
  }

}
