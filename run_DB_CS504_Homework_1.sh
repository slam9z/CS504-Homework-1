#!/usr/bin/env bash

printf "\nLogin into MySQL for creating running_information_analysis_service_db ..."
echo "==========================================================================="
#Login into MySQL Database
mysql --host=127.0.0.1 --port=3306 --user=root --password=root <<MYSQL_SCRIPT

#Create Database if not exists
CREATE DATABASE IF NOT EXISTS running_information_analysis_service_db;

#show MySQL
show databases;

MYSQL_SCRIPT
echo "==========================================================================="
printf "Database schema created...\n"

