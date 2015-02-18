# spring-boot-multi-auth
Example of getting a spring boot app to cover some routes with CAS and others with Basic Auth


Just do `./gradlew bootRun`

The internet will download, then it will be running on port 8080

There are two endopoints

* `http://loclahost:8080/noodle`

Returns a web page

* `http://loclahost:8080/api/noodle`

Returns some JSON

Currently both endpoints are covered by CAS authentication using the NPS "global ecco"
CAS server.  So you will need a global ecco account to view the pages.

## Goal

Change the Spring configuration so that:

1. The REST endpoints (`/api/`) are not covered by CAS, but instead use basic auth.
2. The identity of the basic auth user can be a fixed username password that is set up from constants in the environment (no need fora database of users)
3. No forms! this is intended to be an API endpoint, not a user interface.
 
