package com.hcl

class WorkOrder {

    String workOrderNumber
    String description
    String status
    String workType
    Asset asset
    Location location
    String updatedBy
    Date lastUpdated
    double estimateDuration
    double estimateLaborHours
    double estimateMaterialCost
    double estimateLaborCost
    double estimateToolCost
    double actualLaborHours
    double actualMaterialCost
    double actualLaborCost
    double actualToolCost
    double outLaborCost
    double outMaterialCost
    double outToolCost
    int priority
    Date targetComplete
    Date targetStart
    String reportedBy
    Date reportDate
    int downTime
    Date actualStart
    Date actualFinish
    Date scheduledStart
    Date scheduledFinish
    double remainingDuration
    User supervisor
    boolean isDisabled
    String companyId
    String siteId
    String vendor
    User owner
    String ownerGroup
    Date dateCreated

    static constraints = {
        workOrderNumber maxSize: 25
        description maxSize: 100, nullable: true
        status maxSize: 16
        workType maxSize: 5, nullable: true
        asset nullable: true
        location nullable: true
        updatedBy maxSize:30, display: false, nullable: true
        estimateDuration()
        estimateLaborHours()
        estimateMaterialCost scale: 2
        estimateLaborCost scale: 2
        estimateToolCost scale: 2
        actualLaborHours()
        actualMaterialCost scale: 2
        actualLaborCost scale: 2
        actualToolCost  scale: 2
        outLaborCost scale: 2
        outMaterialCost scale: 2
        outToolCost  scale: 2
        priority nullable: true
        targetComplete nullable: true
        targetStart nullable: true
        reportedBy maxSize: 60, nullable: true
        reportDate nullable: true
        downTime()
        actualStart nullable: true
        actualFinish nullable: true
        scheduledStart nullable: true
        scheduledFinish nullable: true
        remainingDuration nullable: true, display: false
        supervisor maxSize: 30, nullable: true
        isDisabled display: false
        companyId maxSize: 8
        siteId maxSize: 8
        vendor maxSize: 12, nullable: true
        owner maxSize: 30, nullable: true
        ownerGroup maxSize: 8, nullable: true
    }
    static mapping = {
        id column: 'workorderuid'
    }

    String toString() {
        workOrderNumber + " "+ siteId
    }
}
