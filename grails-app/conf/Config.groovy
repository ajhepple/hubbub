// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

grails.config.locations = [ "classpath:${appName}-config.properties",
                            "classpath:${appName}-config.groovy",
                            "file:${userHome}/.grails/${appName}-config.properties",
                            "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = hubbub // change this to alter the default package name and Maven publishing destination

// The ACCEPT header will not be used for content negotiation for user agents containing the following strings (defaults to the 4 major rendering engines)
grails.mime.disable.accept.header.userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
grails.mime.types = [ // the first one is the default format
    all:           '*/*', // 'all' maps to '*' or the first available format in withFormat
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    hal:           ['application/hal+json','application/hal+xml'],
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']
grails.resources.adhoc.includes = ['/images/**', '/css/**', '/js/**', '/plugins/**']

// Legacy setting for codec used to encode data with ${}
grails.views.default.codec = "html"

// The default scope for controllers. May be prototype, session or singleton.
// If unspecified, controllers are prototype scoped.
grails.controllers.defaultScope = 'singleton'

// GSP settings
grails {
    views {
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml' // use xml escaping instead of HTML4 escaping
            codecs {
                expression = 'html' // escapes values inside ${}
                scriptlet = 'html' // escapes output from scriptlets in GSPs
                taglib = 'none' // escapes output from taglibs
                staticparts = 'none' // escapes output from static template parts
            }
        }
        // escapes all not-encoded output at final stage of outputting
        // filteringCodecForContentType.'text/html' = 'html'
    }
}


grails.converters.encoding = "UTF-8"
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

// configure passing transaction's read-only attribute to Hibernate session, queries and criterias
// set "singleSession = false" OSIV mode in hibernate configuration after enabling
grails.hibernate.pass.readonly = false
// configure passing read-only to OSIV session by default, requires "singleSession = false" OSIV mode
grails.hibernate.osiv.readonly = false


environments {
    development {
        grails.logging.jul.usebridge = true

        // Mail plugin configuration for gmail
        // See also ~/.grails/hubbub-config.groovy for credentials
        //
        // NB These configuration settings are deliberately isolated
        // from those of the test environment as Dumbster does not
        // override them all.
        grails {
            mail {
                host = "smtp.gmail.com"
                port = 465
                props = ["mail.smtp.auth":"true",
                         "mail.smtp.socketFactory.port":"465",
                         "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                         "mail.smtp.socketFactory.fallback":"false"]
            }
        }
        grails.mail.default.from = "Hubbub <sailingbye@gmail.com>"

    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
        
        // TODO: Production mail configuration 

        // Chapter 10.4 Database Migration
        // Configure auto migration on application start up
        grails.plugin.databasemigration.updateOnStart = true
        grails.plugin.databasemigration.updateOnStartFileNames = ['changelog.groovy']
    }
    test {
        // Use the Dumbster plugin as a mock mail server for testing AJH Ch.10
        dumbster.enabled = true
        grails.mail.host = "127.0.0.1"
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error  'org.codehaus.groovy.grails.web.servlet',        // controllers
           'org.codehaus.groovy.grails.web.pages',          // GSP
           'org.codehaus.groovy.grails.web.sitemesh',       // layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping',        // URL mapping
           'org.codehaus.groovy.grails.commons',            // core / classloading
           'org.codehaus.groovy.grails.plugins',            // plugins
           'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    /* These two entries help to provide an insight into SQL generation */
    //debug 'org.hibernate.SQL'
    //trace 'org.hibernate.type.descriptor.sql.BasicBinder'
}

// Example config of caching that the ehcache plugin allows
// See http://grails-plugins.github.com/grails-cashe-ehcache
grails.cache.config = {
    defaultCache {
        maxElementsInMemory 10000
        external false
        timeToIdleSeconds 120
        timeToLiveSeconds 120
        overflowToDisk true     // assumes disk availability!
        maxElementsOnDisk 100000000
        diskPersistent false
        diskExpiryThreadIntervalSeconds 120
        memoryStoreEvictionPolilcy 'LRU'
    }

    cache {
        name 'myDailyCache'
        timeToLiveSeconds 60*60*24
    }
}

// Fix grails taglib <g:paginate/> to work with Twitter bootstap CSS
grails.plugins.twitterbootstrap.fixtaglib = true


// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.grailsinaction.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.grailsinaction.UserRole'
grails.plugin.springsecurity.authority.className = 'com.grailsinaction.Role'
grails.plugin.springsecurity.userLookup.usernamePropertyName = "loginId"
grails.plugin.springsecurity.userLookup.passwordPropertyName = "passwordHash"
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.interceptUrlMap = [
	'/':                ['permitAll'],
	'/image/**':        ['permitAll'],
	'/post/global':     ['permitAll'],
        '/user/register':   ['permitAll'],
	'/login':           ['permitAll'],
	'/logout':          ['permitAll'],
	'/**/js/**':        ['permitAll'],
	'/**/css/**':       ['permitAll'],
	'/**/images/**':    ['permitAll'],
	'/user/**':         ['hasRole("ROLE_ADMIN")'],
	'/role/**':         ['hasRole("ROLE_ADMIN")'],
	//'/**':   ['isFullyAuthenticated()'] // i.e. require full authentication, every session
	'/**':   ['isAuthenticated()'] // makes use of the 'remember me' facility between sessions
]

grails.plugin.springsecurity.auth.loginFormUrl = "/login"
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = "/login"
grails.plugin.springsecurity.successHandler.defaultTargetUrl = "/timeline"
