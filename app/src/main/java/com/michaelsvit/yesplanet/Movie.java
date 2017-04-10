package com.michaelsvit.yesplanet;

/**
 * Created by Michael on 4/9/2017.
 * Single movie featured in Yes Planet.
 */

public class Movie {
    private String subtitlesLanguage;  // "Hebrew"
    private boolean is3d;              // false
    private String actors;             // "Actor1, Actor2" - can be empty
    private long releaseTimestamp;     // 1487196000
    private String releaseDate;        // "Apr 1, 1999"
    private int length;                // 122
    private String hebrewTitle;        // "Name"
    private String englishTitle;       // "Name"
    private String country;            // "US"
    private String director;           // "Director" - can be empty
    private String id;                 // "2012S2R"
    private String ageRating;          // "Restricted to age 16+"
    private String youtubeTrailerId;   // "zQbuwG5R85k"

    public Movie(
            String subtitlesLanguage,
            boolean is3d, String actors,
            long releaseTimestamp,
            String releaseDate,
            int length,
            String hebrewTitle,
            String englishTitle,
            String country,
            String director,
            String id,
            String ageRating,
            String youtubeTrailerId) {
        this.subtitlesLanguage = subtitlesLanguage;
        this.is3d = is3d;
        this.actors = actors;
        this.releaseTimestamp = releaseTimestamp;
        this.releaseDate = releaseDate;
        this.length = length;
        this.hebrewTitle = hebrewTitle;
        this.englishTitle = englishTitle;
        this.country = country;
        this.director = director;
        this.id = id;
        this.ageRating = ageRating;
        this.youtubeTrailerId = youtubeTrailerId;
    }

    public String getSubtitlesLanguage() {
        return subtitlesLanguage;
    }

    public boolean is3d() {
        return is3d;
    }

    public String getActors() {
        return actors;
    }

    public long getReleaseTimestamp() {
        return releaseTimestamp;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getLength() {
        return length;
    }

    public String getHebrewTitle() {
        return hebrewTitle;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public String getCountry() {
        return country;
    }

    public String getDirector() {
        return director;
    }

    public String getId() {
        return id;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getYoutubeTrailerId() {
        return youtubeTrailerId;
    }
}
