package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
        PlayInternal.logger().info("---------------> index -> node "
                + Play.application().configuration().getString("node.id"));

        Greeter.notifyAllNodes();

        return ok(index.render("Your new application is ready."));
    }

}
