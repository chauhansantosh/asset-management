package com.hcl

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class LocationController {

    LocationService locationService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond locationService.list(params), model:[locationCount: locationService.count()]
    }

    @Secured(['ROLE_ADMIN', 'ROLE_ASSETMANAGER'])
    def show(Long id) {
        respond locationService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def create() {
        respond new Location(params)
    }
    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def save(Location location) {
        if (location == null) {
            notFound()
            return
        }

        try {
            locationService.save(location)
        } catch (ValidationException e) {
            respond location.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'location.label', default: 'Location'), location.id])
                redirect location
            }
            '*' { respond location, [status: CREATED] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def edit(Long id) {
        respond locationService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def update(Location location) {
        if (location == null) {
            notFound()
            return
        }

        try {
            locationService.save(location)
        } catch (ValidationException e) {
            respond location.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'location.label', default: 'Location'), location.id])
                redirect location
            }
            '*'{ respond location, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        locationService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'location.label', default: 'Location'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_ASSETMANAGER'])
    def search ( params, Location location ) {
        def result = [: ]

        if ( params.q ) {
            def criteria = Location.createCriteria( )
            result.locationInstanceList = criteria.list( params ){
                or {
                    like("location", "%${params.q}%")
                    like("description", "%${params.q}%")
                    like("status", "%${params.q}%")
                    like("locationType", "%${params.q}%")
                }
            }
        } else {
            result.locationInstanceList = Location.list( params )
        }
        result.locationInstanceTotal = Location.count( )

        render ( view: "search", model: [ locationInstanceTotal: result.locationInstanceTotal,
                                          locationInstanceList: result.locationInstanceList, searchText: params.q ] )
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'location.label', default: 'Location'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
