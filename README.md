![Maven Build](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Maven%20Build/badge.svg)

![Graal Native Image Release](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Graal%20Native%20Image%20Release/badge.svg)

![Build and Deploy to GKE](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Build%20and%20Deploy%20to%20GKE/badge.svg)


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


####  CSP Security checks - clear
``` 
 https://securityheaders.com/?q=https%3A%2F%2Fqa.awacscloud.tech%2F&followRedirects=on

```

![oauth_nutshell](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/blob/master/src/main/resources/security.png?raw=true)

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

### upload a pdf file

```
curl -kSs -X POST https://app.awacscloud.tech/productservice/api/files/ -F "file=@.cockpit/demo.pdf" -H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJh dWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIzNjAxOCwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmV hZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZW F0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6IjAxMDY2ZmJhLTk5ZTktNDk3ZC1hZDE5LTgyNGM4OThlYmQxMCIsImNsaWVudF9pZCI6Im5lbyJ9.ofqBYuY6UH2TIT6EuTNE-onJTQFj6MzlzMFmDVyRojY"             
File successfully uploaded (demo.pdf!)  
```

### process an uploaded pdf file
```
curl -kSs -X GET https://app.awacscloud.tech/productservice/api/files/pdf/tenant/swami_medical/demo.pdf \
-H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJh dWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIzNjAxOCwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmV hZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZW F0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6IjAxMDY2ZmJhLTk5ZTktNDk3ZC1hZDE5LTgyNGM4OThlYmQxMCIsImNsaWVudF9pZCI6Im5lbyJ9.ofqBYuY6UH2TIT6EuTNE-onJTQFj6MzlzMFmDVyRojY"

PHI;UT;Greg;Gross;Left;101;11;25;0;8;1;0.248
SDP;RF;Tony;Gwynn;Left;642;107;211;14;59;37;0.329
LAD;3B;Jeff;Hamilton;Right;147;22;33;5;19;0;0.224
ATL;LF;Terry;Harper;Right;265;26;68;8;30;3;0.257
HOU;CF;Billy;Hatcher;Right;419;55;108;6;36;38;0.258
PHI;1B;Von;Hayes;Left;610;107;186;19;98;24;0.305
STL;C;Mike;Heath;Right;190;19;39;4;25;2;0.205
NYM;LF;Danny;Heep;Left;195;24;55;5;33;1;0.282
NYM;1B;Keith;Hernandez;Left;551;94;171;13;83;2;0.31

```

### upload an excel file 

```
curl -kSs -X POST https://app.awacscloud.tech/productservice/api/files/ -F "file=@.cockpit/test.xlsx" -H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJh dWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIzNjAxOCwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmV hZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZW F0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6IjAxMDY2ZmJhLTk5ZTktNDk3ZC1hZDE5LTgyNGM4OThlYmQxMCIsImNsaWVudF9pZCI6Im5lbyJ9.ofqBYuY6UH2TIT6EuTNE-onJTQFj6MzlzMFmDVyRojY"

File successfully uploaded (test.xlsx!) 
```

### process an uploaded excel file
```
curl -kSs -X POST https://app.awacscloud.tech/productservice/api/files/excel/tenant/swami_medical/distributor/test.xlsx -F "file=@.cockpit/test.xlsx" -H "Authorization":"bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJh dWQiOlsicmVzb3VyY2Utc2VydmVyLXJlc3QtYXBpIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sImV4cCI6MTYwNjIzNjAxOCwiYXV0aG9yaXRpZXMiOlsiU1lTVEVNIiwib3JkZXJfcmV hZCIsIm9yZGVyX2NyZWF0ZSIsInByb2R1Y3RfdXBkYXRlIiwib3JkZXJfZGVsZXRlIiwicm9sZV9wcm9kdWN0X29yZGVyX3JlYWRlciIsIlVTRVIiLCJvcmRlcl91cGRhdGUiLCJwcm9kdWN0X3JlYWQiLCJwcm9kdWN0X2NyZW F0ZSIsInByb2R1Y3RfZGVsZXRlIl0sImp0aSI6IjAxMDY2ZmJhLTk5ZTktNDk3ZC1hZDE5LTgyNGM4OThlYmQxMCIsImNsaWVudF9pZCI6Im5lbyJ9.ofqBYuY6UH2TIT6EuTNE-onJTQFj6MzlzMFmDVyRojY" | json_pp
[                                                                                                                                                                              {                                                                                                                                                                              "name" : "SUMATRIPTAN",                                                                                                                                                     "genericName" : "sumatriptan",                                                                                                                                              "quantity" : 17,                                                                                                                                                            "address" : "00 Kingsford Plaza",                                                                                                                                           "distributorName" : "Physicians Total Care, Inc.",                                                                                                                          "id" : 14,                                                                                                                                                                  "price" : 54                                                                                                                                                             },                                                                                                                                                                          {                                                                                                                                                                              "address" : "26 Banding Court",                                                                                                                                             "distributorName" : "STAT Rx USA LLC",                                                                                                                                      "id" : 71,                                                                                                                                                                  "price" : 60,                                                                                                                                                               "genericName" : "GLIMEPIRIDE",                                                                                                                                              "quantity" : 94,                                                                                                                                                            "name" : "GLIMEPIRIDE"                                                                                                                                                   },                                                                                                                                                                          {                                                                                                                                                                              "id" : 61,                                                                                                                                                                  "price" : 93,                                                                                                                                                               "address" : "4857 Fordem Court",                                                                                                                                            "distributorName" : "Perrigo New York Inc",                                                                                                                                 "quantity" : 81,                                                                                                                                                            "genericName" : "Halobetasol Propionate",                                                                                                                                   "name" : "Halobetasol Propionate"                                                                                                                                        },                                                                                                                                                                          {                                                                                                                                                                              "name" : "potassium chloride",                                                                                                                                              "distributorName" : "NCS HealthCare of KY, Inc dba Vangard Labs",                                                                                                           "address" : "48254 Oneill Street",                                                                                                                                          "price" : 86,                                                                                                                                                               "id" : 43,
      "genericName" : "potassium chloride",
      "quantity" : 63
   },
   {
      "genericName" : "SOYBEAN OIL",
      "quantity" : 59,
      "distributorName" : "Baxter Healthcare Corporation",
      "address" : "2 Paget Park",
      "id" : 56,
      "price" : 1,
      "name" : "Intralipid"
   },
   {
      "name" : "Clonidine Hydrochloride",
      "price" : 13,
      "id" : 78,
      "distributorName" : "State of Florida DOH Central Pharmacy",
      "address" : "7761 Summerview Parkway",
      "quantity" : 85,
      "genericName" : "CLONIDINE HYDROCHLORIDE"
   },
   {
      "name" : "Extra Strength Headache Relief",
      "price" : 84,
      "id" : 90,
      "distributorName" : "CHAIN DRUG MARKETING ASSOCIATION INC",
      "address" : "82 Hollow Ridge Plaza",
      "quantity" : 85,
      "genericName" : "Acetaminophen, Aspirin and caffeine"
   },
   {
      "address" : "55 Bayside Terrace",
      "distributorName" : "Bryant Ranch Prepack",
      "price" : 8,
      "id" : 34,
      "genericName" : "gabapentin",
      "quantity" : 65,
      "name" : "Gralise"
   },
   {
      "name" : "Treatment Set TS332343",
      "genericName" : "Treatment Set TS332343",
      "quantity" : 88,
      "address" : "47926 Merrick Pass",
      "distributorName" : "Antigen Laboratories, Inc.",
      "price" : 59,
      "id" : 58
   },
   {
      "genericName" : "calcium carbonate",
      "quantity" : 94,
      "address" : "78117 Erie Drive",
      "distributorName" : "Solbin Co., Ltd",
      "id" : 74,
      "price" : 47,
      "name" : "Solmeet denti doctor"
   }
]
```

### OR, User IAM Profile call - 

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

### In nutshell, product service will do internally stub call with authserver on grpc check_token this way to confirm the authenticity of a token being valid
```
grpcurl --rpc-header "Authorization: Basic YWRtaW46YWRtaW4xMjM0" --plaintext -d "{\"token\": \"tokenvalue\"}" authserver:9345 com.aiocdawacs.boot.grpc.interface.GrpcAwacsTokenService/CheckToken
```
### See GRPC Implementation
v2.0
|- GRPC.md


#### CSP Report Endpoint Incident payload (No notification release so far just a stackdriver warn logging)
```sh
curl -kisS --location --request GET 'https://qa.awacscloud.tech/authserver/actuator/csp/report' \
-H 'Content-Type: application/json' \
--data-raw '{
    "csp-report": {
        "document-uri": "https://example.com/foo/bar",
        "referrer": "https://www.google.com/",
        "violated-directive": "default-src self",
        "original-policy": "default-src self; report-uri /csp-hotline.php",
        "blocked-uri": "http://evilhackerscripts.com"
    }
}'
HTTP/1.1 200
Server: nginx/1.19.5
Date: Wed, 25 Nov 2020 13:04:55 GMT
Content-Type: text/plain;charset=UTF-8
Content-Length: 20
Connection: keep-alive
Content-Security-Policy: default-src 'self'; script-src 'self' https://app.awacscloud.tech; object-src 'self' https://qa.awacscloud.tech; report-uri /authserver/actuator/csp/report
Strict-Transport-Security: max-age=16070400; includeSubDomains
X-Frame-Options: DENY
X-XSS-Protection: 1; mode=block
X-Content-Type-Options: nosniff
Referrer-Policy:: no-referrer
Feature-Policy:: none
Permissions-Policy: accelerometer=(), camera=(), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), payment=(), usb=()

incident acknowledge
```

See Also - https://github.com/girishaiocdawacs/awacs-cloud-production-shakeout/runs/1447024093?check_suite_focus=true
