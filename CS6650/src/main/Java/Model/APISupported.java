package Model;

public enum APISupported {

    PostLiftRide("/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"),
    GetLiftRide( "/skiers/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"),
    GetSkierVertical(""),
    PostNewSeason(""),
    GetSkiResorts(""),
    GetSeasonsForResort("");

    private String url;

    APISupported (String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
