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

    get("/bands/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band task = Band.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("allCategories", Venue.all());
      model.put("template", "templates/task.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/tasks/edit/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band task = Band.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("template", "templates/edit-task.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue category = Venue.find(Integer.parseInt(request.params("id")));
      model.put("category", category);
      model.put("allBands", Band.all());
      model.put("template", "templates/category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/edit/:id", (request,response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band task = Band.find(Integer.parseInt(request.params("id")));
      model.put("task", task);
      model.put("template", "templates/edit-category.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String description = request.queryParams("description");
      String duedate = request.queryParams("duedate");
      Band newBand = new Band(description, duedate);
      newBand.save();
      response.redirect("/tasks");
      return null;
    });

    post("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Venue newVenue = new Venue(name);
      newVenue.save();
      response.redirect("/venues");
      return null;
    });

    post("delete/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band task;
      int n = Integer.parseInt(request.queryParams("type"));
      String id = request.queryParams("id");
      switch(n){
        case 1:
          task = Band.find(Integer.parseInt(request.params("id")));
          task.delete();
          response.redirect("/tasks");
          break;
        case 2:
          task = Band.find(Integer.parseInt(request.params("id")));
          task.delete();
          response.redirect("/venues/" + id);
          break;
        case 3:
          Venue category = Venue.find(Integer.parseInt(request.params("id")));
          category.delete();
          response.redirect("/venues");
          break;
        default:
          response.redirect("/");
          break;
      }

      return null;
    });

    post("complete/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int n = Integer.parseInt(request.queryParams("type"));
      String id = request.queryParams("id");
      Band task = Band.find(Integer.parseInt(request.params("id")));
      task.complete();
      switch(n){
        case 1:
          response.redirect("/tasks");
          break;
        case 2:
          response.redirect("/venues/" + id);
          break;
        case 3:
          response.redirect("/tasks/" + request.params("id"));
        default:
          response.redirect("/");
          break;
      }
      return null;
    });

    post("/add_tasks", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      Venue category = Venue.find(categoryId);
      Band task = Band.find(taskId);
      category.addBand(task);
      response.redirect("/venues/" + categoryId);
      return null;
    });

    post("/tasks-update/:id", (request, response) -> {
      String description = request.queryParams("description");
      String duedate = request.queryParams("duedate");
      Band task = Band.find(Integer.parseInt(request.params("id")));
      task.update(description, duedate);
      response.redirect("/tasks");
      return null;
    });

    post("/add_venues", (request, response) -> {
      int taskId = Integer.parseInt(request.queryParams("task_id"));
      int categoryId = Integer.parseInt(request.queryParams("category_id"));
      Venue category = Venue.find(categoryId);
      Band task = Band.find(taskId);
      task.addVenue(category);
      response.redirect("/tasks/" + taskId);
      return null;
    });

  }
}
