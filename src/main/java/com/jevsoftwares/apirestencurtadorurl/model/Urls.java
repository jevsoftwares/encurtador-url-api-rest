package com.jevsoftwares.apirestencurtadorurl.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Urls extends JpaRepository<Url,Integer> {

    Url findByUrlOriginal(String urlOriginal);

    Url findByUrlCurta(String urlCurta);
}
