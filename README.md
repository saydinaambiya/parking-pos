# Parking POS System

## Tech Stack

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **PostgreSQL** (production)
- **H2 Database** (development/testing)
- **Maven** (build tool)
- **Lombok** (code generation)

## Prerequisites

Ensure your system has:

- Java 17 or higher
- Maven 3.6+ 
- PostgreSQL (for production)
- Git

## Environment Variables Configuration

Before running the application, set the following environment variables:

```bash
# Database Configuration
DB_URL=jdbc:postgresql:your_db_url || //localhost:5432/parking_pos
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# Application Configuration
APP.PARKING.RATE-PER-HOUR=3000

# CORS Configuration
CORS_ALLOWED_ORIGIN=http://localhost:5173
CORS_ALLOWED_METHOD=GET,POST,PUT,DELETE,OPTIONS
CORS_ALLOWED_HEADERS=*
CORS_ALLOW_CREDENTIALS=true
```

## How to Run the Application

### 1. Clone Repository
```bash
git clone https://github.com/saydinaambiya/parking-pos.git
cd parking-pos
```

### 2. Setup PostgreSQL Database
```sql
CREATE DATABASE parking_pos;
```
### 3. Run the Application
### 4. Verify Application is Running
Open browser and access: `http://localhost:8080/api/v1/parking/health`

If successful, it will display: `"Parking POS API is running"`

## API Endpoints

Base URL: `http://localhost:8080/api/v1`

### 1. Health Check
```
GET /parking/health
```

### 2. Vehicle Check-In
```
POST /parking/check-in
Content-Type: application/json

{
  "vehiclePlateNumber": "B1010A",
  "vehicleType": "CAR"
}
```

**Vehicle Types:**
- `CAR`
- `TRUCK` 
- `MOTORCYCLE`

### 3. Get Active Ticket
```
GET /parking/ticket/{vehiclePlateNumber}
```

### 4. Vehicle Check-Out
```
POST /parking/check-out
Content-Type: application/json

{
  "vehiclePlateNumber": "B1010A",
  "paymentMethod": "CASH"
}
```

**Payment Methods:**
- `CASH`
- `CARD`

## API Usage Examples

### Check-In
```bash
curl -X POST http://localhost:8080/api/v1/parking/check-in \
  -H "Content-Type: application/json" \
  -d '{
    "vehiclePlateNumber": "B1010A",
    "vehicleType": "CAR"
  }'
```

### Get Ticket
```bash
curl -X GET http://localhost:8080/api/v1/parking/ticket/B1010A
```

### Check-Out
```bash
curl -X POST http://localhost:8080/api/v1/parking/check-out \
  -H "Content-Type: application/json" \
  -d '{
    "vehiclePlateNumber": "B1010A",
    "paymentMethod": "CASH"
  }'
```

## Project Structure

```
src/
├── main/
│   ├── java/org/example/parkingpos/
│   │   ├── config/          # Application configuration
│   │   ├── controller/      # REST Controllers
│   │   ├── exception/       # Exception handlers
│   │   ├── model/          # Data models (DTO, Entity, Enums)
│   │   ├── repository/     # Data access layer
│   │   ├── service/        # Business logic
│   │   └── Util/          # Utility classes
│   └── resources/
│       └── application.properties
```

## Development

## Troubleshooting

### Environment Variables Not Set
Ensure all environment variables are set before running the application.

## Monitoring

The application uses Spring Boot Actuator for monitoring. Endpoints available at:
- Health: `http://localhost:8080/api/v1/actuator/health`
- Info: `http://localhost:8080/api/v1/actuator/info`