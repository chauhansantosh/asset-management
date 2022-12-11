package com.hcl

import grails.validation.ValidationException
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import com.hcl.User
import com.hcl.Role
import com.hcl.UserRole

@Transactional
@Secured('permitAll')
class RegisterController {

    static allowedMethods = [register: "POST"]

    def index() {}

    def register() {
        if (!params.password.equals(params.repassword)) {
            flash.message = "Password and Re-Password not match"
            redirect action: "index"
            return
        } else {
            try {
                def user = User.findByUsername(params.username) ?: new User(username: params.username, password: params.password, person: new Person(name: params.name, lastName: params.lastName, email: params.email)).save()
                def role = getOrCreateRole("ROLE_USER")
                if (user && role) {
                    UserRole.create user, role

                    UserRole.withSession {
                        it.flush()
                        it.clear()
                    }

                    flash.message = "You have registered successfully. Please login."
                    redirect controller: "login", action: "auth"
                } else {
                    flash.message = "Register failed"
                    render view: "index"
                    return
                }
            } catch (ValidationException e) {
                flash.message = "Register Failed"
                redirect action: "index"
                return
            }
        }
    }

    private getOrCreateRole(name) {
        def role = Role.findByAuthority(name)
        if (!role) role = new Role(authority: name).save()
        if (!role)  println "Unable to save role ${name}"
        return role
    }
}