import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getName_VenueInstantiatesWithName_String() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    assertEquals("Moda Center", myVenue.getName());
  }

  @Test
  public void getLocation_VenueInstantiatesWithLocation_String() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    assertEquals("13105", myVenue.getLocation());
  }

  @Test
  public void getMinor_VenueInstantiatesWithMinor_Bool() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    assertEquals(true, myVenue.getMinor());
  }

  @Test
  public void getId_VenueInstantiatesWithId_Int() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    assertEquals(1, myVenue.getId());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    Venue myVenue1 = new Venue("Moda Center", "13105", true);
    Venue myVenue2 = new Venue("Moda Center", "13105", true);
    assertTrue(myVenue1.equals(myVenue2));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findVenueInDatabase_true() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getBands_returnsAllBands_List() {
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }
}
