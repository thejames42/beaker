package com.ca.apitest.beaker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.ca.apitest.beaker.domain.enumeration.Classification;

/**
 * A Injury.
 */

@Document(collection = "injury")
public class Injury implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("classification")
    private Classification classification;

    @NotNull
    @Min(value = 1)
    @Max(value = 10)
    @Field("severity")
    private Integer severity;

    @NotNull
    @Field("location")
    private String location;

    @NotNull
    @Field("inflicted")
    private LocalDate inflicted;

    @Field("fatal")
    private Boolean fatal;

    @Field("source")
    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Classification getClassification() {
        return classification;
    }

    public Injury classification(Classification classification) {
        this.classification = classification;
        return this;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public Integer getSeverity() {
        return severity;
    }

    public Injury severity(Integer severity) {
        this.severity = severity;
        return this;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public String getLocation() {
        return location;
    }

    public Injury location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getInflicted() {
        return inflicted;
    }

    public Injury inflicted(LocalDate inflicted) {
        this.inflicted = inflicted;
        return this;
    }

    public void setInflicted(LocalDate inflicted) {
        this.inflicted = inflicted;
    }

    public Boolean isFatal() {
        return fatal;
    }

    public Injury fatal(Boolean fatal) {
        this.fatal = fatal;
        return this;
    }

    public void setFatal(Boolean fatal) {
        this.fatal = fatal;
    }

    public String getSource() {
        return source;
    }

    public Injury source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Injury injury = (Injury) o;
        if (injury.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, injury.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Injury{" +
            "id=" + id +
            ", classification='" + classification + "'" +
            ", severity='" + severity + "'" +
            ", location='" + location + "'" +
            ", inflicted='" + inflicted + "'" +
            ", fatal='" + fatal + "'" +
            ", source='" + source + "'" +
            '}';
    }
}
