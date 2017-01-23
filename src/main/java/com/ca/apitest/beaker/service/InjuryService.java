package com.ca.apitest.beaker.service;

import com.ca.apitest.beaker.domain.Injury;

import java.util.List;

/**
 * Service Interface for managing Injury.
 */
public interface InjuryService {

    /**
     * Save a injury.
     *
     * @param injury the entity to save
     * @return the persisted entity
     */
    Injury save(Injury injury);

    /**
     *  Get all the injuries.
     *
     *  @return the list of entities
     */
    List<Injury> findAll();

    /**
     *  Get the "id" injury.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Injury findOne(String id);

    List<Injury> findByLocationAndSeverity(String location, Integer severity);

    List<Injury> findByLocationAndSource(String location, String source);

    /**
     *  Delete the "id" injury.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
