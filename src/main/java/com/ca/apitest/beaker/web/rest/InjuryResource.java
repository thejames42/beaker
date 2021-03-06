package com.ca.apitest.beaker.web.rest;

import com.ca.apitest.beaker.domain.enumeration.Classification;
import com.codahale.metrics.annotation.Timed;
import com.ca.apitest.beaker.domain.Injury;
import com.ca.apitest.beaker.service.InjuryService;
import com.ca.apitest.beaker.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Injury.
 */
@RestController
@RequestMapping("/api")
public class InjuryResource {

    private final Logger log = LoggerFactory.getLogger(InjuryResource.class);

    @Inject
    private InjuryService injuryService;

    /**
     * POST  /injuries : Create a new injury.
     *
     * @param injury the injury to create
     * @return the ResponseEntity with status 201 (Created) and with body the new injury, or with status 400 (Bad Request) if the injury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/injuries")
    @Timed
    public ResponseEntity<Injury> createInjury(@Valid @RequestBody Injury injury) throws URISyntaxException {
        log.debug("REST request to save Injury : {}", injury);
        if (injury.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("injury", "idexists", "A new injury cannot already have an ID")).body(null);
        }
        Injury result = injuryService.save(injury);
        return ResponseEntity.created(new URI("/api/injuries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("injury", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /injuries : Updates an existing injury.
     *
     * @param injury the injury to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated injury,
     * or with status 400 (Bad Request) if the injury is not valid,
     * or with status 500 (Internal Server Error) if the injury couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/injuries")
    @Timed
    public ResponseEntity<Injury> updateInjury(@Valid @RequestBody Injury injury) throws URISyntaxException {
        log.debug("REST request to update Injury : {}", injury);
        if (injury.getId() == null) {
            return createInjury(injury);
        }
        Injury result = injuryService.save(injury);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("injury", injury.getId().toString()))
            .body(result);
    }

    /**
     * GET  /injuries : get all the injuries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of injuries in body
     */
    @GetMapping("/injuries")
    @Timed
    public List<Injury> getAllInjuries() {
        log.debug("REST request to get all Injuries");
        return injuryService.findAll();
    }

    /**
     * GET  /injuries/:id : get the "id" injury.
     *
     * @param id the id of the injury to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the injury, or with status 404 (Not Found)
     */
    @GetMapping("/injuries/{id}")
    @Timed
    public ResponseEntity<Injury> getInjury(@PathVariable String id) {
        log.debug("REST request to get Injury : {}", id);
        Injury injury = injuryService.findOne(id);
        return Optional.ofNullable(injury)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /injuries/location/:location/severity/:severity : get all injuries by location and severity. Currently returns all injuries.
     *
     * @param  location the location of the injuries to retrieve
     * @param  severity the severity of the injuries to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of injuries in body
     */
    @GetMapping("/injuries/location/{location}/severity/{severity}")
    @Timed
    public List<Injury> getInjuriesByLocationAndSeverity(@PathVariable String location, @PathVariable Integer severity) {
        log.debug("REST request to get Injuries by location and severity : {}", location, severity);
        return injuryService.findByLocationAndSeverity(location, severity);
    }

    /**
     * GET  /injuries/location/:location/severity/:severity : get all injuries by location and severity. Currently returns all injuries.
     *
     * @param  location the location of the injuries to retrieve
     * @param  source the source of the injuries to retrieve
     * @return the ResponseEntity with status 200 (OK) and the list of injuries in body
     */
    @GetMapping("/injuries/location/{location}")
    @Timed
    public List<Injury> getInjuriesByIdAndLocation(@PathVariable String location, @RequestParam(value = "source") String source) {
        log.debug("REST request to get Injuries by location and severity : {}", location, source);
        return injuryService.findByLocationAndSource(location, source);
    }

    /**
     * DELETE  /injuries/:id : delete the "id" injury.
     *
     * @param id the id of the injury to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/injuries/{id}")
    @Timed
    public ResponseEntity<Void> deleteInjury(@PathVariable String id) {
        log.debug("REST request to delete Injury : {}", id);
        injuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("injury", id.toString())).build();
    }

    /**
     * POST  /injuries/lovetap/:location : Create a minor injury at a specific location
     *
     * @param location the location of the injury
     * @return the ResponseEntity with status 201 (Created) and with body the new injury, or with status 400 (Bad Request) if the injury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/injuries/lovetap/{location}")
    @Timed
    public ResponseEntity<Injury> createSimpleInjury(@PathVariable String location) throws URISyntaxException {
        log.debug("REST request to inflict random injury to location : {}", location);
        Injury injury= new Injury();
        injury.setInflicted(LocalDate.now());
        injury.setClassification(Classification.laceration);
        injury.setFatal(false);
        injury.setLocation(location);
        injury.setSeverity(1);
        injury.setSource("Team Building Excercises");
        Injury result = injuryService.save(injury);
        return ResponseEntity.created(new URI("/api/injuries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("injury", result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /injuries/lovetaps/:howMany : Create a set of Injuries to location howMany!"
     *
     * @param howMany the number of the lovetaps to inflict
     * @return the ResponseEntity with status 201 (Created) and with body the new injury, or with status 400 (Bad Request) if the injury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/injuries/lovetaps/{howMany}")
    @Timed
    public ResponseEntity<List<Injury>> createSimpleInjuryWithInteger(@PathVariable Integer howMany) throws URISyntaxException {
        log.debug("REST request to inflict random injuries : {}", howMany);
        List<Injury> result = new ArrayList<>();
        StringBuilder idList = new StringBuilder();
        if (howMany > 3)
            howMany = 3;
        for (int i = 0; i < howMany; i++) {
            Injury injury = new Injury();
            injury.setInflicted(LocalDate.now());
            injury.setClassification(Classification.laceration);
            injury.setFatal(false);
            injury.setLocation("location" + i);
            injury.setSeverity(1);
            injury.setSource("Gratuitous flirtations");
            Injury saved = injuryService.save(injury);
            if (i != 0)
                idList.append(",");
            idList.append(saved.getId());
            result.add(saved);
        }
        return ResponseEntity.created(new URI("/api/injuries/" + idList.toString()))
            .headers(HeaderUtil.createEntityCreationAlert("injury", idList.toString()))
            .body(result);
    }

}
