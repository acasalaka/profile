package com.tk.profile.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class Patient extends EndUser {

    @NotBlank
    @Column(name = "nik", nullable = false, unique = true)
    private String nik;

    @NotNull
    @Column(name = "birth_place", nullable = false)
    private String birthPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "birth_date", columnDefinition = "DATE", nullable = false)
    private Date birthDate;

    @NotNull
    @Column(name = "p_class", nullable = false)
    private int pClass;

    public String assignGender() {
        return isGender() ? "Perempuan" : "Laki-laki";
    }
}
