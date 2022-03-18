package com.example.sunny.moviemagazine;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by DELL on 29-05-2018.
 */

public class MovieAttributes {

    public static final String AUTHORITY = "com.example.sunny.moviemagazine";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "favourites";

    public static final class dataAttributes implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_TASKS).build();
        public static final String TABLE_NAME = "favourites";
        public static final String COLUMN_MOVIEID = "MovieId";
        public static final String COLUMN_ORG_TITLE = "OriginalTitle";
        public static final String COLUMN_ORG_IMG = "Image";
        public static final String COLUMN_ORG_RAT = "Rating";
        public static final String COLUMN_ORG_REL = "Release";

    }
}
