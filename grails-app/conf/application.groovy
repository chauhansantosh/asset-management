
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/dashboard'
grails.plugin.springsecurity.cacheUsers = false

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.hcl.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.hcl.UserRole'
grails.plugin.springsecurity.authority.className = 'com.hcl.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/dashboard/**',   access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/logoff/**',      access: ['IS_AUTHENTICATED_FULLY']],
	[pattern: '/user/**',      access: ['ROLE_ADMIN']],
	[pattern: '/role/**',      access: ['ROLE_ADMIN']],
	[pattern: '/person/**',      access: ['ROLE_ADMIN']]

]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

