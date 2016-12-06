package com.ca.apitest.beaker.service.impl;

import com.ca.apitest.beaker.service.InjuryService;
import com.ca.apitest.beaker.domain.Injury;
import com.ca.apitest.beaker.repository.InjuryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Injury.
 */
@Service
public class InjuryServiceImpl implements InjuryService{

    private final Logger log = LoggerFactory.getLogger(InjuryServiceImpl.class);
    
    @Inject
    private InjuryRepository injuryRepository;

    /**
     * Save a injury.
     *
     * @param injury the entity to save
     * @return the persisted entity
     */
    public Injury save(Injury injury) {
        log.debug("Request to save Injury : {}", injury);
        Injury result = injuryRepository.save(injury);
        return result;
    }

    /**
     *  Get all the injuries.
     *  
     *  @return the list of entities
     */
    public List<Injury> findAll() {
        log.debug("Request to get all Injuries");
        List<Injury> result = injuryRepository.findAll();

        return result;
    }

    /**
     *  Get one injury by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Injury findOne(String id) {
        log.debug("Request to get Injury : {}", id);
        Injury injury = injuryRepository.findOne(id);
        return injury;
    }

    /**
     *  Delete the  injury by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Injury : {}", id);
        injuryRepository.delete(id);
    }
}
