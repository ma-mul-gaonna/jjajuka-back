package com.mmgon.jjajuka.global.enums;

public enum Position {
    JEONMU(Grade.A),
    GWAJANG(Grade.A),
    CHAJANG(Grade.A),
    DAERI(Grade.B),
    JUIM(Grade.B),
    SAWON(Grade.C);

    private final Grade grade;

    Position(Grade grade) {
        this.grade = grade;
    }

    public Grade getGrade() {
        return grade;
    }
}
