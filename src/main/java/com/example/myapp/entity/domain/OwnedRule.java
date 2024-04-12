package com.example.myapp.entity.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OwnedRule {
    @JacksonXmlProperty(localName = "specification")
    private Specification specification;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}
