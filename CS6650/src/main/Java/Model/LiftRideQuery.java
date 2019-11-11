package Model;

public class LiftRideQuery {
    private String resortID;
    private String seasonID;
    private String dayID;
    private String skierID;
    private String liftID;
    private String time;
    private String vertical;

    public LiftRideQuery(String[] urlParts, LiftRide liftRide) {
        this.resortID = urlParts[1];
        this.seasonID = urlParts[3];
        this.dayID = urlParts[5];
        this.skierID = urlParts[7];
        if (liftRide != null) {
            this.liftID = liftRide.getLiftID().toString();
            this.time = liftRide.getTime().toString();
            this.vertical = Integer.parseInt(liftID)*10+"";
        } else {
            this.liftID = null;
            this.time = null;
            this.vertical = null;
        }

    }

    public String getDayID() {
        return dayID;
    }

    public String getLiftID() {
        return liftID;
    }

    public String getResortID() {
        return resortID;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public String getSkierID() {
        return skierID;
    }

    public String getTime() {
        return time;
    }

    public String getVertical() {
        return vertical;
    }
}
