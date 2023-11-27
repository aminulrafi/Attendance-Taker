package com.rafi.attendecetaker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.rafi.attendecetaker.FeedbackItems;
import com.rafi.attendecetaker.TeacherItems;

import java.io.ByteArrayOutputStream;

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] imageByte;

    public DbHelper(@Nullable Context context) {
        super(context, Constant.DATABASE_NAME, null, Constant.VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constant.CREATE_CLASS_TABLE);
        db.execSQL(Constant.CREATE_STUDENT_TABLE);
        db.execSQL(Constant.CREATE_STATUS_TABLE);
        db.execSQL(Constant.CREATE_TEACHER_REGISTRATION_TABLE);
        db.execSQL(Constant.CREATE_ADMIN_REGISTRATION_TABLE);
        db.execSQL(Constant.CREATE_FEEDBACK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(Constant.DROP_CLASS_TABLE);
            db.execSQL(Constant.DROP_STUDENT_TABLE);
            db.execSQL(Constant.DROP_STATUS_TABLE);
            db.execSQL(Constant.DROP_TEACHER_REGISTRATION_TABLE);
            db.execSQL(Constant.DROP_ADMIN_REGISTRATION_TABLE);
            db.execSQL(Constant.DROP_FEEDBACK_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // This is for add the data into the database.
    public long addClass(String className, String subjectName, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.CLASS_NAME_KEY, className);
        values.put(Constant.SUBJECT_NAME_KEY, subjectName);
        values.put(Constant.SUBJECT_TEACHER_EMAIL,email);

        // insert mathod return long value that is row id .
        return sqLiteDatabase.insert(Constant.CLASS_TABLE_NAME, null, values);
    }

    // This method is for the get the class data from the database.
    public Cursor getClassTable(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = " SELECT * FROM "+Constant.CLASS_TABLE_NAME + " WHERE "+Constant.SUBJECT_TEACHER_EMAIL+ " =? ";
        return database.rawQuery(Constant.SELECT_CLASS_TABLE, new String[]{email});
    }

    // This method is for the delete the data from database.
    public int deleteClass(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        // This is use for the delete the data from the database.
        return database.delete(Constant.CLASS_TABLE_NAME, Constant.C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    // This method is for the update the database from the database.
    public long updateClass(long cid, String className, String subjectName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.CLASS_NAME_KEY, className);
        values.put(Constant.SUBJECT_NAME_KEY, subjectName);

        // insert mathod return long value that is row id .
        return sqLiteDatabase.update(Constant.CLASS_TABLE_NAME, values, Constant.C_ID + "=?", new String[]{String.valueOf(cid)});
    }

    // This method is used for the add the student to the Student Table.
    public long addStudent(long cid, int roll, String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.C_ID, cid);
        values.put(Constant.STUDENT_ROLL_KEY, roll);
        values.put(Constant.STUDENT_NAME_KEY, name);
        return database.insert(Constant.STUDENT_TABLE_NAME, null, values);
    }

    // This method is use for the take the data from the Student table.
    public Cursor getStudentTable(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        //return database.rawQuery(Constant.SELECT_STUDENT_TABLE,null);
        String query = "SELECT * FROM STUDENT_TABLE WHERE _CID=? GROUP BY _SID";
        return database.rawQuery(query, new String[]{Long.toString(cid)});
        //return  database.query(Constant.SELECT_STUDENT_TABLE,null,Constant.C_ID+"=?",new String[]{String.valueOf(cid)},null,null,Constant.S_ID);
    }

    // This method is used for the delete the data from the student table database.
    public int deleteStudent(long sid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(Constant.STUDENT_TABLE_NAME, Constant.S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    // This method is used for the update the data to the student table.
    public long updateStudent(long sid, String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.STUDENT_NAME_KEY, name);
        //values.put(Constant.STUDENT_ROLL_KEY,sid);
        // insert mathod return long value that is row id .
        return sqLiteDatabase.update(Constant.STUDENT_TABLE_NAME, values, Constant.S_ID + "=?", new String[]{String.valueOf(sid)});
    }

    /* This Part is used for the status table operation */
    public long addStatus(long sid, long cid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.S_ID, sid);
        values.put(Constant.C_ID, cid);
        values.put(Constant.DATE_KEY, date);
        values.put(Constant.STATUS_KEY, status);

        // if data is not inserted any reason than it return -1 other wise return the row id.
        return database.insert(Constant.STATUS_TABLE_NAME, null, values);
    }

    // This part is used for the update the status table.
    public long updateStatus(long sid, String date, String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.STATUS_KEY, status);
        String whereClause = Constant.DATE_KEY + "='" + date + "' AND " + Constant.S_ID + "=" + sid;
        return database.update(Constant.STATUS_TABLE_NAME, values, whereClause, null);
    }

    // The method is used for get the status from the status table.
    public String getStatus(long sid, String date) {
        String status = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = Constant.DATE_KEY + "='" + date + "' AND " + Constant.S_ID + "=" + sid;
        Cursor cursor = database.query(Constant.STATUS_TABLE_NAME, null, whereClause, null, null, null, null);
        if (cursor.moveToFirst()) {
            status = cursor.getString(4);
        }
        return status;
    }

    /* Form this portion we use it for the data show in sheet */

    public Cursor getDistinctMonths(long cid) {
        SQLiteDatabase database = this.getReadableDatabase();
        return database.query(Constant.STATUS_TABLE_NAME, new String[]{Constant.DATE_KEY}, Constant.C_ID + "=" + cid, null, "substr(" + Constant.DATE_KEY + ",4,7)", null, null);
    }


    // This part is used for the teacher registation table.

    // This method add the data into the teacher registration table .
    public void addTeacherRegistrationTable(TeacherItems teacherItems) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //imageHandelar imageHandelar = new imageHandelar();
        Bitmap imageStore = teacherItems.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageByte = byteArrayOutputStream.toByteArray();

        int number = Integer.parseInt(teacherItems.getNumber());
        int password = Integer.parseInt(teacherItems.getPassword());

        values.put(Constant.TEACHER_NAME, teacherItems.getName());
        values.put(Constant.TEACHER_NUMBER, number);
        values.put(Constant.TEACHER_EMAIL, teacherItems.getEmail());
        values.put(Constant.TEACHER_PASSWORD, password);
        values.put(Constant.TEACHER_IMAGE, imageByte);

        long checkIfQueryRun = database.insert(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, values);

        if (checkIfQueryRun != -1) {
            Toast.makeText(context.getApplicationContext(), "Insert Successfully", Toast.LENGTH_LONG).show();
            database.close();
        } else {
            Toast.makeText(context.getApplicationContext(), "Fail To Insert", Toast.LENGTH_LONG).show();
        }
    }

    // This is for the fetch the all teacher information.
    public Cursor fetchTeacherInformationAll() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(Constant.SELECT_TEACHER_REGISTRATION_TABLE, null);
        return cursor;
    }

    // Cheak for the registration login .
    public Cursor registrationLogin(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String query = Constant.TEACHER_EMAIL + "=" + email + " AND " + Constant.TEACHER_PASSWORD + "=" + password+";";
        String query = "SELECT * FROM " + Constant.TEACHER_REGISTRATION_TABLE_NAME + " WHERE " + Constant.TEACHER_EMAIL + " =? AND " + Constant.TEACHER_PASSWORD + " =? ";
        //Cursor cursor = sqLiteDatabase.query(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, query, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email, password});
        return cursor;
    }

    public Cursor registrationEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String query = Constant.TEACHER_EMAIL + "=" + email + " AND " + Constant.TEACHER_PASSWORD + "=" + password+";";
        String query = "SELECT * FROM " + Constant.TEACHER_REGISTRATION_TABLE_NAME + " WHERE " + Constant.TEACHER_EMAIL + " =?";
        //Cursor cursor = sqLiteDatabase.query(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, query, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        return cursor;
    }
    public Cursor registrationEmailAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String query = Constant.TEACHER_EMAIL + "=" + email + " AND " + Constant.TEACHER_PASSWORD + "=" + password+";";
        String query = "SELECT * FROM " + Constant.ADMIN_REGISTRATION_TABLE_NAME + " WHERE " + Constant.ADMIN_EMAIL + " =?";
        //Cursor cursor = sqLiteDatabase.query(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, query, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email});
        return cursor;
    }
    // This method is for the teacher forgot password field
    public long forgotPasswordTeacher(String password, String email) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.TEACHER_PASSWORD, password);
        // insert mathod return long value that is row id .
        return sqLiteDatabase.update(Constant.TEACHER_REGISTRATION_TABLE_NAME, values, Constant.TEACHER_EMAIL + "=?", new String[]{email});
    }

    // This method is for the update teacher information.
    public void updateTeacherInformation(TeacherItems teacherItems, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //imageHandelar imageHandelar = new imageHandelar();
        Bitmap imageStore = teacherItems.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageByte = byteArrayOutputStream.toByteArray();

        int number = Integer.parseInt(teacherItems.getNumber());
        int password = Integer.parseInt(teacherItems.getPassword());
        String pemail = teacherItems.getEmail();
        Toast.makeText(context.getApplicationContext(), "Update Successfully" + pemail, Toast.LENGTH_LONG).show();

        values.put(Constant.TEACHER_NAME, teacherItems.getName());
        values.put(Constant.TEACHER_NUMBER, number);
        values.put(Constant.TEACHER_EMAIL, pemail);
        values.put(Constant.TEACHER_PASSWORD, password);
        values.put(Constant.TEACHER_IMAGE, imageByte);

        long checkIfQueryRun = database.update(Constant.TEACHER_REGISTRATION_TABLE_NAME, values, Constant.TEACHER_EMAIL + "=?", new String[]{email});

        if (checkIfQueryRun != -1) {
            Toast.makeText(context.getApplicationContext(), "Update Successfully", Toast.LENGTH_LONG).show();
            database.close();
        } else {
            Toast.makeText(context.getApplicationContext(), "Fail To Update", Toast.LENGTH_LONG).show();
        }
    }

    // This method is for the find the unique email.
    public long uniqueEmailPasswordTeacher(String email1) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String query = Constant.TEACHER_EMAIL + "=" + email + " AND " + Constant.TEACHER_PASSWORD + "=" + password+";";
        String query = "SELECT * FROM " + Constant.TEACHER_REGISTRATION_TABLE_NAME + " WHERE " + Constant.TEACHER_EMAIL + " =?";
        //Cursor cursor = sqLiteDatabase.query(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, query, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email1});
        return cursor.getCount();
    }

    public void addAdminRegistrationTable(TeacherItems teacherItems) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //imageHandelar imageHandelar = new imageHandelar();
        Bitmap imageStore = teacherItems.getImage();
        byteArrayOutputStream = new ByteArrayOutputStream();
        imageStore.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageByte = byteArrayOutputStream.toByteArray();

        int number = Integer.parseInt(teacherItems.getNumber());
        int password = Integer.parseInt(teacherItems.getPassword());

        values.put(Constant.ADMIN_NAME, teacherItems.getName());
        values.put(Constant.ADMIN_NUMBER, number);
        values.put(Constant.ADMIN_EMAIL, teacherItems.getEmail());
        values.put(Constant.ADMIN_PASSWORD, password);
        values.put(Constant.ADMIN_IMAGE, imageByte);

        long checkIfQueryRun = database.insert(Constant.ADMIN_REGISTRATION_TABLE_NAME, null, values);

        if (checkIfQueryRun != -1) {
            Toast.makeText(context.getApplicationContext(), "Insert Successfully", Toast.LENGTH_LONG).show();
            database.close();
        } else {
            Toast.makeText(context.getApplicationContext(), "Fail To Insert", Toast.LENGTH_LONG).show();
        }
    }

    // Cheak for the Adminregistration login .
    public Cursor registrationAdminLogin(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String query = Constant.TEACHER_EMAIL + "=" + email + " AND " + Constant.TEACHER_PASSWORD + "=" + password+";";
        String query = "SELECT * FROM " + Constant.ADMIN_REGISTRATION_TABLE_NAME + " WHERE " + Constant.ADMIN_EMAIL + " =? AND " + Constant.ADMIN_PASSWORD + " =? ";
        //Cursor cursor = sqLiteDatabase.query(Constant.TEACHER_REGISTRATION_TABLE_NAME, null, query, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{email, password});
        return cursor;
    }

    /* This portion is used for the feedBack Operation */
    public void addFeedback(FeedbackItems feedbackItems) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int status = Integer.parseInt(feedbackItems.getStatus());
        values.put(Constant.FEEDBACK_EMAIL, feedbackItems.getEmail());
        values.put(Constant.FEEDBACK_OCCUPATION, feedbackItems.getOccupation());
        values.put(Constant.FEEDBACK_TEXT, feedbackItems.getFeedback());
        values.put(Constant.FEEDBACK_STATUS, status);
        long cheekToAdd = database.insert(Constant.FEEDBACK_TABLE_NAME, null, values);
        if (cheekToAdd != -1) {
            Toast.makeText(context.getApplicationContext(), "Feedback Added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Feedback Not Added", Toast.LENGTH_LONG).show();
        }
    }

    /* Check the uniqueness of feedback id */
    public Cursor checkUserAlreadyExists(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.FEEDBACK_TABLE_NAME + " WHERE " + Constant.FEEDBACK_EMAIL + " =? ";
        return database.rawQuery(query, new String[]{email});
    }

    /* This method is used for the retrive the Feedback */
    public Cursor retriveFeedback() {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Constant.TEACHER_REGISTRATION_TABLE_NAME + " , " + Constant.FEEDBACK_TABLE_NAME;
        return database.rawQuery(query, null);
    }
    /* This method is use for the update the feedback field */
    public void updateFeedbackTable(String feedback,String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constant.FEEDBACK_TEXT, feedback);
        // insert mathod return long value that is row id .
        sqLiteDatabase.update(Constant.FEEDBACK_TABLE_NAME, values, Constant.FEEDBACK_EMAIL + "=?", new String[]{email});
    }


}
