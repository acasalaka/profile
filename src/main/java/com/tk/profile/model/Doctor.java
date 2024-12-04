package com.tk.profile.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    @CollectionTable(name = "schedules", joinColumns = @JoinColumn(name = "doctor_id"))
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

    public String setDoctorName(String name, int specialist) {
        String title = getTitle(specialist);
        String degree = getDegree(specialist);

        if (!degree.isEmpty()) {
            return title + " " + name + ", " + degree;
        }
        return title + " " + name;
    }

    public List<String> getFourWeeksSchedule() {
        List<String> scheduleDates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, d MMMM yyyy", new Locale("id", "ID"));

        for (int i = 0; i < 28; i++) {
            int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            for (Integer schedule : schedules) {
                if (currentDayOfWeek == convertScheduleToCalendarDay(schedule)) {
                    scheduleDates.add(dateFormatter.format(calendar.getTime()));
                }
            }

            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return scheduleDates;
    }

    private int convertScheduleToCalendarDay(int schedule) {
        return switch (schedule) {
            case 1 -> Calendar.SUNDAY;
            case 2 -> Calendar.MONDAY;
            case 3 -> Calendar.TUESDAY;
            case 4 -> Calendar.WEDNESDAY;
            case 5 -> Calendar.THURSDAY;
            case 6 -> Calendar.FRIDAY;
            case 7 -> Calendar.SATURDAY;
            default -> throw new IllegalStateException("Unexpected value: " + schedule);
        };
    }

    public String sanitizeName(String name) {
        String sanitized = name.replaceAll("(?i)^(dr\\.\\s*|drg\\.\\s*)", "");
        sanitized = sanitized.replaceAll("(?i),\\s*Sp\\.[A-Za-z]*$", "");

        return sanitized.trim();
    }
}
