# mock-api

## Run

Requirements:
- Java 11

Execute:
```shell
./gradlew bootRun
```

## Examples:
- GET
```shell
http 'http://localhost:8080/mockadsasd/adasda'
```
response:
```json
{
"result": "/mockadsasd/adasda"
}
```

- POST without body
```shell
http POST 'http://localhost:8080/mockadsasd/adasda'
```
response
```json
{
    "result": "/mockadsasd/adasda"
}
```

- POST with body
```shell
http POST 'http://localhost:8080/mockadsasd/adasda' <<<'{"my_body":"value"}'
```
response
```json
{
    "my_body": "value"
}
```
