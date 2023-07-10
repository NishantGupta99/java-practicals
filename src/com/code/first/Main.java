package com.code.first;

class MarkExceededException extends Exception {
    public MarkExceededException(String message) {
        super(message);
    }
}

class Student {
    private int internalMarks;
    private int externalMarks;

    public Student(int internalMarks, int externalMarks) {
        this.internalMarks = internalMarks;
        this.externalMarks = externalMarks;
    }

    public void checkMarks() throws MarkExceededException {
        if (internalMarks > 30) {
            throw new MarkExceededException("Internal mark exceeded");
        }
        if (externalMarks > 70) {
            throw new MarkExceededException("External mark exceeded");
        }
        System.out.println("Marks within limits.");
    }
}

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student(25, 60);
        Student student2 = new Student(30, 70);

        try {
            student1.checkMarks();
        } catch (MarkExceededException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        try {
            student2.checkMarks();
        } catch (MarkExceededException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
