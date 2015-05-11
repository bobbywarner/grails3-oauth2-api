# Demo API

## Run

Use Gradle:

```sh
./gradlew bootRun
```

## Test

Run functional tests:

```sh
./gradlew integrationTest
```


## Usage

Test the `profile` endpoint:

```sh
curl http://localhost:8080/profile
```

You should receive the following JSON response, which indicates you are not authorized to access the resource:

```json
{
  "error": "unauthorized",
  "error_description": "Full authentication is required to access this resource"
}
```

In order to access the protected resource, you must first request an access token via the OAuth handshake. The API supports both `password` and `client_credentials` OAuth grant types.
The `password` grant type should be used for API calls that require a particular user. The `client_credentials` grant type should be used for API calls when there is no user (i.e. registration).
The entire API is secured by OAuth with the exception of the token endpoint `/oauth/token` which is secured by basic authentication.

### Client Credentials Grant Type (non-user API calls)

Request an OAuth authorization with `client_credentials` grant type using the `client_id` and `client_secret` for basic authentication:

```sh
curl -X POST -u demo-client:123456 http://localhost:8080/oauth/token\?grant_type=client_credentials -H "Accept: application/json"
```
A successful `client_credentials` authorization results in the following JSON response:

```json
{
  "access_token": "cf96e458-12a1-4854-908d-419a35118363",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "read write"
}
```

Then use the `access_token` to call the `/register` endpoint to create a new user account:

```sh
curl http://localhost:8080/register -H "Authorization: Bearer cf96e458-12a1-4854-908d-419a35118363" -H "Content-Type: application/json" -X POST -d '{"username":"bobbywarner", "password":"xyz", "email":"bobbywarner@gmail.com", "fullName": "Bobby Warner"}'
```

A successful registration results in the following JSON response (the password is not returned):

```json
{
  "fullName": "Bobby Warner",
  "email": "bobbywarner@gmail.com",
  "username": "bobbywarner"
}
```
### Password Grant Type (user-specific API calls)

Request an OAuth authorization with `password` grant type using the `client_id` and `client_secret` for basic authentication as well as the username and password for the specific user:

```sh
curl -X POST -u demo-client:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=xyz&username=bobbywarner&grant_type=password&scope=read%20write"
```

A successful `password` authorization results in the following JSON response. This is very similar to the `client_credentials` authorization except it also includes a `refresh_token`.

```json
{
  "access_token": "ff16372e-38a7-4e29-88c2-1fb92897f558",
  "token_type": "bearer",
  "refresh_token": "f554d386-0b0a-461b-bdb2-292831cecd57",
  "expires_in": 43199,
  "scope": "read write"
}
```

Then use the `access_token` returned in the previous request to make authorized requests to any additional protected endpoints:

```sh
curl http://localhost:8080/profile -H "Authorization: Bearer ff16372e-38a7-4e29-88c2-1fb92897f558"
```

### Refresh Token Grant Type

After the specified time period, the `access_token` will expire. Use the `refresh_token` that was returned in the `password` OAuth authorization to retrieve a new `access_token`:

```sh
curl -X POST -u demo-client:123456 http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=f554d386-0b0a-461b-bdb2-292831cecd57"
```
