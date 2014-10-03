import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import controllers.Greeter;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        Greeter.actorRef = Akka.system().actorOf(Props.create(Greeter.class),"Greeter");
    }

    @Override
    public void onStop(Application app) {
    }
}
