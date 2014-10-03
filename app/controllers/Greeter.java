package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import play.Play;
import play.libs.Akka;

public class Greeter extends UntypedActor {

    static ActorRef defaultRoom = Akka.system().actorOf(Props.create(Greeter.class));

    Cluster cluster = Cluster.get(getContext().system());

    public static enum Msg {
        GREET, DONE;
    }

    public static void notifyAllNodes(){

    }

    //subscribe to cluster changes
    @Override
    public void preStart() {
        //#subscribe
//        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
//                ClusterEvent.MemberEvent.class, UnreachableMember.class);
        //#subscribe
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
//        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        if (msg == Msg.GREET) {
            System.out.println("Hello from node " + Play.application().configuration().getString("node.id"));
//      getSender().tell(Msg.DONE, getSelf());
        } else
            unhandled(msg);
    }

}
