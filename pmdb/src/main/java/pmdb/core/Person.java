package pmdb.core;

public class Person extends human {

    private String email;
    private String password;
    private char gender;
    private static int id;

    public Person(String name, int age, char gender, String email, String password) {
        super(name, age, gender);
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setId(int id){
        this.id = id;
    }
    public static int getId() {
        return id;
    }
    

    
    
    
}
