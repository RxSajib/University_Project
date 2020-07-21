package All_PogoClass;

public class MessageiteamsList {

    private String Date, Message, Time, Type, From;

    private MessageiteamsList(){

    }


    public MessageiteamsList(String date, String message, String time, String type, String from) {
        Date = date;
        Message = message;
        Time = time;
        Type = type;
        From = from;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }
}
