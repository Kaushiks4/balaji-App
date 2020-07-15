package my.app.vehicle;

public class DailyDetails {
    public String vehicleNumber,description,mechanicName,mPhoto,time,uploadedBy,driverName;
    DailyDetails(){}
    public DailyDetails(String vnum,String desc,String mname,String mp,String t,String name,String dname){
        vehicleNumber = vnum;
        description = desc;
        mechanicName = mname;
        mPhoto = mp;
        time = t;
        uploadedBy = name;
        driverName = dname;
    }
}
