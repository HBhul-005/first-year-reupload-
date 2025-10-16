import java.util.ArrayList;
import java.util.Collections;
public class Playlist  {
    private String playlistName; //  Private instance variable for the playlist name

    private ArrayList<Song> list = new ArrayList<>(); // private instance variable for the playlist (arraylist)

    public Playlist(String playlistName){   // Playlist constructor (playlist name as parameter)
        this.playlistName = playlistName;
    }

    public String getName(){        // Getter method to return playlist name
        return playlistName;
    }

    public void changeName(String s){       // Setter method to change playlist name after initialization
        playlistName = s;
    }

    public void addSong(String artist, String name, int minutes, int seconds ){ 
        // Method to add songs to the playlist

        if(seconds > 59 || seconds < 0 || minutes < 0){
            System.out.println("Invalid song runtime"); // Error message if seconds input is invalid
        }else{

            list.add(new Song(artist, name, minutes, seconds));
            // This statement will add a string representation of the song into the playlist
        }
    }
    
    public void removeSong(int num){                            // Method to remove songs from the playlist (by order in playlist)
        if(num < 1 || num > list.size() - 1){                            // Will check from number 1, which will be index 0 in the arraylist
            System.out.println("Song number is invalid");
        }else{
        list.remove(num-1); }
    }

    public void removeSong(String s){           // Overloaded removeSong, this method will remove by song name
        boolean r = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(s)) { // Find song name in the formatted string
                list.remove(i);
                System.out.println("Successfully removed: " + s);
                r = true;
                break;                  // Break after finding the song to remove
            }
        }
        if (!r) {
            System.out.println("Song not found: " + s);     // Error message in case song is not found
            }     
    }

    public void sortPlaylist(){
        Collections.sort(list);         // This sort method will call the Song compareTo method, which will also call .equals() from Song
    }

    public ArrayList<Song> getPlaylist(){
        return list;    // Getter method to return the playlist (useful for subclasses)
    }

    @Override
    public String toString(){
        int count = 1; // counter for order in playlist
        String res = " <| " + playlistName + " |> " + '\n' + "---------------------";

        for(int i = 0; i < list.size();i++){
            res += '\n';
            res += Integer.toString(count++) + ". " + list.get(i);
        }
        return res;
    }

    
}
//---------------------------------------------------------------------------------------------------------------------------/

class Song implements Comparable<Song>{         // A smaller class that will be used as objects in a Playlist arraylist

    private String artist;
    private String name;
    private int minutes;                        // private instance variables for Song characteristics
    private int seconds;
    private int runtime;

    public Song(String artist, String name, int minutes, int seconds){
        this.artist = artist;                                               
        this.name = name;
        this.minutes=  minutes;
        this.seconds = seconds;
        this.runtime = (minutes * 60) + seconds; // full runtime in seconds
    }

     /* This compare method will sort the playlist by song length (in ascending order)
     * if same length, then compare by song name (alphabetical order)
     * else, return 1 or -1 based on which song length is greater
     */

    @Override
    public int compareTo(Song o) {      // This method will compare by song length, by converting into seconds first
        if (this.equals(o)){
            return this.name.toLowerCase().compareTo(o.name.toLowerCase());     // Compare name strings in alphabetical order (case insensitive)
        }else if(this.runtime > o.runtime){
            return 1;
        }else{
            return -1;
        }
    }

    @Override
    public boolean equals(Object o){        // Used to compare 2 song objects
        if (this == o)      // Check if same object
        return true;
        if (!(o instanceof Song))          // Check if o is an instance of Song
        return false;

        Song s = (Song) o;              // Downcast Object to a Song
        return this.runtime == s.runtime;
    }
    
    @Override
    public String toString(){       // Return string representation of a Song
        return (artist + " - " + name + " (" + Integer.toString(minutes) + ":" + Integer.toString(seconds) +
            ") ");      
    }
    public int getTotalSeconds(){       // Return the song runtime in seconds
        return runtime;
    }
    public String getArtist() {         // Return the artist name
        return artist;
    }

    public String getName() {           // Return the song name
        return name;
    }
    
}
