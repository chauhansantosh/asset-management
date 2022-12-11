package com.hcl

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserRoleController {

    UserRoleService userRoleService
    MyUserRoleService myUserRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userRoleService.list(params), model:[userRoleCount: userRoleService.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def show(Long id) {
        respond userRoleService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        respond new UserRole(params)
    }

    @Secured(['ROLE_ADMIN'])
    def save(UserRole userRole) {
        if (userRole == null) {
            notFound()
            return
        }

        try {
            myUserRoleService.updateUserRoles(userRole.user.id, Arrays.asList([userRole.id]), false)
        } catch (ValidationException e) {
            respond userRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRole.id])
                redirect userRole
            }
            '*' { respond userRole, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        respond userRoleService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def update(UserRole userRole) {
        if (userRole == null) {
            notFound()
            return
        }

        try {
            userRoleService.save(userRole)
        } catch (ValidationException e) {
            respond userRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userRole.label', default: 'UserRole'), userRole.id])
                redirect userRole
            }
            '*'{ respond userRole, [status: OK] }
        }
    }
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        myUserRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userRole.label', default: 'UserRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userRole.label', default: 'UserRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
