package com.hcl
import java.util.*;

class BootStrap {
    def myUserRoleService
    def init = { servletContext ->
        def adminRole = getOrCreateRole("ROLE_ADMIN")
        def adminUser = getOrCreateUser("admin","admin", "admin", "admin@hcl.com")
        if (adminRole && adminUser){
            myUserRoleService.updateUserRoles(adminUser.id, Arrays.asList([adminRole.id]), false)
        }
    }
    def destroy = {
    }

    private getOrCreateRole(name) {
        def role = Role.findByAuthority(name)
        if (!role) role = new Role(authority: name).save()
        if (!role)  println "Unable to save role ${name}"
        return role
    }
    private getOrCreateUser(String username, String password, String lastname, String email) {
        def user = User.findByUsername(username)
        if (!user) user = new User(
                username: username,
                password: password,
                siteId: "NOIDA",
                companyId: "HCL",
                enabled: true,
                person: new Person(name: username, lastName: lastname, email:email)).save()
        if (!user)  println "Unable to save user ${username}"
        return user
    }

}
