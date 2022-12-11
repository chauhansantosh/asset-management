package com.hcl

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class WorkOrderServiceSpec extends Specification {

    WorkOrderService workOrderService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new WorkOrder(...).save(flush: true, failOnError: true)
        //new WorkOrder(...).save(flush: true, failOnError: true)
        //WorkOrder workOrder = new WorkOrder(...).save(flush: true, failOnError: true)
        //new WorkOrder(...).save(flush: true, failOnError: true)
        //new WorkOrder(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //workOrder.id
    }

    void "test get"() {
        setupData()

        expect:
        workOrderService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<WorkOrder> workOrderList = workOrderService.list(max: 2, offset: 2)

        then:
        workOrderList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        workOrderService.count() == 5
    }

    void "test delete"() {
        Long workOrderId = setupData()

        expect:
        workOrderService.count() == 5

        when:
        workOrderService.delete(workOrderId)
        sessionFactory.currentSession.flush()

        then:
        workOrderService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        WorkOrder workOrder = new WorkOrder()
        workOrderService.save(workOrder)

        then:
        workOrder.id != null
    }
}
