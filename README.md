![Maven Build](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Maven%20Build/badge.svg)
![Build and Deploy to GKE](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Build%20and%20Deploy%20to%20GKE/badge.svg)

![Graal Native Image Release](https://github.com/girishaiocdawacs/awacs-cloud-auth-server/workflows/Graal%20Native%20Image%20Release/badge.svg)




## create a session by generating an access token (expires_in is in second) (authserver) - username: a / password: a

```
curl -X POST "http://localhost:8100/oauth/token?grant_type=client_credentials" -H "Authorization: Basic YTph"                                                             

{"access_token":"c90d628a-4433-4a71-b8d8-db3d1b38fed3","token_type":"bearer","expires_in":43199,"scope":"all"}    
```

## then accessing a resource (productservice)

```curl -X GET -H "Authorization: Bearer c90d628a-4433-4a71-b8d8-db3d1b38fed3" http://localhost:8181/api/product/order/2  

{"id":2,"product":{"id":837,"name":"Brome Grass","price":"3065","distributorName":"Rath, Stroman and Kilback","genericName":"Brome Grass","companyName":"Nelco Laboratories, Inc."},"distributor":{"id":21,"stockistName":"Prohaska, Miller and Morissette","address":"52 Farmco Avenue","email":"pforthk@mediafire.com","productId":21,"availableQuantity":30},"pharmasist":{"id":1,"pharmasistName":"Calvin","pharmasistAddress":"Dinnies","email":"cdinnies0@redcross.org"},"creationDate":[2020,11,6,14,16,43],"quantity":30,"orderStatus":true}   
```

```
mysql> select user_name, token_id , client_id from oauth_access_token;
+-----------+----------------------------------+-----------+
| user_name | token_id                         | client_id |
+-----------+----------------------------------+-----------+
| NULL      | 61b664271b52e5c6522486e20c969b4f | a         |
+-----------+----------------------------------+-----------+
1 row in set (0.00 sec)

```

## In nutshell, product service internally calls again to authserver on /check_token this way to confirm the authenticity of a token being valid

```
curl -X POST -H "Authorization: Basic Yjpi" http://localhost:8100/oauth/check_token?token=c90d628a-4433-4a71-b8d8-db3d1b38fed3
{
    "scope": [
        "all"
    ],
    "active": true,
    "exp": 1604692423,
    "authorities": [
        "ROLE_TRUSTED_CLIENT",
        "ROLE_A",
        "ROLE_B"
    ],
    "client_id": "a"
}
```