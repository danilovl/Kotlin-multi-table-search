# Simple multi table search by Kotlin #

## About ##

Simple multi table search where one query can search in several tables by certain parameters.

### Requirements

* Kotlin 2.0.0
* SQL DB

### 1. Installation

Change `DATABASE_DSN` in `.env`

``` text
DATABASE_DSN="root:root@tcp(127.0.0.1:3306)/db?charset=utf8mb4&parseTime=True&loc=Local"
```

Run command:

``` bash
run Application.main()
```

### 2. Usage

Create POST request to `localhost:8080/multi-table-search` with `json` and `X-Auth-Token` token

Request:
``` json
[
  {
    "identifier": "product-1",
    "tableName": "product",
    "search": "%orb%",
    "selectColumns": ["id", "name", "created_at"],
    "whereColumns": ["name"],
    "limit": 5
  },
  {
    "identifier": "product-2",
    "tableName": "product",
    "search": "%m&m",
    "selectColumns": ["id", "name", "created_at"],
    "whereColumns": ["name"],
    "limit": 5
  },
  {
    "identifier": "product-3",
    "tableName": "product",
    "search": "asus%",
    "selectColumns": ["id", "name", "created_at"],
    "whereColumns": ["name"],
    "limit": 5
  },
  {
    "identifier": "product-4",
    "tableName": "product",
    "search": "days",
    "searchCondition": "=",
    "selectColumns": ["id", "name", "created_at"],
    "whereColumns": ["name"],
    "limit": 5
  },
  {
    "identifier": "product-5",
    "tableName": "product",
    "search": "black",
    "selectColumns": ["id", "name", "created_at"],
    "whereColumns": ["name"],
    "limit": 5
  },
  {
    "identifier": "city-1",
    "tableName": "city",
    "selectColumns": ["*"],
    "limit": 100,
    "offset": 2
  }
]
```

Unsuccessful responses:
```json
{
  "error": {
    "type": "InternalError",
    "message": [
      "Server error"
    ]
  },
  "result": null
}
```

```json
{
  "error": {
    "type": "ParamError",
    "message": [
      "Identifier must be not empty"
    ]
  },
  "result": null
}
```

Successful response:
``` json
{
  "error": null,
  "result": {
    "city-1": {
      "count": 6,
      "result": [
        {
          "created_at": "2020-07-18T09:21:36+02:00",
          "id": "3",
          "id_country": "3",
          "latitude": null,
          "longitude": null,
          "name": "Moscow",
          "updated_at": "2020-07-18T09:21:36+02:00"
        },
        {
          "created_at": "2021-11-17T22:03:31+01:00",
          "id": "6",
          "id_country": "5",
          "latitude": null,
          "longitude": null,
          "name": "Amsterdam",
          "updated_at": "2021-11-17T22:03:31+01:00"
        }
      ]
    },
    "product-1": {
      "count": 1,
      "result": [
        {
          "created_at": "2017-03-23T00:00:00+01:00",
          "id": "32",
          "name": "Orbit 10x"
        }
      ]
    },
    "product-2": {
      "count": 5,
      "result": [
        {
          "created_at": "2017-03-18T00:00:00+01:00",
          "id": "8",
          "name": "M&M's 90g"
        },
        {
          "created_at": "2017-03-24T00:00:00+01:00",
          "id": "39",
          "name": "M&M's 150g"
        },
        {
          "created_at": "2017-04-21T00:00:00+02:00",
          "id": "121",
          "name": "M&M's 90g"
        },
        {
          "created_at": "2017-06-09T00:00:00+02:00",
          "id": "177",
          "name": "M&M's 150g"
        },
        {
          "created_at": "2018-10-18T00:00:00+02:00",
          "id": "412",
          "name": "M&M's 250g"
        }
      ]
    },
    "product-3": {
      "count": 2,
      "result": [
        {
          "created_at": "2017-05-24T00:00:00+02:00",
          "id": "159",
          "name": "Myš ASUS WT465 V2 černá"
        },
        {
          "created_at": "2022-02-01T18:38:15+01:00",
          "id": "949",
          "name": "27\" ASUS TUF Gaming VG27AQL1A"
        }
      ]
    },
    "product-4": {
      "count": 2,
      "result": [
        {
          "created_at": "2017-04-08T00:00:00+02:00",
          "id": "90",
          "name": "7 Days Bake Rolls pizza 80g"
        },
        {
          "created_at": "2020-07-10T09:42:33+02:00",
          "id": "663",
          "name": "7Days Mini Croissants kakao 200g"
        }
      ]
    },
    "product-5": {
      "count": 3,
      "result": [
        {
          "created_at": "2018-06-20T00:00:00+02:00",
          "id": "320",
          "name": "Xiaomi Amazfit Bip Black"
        },
        {
          "created_at": "2019-04-13T00:00:00+02:00",
          "id": "480",
          "name": "BAGGY Trail Black M"
        },
        {
          "created_at": "2019-11-12T00:00:00+01:00",
          "id": "568",
          "name": "Adidas Copa 19.3 TF - Solar Yellow/Core Black"
        }
      ]
    }
  }
}
```
