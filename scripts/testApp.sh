./mvnw -versions
#!/bin/bash

set -euxo pipefail

##############################################################################
##
##  GH actions CI test script
##
##############################################################################

./mvnw -ntp -Dhttp.keepAlive=false \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=120 \
    -q clean package liberty:create liberty:install-feature liberty:deploy

./mvnw test

./mvnw -ntp liberty:start

sleep 20

cat target/liberty/wlp/usr/servers/defaultServer/logs/messages.log || exit 1

status_code=$(curl -o /dev/null -s -w "%{http_code}" http://localhost:9080/index.xhtml)
[ "$status_code" -eq 200 ] || exit 1

./mvnw -ntp liberty:stop

./mvnw -ntp failsafe:verify
