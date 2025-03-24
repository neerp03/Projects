package Package1;

public class Time implements Comparable<Time> {
    private int hour;
    private int minutes;
    private double seconds;

    public Time() {
        this.hour = 0;
        this.minutes = 0;
        this.seconds = 0.0;
    }

    public Time(int hour, int minutes, double seconds) {
        this.hour = hour;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String toString() {
        return String.format("%02d:%02d:%06.3f", hour, minutes, seconds);
    }

    public boolean equals(Time other) {
        return hour == other.hour && minutes == other.minutes && seconds == other.seconds;
    }

    public int compareTo(Time other) {
        if (hour != other.hour) {
            return hour - other.hour;
        }
        if (minutes != other.minutes) {
            return minutes - other.minutes;
        }
        return Double.compare(seconds, other.seconds);
    }

    public Time minus(Time other) {
        int dHours = hour - other.hour;
        int dMinutes = minutes - other.minutes;
        double dSeconds = seconds - other.seconds;

        if (dSeconds < 0) {
            dSeconds += 60;
            dMinutes--;
        }

        if (dMinutes < 0) {
            dMinutes += 60;
            dHours--;
        }

        return new Time(dHours, dMinutes, dSeconds);
    }
}
