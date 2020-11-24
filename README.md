![Maven Build](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Maven%20Build/badge.svg)

![Build and Deploy to GKE](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Build%20and%20Deploy%20to%20GKE/badge.svg)

![Graal Native Image Release](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Graal%20Native%20Image%20Release/badge.svg)



![oauth_nutshell](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/blob/master/src/main/resources/oauth.png?raw=true)


Available logins - 
| username | password    |
| -------- | ----------- |
| admin    | admin1234   |
| reader   | reader1234  |

Available clients - 
| client_id | client_secret|
| --------- | ------------ |
| morpheus  | morpheus     |
| trinity   | trinity      |
| neo       | neo          |
| bluesky   | bluesky      |


```
curl -kSs -X POST \
  https://app.awacscloud.tech/authserver/oauth/token \
  -u "trinity:trinity" \
  -F grant_type=password \
  -F username=admin \
  -F password=admin1234 \
  -F client_id=trinity | json_pp
{
   "expires_in" : 10799,
   "scope" : "read write",
   "access_token" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0",
   "refresh_token" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImF0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImV4cCI6MTYwODgwOTg3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImVmZTBkODE0LWQyYzQtNGQyZC05ZjQzLTBhMDUwOWYwZDA2ZSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.pPU6010lxAjs60ILhlaaeIyxrS1h9jQ2ZEAjteevbos",
   "token_type" : "bearer",
   "jti" : "dc74a90c-cbc5-43ff-8e87-a49ba1703079"
}
```

### then accessing a resource (productservice)

```
curl -kSs -X GET  https://app.awacscloud.tech/productservice/api/product/837 \
-H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0" \
| json_pp
{
   "id" : 837,
   "genericName" : "Brome Grass",
   "price" : "3065",
   "companyName" : "Nelco Laboratories, Inc.",
   "name" : "Brome Grass",
   "distributorName" : "Rath, Stroman and Kilback"
} 
```

### OR

```
curl -kSs -X GET  https://app.awacscloud.tech/productservice/api/product/order/4 \
-H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0" \
| json_pp
```
```
{
   "id" : 4,
   "orderStatus" : true,
   "distributor" : {
      "productId" : 24,
      "id" : 24,
      "email" : "hbrownriggn@yale.edu",
      "address" : "9 Sycamore Plaza",
      "stockistName" : "Tromp and Sons",
      "availableQuantity" : 62
   },
   "pharmasist" : {
      "pharmasistName" : "Ainsley",
      "pharmasistAddress" : "Stieger",
      "id" : 2,
      "email" : "astieger1@fema.gov"
   },
   "product" : {
      "distributorName" : "Rath, Stroman and Kilback",
      "companyName" : "Nelco Laboratories, Inc.",
      "genericName" : "Brome Grass",
      "name" : "Brome Grass",
      "price" : "3065",
      "id" : 837
   },
   "creationDate" : "2020-11-24T08:04:03.701768",
   "quantity" : 300
}
```
OR, User IAM Profile call - 

```
curl -kSs -X GET -u neo:neo https://app.awacscloud.tech/authserver/api/users/me \
-H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0" \
| json_pp

{
   "authorities" : [
      {
         "authority" : "SYSTEM"
      },
      {
         "authority" : "order_read"
      },
      {
         "authority" : "order_create"
      },
      {
         "authority" : "product_update"
      },
      {
         "authority" : "order_delete"
      },
      {
         "authority" : "role_product_order_reader"
      },
      {
         "authority" : "USER"
      },
      {
         "authority" : "order_update"
      },
      {
         "authority" : "product_read"
      },
      {
         "authority" : "product_create"
      },
      {
         "authority" : "product_delete"
      }
   ],
   "credentials" : "",
   "clientOnly" : false,
   "userAuthentication" : {
      "credentials" : "N/A",
      "authorities" : [
         {
            "authority" : "SYSTEM"
         },
         {
            "authority" : "order_read"
         },
         {
            "authority" : "order_create"
         },
         {
            "authority" : "product_update"
         },
         {
            "authority" : "order_delete"
         },
         {
            "authority" : "role_product_order_reader"
         },
         {
            "authority" : "USER"
         },
         {
            "authority" : "order_update"
         },
         {
            "authority" : "product_read"
         },
         {
            "authority" : "product_create"
         },
         {
            "authority" : "product_delete"
         }
      ],
      "name" : "admin",
      "authenticated" : true,
      "details" : null,
      "principal" : "admin"
   },
   "authenticated" : true,
   "principal" : "admin",
   "name" : "admin",
   "oauth2Request" : {
      "redirectUri" : null,
      "grantType" : null,
      "extensions" : {},
      "requestParameters" : {
         "client_id" : "trinity"
      },
      "authorities" : [],
      "clientId" : "trinity",
      "responseTypes" : [],
      "refreshTokenRequest" : null,
      "approved" : true,
      "resourceIds" : [
         "resource-server-rest-api"
      ],
      "refresh" : false,
      "scope" : [
         "read",
         "write"
      ]
   },
   "details" : {
      "tokenValue" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0",
      "remoteAddress" : "49.35.4.158",
      "tokenType" : "bearer",
      "decodedDetails" : null,
      "sessionId" : null
   }
}
```

### In nutshell, product service internally calls again to authserver on /check_token this way to confirm the authenticity of a token being valid

```
curl -kSs -X GET -u SYSTEM!ClientId:SYSTEM!ClientSecret https://app.awacscloud.tech/authserver/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIyODY3NSwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmVhZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZWF0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6ImRjNzRhOTBjLWNiYzUtNDNmZi04ZTg3LWE0OWJhMTcwMzA3OSIsImNsaWVudF9pZCI6InRyaW5pdHkifQ.2Y3fs8IzWSQvGyFWVgCfv1aNCd4dAm2gnF4LvnX3xf0 | json_pp
{
   "aud" : [
      "resource-server-rest-api"
   ],
   "jti" : "dc74a90c-cbc5-43ff-8e87-a49ba1703079",
   "authorities" : [
      "SYSTEM",
      "order_read",
      "order_create",
      "product_update",
      "order_delete",
      "role_product_order_reader",
      "USER",
      "order_update",
      "product_read",
      "product_create",
      "product_delete"
   ],
   "scope" : [
      "read",
      "write"
   ],
   "active" : true,
   "user_name" : "admin",
   "client_id" : "trinity",
   "exp" : 1606228675
}
```
See Also - https://github.com/girishaiocdawacs/awacs-cloud-production-shakeout/runs/1447024093?check_suite_focus=true
