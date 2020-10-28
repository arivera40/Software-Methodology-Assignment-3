package MVC;

import java.util.StringTokenizer;

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    //-1: this account made before date, 0: Account made on same date, 1: this account made after date
    public int compareTo(Date date) {
        if(this.year < date.year){
            return -1;
        }else if(this.year > date.year){
            return 1;
        }else{
            if(this.month < date.month){
                return -1;
            }else if(this.month > date.month){
                return 1;
            }else{
                if(this.day < date.day){
                    return -1;
                }else if(this.day > date.day){
                    return 1;
                }else{
                    return 0;
                }
            }
        }
    }

    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    public int getYear() {  // getting year opened
        return this.year;
    }

    public int getMonth() {  // getting month opened
        return this.month;
    }

    public int getDay() {  // getting day opened
        return this.day;
    }

    public boolean isValid() {
        if(this.year < 0){
            return false;
        }else if(this.month < 1 || this.month > 12){
            return false;
        }else if(this.day < 1 || this.day > 31){
            return false;
        }else{
            switch(this.month){
                case 1: return true;	//31 days
                case 2: return ((isLeapYear(this.year)) ? day <= 29 : day <= 28);
                case 3: return true;	//31 days
                case 4: return this.day < 31;	//30 days
                case 5: return true;
                case 6: return day < 31;	//30 days
                case 7: return true;
                case 8: return true;
                case 9: return day < 31;	//30 days
                case 10: return true;
                case 11: return day < 31;	//30 days
                case 12: return true;
                default: return false; //month is more than actual months of year
            }
        }
    }

    //Wikipedia: leap year if year multiple of 4
    // (except for years evenly divisble by 100, whcih are not leap years unless evenly divisble by 400)
    public boolean isLeapYear(int year){
        if(year % 4 == 0){
            if(year % 100 == 0){
                if(year % 400 == 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return true;
            }
        }
        return false;
    }

    public String checkDate(String date){
        int count = 0;
        for(int i=0; i < date.length(); i++){
            if(date.charAt(i) == '/') count++;
        }
        if(count != 2){
            return "Input data type mismatch";
        }
        StringTokenizer tokens = new StringTokenizer(date, "/");
        int day = 0;
        int month = 0;
        int year = 0;
        for(int i=0; tokens.hasMoreTokens(); i++){
            String temp = tokens.nextToken();
            try{
                if(i == 0){
                    month = Integer.parseInt(temp);
                }else if(i == 1){
                    day = Integer.parseInt(temp);
                }else if(i == 2){
                    year = Integer.parseInt(temp);
                }else{
                    return null;	//malformed
                }
            }catch(NumberFormatException e){
                return "Input data type mismatch";
            }
        }
        if(new Date(year, month, day).isValid()){
            return "";
        }else{
            return "Invalid date";
        }

    }
}

