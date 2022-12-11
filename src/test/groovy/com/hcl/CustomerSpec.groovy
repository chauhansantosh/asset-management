package com.hcl

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CustomerSpec extends Specification implements DomainUnitTest<Person> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
