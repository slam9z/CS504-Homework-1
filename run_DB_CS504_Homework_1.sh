#!/usr/bin/env bash

echo "\nLogin into MySQL for creating running_information_analysis_service_db ..."
echo "==========================================================================="
#Login into MySQL Database
mysql --host=127.0.0.1 --port=3306 --user=root --password=root <<MYSQL_SCRIPT

#Create Database if not exists
CREATE DATABASE IF NOT EXISTS running_information_analysis_service_db;

#show MySQL
show databases;

#quit mySQL


MYSQL_SCRIPT
echo "==========================================================================="
echo "Database schema created...\n"

