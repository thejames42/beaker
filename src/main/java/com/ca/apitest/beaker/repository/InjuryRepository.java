package com.ca.apitest.beaker.repository;

import com.ca.apitest.beaker.domain.Injury;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Injury entity.
 */
@SuppressWarnings("unused")
public interface InjuryRepository extends MongoRepository<Injury,String> {

}
