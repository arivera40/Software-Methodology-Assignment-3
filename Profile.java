package MVC;

public class Profile {
    private String fname;
    private String lname;


    Profile(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }

    public String getFirstName(){
        return fname;
    }

    public String getLastName(){
        return lname;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile profile = (Profile) obj;
            if(profile.fname.equals(this.fname) && profile.lname.equals(this.lname)){
                return true;
            }
        }
        return false;
    }
}

