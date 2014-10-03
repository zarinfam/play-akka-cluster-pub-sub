package controllers;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import play.Play;
import play.PlayInternal;
import play.libs.Akka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Greeter extends UntypedActor {

    public static ActorRef actorRef = Akka.system().actorOf(Props.create(Greeter.class),"Greeter");

    Cluster cluster = Cluster.get(getContext().system());
    List<Member> clusterMemberList = new ArrayList<Member>();

    public static enum Msg implements Serializable{
        GREET, DONE, NOTIFY_ALL;
    }

    public static void notifyAllNodes(){
        PlayInternal.logger().info("---------------> notifyAllNodes");
        actorRef.tell(Msg.NOTIFY_ALL, null);
    }

    //subscribe to cluster changes
    @Override
    public void preStart() {
        //#subscribe
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
                ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
        //#subscribe
    }

    //re-subscribe when restart
    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }

    @Override
    public void onReceive(Object message) {
        if (message == Msg.GREET) {
            PlayInternal.logger().info("---------------> Hello from node " + Play.application().configuration().getString("node.id"));
//      getSender().tell(Msg.DONE, getSelf());
        }else if (message == Msg.NOTIFY_ALL){
            PlayInternal.logger().info("---------------> start notify");
            for(Member member: clusterMemberList){
                PlayInternal.logger().info("---------------> notify = "+member.address());
                getContext().actorSelection(member.address()+"/user/Greeter").tell(
                        Greeter.Msg.GREET, getSelf());

//                member.tell(Greeter.Msg.GREET, null);

            }
        }
        else if (message instanceof ClusterEvent.MemberUp) {
            ClusterEvent.MemberUp mUp = (ClusterEvent.MemberUp) message;
            PlayInternal.logger().info("---------------> Member is Up: {}", mUp.member());
            clusterMemberList.add(mUp.member());
        } else if (message instanceof ClusterEvent.UnreachableMember) {
            ClusterEvent.UnreachableMember mUnreachable = (ClusterEvent.UnreachableMember) message;
            PlayInternal.logger().info("---------------> Member detected as unreachable: {}", mUnreachable.member());
        } else if (message instanceof ClusterEvent.MemberRemoved) {
            ClusterEvent.MemberRemoved mRemoved = (ClusterEvent.MemberRemoved) message;
            PlayInternal.logger().info("---------------> Member is Removed: {}", mRemoved.member());
            clusterMemberList.remove(mRemoved.member());
        } else if (message instanceof ClusterEvent.MemberEvent) {
            // ignore

        }else
            unhandled(message);
    }

}
