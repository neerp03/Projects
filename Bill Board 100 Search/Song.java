import java.util.Comparator;

public class Song implements Comparable<Song> {

	private String url;
	private Date weekID;
	private String songName;
	private String performer;
	private String songID;
	private int instance;
	private int peakPosition;
	private int weeksOnChart;

	// default constructor
	public Song() {
	}


	 //Construct new Song object using input from Billboard100 file

	public Song(String url, Date weekID, String songName, String performer, String songID, int instance,
			int peakPosition, int weeksOnChart) {
		this.url = url;
		this.weekID = weekID;
		this.songName = songName;
		this.performer = performer;
		this.songID = songID;
		this.instance = instance;
		this.peakPosition = peakPosition;
		this.weeksOnChart = weeksOnChart;
	}

	
	public String getSongName() {
		return songName;
	}

	
	public boolean isOverWeeks(int weeks) {
		return weeksOnChart > weeks;
	}

	public boolean isInWeek(Date date) {
		return weekID.equals(date.getWeek());
	}

	
	public boolean isPerformedBy(String artist) {
		return performer.toLowerCase().equals(artist.toLowerCase());
	}

	public boolean isSongTitle(String name) {
		return songName.toLowerCase().equals(name.toLowerCase());
	}

	
	public boolean isOverInstances(int times) {
		return instance > times;
	}

	
	public boolean isAtPeakPosition() {
		return peakPosition == 1;
	}

	
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Song)) {
			return false;
		} else {
			Song s = (Song) o;
			return songID.equals(s.songID);
		}

	}

	
	public int compareTo(Song other) {
		if (this.equals(other))
			return weekID.compareTo(other.weekID);
		return songID.compareTo(other.songID);
	}

	
	public String toString() {
		return String.format("%-53s|%-12s|%-56s|%-115s|%-125s|%-10s|%-15s|%-16s", url, weekID, songName, performer,
				songID, instance, peakPosition, weeksOnChart);
	}

	
	public static Comparator<Song> byWeekID() {
		return new Comparator<Song>() {
			public int compare(Song one, Song two) {
				if (one.weekID == two.weekID)
					return one.compareTo(two);
				else
					return one.weekID.compareTo(two.weekID);
			}
		};
	}

	
	public static Comparator<Song> bySongName() {
		return new Comparator<Song>() {
			public int compare(Song one, Song two) {
				if (one.songName.equals(two.songName))
					return one.weekID.compareTo(two.weekID);
				else
					return one.songName.compareTo(two.songName);
			}
		};
	}

	
	public static Comparator<Song> byPerformer() {
		return new Comparator<Song>() {
			public int compare(Song one, Song two) {
				if (one.performer.equals(two.performer))
					if (one.songName.equals(two.songName))
						return one.weekID.compareTo(two.weekID);
					else
						return one.songName.compareTo(two.songName);
				else
					return one.performer.compareTo(two.performer);
			}
		};
	}

	
	public static Comparator<Song> byInstance() {
		return new Comparator<Song>() {
			public int compare(Song one, Song two) {
				if (one.instance == two.instance)
					return one.performer.compareTo(two.performer);
				else
					return (two.instance - one.instance);
			}
		};
	}
}
