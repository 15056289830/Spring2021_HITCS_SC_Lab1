package P3;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private List<Person> FriendList;

    public Person(String name){
        this.name = name;
        FriendList = new ArrayList<>();
    }
    public void newfriend(Person p){
        FriendList.add(p);
    }
    public String myname(){
        return this.name;
    }
    public List<Person> Freiends(){
        return FriendList;
    }
}
