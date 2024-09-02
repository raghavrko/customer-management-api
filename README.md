# customer-management-api
REST-APIs for Customer Management System

# Deployment
- start docker 
- navigate to customer-management-api directory
- run docker-compose up (will start mysql-db)
- run spring boot from IDE 

# API Endpoints
- Use basic auth for authorization 
  - username: user
  - password: test123


- /v1/customers
  - POST
  - Content-Type: application/json
  - body:
    - {
      "name":"Raghav",
      "age":26,
      "dob":"1998-12-01",
      "gender":"M",
      "address":"M-2, test location"
    }

- /v1/customers/{customerId}
    - PUT
    - Content-Type: application/json
    - body:
        - {
          "name":"Raghav",
          "age":26,
          "dob":"1998-12-01",
          "gender":"M",
          "address":"M-2, test location"
          }

- /v1/customers
    - GET
    - query parameters:
      - page(default:0), pageSize(default:10), sortBy(default:name)

- /v1/customers/{customerId}
    - DELETE

- /v1/audits
  - GET
  - query parameters:
    - page(default:0), pageSize(default:100), sortBy(default:creationDate)

- /actuator/health
  - GET
  - No authorization required


# Notes:
- Tables will be created when db is deployed for the first time.
- There could be an initial load, for that created database/data.sql which can be moved to resources folder and will load the data once application starts. I did not load it as it will create the customer records but there will be no audit logs for those.
- I tried running spring boot in docker, the application ran but was not able to connect to db, so commented the commands in docker-compose)

# Performance
- I did some load testing using Jmeter, and the application was able to perform simultaneous operations successfully (50 write request/sec with <150 ms response time).
- My machine has very limit resources and was not able to test more properly. I also wanted to test reactive apis using WebFlux and compare the performance, but I couldn't.