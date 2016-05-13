import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getName_BandInstantiatesWithName_String() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    assertEquals("twenty one pilots", myBand.getName());
  }

  @Test
  public void getGenre_BandInstantiatesWithGenre_String() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    assertEquals("bad", myBand.getGenre());
  }

  @Test
  public void getDescription_BandInstantiatesWithDescription_String() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    assertEquals("a band", myBand.getDescription());
  }

  @Test
  public void getId_BandInstantiatesWithId_Int() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    assertEquals(1, myBand.getId());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame_true() {
    String[] array = {"1", "3"};
    Band myBand1 = new Band("twenty one pilots", "a band", "bad", array);
    String[] array2 = {"1", "3"};
    Band myBand2 = new Band("twenty one pilots", "a band", "bad", array2);
    assertTrue(myBand1.equals(myBand2));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void save_assignsIdToObject_int() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void update_UpdatesBand_true() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    Band myBandcopy = Band.find(myBand.getId());
    myBand.update("twenty two pilots", "a band", "bad", array);
    assertTrue(myBand.getName() != myBandcopy.getName());
  }

  @Test
  public void getVenues_returnsAllVenues_List() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    List savedVenues = myBand.getVenues();
    assertEquals(1, savedVenues.size());
  }

  @Test
  public void getGenre_returnsAllgenres_List() {
    String[] array = {"1", "3"};
    Band myBand1 = new Band("twenty one pilots", "a band", "Rock", array);
    myBand1.save();
    Band myBand2 = new Band("twenty one pilots", "a band", "Rap", array);
    myBand2.save();
    Band myBand3 = new Band("twenty one pilots", "a band", "Rock", array);
    myBand3.save();
    Band myBand4 = new Band("twenty one pilots", "a band", "Bad", array);
    myBand4.save();
    List savedGenres = Band.allGenre();
    assertEquals(3, savedGenres.size());
  }

  @Test
  public void delete_deletesAllBandsAndVenuesAssociations() {
    String[] array = {"1", "3"};
    Band myBand = new Band("twenty one pilots", "a band", "bad", array);
    myBand.save();
    Venue myVenue = new Venue("Moda Center", "13105", true);
    myVenue.save();
    myBand.delete();
    assertEquals(0, myVenue.getBands().size());
  }

}
