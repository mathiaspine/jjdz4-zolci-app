#!/bin/bash

mysql --user=$MYSQL_USER --password=$MYSQL_PASSWORD $MYSQL_DATABASE < $MYSQL_INIT_PATH

echo "=> AUTOPARTS-DB INIT EXECUTED"
