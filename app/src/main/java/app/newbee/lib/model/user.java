package app.newbee.lib.model;

/**
 * Created by cao_ruixiang on 15/8/14.
 */
public class User {
    private String name;
    private String age;
    public String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
