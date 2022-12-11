package com.hcl

import grails.gorm.transactions.Transactional

class MyUserRoleService implements UserRoleService{
    @Transactional
    User updateUserRoles(Long userId, List<Long> newRoles, boolean flush = false) {
        def user = User.get(userId)
        if (!user) {
            return null
        }
        if (!newRoles) {
            removeAllUserRoleByUser(user, flush)
            return user
        }

        def existingRoles = findAllRoleIdsByUser(userId)

        def rolesToBeRemoved = existingRoles - newRoles

        def rolesToBeInsert = newRoles - existingRoles

        rolesToBeRemoved.each { Long roleId ->
            removeUserRoleByUserAndRole(user, Role.get(roleId), flush)
        }
        rolesToBeInsert.each { Long roleId ->
            def userRole = new UserRole(user: user, role: Role.get(roleId))
            userRole.save(flush: flush)
        }

        user
    }
    @Transactional
    boolean removeUserRoleByUserAndRole(User u, Role r, boolean flush = false) {
        if (u == null || r == null) {
            return false
        }

        int rowCount = UserRole.where { user == u && role == r }.deleteAll()

        if (flush) { UserRole.withSession { it.flush() } }

        rowCount
    }

    @Transactional(readOnly = true)
    List<Long> findAllRoleIdsByUser(Long userId) {
        def c = UserRole.createCriteria()
        c.list {
            projections {
                role {
                    property('id')
                }
            }
            user {
                eq('id', userId)
            }
        } as List<Long>
    }

    @Transactional
    void removeAllUserRoleByUser(User u, boolean flush = false) {
        if (u == null) {
            return
        }

        UserRole.where { user == u }.deleteAll()

        if (flush) { UserRole.withSession { it.flush() } }
    }

    @Override
    UserRole get(Serializable id) {
        return null
    }

    @Override
    List<UserRole> list(Map args) {
        return null
    }

    @Override
    Long count() {
        return null
    }

    @Override
    void delete(Serializable id) {

    }

    @Override
    UserRole save(UserRole userRole) {
        return null
    }
}