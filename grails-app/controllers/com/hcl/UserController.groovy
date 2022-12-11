package com.hcl
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserController {

    UserService userService
    MyUserRoleService myUserRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    @Secured(['ROLE_ADMIN'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    @Secured(['ROLE_ADMIN'])
    def show(Long id) {
        respond userService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def create() {
        respond new User(params)
    }

    @Secured(['ROLE_ADMIN'])
    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def edit(Long id) {
        respond userService.get(id)
    }

    @Secured(['ROLE_ADMIN'])
    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            def savedUser = userService.save(user)
            def role = Role.get(params.role.id)
            if(savedUser && role) {
                myUserRoleService.updateUserRoles(savedUser.id, Arrays.asList([role.id]), false)
            } else {
                respond user.errors, view:'edit'
                return
            }
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Secured(['ROLE_ADMIN'])
    def search ( params, User user ) {
        def result = [: ]

        if ( params.q ) {
            def criteria = User.createCriteria( )
            result.userInstanceList = criteria.list( params ){
                or {
                    like("username", "%${params.q}%")
                }
            }
        } else {
            result.userInstanceList = User.list( params )
        }
        result.userInstanceTotal = User.count( )

        render ( view: "search", model: [ userInstanceTotal: result.userInstanceTotal, userInstanceList: result.userInstanceList, searchText: params.q ] )
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
