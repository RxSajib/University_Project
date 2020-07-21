package All_PogoClass;

public class UsersList {

    private String Age , BloodGroup, Campus, Depertment, Gender, Health, Height, Imageuri, Username, Weight, Year_Of_Student;


    public UsersList(){

    }

    public UsersList(String age, String bloodGroup, String campus, String depertment, String gender, String health, String height, String imageuri, String username, String weight, String year_Of_Student) {
        Age = age;
        BloodGroup = bloodGroup;
        Campus = campus;
        Depertment = depertment;
        Gender = gender;
        Health = health;
        Height = height;
        Imageuri = imageuri;
        Username = username;
        Weight = weight;
        Year_Of_Student = year_Of_Student;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getDepertment() {
        return Depertment;
    }

    public void setDepertment(String depertment) {
        Depertment = depertment;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHealth() {
        return Health;
    }

    public void setHealth(String health) {
        Health = health;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getImageuri() {
        return Imageuri;
    }

    public void setImageuri(String imageuri) {
        Imageuri = imageuri;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getYear_Of_Student() {
        return Year_Of_Student;
    }

    public void setYear_Of_Student(String year_Of_Student) {
        Year_Of_Student = year_Of_Student;
    }
}
