import java.util.ArrayList;
import java.util.HashMap;

public class Playcount extends Playlist  {
   
    private HashMap<String, Integer> artistPlayCount;           // private instance variables for Playcount
    private ArrayList<String> topArtists;

    /* Here are the 2 constructors for the Playcount class.
     * They are overloaded, meaning they take in 2 different parameters.
     * The first constructor will initialize the instance variables, and take in the playlist name
     * The second constructor will initialize the instance variables, and take in a Playlist object
     * The user has the option to choose whichever one.
     */

    public Playcount(String playListName){                  
        super(playListName);
        artistPlayCount = new HashMap<>();
        topArtists = new ArrayList<>();
    }

    public Playcount(Playlist playlist) {
        super(playlist.getName());  
        artistPlayCount = new HashMap<>();
        topArtists = new ArrayList<>();
    }
    /* This method will play songs in the Playcount class
     * It will use a HashMap to count plays by artist
     */

    public void playSong(Song s) {      
    
        artistPlayCount.put(s.getArtist(), artistPlayCount.getOrDefault(s.getArtist(), 0) + 1); // Add counter to map, by value

        System.out.println("Playing: " + s);        // Print playing message
    }

    /* This method will use a sorting algorithm to sort 
     * the map by values and keys, in descending order. This is used for the overidden toString() method.
     * The artists will only be sorted by playcount.
     */

    public ArrayList<String> getArtistCounts(){                     // return the artist counts in descending order           
        topArtists = new ArrayList<>(artistPlayCount.keySet());
        
        for (int i = 0; i < topArtists.size(); i++) {
            for (int j = i + 1; j < topArtists.size(); j++) {
                if (artistPlayCount.get(topArtists.get(i)) < artistPlayCount.get(topArtists.get(j))) {
                    // The keys will swap
                    String temp = topArtists.get(i);
                    topArtists.set(i, topArtists.get(j));
                    topArtists.set(j, temp);
                }
            }
        }

        return topArtists;
    }


/* This toString() method will be overidden to mimmick a Spotify Wrapped interface, but in a simpler way
 * The top 5 artists will be displayed
 * If less than 5 artists are available, an error message will be printed.
 * It will first call the getArtistCounts method to return the sorted arraylist of artists.
 */

    @Override                                             
    public String toString(){

        topArtists = getArtistCounts();
        if(topArtists.size() < 5)
        return "Not enough artists (< 5)";


        return "      Your Music Wrapped: " + super.getName() + '\n'
            +   "           Top 5 Artists           " + '\n' 
            +   "1. " + topArtists.get(0)  + '\n'
            +   "2. " + topArtists.get(1)   + '\n'
            +   "3. " + topArtists.get(2) + '\n'
            +   "4. " + topArtists.get(3) + '\n'
            +   "5. " + topArtists.get(4);
    }

}


