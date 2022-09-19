package pmdb.core;

public abstract class human {
    
    private String name;
    private int age;
    private char gender;


    public human(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public char getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return name;
    }


    

    

}
