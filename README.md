# CORSFilter
CORSFilter for generic subdomains in springboot application.

In case you have a request in order to allow for CORS generic subdomains (https:*.domain.com) you can use the CORSFilter.java class.

In the application.properties file you need to have a property cors.allowedOrigins that contains the regular expression for the domain in question. The regular expression is fairly simple as presented in the example.
