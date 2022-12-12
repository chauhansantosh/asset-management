package com.hcl

import grails.gorm.services.Service

@Service(WorkOrder)
interface WorkOrderService {

    WorkOrder get(Serializable id)

    List<WorkOrder> list(Map args)

    Long count()

    void delete(Serializable id)

    WorkOrder save(WorkOrder workOrder)

}