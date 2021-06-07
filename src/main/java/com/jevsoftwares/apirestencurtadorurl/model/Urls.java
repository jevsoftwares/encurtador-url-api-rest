package com.jevsoftwares.apirestencurtadorurl.model;

import org.springframework.data.repository.CrudRepository;

public interface Urls extends CrudRepository<Url,Integer> {

    Url findByOriginal(String original);

    Url findByEncurtada(String encurtada);
}
