# Mauritz's Thesis Project

This application is part of my CS master's thesis on _financial transaction deduplication_ at [Tink AB](https://tink.com).
It creates users in Tink's backend using Tink's Enterprise API, and then schedules a Spring Batch job to 

Spring Boot + Security + MVC + Data JPA + Batch

### Running

Run with JDK 12. The environment variables `CLIENT_ID` and `CLIENT_SECRET` are required and are your Tink OAuth credentials, with persistent users enabled.

Should run with any JDBC compliant RDBMS but is deployed with PostgreSQL 11.
Configured by `JDBC_DATABASE_URL`, `JDBC_DATABASE_USERNAME`, and `JDBC_DATABASE_PASSWORD`.
