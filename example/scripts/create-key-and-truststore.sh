#!/bin/sh

set -e

cd "$1"

keytool -genkeypair \
  -alias localhost \
  -keyalg RSA \
  -keysize 2048 \
  -keystore keystore.p12 \
  -storetype PKCS12 \
  -storepass changeit \
  -validity 365 \
  -dname "CN=localhost, OU=Unknown, O=Unknown, L=Unknown, S=Unknown, C=Unknown"

keytool -exportcert \
  -alias localhost \
  -file localhost.crt \
  -keystore keystore.p12 \
  -storetype PKCS12 \
  -storepass changeit

keytool -importcert \
  -alias localhost \
  -file localhost.crt \
  -keystore truststore.p12 \
  -storetype PKCS12 \
  -storepass changeit \
  -noprompt
