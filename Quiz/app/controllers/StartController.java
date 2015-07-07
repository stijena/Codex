package controllers;

import com.google.inject.Inject;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.model.NoveltyService;
import services.model.QuestionService;
import views.html.admin_home;

/**
 * Controller that is called when user attempts to access /, top application domain (i.e. localhost:9000/).
 * It redirect him to login if no session attributes are present or to corresponding site if user is 
 * already logged in.
 * 
 * @author Luka Ruklic
 *
 */

@Transactional
public class StartController extends Controller {
	
	@Inject
	public static QuestionService questionService;
	
	@Inject
	public static NoveltyService noveltyService;
	
	public static Result redirect() {
		
		String type = session("type");
		String firstName = session("firstName");
		
		if(type == null) {
			return redirect(controllers.routes.LoginController.login());
		} else {
			switch(type) {
			case "ADMIN":
				// TODO unauthorized attempt?
				// error with session().get("clearance")
				return ok(admin_home.render(firstName, Integer.parseInt(session().get("clearance")), noveltyService.findAll()));	// TODO instead of findAll send news by type and priority
			}
		}
		
		return ok();
		
	}
	
}
