# Rest Client
Generic implementation for consuming RESTful services (JSON and XML).

## How It Works
1. Extend *AbstractJsonClientService* for consuming JSON service and *AbstractXmlClientService* for XML service.
#### JSON
JSON Client Implementation by extending *AbstractJsonClient* and annotating it with *@Service* and *@ConfigurationProperties*
``` java
@Service
@ConfigurationProperties(prefix = "application.json")
public class JsonClientImplementation extends AbstractJsonClient {
}
```
#### XML
XML Client Implementation by extending *AbstractXmlClient* and annotating it with *@Service* and *@ConfigurationProperties*
``` java
@Service
@ConfigurationProperties(prefix = "application.xml")
public class XmlClientImplementation extends AbstractXmlClient {
}
```

2. Configurable Http environment variables can be set with read-timeout and connection-timeout for individual service in application properties
``` bash
# Json Client Config
application.json.config.connection-timeout=30000
application.json.config.read-timeout=2000
# Xml Client Config
application.xml.config.connection-timeout=30000
application.xml.config.read-timeout=5000
```
3. Invoke desired Http Method GET, PUT, POST, DELETE with following functions
   - *invokeGetRequest*
   - *invokePostRequest*
   - *invokePutRequest*
   - *invokeDeleteRequest*
