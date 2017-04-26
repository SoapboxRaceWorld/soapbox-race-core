#!/bin/bash
xjc -p com.soapboxrace.jaxb.http -npa -no-header \
   Main.xsd

cp -r com ../src/main/java
rm -rf com
