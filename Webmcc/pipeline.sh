#!/bin/bash

build=$1
mvn clean package
if [ "$build" == "build" ] ; then
	(cd docker; docker-compose rm -f;docker-compose build --no-cache;docker-compose up)
	else
		(cd docker; docker-compose up)
fi