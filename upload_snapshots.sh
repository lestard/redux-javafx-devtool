#!/bin/sh

./gradlew uploadArchives -Psnapshot -PsonatypeUsername=$SONATYPE_USERNAME -PsonatypePassword=$SONATYPE_PASSWORD