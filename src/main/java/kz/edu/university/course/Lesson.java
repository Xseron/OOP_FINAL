package kz.edu.university.course;

import kz.edu.university.enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;

/** single lesson */
public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private LessonType lessonType;
    private LocalDateTime dateTime;
    private String room;

    public Lesson() {
    }

    public Lesson(LessonType lessonType, LocalDateTime dateTime, String room) {
        this.lessonType = lessonType;
        this.dateTime = dateTime;
        this.room = room;
    }

    public LessonType getLessonType() {
        return lessonType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setLessonType(LessonType t) {
        this.lessonType = t;
    }

    public void setDateTime(LocalDateTime dt) {
        this.dateTime = dt;
    }

    public void setRoom(String r) {
        this.room = r;
    }

    @Override
    public String toString() {
        return "Lesson{" + lessonType + " @ " + room + " " + dateTime + "}";
    }
}
