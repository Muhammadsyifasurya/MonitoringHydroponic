package id.mentoring.monitoringhidroponik.model;

public class MahasiswaResponse {
    private String nama;
    private String matkul;
    private String key;
    String TdsMinVal;

    public String getTdsMinVal() {
        return TdsMinVal;
    }

    public void setTdsMinVal(String tdsMinVal) {
        TdsMinVal = tdsMinVal;
    }

    public String getTdsMaxVal() {
        return TdsMaxVal;
    }

    public void setTdsMaxVal(String tdsMaxVal) {
        TdsMaxVal = tdsMaxVal;
    }

    public String getPhMinVal() {
        return PhMinVal;
    }

    public void setPhMinVal(String phMinVal) {
        PhMinVal = phMinVal;
    }

    public String getPhMaxValue() {
        return PhMaxValue;
    }

    public void setPhMaxValue(String phMaxValue) {
        PhMaxValue = phMaxValue;
    }

    public String getMinuteNoonVal() {
        return MinuteNoonVal;
    }

    public void setMinuteNoonVal(String minuteNoonVal) {
        MinuteNoonVal = minuteNoonVal;
    }

    public String getMinuteMorVal() {
        return MinuteMorVal;
    }

    public void setMinuteMorVal(String minuteMorVal) {
        MinuteMorVal = minuteMorVal;
    }

    public String getHoursNoonVal() {
        return HoursNoonVal;
    }

    public void setHoursNoonVal(String hoursNoonVal) {
        HoursNoonVal = hoursNoonVal;
    }

    public String getHoursMorVal() {
        return HoursMorVal;
    }

    public void setHoursMorVal(String hoursMorVal) {
        HoursMorVal = hoursMorVal;
    }

    String TdsMaxVal;
    String PhMinVal;
    String PhMaxValue;
    String MinuteNoonVal;
    String MinuteMorVal;
    String HoursNoonVal;
    String HoursMorVal;

    public  MahasiswaResponse(){

    }

    public MahasiswaResponse(String nama, String matkul) {
        this.nama = nama;
        this.matkul = matkul;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMatkul() {
        return matkul;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
