#!/bin/bash

mvn clean package
(cd docker; docker-compose up)