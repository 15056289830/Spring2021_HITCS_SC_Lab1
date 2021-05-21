package P3;

import java.util.*;

public class FriendshipGraph {
    public List<Person>crow = new ArrayList<Person>();
    public Set<String>names = new HashSet<String>();
    public void addVertex(Person a){
        if(names.contains(a.myname())){
            System.out.println("已存在名为"+a.myname()+"的人。");
            System.exit(0);
        }
        crow.add(a);
        names.add(a.myname());
    }
    public void addEdge(Person p, Person f){
        if(p.Freiends().contains(f)){
            System.out.println(f.myname()+"已经是"+p.myname()+"的朋友。");
            System.exit(0);
        }
        p.newfriend(f);
    }
    public int getDistance(Person p1, Person p2){
        if(p1==p2){
            return 0;
        }
        Queue<Person>queue = new LinkedList<>();
        queue.offer(p1);
        Map<Person,Integer>graph = new HashMap<>();
        graph.put(p1,0);
        while (!queue.isEmpty()){
            Person per = queue.poll();
            List<Person>Friends = per.Freiends();
            for(Person e: Friends){
                if(!graph.containsKey(e)){
                    queue.offer(e);
                    graph.put(e,graph.get(per)+1);
                    if(e==p2){
                        return graph.get(p2);
                    }
                }
            }
        }
        return -1;
    }
    public static void main(String argv[]){
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
//should print 1
        System.out.println(graph.getDistance(rachel, ben));
//should print 2
        System.out.println(graph.getDistance(rachel, rachel));
//should print 0
        System.out.println(graph.getDistance(rachel, kramer));
//should print -1
    }
}
