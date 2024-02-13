# Timezone POC

## Overview

This document outlines a Proof of Concept (POC) for converting request times from the client's timezone to UTC and response times from UTC back to the client's timezone.

## POST Request

### Endpoint

http://localhost:8080/api/test-timezone/

### Headers

User-TimeZone: Asia/Kabul
Content-Type: application/json



### Body

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "notificationDate": "2024-02-13T11:57:32.720477",
  "localDate": "2024-02-13",
  "date": "2024-02-13 13:09:34"
}

**cURL**

curl --location 'http://localhost:8080/api/test-timezone/' \
--header 'User-TimeZone: Asia/Kabul' \
--header 'Content-Type: application/json' \
--data-raw '{
  "name": "John Doe",
  "email": "john@example.com",
  "notificationDate": "2024-02-13T11:57:32.720477",
  "localDate": "2024-02-13",
  "date": "2024-02-13 13:09:34"
}'

**POST Response**
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "notificationDate": "2024-02-13T11:57:32.720477",
  "localDate": "2024-02-13T04:30:00.000000",
  "date": null,
  "createdAt": "2024-02-13T12:21:25.343401",
  "updatedAt": "2024-02-13T12:21:25.343401"
}

The response returns details of the user's test timezone request including their ID, name, email, notification date, local date, and timestamps for when the data was created and last updated.


GET Request
Endpoint
http://localhost:8080/api/test-timezone/?User-TimeZone=Asia%2FKabul

cURL
curl --location 'http://localhost:8080/api/test-timezone/?User-TimeZone=Asia%2FKabul'


[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john@example.com",
    "notificationDate": "2024-02-13T07:27:32.720477",
    "localDate": "2024-02-13T00:00:00.000000",
    "date": null,
    "createdAt": "2024-02-13T07:51:25.343401",
    "updatedAt": "2024-02-13T07:51:25.343401"
  }
]
