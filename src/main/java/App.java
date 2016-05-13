import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("genres", Band.allGenre());
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("venue", venue);
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/edit/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("venues", Venue.all());
      model.put("band", band);
      model.put("template", "templates/edit-band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // get("/venues/:id", (request,response) ->{
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Venue category = Venue.find(Integer.parseInt(request.params("id")));
    //   model.put("category", category);
    //   model.put("allBands", Band.all());
    //   model.put("template", "templates/category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // get("/venues/edit/:id", (request,response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   Band band = Band.find(Integer.parseInt(request.params("id")));
    //   model.put("band", band);
    //   model.put("template", "templates/edit-category.vtl");
    //   return new ModelAndView(model, layout);
    // }, new VelocityTemplateEngine());
    //
    // post("/bands", (request, response) -> {
    //   HashMap<String, Object> model = new HashMap<String, Object>();
    //   String description = request.queryParams("description");
    //   String duedate = request.queryParams("duedate");
    //   Band newBand = new Band(description, duedate);
    //   newBand.save();
    //   response.redirect("/bands");
    //   return null;
    // });
    //
    post("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String location = request.queryParams("location");
      boolean minor = Boolean.parseBoolean(request.queryParams("minor"));
      Venue newVenue = new Venue(name, location, minor);
      newVenue.save();
      response.redirect("/venues");
      return null;
    });

    post("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      String genre = request.queryParams("genre");
      String[] checkedVenues = request.queryParamsValues("venues");
      Band newBand = new Band(name, description, genre, checkedVenues);
      newBand.save();
      response.redirect("/bands/" + Integer.toString(newBand.getId()));
      return null;
    });

    post("delete/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band;
      int n = Integer.parseInt(request.queryParams("type"));
      String id = request.queryParams("id");
      switch(n){
        case 1:
          band = Band.find(Integer.parseInt(request.params("id")));
          band.delete();
          response.redirect("/bands");
          break;
        case 2:
          band = Band.find(Integer.parseInt(request.params("id")));
          band.delete();
          response.redirect("/venues/" + id);
          break;
        // case 3:
        //   Venue category = Venue.find(Integer.parseInt(request.params("id")));
        //   category.delete();
        //   response.redirect("/venues");
        //   break;
        default:
          response.redirect("/");
          break;
      }

      return null;
    });

    // get("/:", (request, response) -> {
    //   response.redirect("/venues");
    //   return null;
    // });

    post("/band-edit/:id", (request, response) -> {
      String name = request.queryParams("name");
      String description = request.queryParams("description");
      String genre = request.queryParams("genre");
      String[] checkedVenues = request.queryParamsValues("venues");
      Band band = Band.find(Integer.parseInt(request.params("id")));
      band.update(name, description, genre, checkedVenues);
      response.redirect("/bands/" + request.params("id"));
      return null;
    });

    // post("/add_venues", (request, response) -> {
    //   int bandId = Integer.parseInt(request.queryParams("band_id"));
    //   int categoryId = Integer.parseInt(request.queryParams("category_id"));
    //   Venue category = Venue.find(categoryId);
    //   Band band = Band.find(bandId);
    //   band.addVenue(category);
    //   response.redirect("/bands/" + bandId);
    //   return null;
    // });

  }
}
