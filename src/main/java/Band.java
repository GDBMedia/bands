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
  private List<Integer> checkedVenues = new ArrayList<Integer>();
  private String genre;

  public Band(String name, String description, String genre, String[] stringVenues) {
    this.name = name;
    this.description = description;
    this.genre = genre;
    List<Integer> intVenues = new ArrayList<Integer>();
     for(String stringtag : stringVenues){
       intVenues.add(Integer.parseInt(stringtag));
     }
    this.checkedVenues = intVenues;
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
    String sql = "SELECT * FROM bands ORDER BY genre ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }
  public static List<String> allGenre() {
    String sql = "SELECT DISTINCT genre FROM bands ORDER BY genre ASC";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(String.class);
    }
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
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

      for (Integer checkedvenue : this.checkedVenues) {
       String joinvenues_bandsTableAdd = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
       con.createQuery(joinvenues_bandsTableAdd)
       .addParameter("venue_id", checkedvenue)
       .addParameter("band_id", this.getId())
       .executeUpdate();
     }
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

  public void update(String newName, String newDescription, String newGenre, String[] stringVenues) {
    List<Integer> intVenues = new ArrayList<Integer>();
     for(String stringtag : stringVenues){
       intVenues.add(Integer.parseInt(stringtag));
     }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name, description = :description, genre = :genre WHERE id = :id";
      con.createQuery(sql)
      .addParameter("name", newName)
      .addParameter("description", newDescription)
      .addParameter("genre", newGenre)
      .addParameter("id", this.id)
      .executeUpdate();

      String joinDeleteQuery = "DELETE FROM venues_bands WHERE band_id = :bandId";
        con.createQuery(joinDeleteQuery)
          .addParameter("bandId", this.getId())
          .executeUpdate();

      for (Integer checkedvenue : intVenues) {
       String joinvenues_bandsTableAdd = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
       con.createQuery(joinvenues_bandsTableAdd)
       .addParameter("venue_id", checkedvenue)
       .addParameter("band_id", this.getId())
       .executeUpdate();
     }

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

  public List<Venue> getVenues() {
  try(Connection con = DB.sql2o.open()){
    String joinQuery = "SELECT venues.* FROM bands JOIN venues_bands ON (bands.id = venues_bands.band_id) JOIN venues ON (venues_bands.venue_id = venues.id) WHERE bands.id = :band_id ORDER BY venues.name ASC";
    List<Venue> venues = con.createQuery(joinQuery)
    .addParameter("band_id", this.getId())
    .executeAndFetch(Venue.class);
    return venues;
  }
}

}
