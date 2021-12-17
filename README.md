# Thermostat

Thermostat to be used in my local raspberrypi 2B (or maybe 3B, because I am thinking on upgrading it to a domotic station)

###To launch:

In root: 

`./gradlew bootRun`

I use a custom library to improve the legibility of tests because I expect my wife to code it so I want to, only, write the e2e tests and let her fulfit them

At the moment library is private, soon it will be in github and I expect that it would end in a common maven repository

###To launch E2Etests:

Launch application and, in another console, launch

`./gradlew e2eTests`
