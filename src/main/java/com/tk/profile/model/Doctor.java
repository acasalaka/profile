package com.tk.profile.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor extends EndUser {

    @NotNull
    @Column(name = "specialist", nullable = false)
    private int specialist;

    @NotNull
    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Long fee;

    @ElementCollection
    @CollectionTable(name = "doctor_schedules", joinColumns = @JoinColumn(name = "doctor_id"))
    @Column(name = "schedule")
    private List<Integer> schedules;

    public String assignGender() {
        return isGender() ? "Perempuan" : "Laki-laki";
    }

    public String getTitle(int specialist) {
        return switch (specialist) {
            case 1  -> "drg.";
            default -> "dr.";
        };
    }

    public String getDegree(int specialist) {
        return switch (specialist) {
            case 2 -> "Sp.A";
            case 3 -> "Sp.B";
            case 4 -> "Sp.BP-RE";
            case 5 -> "Sp.JP";
            case 6 -> "Sp.KK";
            case 7 -> "Sp.M";
            case 8 -> "Sp.OG";
            case 9 -> "Sp.PD";
            case 10 -> "Sp.P";
            case 11 -> "Sp.THT-KL";
            case 12 -> "Sp.Rad";
            case 13 -> "Sp.KJ";
            case 14 -> "Sp.An";
            case 15 -> "Sp.N";
            case 16 -> "Sp.U";
            default -> "";
        };
    }

    public String getSpecializationCode(int specialist) {
        return switch (specialist) {
            case 1 -> "GGI";
            case 2 -> "ANK";
            case 3 -> "BDH";
            case 4 -> "PRE";
            case 5 -> "JPD";
            case 6 -> "KKL";
            case 7 -> "MTA";
            case 8 -> "OBG";
            case 9 -> "PDL";
            case 10 -> "PRU";
            case 11 -> "THT";
            case 12 -> "RAD";
            case 13 -> "KSJ";
            case 14 -> "ANS";
            case 15 -> "NRO";
            case 16 -> "URO";
            default -> "UMM";
        };
    }

    public String getSpecialistString() {
        return switch (specialist) {
            case 1 -> "Dokter Gigi";
            case 2 -> "Spesialis Anak";
            case 3 -> "Bedah";
            case 4 -> "Bedah Plastik, Rekonstruksi, dan Estetik";
            case 5 -> "Jantung dan Pembuluh Darah";
            case 6 -> "Kulit dan Kelamin";
            case 7 -> "Mata";
            case 8 -> "Obstetri dan Ginekologi";
            case 9 -> "Penyakit Dalam";
            case 10 -> "Paru";
            case 11 -> "Telinga, Hidung, Tenggorokan, Bedah Kepala Leher";
            case 12 -> "Radiologi";
            case 13 -> "Kesehatan Jiwa";
            case 14 -> "Anestesi";
            case 15 -> "Neurologi";
            case 16 -> "Urologi";
            default -> "Dokter Umum";
        };
    }

    public String getName(int specialist) {
        String title = getTitle(this.specialist);
        String degree = getDegree(this.specialist);

        if (!degree.isEmpty()) {
            return title + " " + getName() + ", " + degree;
        }
        return title + " " + getName();
    }

    public String scheduleString(int schedules) {
        return switch (schedules) {
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            case 7 -> "Saturday";
            default -> throw new IllegalStateException("Unexpected value: " + schedules);
        };
    }

    public List<String> getScheduleDayStrings() {
        List<String> dayNames = new ArrayList<>();

        for (Integer schedule : schedules) {
            String dayName = scheduleString(schedule);
            dayNames.add(dayName);
        }

        return dayNames;
    }

    public String sanitizeName(String name) {
        String sanitized = name.replaceAll("(?i)^(dr\\.\\s*|drg\\.\\s*)", "");
        sanitized = sanitized.replaceAll("(?i),\\s*Sp\\.[A-Za-z]*$", "");

        return sanitized.trim();
    }
}
