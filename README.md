<h1 align="left"> Qardio </h1>

### Qardio - Temperature API

#How To Run:
- The project has been dockerized. In order to run the service, it is sufficient to call the **"docker-compose up"** command from the path of the project folder which include docker-compose.yaml.
  It can also be run by TemperatureApplication.java class. There are no need any environment variables.

- Unit tests can be run with the **"mvn test"** command.

# Technical Details
- The application was developed using Java 11.
- Spring Boot was used as application framework.
- H2 in memory db was used as database.
- Junit was used for unit tests.
- The application was built with maven.
- The application was dockerized and a docker-compose.yml was written.


#Sample Request And Response:

You can find the sample json file for Postman under the **"Postman-Files"** directory under the project directory.

**- Sample Request and Response For Temperature Data Saving:**

For Temperature Data Saving;

**Post Url**: http://localhost:8080/temperature/save

**Body**:
```javascript
{
 "deviceId": "test",
 "tempDegree": 24.0,
 "date": "2022-06-20T13:32:59.286402400"
}
```

**Response**:
```javascript
{
    "timestamp": "2022-06-20T12:07:29.6026728"
}
```

For Bulk Temperature Data Saving;
**Post Url**: http://localhost:8080/temperature/bulk-save

**Body**:
```javascript
[{
  "deviceId": "test2",
  "tempDegree": 21.0,
  "date": "2022-06-20T13:32:59.286402400"
}, {
  "deviceId": "test1",
  "tempDegree": 28.0,
  "date": "2022-06-19T13:32:59.286402400"
}]
```
**Response**:
```javascript
{
  "timestamp": "2022-06-20T12:08:34.9800912"
}
```

**-Sample Request and Response For Temperature Aggregate Data Report:**

For Hourly Aggregate Data;
**URL:** http://localhost:8080/temperature/hourly

For Daily Aggregate Data;
**URL:** http://localhost:8080/temperature/daily

**Response:**
```javascript
{
  "tempDataCount": 3,
  "averageTempDegree": 23.333333333333332,
  "minTempDegree": 21.0,
  "maxTempDegree": 25.0
}
```
