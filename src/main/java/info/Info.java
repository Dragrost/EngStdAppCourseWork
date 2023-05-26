package info;

public class Info
{
    private String ID;
    private String data1;
    private String data2;

    public Info(String id, String data1, String data2)
    {
        this.ID = id;
        this.data1 = data1;
        this.data2 = data2;
    }
    public String getID() {
        return ID;
    }
    public String getData1() {
        return data1;
    }
    public String getData2() {
        return data2;
    }
}
