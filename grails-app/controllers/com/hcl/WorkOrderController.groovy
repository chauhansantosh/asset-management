package com.hcl

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.SpringSecurityUtils

class WorkOrderController {

    WorkOrderService workOrderService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        if(SpringSecurityUtils.ifAllGranted("ROLE_TECHNICIAN")) {
            User user = User.findById(springSecurityService.currentUserId)
            respond workOrderService.findAllByOwner(user), model:[workOrderCount: workOrderService.countByOwner(user)]
        } else {
            respond workOrderService.list(params), model:[workOrderCount: workOrderService.count()]
        }
    }
    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def show(Long id) {
        respond workOrderService.get(id)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR'])
    def create() {
        respond new WorkOrder(params)
    }
    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR'])
    def save(WorkOrder workOrder) {
        if (workOrder == null) {
            notFound()
            return
        }

        try {
            workOrderService.save(workOrder)
        } catch (ValidationException e) {
            respond workOrder.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), workOrder.id])
                redirect workOrder
            }
            '*' { respond workOrder, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def edit(Long id) {
        respond workOrderService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def update(WorkOrder workOrder) {
        if (workOrder == null) {
            notFound()
            return
        }

        try {
            workOrderService.save(workOrder)
        } catch (ValidationException e) {
            respond workOrder.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), workOrder.id])
                redirect workOrder
            }
            '*'{ respond workOrder, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        workOrderService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_SUPERVISOR','ROLE_TECHNICIAN'])
    def search(params, WorkOrder workOrder) {
        def result =[:]
        if(SpringSecurityUtils.ifAllGranted("ROLE_TECHNICIAN")) {
            User user = User.get(springSecurityService.currentUserId)
            if (params.q) {
                def results = WorkOrder.findAll("""
                                        from WorkOrder as w
                                        where w.owner.id = ${user.id}
                                        and (w.workOrderNumber like :q
                                        or w.description like :q
                                        or w.status like :q
                                        or w.workType like :q)""", [q: "%${params.q}%"])
                result.workOrderInstanceList = results
            } else {
                result.workOrderInstanceList = WorkOrder.findAllByOwner(user)
            }
            result.workOrderInstanceTotal = workOrderService.countByOwner(user)

        } else {
            if (params.q) {
                def criteria = WorkOrder.createCriteria()
                result.workOrderInstanceList = criteria.list(params) {
                    or {
                        like("workOrderNumber", "%${params.q}%")
                        like("description", "%${params.q}%")
                        like("status", "%${params.q}%")
                        like("workType", "%${params.q}%")
                    }
                }
            } else {
                result.workOrderInstanceList = WorkOrder.list(params)
            }
            result.workOrderInstanceTotal = WorkOrder.count()
        }


        render(view: "search", model: [workOrderInstanceTotal: result.workOrderInstanceTotal,
                                       workOrderInstanceList: result.workOrderInstanceList,searchText:params.q])
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'workOrder.label', default: 'WorkOrder'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
