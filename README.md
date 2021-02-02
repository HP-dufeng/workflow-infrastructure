# DangQu.PT.Infrastructure

Common packages for java projects.

## Reference
* [Maven CI Friendly Versions](https://maven.apache.org/maven-ci-friendly.html)

## powertrade-security-conf

> validate token, get token from identityserver, generate webClient for http request.

### how to use 

add config in application.yaml
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://test.powertrade.lumicable.cn/useridentity
          key-set-uri: https://test.powertrade.lumicable.cn/useridentity/.well-known/openid-configuration/jwks
      client:
        registration:
          permission_manage:
            provider: uaa
            client-id: permission_manage
            client-secret: permission_manage
            authorization-grant-type: client_credentials
            scope: internal_service
        provider:
          uaa:
            issuer-uri: https://test.powertrade.lumicable.cn/useridentity

```

## powertrade-healthcheck-conf
> export below health check endpoints for k8s.  
```
/health/ready
/health/live
/your_application_prefix/health/ready
/your_application_prefix/health/live
```