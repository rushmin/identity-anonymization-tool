if [ $# -eq 0 ]
 then
   printf "\nUsage : \n\t./deploy.sh <source location of identity-anonymization-tool> <WSO2 IS Home>\n\n"
   exit 1
fi

SRC_DIR=$1
IS_DIR=$2
FORGETME_DIR="$IS_DIR/repository/components/tools/forget-me"

mvn clean install -f $SRC_DIR/pom.xml
cp -v $SRC_DIR/components/org.wso2.carbon.privacy.forgetme.userstore/target/org.wso2.carbon.privacy.forgetme.userstore-1.1.15.jar $FORGETME_DIR/lib/
cp -v $SRC_DIR/components/org.wso2.carbon.privacy.forgetme.rest/target/org.wso2.carbon.privacy.forgetme.rest-1.1.15.jar $FORGETME_DIR/lib/

# Copy runtime libs
cp -v $IS_DIR/repository/components/plugins/axiom_1.2.11.wso2v11.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins/org.wso2.carbon.base_4.4.26.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins/org.wso2.carbon.logging_4.4.26.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins/org.wso2.carbon.user.api_4.4.26.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins/org.wso2.carbon.user.core_4.4.26.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins/org.wso2.carbon.utils_4.4.26.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/repository/components/plugins//org.wso2.securevault_1.0.0.wso2v2.jar $FORGETME_DIR/lib/
cp -v $IS_DIR/lib/endorsed/xercesImpl-2.8.1.wso2v2.jar $FORGETME_DIR/lib/


cp -v ~/.m2/repository/org/wso2/msf4j/msf4j-core/2.6.2/msf4j-core-2.6.2.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/org/wso2/msf4j/jaxrs-delegates/2.6.2/jaxrs-delegates-2.6.2.jar $FORGETME_DIR/lib/

cp -v ~/.m2/repository/org/wso2/carbon/messaging/org.wso2.carbon.messaging/1.0.4/org.wso2.carbon.messaging-1.0.4.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/org/wso2/transport/http/org.wso2.transport.http.netty/6.0.163/org.wso2.transport.http.netty-6.0.163.jar $FORGETME_DIR/lib/
#cp -v ~/.m2/repository/org/wso2/carbon/transport/org.wso2.carbon.transport.http.netty/2.1.2/org.wso2.carbon.transport.http.netty-2.1.2.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-transport/4.1.19.Final/netty-transport-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-common/4.1.19.Final/netty-common-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-buffer/4.1.19.Final/netty-buffer-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-codec/4.1.19.Final/netty-codec-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-codec-http/4.1.19.Final/netty-codec-http-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-codec-http2/4.1.19.Final/netty-codec-http2-4.1.19.Final.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/netty/netty-handler/4.1.19.Final/netty-handler-4.1.19.Final.jar $FORGETME_DIR/lib/

cp -v ~/.m2/repository/org/wso2/carbon/config/org.wso2.carbon.config/2.1.5/org.wso2.carbon.config-2.1.5.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/javax/websocket/javax.websocket-api/1.1/javax.websocket-api-1.1.jar $FORGETME_DIR/lib/

cp -v ~/.m2/repository/org/apache/servicemix/bundles/org.apache.servicemix.bundles.commons-beanutils/1.8.3_2/org.apache.servicemix.bundles.commons-beanutils-1.8.3_2.jar $FORGETME_DIR/lib/

cp -v ~/.m2/repository/com/google/guava/guava/18.0/guava-18.0.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/javax/ws/rs/javax.ws.rs-api/2.0/javax.ws.rs-api-2.0.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/swagger/swagger-jaxrs/1.5.8/swagger-jaxrs-1.5.8.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/io/swagger/swagger-core/1.5.8/swagger-core-1.5.8.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/commons-pool/wso2/commons-pool/1.5.6.wso2v1/commons-pool-1.5.6.wso2v1.jar $FORGETME_DIR/lib/
cp -v ~/.m2/repository/org/wso2/orbit/com/lmax/disruptor/3.3.2.wso2v2/disruptor-3.3.2.wso2v2.jar $FORGETME_DIR/lib/

cp -rv user-rename-conf $FORGETME_DIR
cp -v bin/rename.sh $IS_DIR/bin
cp -v bin/start-api-server.sh $IS_DIR/bin
cp -v bin/forgetme-start-api-server.sh $FORGETME_DIR/bin/start-api-server.sh
cp -v carbon-conf/user-mgt.xml $IS_DIR/repository/conf
