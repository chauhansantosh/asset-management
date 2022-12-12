package com.hcl

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.SpringSecurityUtils


class DashboardController {
    SpringSecurityService springSecurityService
    WorkOrderService workOrderService
    def index() {
        User loggedInUser = User.findById(springSecurityService.currentUserId)
        Long woCountByUser =  0
        if(SpringSecurityUtils.ifAllGranted("ROLE_TECHNICIAN")){
            woCountByUser = workOrderService.countByOwner(loggedInUser)
        } else {
            woCountByUser = workOrderService.count()
        }
        [woCountByUser: woCountByUser]
    }
}
