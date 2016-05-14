import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void BandIsCreatedTest() {
    Venue myVenue1 = new Venue("Moda Center", "13105", true);
    myVenue1.save();
    Venue myVenue2 = new Venue("River Rock", "13105", true);
    myVenue2.save();
    Venue myVenue3 = new Venue("Lounge", "13105", true);
    myVenue3.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a Band"));
    fill("#name").with("twenty one pilots");
    fill("#description").with("a band");
    fill("#genre").with("bad");
    click("label", withText("Moda Center"));
    click("label", withText("Lounge"));
    submit(".btn");
    assertThat(pageSource()).contains("Moda Center");
    assertThat(pageSource()).contains("Lounge");
    assertThat(pageSource()).contains("twenty one pilots");
  }

  @Test
  public void BandIsCreatedAndViewableTest() {
    Venue myVenue1 = new Venue("Moda Center", "13105", true);
    myVenue1.save();
    Venue myVenue2 = new Venue("River Rock", "13105", true);
    myVenue2.save();
    Venue myVenue3 = new Venue("Lounge", "13105", true);
    myVenue3.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a Band"));
    fill("#name").with("twenty one pilots");
    fill("#description").with("a band");
    fill("#genre").with("bad");
    click("label", withText("Moda Center"));
    click("label", withText("Lounge"));
    submit(".btn");
    goTo("http://localhost:4567/");
    click("a", withText("View Bands"));
    assertThat(pageSource()).contains("twenty one pilots");
    assertThat(pageSource()).contains("bad");
  }

  @Test
  public void venueIsCreated() {
    goTo("http://localhost:4567/");
    click("a", withText("View Venues"));
    fill("#name").with("Moda Center");
    fill("#location").with("13105");
    click("option", withText("Yes"));
    submit(".btn");
    assertThat(pageSource()).contains("Moda Center");
    assertThat(pageSource()).contains("Yes");
  }

  @Test
  public void bandIsCreatedandAddedToVenue() {
    goTo("http://localhost:4567/");
    click("a", withText("View Venues"));
    fill("#name").with("Moda Center");
    fill("#location").with("13105");
    click("option", withText("Yes"));
    submit(".btn");
    click("a", withText("Home"));
    click("a", withText("Add a Band"));
    fill("#name").with("twenty one pilots");
    fill("#description").with("a band");
    fill("#genre").with("bad");
    click("label", withText("Moda Center"));
    submit(".btn");
    click("a", withText("Home"));
    click("a", withText("View Venues"));
    click("a", withText("Moda Center"));
    assertThat(pageSource()).contains("Moda Center");
    assertThat(pageSource()).contains("twenty one pilots");
  }

  @Test
  public void BandIsEditedAndViewableTest() {
    Venue myVenue1 = new Venue("Moda Center", "13105", true);
    myVenue1.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a Band"));
    fill("#name").with("twenty one pilots");
    fill("#description").with("a band");
    fill("#genre").with("bad");
    click("label", withText("Moda Center"));
    submit(".btn");
    submit(".btn", withText("Edit"));
    fill("#name").with("twenty two pilots");
    submit(".btn");
    assertThat(pageSource()).contains("twenty two pilots");
    assertThat(pageSource()).contains("bad");
  }

  @Test
  public void BandIsDeletedTest() {
    Venue myVenue1 = new Venue("Moda Center", "13105", true);
    myVenue1.save();
    goTo("http://localhost:4567/");
    click("a", withText("Add a Band"));
    fill("#name").with("twenty one pilots");
    fill("#description").with("a band");
    fill("#genre").with("bad");
    click("label", withText("Moda Center"));
    submit(".btn");
    assertEquals(1, Band.all().size());
    submit(".btn", withText("Delete"));
    assertEquals(0, Band.all().size());
  }
}
