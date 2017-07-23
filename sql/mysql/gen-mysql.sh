#!/bin/bash
SCRIPT_NAME=full-script.sql
cat 00-mysql-structure.sql > $SCRIPT_NAME
cat 01-mysql-openfire.sql >> $SCRIPT_NAME
cat ../01*.sql >> $SCRIPT_NAME
cat ../02*.sql >> $SCRIPT_NAME
