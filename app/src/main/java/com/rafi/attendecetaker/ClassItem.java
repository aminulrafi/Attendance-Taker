package com.rafi.attendecetaker;

public class ClassItem {
    private long c_id;
    private String classname;
    private String subjectname;

    public ClassItem(String classname, String subjectname) {
        this.classname = classname;
        this.subjectname = subjectname;
    }

    public ClassItem(long c_id, String classname, String subjectname) {
        this.c_id = c_id;
        this.classname = classname;
        this.subjectname = subjectname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public long getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }
}
