
public class ProjectOneTester {
    /* These files will be used to implement an app similar to a music platform.
     * The main purpose of creating this is to mimmick an app like Spotify or
     * Apple Music, by incorporating playlists and music sorting.
     * The superclass will be named Playlist, which will be extended by Playcount, 
     * and use Song to help with sorting the playlist. The interface being used is Comparable,
     * which the Song class will be using to compare with itself in a playlist.
     * Playcount will be used to work with playcounts of artists. 
     * Playcount will also mimmick Spotify Wrapped but in a simpler way, by returning a list of the user's top 5
     * artists. The Playlist arraylist will contain Song objects, which will be compared with each other using
     * the different methods implemented here.
     */ 
    public static void main(String[] args) {
       Playlist p = new Playlist("Main Playlist");              // Initialize the playlist 
        p.addSong("Artist1", "Song1", 1, 52);                  // Adding some songs to the playlist (using addSong)
        p.addSong("Artist2", "Song2", 2, 57);
        p.addSong("Artist3", "Song3", 4, 10);
        p.addSong("Artist4", "Song4", 1, 53);
        p.addSong("Artist5", "Song5", 2, 35);
        p.addSong("Artist4", "Song6", 3, 34);
        p.addSong("Artist6", "Song7", 3, 47);

        System.out.println("--------------------------------------------------------");

        System.out.println(p.getPlaylist());                                // Print playlist in its arraylist form
        System.out.println(p.toString());                                   // Print playlist in its toString method form
        System.out.println(p.getPlaylist().get(0).getTotalSeconds());       // Check total runtime for first song
        System.out.println(p.getPlaylist().get(1).getArtist());             // Get artist name for second song
        System.out.println(p.getPlaylist().get(2).getName());               // Get song name for third song
        p.removeSong("Song1");                                         // Testing the remove song method
        System.out.println(p.toString());
        p.changeName("main playlist");                                             // Changing the playlist name, and reprinting
        System.out.println(p.getName());

        p.addSong("artist", "song", -1, 0);             // This method call should return an error message
        
        System.out.println("--------------------------------------------------------");

        /* The sortPlaylist() method will call Collections.sort() on the playlist
         * This will also implicitly call the overridden compareTo() and equals() methods as well.
         */
        p.sortPlaylist();                                                   
                                                                            
        System.out.println(p.toString());                                       // print the sorted playlist

        System.out.println("--------------------------------------------------------");

        /* After showing the Playlist class, I will be showing the Playcount class
         * The Playcount class has 2 constructors, which are overloaded
         * Both will behave the same, but can be initialized by either a playlist name or Playlist object,
         * whichever the user prefers to use.
         */


        Playcount play = new Playcount("main playlist");           //  Create a new Playcount object, which will extend Playlist
                                                                        // This initialization is using the first constructor

        System.out.println("--------------------------------------------------------");
        play.playSong(p.getPlaylist().get(0));                          // To increment the artist counters, here are some playSong method calls
                                                                        // to play some songs on the playlist
        play.playSong(p.getPlaylist().get(1));

        play.playSong(p.getPlaylist().get(2));

        play.playSong(p.getPlaylist().get(2));

        play.playSong(p.getPlaylist().get(2));

        play.playSong(p.getPlaylist().get(3));

        play.playSong(p.getPlaylist().get(4));

        System.out.println(play.getArtistCounts());                 // Print the artist counter but in a arraylist
        
        System.out.println(play.toString());                        // The overidden Playcount toString() method call (which is a Music wrapped)

        System.out.println("--------------------------------------------------------"); 


        /* Using the same methods here, but by initializing the Playcount constructor
         * with a Playlist object, instead of a playlist name (String).
         */

        Playcount pl = new Playcount(p);                        // Initialize using the alternative constructor, with the previous Playlist object
        System.out.println(pl.getName());                            
        
        pl.addSong("Artist4", "Song8", 3, 29);           // Testing the inherited addSong method again

        pl.removeSong("Song2");                                            // Testing the removeSong method again (will show song not found)

        pl.addSong("Artist2", "Song9", 1, 36);
        pl.sortPlaylist();                                                                // Sort the playlist using the sortPlaylist method
        System.out.println(pl.getPlaylist());

        System.out.println(pl.toString());                  // Another Music wrapped, should return the error message (< 5 artists)

        pl.removeSong(1);                           // Remove song by int index instead of song name (String)
        System.out.println(pl.getPlaylist());
                                                        // end
    }
}
