package MVC;

/**
 * This is the class for Profile in which information
 * such as first and last name of account holder will be 
 * held
 * 
 * @author Andy Rivera and Joseph Shamma
 *
 */

public class Profile {
    private String fname;
    private String lname;


    Profile(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
    }
    /**
     * get first name of account holder
     * @return
     */
    public String getFirstName(){
        return fname;
    }
    /**
     * get last name of account holder
     * @return
     */
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
