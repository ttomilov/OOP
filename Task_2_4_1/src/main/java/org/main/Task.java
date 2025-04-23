package org.main;

import java.util.Date;

public class Task {
    private String id;
    private String name;
    private int maxPoints;
    private Date softDeadline;
    private Date hardDeadline;

    Task(String id, String name, int maxPoints, Date softDeadline, Date hardDeadline) {
        this.id = id;
        this.name = name;
        this.maxPoints = maxPoints;
        this.softDeadline = softDeadline;
        this.hardDeadline = hardDeadline;
    }

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    int getMaxPoints() {
        return maxPoints;
    }

    Date getSoftDeadline() {
        return softDeadline;
    }

    Date getHardDeadline() {
        return hardDeadline;
    }
}
