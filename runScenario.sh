#!/bin/bash
mvn gatling:test -Dgatling.resultsFolder=/var/www/html/gatling/ -DSUT-IP=$1