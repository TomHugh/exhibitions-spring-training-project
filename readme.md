# PROJECT DESCRIPTION

**Exhibitions App**

There are roles: user, authorized user, administrator.

The administrator makes a list of exhibitions (theme, hall, period of operation and working hours, ticket price) and can also cancel exhibitions, view statistics of visits. The exhibition can occupy one or several halls.

The user can view the exhibits by topic, ticket price, and filter by date. An authorized user can buy a ticket to the selected exhibition.

**Settings**

Application can be run with two profiles. To change profile please provide respective property in application.properties file:
- dev - uses H2 in memory database
- prod - uses MySQL database

