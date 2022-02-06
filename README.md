# Thermostat

Thermostat to be used in my local raspberrypi 2B (or maybe 3B, because I am thinking on upgrading it to a domotic station)

The idea is to do E2E test and let amaia put them in green

### To launch:

In root: 

`./gradlew bootRun`

We use a custom E2E helper library to make E2E test more semantic and to hide complexity

### To launch tests:

`./gradlew test`

### To launch E2Etests:

Launch application and, in another console, launch

`./gradlew e2eTests`

## What do we need to do

- We will have an API for CRU(D?) of users
- We will need a schedules CRUD
- We need an endpoint to manage increases/decreases of temperature
- We should have endpoint to retrieve log info: by days and/or timerange

