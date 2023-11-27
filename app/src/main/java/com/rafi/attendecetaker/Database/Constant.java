package com.rafi.attendecetaker.Database;

public class Constant {
    public static int VERSION = 1;
    public static final String DATABASE_NAME = "Attendance.db";

    // Class Table Entry.
    public static final String CLASS_TABLE_NAME = "CLASS_TABLE";
    public static final String C_ID = "_CID";
    public static final String CLASS_NAME_KEY = "CLASS_NAME";
    public static final String SUBJECT_NAME_KEY = "SUBJECT_NAME";
    public static final String SUBJECT_TEACHER_EMAIL = "TEACHER_EMAIL";

    public static final String CREATE_CLASS_TABLE =
            "CREATE TABLE " + CLASS_TABLE_NAME + "("
                    + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    CLASS_NAME_KEY + " TEXT NOT NULL," +
                    SUBJECT_NAME_KEY + " TEXT NOT NULL," +
                    SUBJECT_TEACHER_EMAIL + " TEXT NOT NULL," +
                    "UNIQUE (" + CLASS_NAME_KEY + "," + SUBJECT_NAME_KEY + ")" +
                    ");";

    public static final String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS " + CLASS_TABLE_NAME;
    public static final String SELECT_CLASS_TABLE = "SELECT * FROM " + CLASS_TABLE_NAME + " WHERE " +SUBJECT_TEACHER_EMAIL+ " =? ";

    // Student Table Entry.
    public static final String STUDENT_TABLE_NAME = "STUDENT_TABLE";
    public static final String S_ID = "_SID";
    public static final String STUDENT_NAME_KEY = "STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY = "ROLL";

    public static final String CREATE_STUDENT_TABLE =
            "CREATE TABLE " + STUDENT_TABLE_NAME + "("
                    + S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    C_ID + " INTEGER NOT NULL," +
                    STUDENT_NAME_KEY + " TEXT NOT NULL," +
                    STUDENT_ROLL_KEY + " INTEGER," +
                    " FOREIGN KEY (" + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "(" + C_ID + ")" +
                    ");";
    public static final String DROP_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME;
    public static final String SELECT_STUDENT_TABLE = "SELECT * FROM " + STUDENT_TABLE_NAME;

    //Status Table Entry.
    public static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "STATUS_ID";
    public static final String DATE_KEY = "STATUS_DATE";
    public static final String STATUS_KEY = "STATUS";

    public static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME + "("
                    + STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    S_ID + " INTEGER NOT NULL," +
                    C_ID + " INTEGER NOT NULL," +
                    DATE_KEY + " DATE NOT NULL," +
                    STATUS_KEY + " TEXT NOT NULL," +
                    " UNIQUE (" + S_ID + "," + DATE_KEY + ")," +
                    " FOREIGN KEY (" + S_ID + ") REFERENCES " + STUDENT_TABLE_NAME + "(" + S_ID + ")," +
                    " FOREIGN KEY (" + C_ID + ") REFERENCES " + CLASS_TABLE_NAME + "(" + C_ID + ")" +
                    ");";
    public static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " + STATUS_TABLE_NAME;
    public static final String SELECT_STATUS_TABLE = "SELECT * FROM " + STATUS_TABLE_NAME;


    // Teacher Registration Table Entry.
    public static final String TEACHER_REGISTRATION_TABLE_NAME = "TEACHER_REGISTRATION";
    public static final String TEACHER_NAME = "T_NAME";
    public static final String TEACHER_NUMBER = "T_NUMBER";
    public static final String TEACHER_EMAIL = "T_EMAIL";
    public static final String TEACHER_PASSWORD = "T_PASSWORD";
    public static final String TEACHER_IMAGE = "T_IMAGE";


    public static final String CREATE_TEACHER_REGISTRATION_TABLE =
            "CREATE TABLE " + TEACHER_REGISTRATION_TABLE_NAME + "("
                    + TEACHER_NAME + " TEXT NOT NULL," +
                    TEACHER_NUMBER + " INTEGER NOT NULL," +
                    TEACHER_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                    TEACHER_PASSWORD + " TEXT NOT NULL ,"+
                    TEACHER_IMAGE + " BLOB);";
    public static final String DROP_TEACHER_REGISTRATION_TABLE = "DROP TABLE IF EXISTS" + TEACHER_REGISTRATION_TABLE_NAME;
    public static final String SELECT_TEACHER_REGISTRATION_TABLE = "SELECT * FROM " + TEACHER_REGISTRATION_TABLE_NAME;

    // Admin Registration Table Entry.
    public static final String ADMIN_REGISTRATION_TABLE_NAME = "ADMIN_REGISTRATION";
    public static final String ADMIN_NAME = "A_NAME";
    public static final String ADMIN_NUMBER = "A_NUMBER";
    public static final String ADMIN_EMAIL = "A_EMAIL";
    public static final String ADMIN_PASSWORD = "A_PASSWORD";
    public static final String ADMIN_IMAGE = "A_IMAGE";


    public static final String CREATE_ADMIN_REGISTRATION_TABLE =
            "CREATE TABLE " + ADMIN_REGISTRATION_TABLE_NAME + "("
                    + ADMIN_NAME + " TEXT NOT NULL," +
                    ADMIN_NUMBER + " INTEGER NOT NULL," +
                    ADMIN_EMAIL + " TEXT PRIMARY KEY NOT NULL," +
                    ADMIN_PASSWORD + " TEXT NOT NULL ,"+
                    ADMIN_IMAGE + " BLOB);";
    public static final String DROP_ADMIN_REGISTRATION_TABLE = "DROP TABLE IF EXISTS" + ADMIN_REGISTRATION_TABLE_NAME;
    // public static final String SELECT_TEACHER_REGISTRATION_TABLE = "SELECT * FROM " + TEACHER_REGISTRATION_TABLE_NAME;

    //Status Table Entry.
    public static final String FEEDBACK_TABLE_NAME = "FEEDBACK_TABLE";
    public static final String FEEDBACK_EMAIL = "EMAIL_ID";
    public static final String FEEDBACK_OCCUPATION = "_OCCUPATION";
    public static final String FEEDBACK_TEXT = "_FEEDBACK";
    public static final String FEEDBACK_STATUS = "FEEDBACK_STATUS";

    public static final String CREATE_FEEDBACK_TABLE =
            "CREATE TABLE " + FEEDBACK_TABLE_NAME + "("
                    + FEEDBACK_EMAIL + " TEXT NOT NULL," +
                    FEEDBACK_OCCUPATION + " TEXT NOT NULL," +
                    FEEDBACK_TEXT + " TEXT NOT NULL," +
                    FEEDBACK_STATUS + " INTEGER NOT NULL," +
                    " FOREIGN KEY (" + FEEDBACK_EMAIL + ") REFERENCES " + TEACHER_REGISTRATION_TABLE_NAME + "(" + TEACHER_EMAIL + ")" +
                    ");";
    public static final String DROP_FEEDBACK_TABLE = "DROP TABLE IF EXISTS " + FEEDBACK_TABLE_NAME;
    public static final String SELECT_FEEDBACK_TABLE = "SELECT * FROM " + FEEDBACK_TABLE_NAME;
}