package com.hcl

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PersonController {

    PersonService personService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond personService.list(params), model:[personCount: personService.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def show(Long id) {
        respond personService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def create() {
        respond new Person(params)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def save(Person person) {
        if (person == null) {
            notFound()
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'person.label', default: 'Person'), person.id])
                redirect person
            }
            '*' { respond person, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def edit(Long id) {
        respond personService.get(id)
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER', 'ROLE_SUPERVISOR', 'ROLE_TECHNICIAN'])
    def update(Person person) {
        if (person == null) {
            notFound()
            return
        }

        try {
            personService.save(person)
        } catch (ValidationException e) {
            respond person.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'person.label', default: 'Person'), person.id])
                redirect person
            }
            '*'{ respond person, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        personService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'person.label', default: 'Person'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def search ( params, Person person ) {
        def result = [: ]

        if ( params.q ) {
            def criteria = Person.createCriteria( )
            result.personInstanceList = criteria.list( params ){
                or {
                    like("name", "%${params.q}%")
                    like("lastName", "%${params.q}%")
                    like("email", "%${params.q}%")
                }
            }
        } else {
            result.personInstanceList = Person.list( params )
        }
        result.personInstanceTotal = Person.count( )

        render ( view: "search", model: [ personInstanceTotal: result.personInstanceTotal,
                                          personInstanceList: result.personInstanceList, searchText: params.q ] )
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
