package com.kaleab.tenaadam.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kaleab on 5/22/2018.
 */

public final class MotherContract {
    public static final String path_mother_profile = "mother_profile";
    public static final Uri BASE_CONTENT_URI = Uri.parse("com.kaleab.tenaadam");

    public static abstract class Mother implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, path_mother_profile);//"com.kaleab.tenaadam";

        public static final String TABLE_NAME = "mother";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_WEEKS_PREGNANT = "weeks_pregnant";
        public static final String COLUMN_PASS_CODE = "pass_code";
        public static final String COLUMN_NICKNAME = "baby_nick_name";
        public static final String COLUMN_SECRETE_QUESTION = "secrete_question";
        public static final String COLUMN_SECRETE_ANSWER = "secrete_answer";

        /*
        *
        * TODO the secrete question has to have an pre_defined integer value
        * */
    }
    public static abstract class Note implements BaseColumns {
        public static final String TABLE_NAME = "note";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_NOTE_CONTENT = "note_content";
        public static final String COLUMN_DATE_INSERTED = "date_inserted";
    }
    public static abstract class BabyStatus implements BaseColumns{
        public static final String TABLE_NAME = "baby_status";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_WEEK_NUMBER = "week_number";
        public static final String COLUMN_HAND_PICTURE_LOCATION = "hand_pic_location";
        public static final String COLUMN_FEET_PICTURE_LOCATION = "feet_pic_location";
        public static final String COLUMN_DESCRIPTION = "description";
    }
    public static abstract class Picture implements BaseColumns {
        public static final String TABLE_NAME = "picture";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_PICTURE_LOCATION = "picture_location";
        public static final String COLUMN_CAPTION = "caption";
        public static final String COLUMN_DATE_TAKEN = "date_taken";
    }
    public static abstract class DailyTip implements BaseColumns {
        public static final String TABLE_NAME = "daily_tips";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DAY_NUMBER = "day_number";
        public static final String COLUMN_TIP = "tip";
    }
    public static abstract class WeeklyTip implements BaseColumns {
        public static final String TABLE_NAME = "weekly_tip";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_WEEK_NUMBER = "week_number";
        public static final String COLUMN_WEEKLY_INFO = "weekly_info";
    }
    public static abstract class Reminder implements BaseColumns {

        public static final String TABLE_NAME = "reminder";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "";
        public static final String COLUMN_TIME_TO_REMIND = "";
        public static final String COLUMN_DATE_TO_REMIND = "";
        public static final String COLUMN_TYPE = "";
        public static final String COLUMN_DESCRIPTION = "";
        public static final String COLUMN_START_TIME = "";
        public static final String COLUMN_TIME_INTERVAL = "";
    }
}
