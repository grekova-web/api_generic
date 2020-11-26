import java.io.Serializable;

class Person implements Serializable {

    public Object firstName;
    public Object lastName;
    public Object sex;
    public String city;
    public Object university;

    Person(Object n, Object l, Object s, String c, Object u){

        firstName=n;
        lastName=l;
        sex=s;
        city=c;
        university=u;
    }
}