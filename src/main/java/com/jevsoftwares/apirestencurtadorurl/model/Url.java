package com.jevsoftwares.apirestencurtadorurl.model;

import javax.persistence.*;

@Entity
@Table(name = "testes_url")
public class Url{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String original;

    private String encurtada;

    public Url() {
    }

    public Url(Integer id, String original, String encurtada) {
        this.id = id;
        this.original = original;
        this.encurtada = encurtada;
    }

    public Url(String original, String encurtada) {
        this.original = original;
        this.encurtada = encurtada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getEncurtada() {
        return encurtada;
    }

    public void setEncurtada(String encurtada) {
        this.encurtada = encurtada;
    }
}
