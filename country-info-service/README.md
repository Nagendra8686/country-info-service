# country-info-service

A Spring Boot microservice that fetches country data from a public API and applies business rules.

## Build & Run

```
./gradlew build
./gradlew bootRun
```

## Test

```
./gradlew test
```

## Design Rationale
- **Layered architecture**: Controller, Service, Model, Exception for separation of concerns and easy extension.
- **Defensive programming**: Input validation, error handling, and clear mapping.
- **Validation**: Two-letter ISO code validation ensures correct input format.
- **Exception handling**: Custom exceptions for clear error responses.
- **Extensibility**: Adding new endpoints or business rules is straightforward due to modular design.
- **Testing**: Unit and integration tests cover mapping, business rules, and error cases.

## API
- `GET api/v1/countries/{code}`
  - Returns country info for a two-letter ISO code.

## Error Handling
- 400: Invalid code format
- 404: Country not found
- 502: Upstream API failure

## External API
- [restcountries.com](https://restcountries.com/v3.1/alpha/{code})

---

Please see code comments for further details on design and defensive programming choices.
