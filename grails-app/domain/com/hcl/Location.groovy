package com.hcl

class Location {
    String location
    String description
    String status
    String locationType
    Date lastUpdated
    String updatedBy
    boolean isDisabled
    String siteId
    String companyId
    Date installDate
    double replaceCost
    String fAddressCode
    Date dateCreated

    static constraints = {
        location maxSize: 12
        description maxSize: 100, nullable: true
        status maxSize: 20
        locationType maxSize: 16
        siteId maxSize: 8, nullable: false
        companyId maxSize: 8, nullable: false
        replaceCost scale: 2, nullable: true
        installDate nullable: true
        updatedBy maxSize: 30, nullable: true, display: false
        lastUpdated display: false
        isDisabled display: false
        dateCreated unique: true, display: false
        fAddressCode maxSize: 12, nullable: true, display: false
    }
    static mapping = {
        id column: 'locationuid'
    }
    String toString() {
        location + " "+ siteId
    }
}
