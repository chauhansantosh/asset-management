package com.hcl

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RoleController {

    RoleService roleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond roleService.list(params), model:[roleCount: roleService.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def show(Long id) {
        respond roleService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        respond new Role(params)
    }

    @Secured(['ROLE_ADMIN'])
    def save(Role role) {
        if (role == null) {
            notFound()
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*' { respond role, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        respond roleService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def update(Role role) {
        if (role == null) {
            notFound()
            return
        }

        try {
            roleService.save(role)
        } catch (ValidationException e) {
            respond role.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'role.label', default: 'Role'), role.id])
                redirect role
            }
            '*'{ respond role, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        roleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'role.label', default: 'Role'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def search ( params, Role role ) {
        def result = [: ]

        if ( params.q ) {
            def criteria = Role.createCriteria( )
            result.roleInstanceList = criteria.list( params ){
                or {
                    like("authority", "%${params.q}%")
                }
            }
        } else {
            result.roleInstanceList = Role.list( params )
        }
        result.roleInstanceTotal = Role.count( )

        render ( view: "search", model: [ roleInstanceTotal: result.roleInstanceTotal,
                                          roleInstanceList: result.roleInstanceList, searchText: params.q ] )
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
