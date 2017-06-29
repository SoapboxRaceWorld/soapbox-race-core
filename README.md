# soapbox-race-core

standalone.xml need to have gzip, here an example


    <subsystem xmlns="urn:jboss:domain:undertow:3.1">
      <buffer-cache name="default" />
      <server name="default-server">
        <http-listener name="default" socket-binding="http" redirect-socket="https" enable-http2="true" />
        <https-listener name="https" socket-binding="https" security-realm="ApplicationRealm" enable-http2="true" />
        <host name="default-host" alias="localhost">
          <location name="/" handler="welcome-content" />
          <filter-ref name="server-header" />
          <filter-ref name="x-powered-by-header" />
          <filter-ref name="gzipFilter"
            predicate="exists['%{o,Content-Type}'] and regex[pattern='(?:application/javascript|text/css|text/html|text/xml|application/json|application/xml)(;.*)?', value=%{o,Content-Type}, full-match=true] and not min-content-size[10]" />
          <filter-ref name="Vary-header" />
        </host>
      </server>
      <servlet-container name="default">
        <jsp-config />
        <websockets />
      </servlet-container>
      <handlers>
        <file name="welcome-content" path="${jboss.home.dir}/welcome-content" />
      </handlers>
      <filters>
        <response-header name="server-header" header-name="Server" header-value="WildFly/10" />
        <response-header name="x-powered-by-header" header-name="X-Powered-By" header-value="Undertow/1" />
        <response-header name="Vary-header" header-name="Vary" header-value="Accept-Encoding" />
        <gzip name="gzipFilter" />
      </filters>
    </subsystem>
    
    
system property example:

	 <system-properties>
	    <property name="openFireToken" value="xzxzx99df4d51z2d" />
	    <property name="openFireAddress" value="http://localhost:9090/plugins/restapi/v1" />
	    <property name="xmppIp" value="127.0.0.1" />
	    <property name="xmppPort" value="5222" />
	    <property name="udpRaceIp" value="127.0.0.1"/>
        <property name="udpRacePort" value="9998"/>
        <property name="udpFreeroamIp" value="127.0.0.1"/>
        <property name="udpFreeroamPort" value="9999"/>
        <property name="ticketToken" value="123456789"/>
        <property name="emailFrom" value="myemail@gmail.com" />
        <property name="serverAddress" value="http://127.0.0.1:8680" />
        <property name="promoCodeToken" value="987654321" />
	  </system-properties>
	  
	  
datasource example:

	<datasource jndi-name="java:jboss/datasources/SoapBoxDS" pool-name="SoapBoxDS" enabled="true" use-java-context="true">
	  <connection-url>jdbc:h2:tcp://localhost/~/git/soapbox-race-core/db/soapbox</connection-url>
	  <driver>h2</driver>
	  <security>
	    <user-name>sa</user-name>
	    <password>sa</password>
	  </security>
	</datasource>


email config example:


    <subsystem xmlns="urn:jboss:domain:mail:2.0">
      <mail-session name="default" jndi-name="java:jboss/mail/Default">
        <smtp-server outbound-socket-binding-ref="mail-smtp" />
      </mail-session>
      <mail-session name="Gmail" from="oncabroadcast@gmail.com" jndi-name="java:jboss/mail/Gmail">
        <smtp-server password="secretPassword" username="myemail@gmail.com" ssl="true" outbound-socket-binding-ref="mail-smtp-gmail" />
      </mail-session>
    </subsystem>
    ......
    <outbound-socket-binding name="mail-smtp">
      <remote-destination host="localhost" port="25" />
    </outbound-socket-binding>
    <outbound-socket-binding name="mail-smtp-gmail" source-port="0" fixed-source-port="false">
      <remote-destination host="smtp.gmail.com" port="465" />
    </outbound-socket-binding>

donations: https://www.patreon.com/nilzao
