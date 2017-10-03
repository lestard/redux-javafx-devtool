#!/bin/sh

BRANCH=${TRAVIS_BRANCH}

if [ "$BRANCH" = "master" ]
then
    gradle uploadArchives -Psnapshot -PsonatypeUsername=$SONATYPE_USERNAME -PsonatypePassword=$SONATYPE_PASSWORD
fi
