package P3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void newfriend() {
        Person Rose = new Person("Rose");
        Person Jay = new Person("Jay");
        Rose.newfriend(Jay);
        assertEquals(Jay,Rose.Freiends().get(0));
    }

    @Test
    void myname() {
        Person Rose = new Person("Rose");
        assertEquals("Rose",Rose.myname());
    }

    @Test
    void freiends() {
        Person Rose = new Person("Rose");
        Person Jay = new Person("Jay");
        Person Joe = new Person("Joe");
        Rose.newfriend(Jay);
        assertEquals(Jay,Rose.Freiends().get(Rose.Freiends().size()-1));
        Rose.newfriend(Joe);
        assertEquals(Joe,Rose.Freiends().get(Rose.Freiends().size()-1));
    }
}