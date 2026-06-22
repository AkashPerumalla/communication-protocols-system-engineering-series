# Validation Workflow

```mermaid
flowchart TD
    Request[Incoming JSON request] --> BeanValidation{@Valid + constraints}
    BeanValidation -->|Pass| Service[Service method executes]
    BeanValidation -->|Fail| ExceptionHandler[GlobalExceptionHandler]
    Service --> Uniqueness{Hostname / IP unique?}
    Uniqueness -->|Pass| Success[ApiResponse with VALIDATION PASSED message]
    Uniqueness -->|Fail| IllegalArgument[IllegalArgumentException]
    IllegalArgument --> ExceptionHandler
    ExceptionHandler --> ErrorJson[Structured JSON error]
```
