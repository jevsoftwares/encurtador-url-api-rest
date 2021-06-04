package com.jevsoftwares.apirestencurtadorurl.model;

import javax.persistence.*;

@Entity
@Table(name = "testes_url")
public class Url{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String urlOriginal;

    private String urlCurta;

    public Url() {
    }

    public Url(String urlOriginal, String urlCurta) {
        this.urlOriginal = urlOriginal;
        this.urlCurta = urlCurta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getUrlCurta() {
        return urlCurta;
    }

    public void setUrlCurta(String urlCurta) {
        this.urlCurta = urlCurta;
    }
}
