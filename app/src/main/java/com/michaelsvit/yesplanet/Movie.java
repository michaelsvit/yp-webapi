package com.michaelsvit.yesplanet;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.michaelsvit.yesplanet.MovieDetailsActivity.LOG_TAG;

/**
 * Created by Michael on 4/9/2017.
 * Single movie featured in Yes Planet.
 */

public class Movie implements Parcelable {

    public enum Category{
        KIDS_SHOW, MORNING_EVENTS, KIDS_CLUB, OPERA, DRAMA, THRILLER, ACTION, COMEDY, KIDS, CLASSIC
    }

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
    private List<Category> categories;

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
        this.categories = new ArrayList<>();
    }

    protected Movie(Parcel in) {
        subtitlesLanguage = in.readString();
        is3d = in.readByte() != 0;
        actors = in.readString();
        releaseTimestamp = in.readLong();
        releaseDate = in.readString();
        length = in.readInt();
        hebrewTitle = in.readString();
        englishTitle = in.readString();
        country = in.readString();
        director = in.readString();
        id = in.readString();
        ageRating = in.readString();
        youtubeTrailerId = in.readString();
        categories = in.readArrayList(null);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subtitlesLanguage);
        dest.writeByte((byte) (is3d ? 1 : 0));
        dest.writeString(actors);
        dest.writeLong(releaseTimestamp);
        dest.writeString(releaseDate);
        dest.writeInt(length);
        dest.writeString(hebrewTitle);
        dest.writeString(englishTitle);
        dest.writeString(country);
        dest.writeString(director);
        dest.writeString(id);
        dest.writeString(ageRating);
        dest.writeString(youtubeTrailerId);
        dest.writeList(categories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    /**
     * Convert category ID to type-safe enum value.
     * @param id  category ID
     * @return    enum value
     */
    public static Category getMovieCategory(int id) {
        switch (id) {
            case 32:
                return Movie.Category.KIDS_SHOW;
            case 34:
                return Movie.Category.MORNING_EVENTS;
            case 36:
                return Movie.Category.KIDS_CLUB;
            case 37:
                return Movie.Category.OPERA;
            case 10:
                return Movie.Category.DRAMA;
            case 11:
                return Movie.Category.THRILLER;
            case 12:
                return Movie.Category.ACTION;
            case 13:
                return Movie.Category.COMEDY;
            case 14:
                return Movie.Category.KIDS;
            case 31:
                return Movie.Category.CLASSIC;
            default:
                Log.e(LOG_TAG, "Unrecognized category with id: " + id);
                return null;
        }
    }
}
