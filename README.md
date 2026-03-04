# Credit Card Service

A Spring Boot microservice for managing credit cards and processing bill payments.

## Prerequisites
- **Java**: JDK 21+ (Required for ZGC and Compact Object Headers)
- **Database**: PostgreSQL (Only if `use-mock` is set to `false`)
- **Build Tool**: Maven (Wrapper included)

## Configuration (`src/main/resources/application.yml`)

The service behavior is controlled via `application.yml`:

- `service.credit-card.use-mock`:
  - `true`: Uses `MockCreditCardService` with 20 pre-generated sample cards (no DB required).
  - `false`: Uses `DatabaseCreditCardService` (requires PostgreSQL).
- `spring.datasource`: Database connection settings (URL, username, password).
- `server.port`: Default is `8085`.

## Build & Run

### Using the Shell Script
The easiest way to build and run with optimized JVM settings:
```bash
./run.sh
```

### Manual Build
```bash
./mvnw clean package
```

### Manual Run
```bash
java -jar target/credit-card-service-0.0.1-SNAPSHOT.jar
```

## JVM Performance & Diagnostic Flags
The `run.sh` script includes the following performance-optimized flags:

| Flag | Purpose |
|------|---------|
| `-Xms4G / -Xmx4G` | Sets initial and max heap size to 4GB. |
| `-XX:+AlwaysPreTouch` | Touches every page on the Java heap during startup. |
| `-XX:+UseCompactObjectHeaders` | Reduces object header size (JDK 21+). |
| `-XX:+UseZGC` | Enables the Z Garbage Collector (Low-latency). |
| `-XX:ZCollectionInterval=300` | Sets ZGC collection interval. |
| `-XX:ZAllocationSpikeTolerance=2.0` | Adjusts how aggressively ZGC reacts to allocation spikes. |
| `-Xlog:...` | Detailed logging for safepoints, class loading, and threads. |
| `-XX:+HeapDumpOnOutOfMemoryError` | Automatically dumps heap if OOM occurs. |
| `-XX:HeapDumpPath=./dumps/` | Location for heap dumps. |
| `-XX:+UnlockDiagnosticVMOptions` | Unlocks additional diagnostic options. |
| `-XX:+DebugNonSafepoints` | Allows better profiling outside of safepoints. |
| `-XX:ParallelGCThreads=4` | Parallel GC threads. |
| `-XX:ConcGCThreads=2` | Concurrent GC threads. |
| `-Djava.util.concurrent.ForkJoinPool.common.parallelism=8` | Sets parallelism for common FJP. |
| `-XX:NativeMemoryTracking=summary` | Tracks native memory usage. |

## API Endpoints

- **Get Customer Cards**: `GET /v2/customers/{customerId}/credit-cards`
- **Pay Bill**: `POST /v2/transactions/credit-card-payment`
  - Body: `{"sourceAccountNumber": "...", "creditCardNumber": "...", "paymentAmount": 100.00}`
