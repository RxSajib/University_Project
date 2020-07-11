package All_PogoClass;

public class Requestgetset {

   private String bloodgroup;
   private String hospital_name;
   private String location;
   private String pasent_name;
   private String pasent_number;
   private String unitof_blood;


    public Requestgetset(){

    }

    public Requestgetset(String bloodgroup, String hospital_name, String location, String pasent_name, String pasent_number, String unitof_blood) {
        this.bloodgroup = bloodgroup;
        this.hospital_name = hospital_name;
        this.location = location;
        this.pasent_name = pasent_name;
        this.pasent_number = pasent_number;
        this.unitof_blood = unitof_blood;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPasent_name() {
        return pasent_name;
    }

    public void setPasent_name(String pasent_name) {
        this.pasent_name = pasent_name;
    }

    public String getPasent_number() {
        return pasent_number;
    }

    public void setPasent_number(String pasent_number) {
        this.pasent_number = pasent_number;
    }

    public String getUnitof_blood() {
        return unitof_blood;
    }

    public void setUnitof_blood(String unitof_blood) {
        this.unitof_blood = unitof_blood;
    }
}
