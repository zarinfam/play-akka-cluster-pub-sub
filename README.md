play-akka-cluster-pub-sub
=========================

In this sample i integrate a play framework app (java) with akka cluster
so that you can easily add new play node to scale your system.
when a new node added all node in the cluster log hello message,
no matter which node receives the message.

run node1:

activator -Dnode.id=1 -Dhttp.port=9000 -Dakka.remote.netty.tcp.port=2551 -Dakka.cluster.seed-nodes.0="akka.tcp://application@127.0.0.1:2551" run

run node2:

activator -Dnode.id=2 -Dhttp.port=9001 -Dakka.remote.netty.tcp.port=2552 -Dakka.cluster.seed-nodes.0="akka.tcp://application@127.0.0.1:2551" -Dakka.cluster.seed-nodes.1="akka.tcp://application@127.0.0.1:2552" run

run node3:

activator -Dnode.id=3 -Dhttp.port=9002 -Dakka.remote.netty.tcp.port=2553 -Dakka.cluster.seed-nodes.0="akka.tcp://application@127.0.0.1:2551" -Dakka.cluster.seed-nodes.1="akka.tcp://application@127.0.0.1:2552" -Dakka.cluster.seed-nodes.2="akka.tcp://application@127.0.0.1:2553" run