import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;


public class Billboard100 {

	private SortedArrayCollection<Song> songs;
	private String heading = String.format("%-53s|%-12s|%-56s|%-115s|%-125s|%-10s|%-15s|%-16s", "URL", "WeekID",
			"Song Title", "Performer", "SongID", "Instance", "Peak Position", "Weeks on Chart");

	
	public Billboard100(Scanner input) {
		songs = new SortedArrayCollection<>();
		loadSongs(input);
	}

	
	 private void loadSongs(Scanner input) {
	        input.nextLine();
	        while (input.hasNext()) {
	            songs.add(buildSongFromData(input.nextLine()));
	        }
	    }
	 private Song buildSongFromData(String songLine) {
	        String[] songInfo = songLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	        return new Song(
	            songInfo[0], new Date(songInfo[1]), songInfo[2], songInfo[3], 
	            songInfo[4], Integer.parseInt(songInfo[5]), 
	            Integer.parseInt(songInfo[6]), Integer.parseInt(songInfo[7])
	        );
	    }

	 public void displayAllSongs(FileWriter writer) throws IOException {
		 generateReportHeader(writer, "All Billboard Hot 100 Songs Between January 2018 and May 2021");
		 processAndWriteSongs(writer, song -> true);
	    }


	
	 public void displaySongsOver12Weeks(FileWriter writer) throws IOException {
		 generateReportHeader(writer, "Billboard Hot 100 Songs on Chart over 12 Weeks Between January 2018 and May 2021");
		 processAndWriteSongs(writer, song -> song.isOverWeeks(12));
	    }


	  public void displaySongsInWeek(FileWriter writer, Date userDate) throws IOException {
	        String title = "Billboard Hot 100 Songs on Chart week of " + userDate;
	        generateReportHeader(writer, title);
	        processAndWriteSongs(writer, song -> song.isInWeek(userDate));
	    }

	
	  public void displaySongsByPerformer(FileWriter writer, String performer) throws IOException {
	        String title = "Billboard Hot 100 Songs on Chart for " + performer;
	        generateReportHeader(writer, title);
	        processAndWriteSongs(writer, song -> song.isPerformedBy(performer));
	    }

	  public void displaySongsByName(FileWriter writer, String songName) throws IOException {
	        String title = "Billboard Hot 100 Songs on Chart for " + songName;
	        generateReportHeader(writer, title);
	        processAndWriteSongs(writer, song -> song.isSongTitle(songName));
	    }


	  public void displayMultipleInstances(FileWriter writer) throws IOException {
		  generateReportHeader(writer, "Multiple Instances of Billboard Hot 100 Songs on Chart Between January 2018 and May 2021 (From most to least)");
	        SortedArrayCollection<Song> byInstance = new SortedArrayCollection<>(Song.byInstance());
	        populateFilteredSongsToCollection(song -> song.isOverInstances(1), byInstance);
	        processAndWriteSongs(writer, byInstance, song -> song.isOverInstances(1));
	    }

		  public void displayTopChartSongs(FileWriter writer) throws IOException {
			  generateReportHeader(writer, "Billboard Hot 100 Songs on Chart that reached Peak Position Between January 2018 and May 2021 (Multiple Instances Removed)");
	        SortedArrayCollection<String> list = new SortedArrayCollection<>();
	        processAndWriteSongs(writer, song -> song.isAtPeakPosition() && !list.contains(song.getSongName()));
	    }

	
	 
	  public void orderSongsByWeekID(FileWriter writer) throws IOException {
		  generateReportHeader(writer, "Billboard Hot 100 Songs on Chart Between January 2018 and May 2021 By WeekID");
	        SortedArrayCollection<Song> byWeekID = new SortedArrayCollection<>(Song.byWeekID());
	        populateAllSongsToCollection(byWeekID);
	        processAndWriteSongs(writer, byWeekID, song -> true);
	    }
	
	  public void orderSongsAlphabeticallyByName(FileWriter writer) throws IOException {
		  generateReportHeader(writer, "Billboard Hot 100 Songs on Chart Between January 2018 and May 2021 By Song Name");
	        SortedArrayCollection<Song> bySongName = new SortedArrayCollection<>(Song.bySongName());
	        populateAllSongsToCollection(bySongName);
	        processAndWriteSongs(writer, bySongName, song -> true);
	    }
	
	 //Print all songs in alphabetical order by Performer Name.
	
	    public void orderSongsAlphabeticallyByPerformer(FileWriter writer) throws IOException {
	    	generateReportHeader(writer, "Billboard Hot 100 Songs on Chart Between January 2018 and May 2021 By Performer");
	        SortedArrayCollection<Song> byPerformer = new SortedArrayCollection<>(Song.byPerformer());
	        populateAllSongsToCollection(byPerformer);
	        processAndWriteSongs(writer, byPerformer, song -> true);
	    }
	 private void generateReportHeader(FileWriter writer, String title) throws IOException {
	        writer.write("----- " + title + " -----\n\n");
	        writer.write(heading + "\n");
	        writer.flush();
	    }
	 private void processAndWriteSongs(FileWriter writer, SongPredicate predicate) throws IOException {
	        for (Song song : songs) {
	            if (predicate.test(song)) {
	                writer.write(song.toString() + "\n");
	            }
	        }
	        writer.flush();
	    }
	 interface SongPredicate {
	        boolean test(Song song);
	    }
	 // Helper method
	    private void populateAllSongsToCollection(SortedArrayCollection<Song> collection) {
	        for (Song song : songs) {
	            collection.add(song);
	        }
	    }
	 // Helper method
	    private void populateFilteredSongsToCollection(SongPredicate predicate, SortedArrayCollection<Song> collection) {
	        for (Song song : songs) {
	            if (predicate.test(song)) {
	                collection.add(song);
	            }
	        }
	    }

	    // Overloaded method to iterate and write songs from a specific collection
	    private void processAndWriteSongs(FileWriter writer, SortedArrayCollection<Song> collection, SongPredicate predicate) throws IOException {
	        for (Song song : collection) {
	            if (predicate.test(song)) {
	                writer.write(song.toString() + "\n");
	            }
	        }
	        writer.flush();
	    }
	 
}