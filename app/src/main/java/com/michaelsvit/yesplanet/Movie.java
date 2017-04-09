package com.michaelsvit.yesplanet;

/**
 * Created by Michael on 4/9/2017.
 * Single movie featured in Yes Planet.
 */

public class Movie {
    private String subtitlesLanguage;  // "Hebrew"
    private boolean is3d;              // false
    private String actorsList;         // "Actor1, Actor2" - can be empty
    private long releaseTimestamp;     // 1487196000
    private String releaseYear;        // "2017"
    private int length;                // 122
    private String hebrewName;         // "Name"
    private String englishName;        // "Name"
    private String country;            // "US"
    private String director;           // "Director" - can be empty
    private String id;                 // "2012S2R"
    private String ageRating;          // "Restricted to age 16+"
    private String youtubeTrailerId;   // "zQbuwG5R85k"

    public Movie(
            String subtitlesLanguage,
            boolean is3d, String actorsList,
            long releaseTimestamp,
            String releaseYear,
            int length,
            String hebrewName,
            String englishName,
            String country,
            String director,
            String id,
            String ageRating,
            String youtubeTrailerId) {
        this.subtitlesLanguage = subtitlesLanguage;
        this.is3d = is3d;
        this.actorsList = actorsList;
        this.releaseTimestamp = releaseTimestamp;
        this.releaseYear = releaseYear;
        this.length = length;
        this.hebrewName = hebrewName;
        this.englishName = englishName;
        this.country = country;
        this.director = director;
        this.id = id;
        this.ageRating = ageRating;
        this.youtubeTrailerId = youtubeTrailerId;
    }
}
