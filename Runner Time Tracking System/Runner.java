package Package1;

public class Runner implements Comparable<Runner> {
    private String name;
    private String country;
    private Time startTime;
    private Time endTime;
    private Time raceTime;

    public Runner(String name, String country, Time startTime, Time endTime) {
        this.name = name;
        this.country = country;
        this.startTime = startTime;
        this.endTime = endTime;
        this.raceTime = endTime.minus(startTime);
    }

   
    public Time raceTime() {
        return raceTime;
    }

    public String toString() {
        return name + " (" + country + ")\nstart: " + startTime + ", end: " + endTime + "\ntime: " + raceTime;
    }

    public int compareTo(Runner other) {
        return raceTime.compareTo(other.raceTime);
    }
}

