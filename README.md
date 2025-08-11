# MapMyTour Backend API

A Spring Boot REST API for managing tour packages with image upload functionality using AWS S3 and PostgreSQL database.

## Features

- **Tour Package Management**: Create, read, update, and delete tour packages
- **Image Upload**: Upload tour images to AWS S3 with public URL generation
- **API Key Authentication**: Secure endpoints with API key-based authentication
- **Location Search**: Search tour packages by location
- **Database Integration**: PostgreSQL database with JPA/Hibernate
- **API Documentation**: Interactive Swagger UI documentation
- **Docker Support**: Containerized deployment

## Tech Stack

- **Framework**: Spring Boot 3.5.4
- **Language**: Java 21
- **Database**: PostgreSQL (Neon Cloud)
- **Cloud Storage**: AWS S3
- **Build Tool**: Gradle
- **Documentation**: OpenAPI 3 (Swagger)
- **Containerization**: Docker

## Setup Instructions

### 1. Clone the Repository

```bash
  git clone https://github.com/kumarwhocodes/MapMyTour.git
  cd mapmytour
```

### 2. Prerequisites

- Java 21 or higher
- Gradle 8.14.3 or higher
- PostgreSQL database (or use the provided Neon cloud database)
- AWS S3 bucket and credentials

### 3. Configuration

Update `src/main/resources/application.properties` with your configurations:

```properties
# Database Configuration
spring.datasource.url=your-postgresql-url
spring.datasource.username=your-username
spring.datasource.password=your-password

# AWS S3 Configuration
aws.s3.bucket-name=your-bucket-name
aws.s3.region=your-region
aws.access-key-id=your-access-key
aws.secret-access-key=your-secret-key
```

### 4. Build the Project

```bash
  ./gradlew build
```

### 5. Run the Application

```bash
  ./gradlew bootRun
```

The application will start on `http://localhost:8080`

### 6. Docker Setup (Optional)

Build and run using Docker:

```bash
  # Build the application
  ./gradlew build

  # Build Docker image
  docker build -t mapmytour-backend .

  # Run Docker container
  docker run -p 8080:8080 mapmytour-backend
```

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
All endpoints (except `/generate-key`) require an API key in the header:
```
x-api-key: your-generated-api-key
```

### Interactive Documentation
Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## API Endpoints

### 1. Generate API Key

**Endpoint**: `POST /api/generate-key`  
**Description**: Generates a new API key for authentication  
**Authentication**: None required

**Request**:
```bash
  curl --location --request POST 'http://localhost:8080/api/generate-key'
```

**Response**:
```json
{
  "apiKey": "your-generated-api-key",
  "message": "API key generated successfully"
}
```

### 2. Create Tour Package

**Endpoint**: `POST /api/tours`  
**Description**: Creates a new tour package  
**Authentication**: Required

**Request**:
```bash
  curl --location 'http://localhost:8080/api/tours' \
--header 'x-api-key: your-generated-api-key' \
--header 'Content-Type: application/json' \
--data '{
    "image": "https://images.unsplash.com/photo-1598275277521-1885382d523a",
    "title": "Himalayan Trek Adventure",
    "description": "14-day trek through the Himalayas",
    "duration": "14Days/13Nights",
    "actualPrice": "$1200",
    "discountedPrice": "$1000",
    "discountInPercentage": "17%",
    "location": "Leh"
}'
```

**Response**:
```json
{
    "id": 1,
    "image": "https://images.unsplash.com/photo-1598275277521-1885382d523a",
    "title": "Himalayan Trek Adventure",
    "description": "14-day trek through the Himalayas",
    "duration": "14Days/13Nights",
    "actualPrice": "$1200",
    "discountedPrice": "$1000",
    "discountInPercentage": "17%",
    "location": "Leh"
}
```

### 3. Get All Tours

**Endpoint**: `GET /api/tours`  
**Description**: Retrieves all tour packages or search by location  
**Authentication**: Required  
**Query Parameters**: 
- `location` (optional): Filter tours by location

**Request**:
```bash
  # Get all tours
curl --location 'http://localhost:8080/api/tours' \
--header 'x-api-key: your-generated-api-key'

# Search by location
curl --location 'http://localhost:8080/api/tours?location=Leh' \
--header 'x-api-key: your-generated-api-key'
```

**Response**:
```json
[
    {
        "id": 1,
        "image": "https://images.unsplash.com/photo-1598275277521-1885382d523a",
        "title": "Himalayan Trek Adventure",
        "description": "14-day trek through the Himalayas",
        "duration": "14Days/13Nights",
        "actualPrice": "$1200",
        "discountedPrice": "$1000",
        "discountInPercentage": "17%",
        "location": "Leh"
    }
]
```

### 4. Get Tour by ID

**Endpoint**: `GET /api/tours/{id}`  
**Description**: Retrieves a specific tour package by ID  
**Authentication**: Required

**Request**:
```bash
  curl --location 'http://localhost:8080/api/tours/2' \
--header 'x-api-key: your-generated-api-key'
```

**Response**:
```json
{
    "id": 2,
    "image": "https://example.com/image.jpg",
    "title": "Beach Paradise",
    "description": "7-day beach vacation",
    "duration": "7Days/6Nights",
    "actualPrice": "$800",
    "discountedPrice": "$650",
    "discountInPercentage": "19%",
    "location": "Goa"
}
```

### 5. Update Tour Package

**Endpoint**: `PUT /api/tours/{id}`  
**Description**: Updates an existing tour package  
**Authentication**: Required

**Request**:
```bash
  curl --location --request PUT 'http://localhost:8080/api/tours/1' \
--header 'x-api-key: your-generated-api-key' \
--header 'Content-Type: application/json' \
--data '{
    "image": "https://updated-image-url.com/image.jpg",
    "title": "Updated Himalayan Trek",
    "description": "Updated 14-day trek through the Himalayas",
    "duration": "14Days/13Nights",
    "actualPrice": "$1300",
    "discountedPrice": "$1100",
    "discountInPercentage": "15%",
    "location": "Leh"
}'
```

**Response**:
```json
{
    "id": 1,
    "image": "https://updated-image-url.com/image.jpg",
    "title": "Updated Himalayan Trek",
    "description": "Updated 14-day trek through the Himalayas",
    "duration": "14Days/13Nights",
    "actualPrice": "$1300",
    "discountedPrice": "$1100",
    "discountInPercentage": "15%",
    "location": "Leh"
}
```

### 6. Delete Tour Package

**Endpoint**: `DELETE /api/tours/{id}`  
**Description**: Deletes a tour package  
**Authentication**: Required

**Request**:
```bash
  curl --location --request DELETE 'http://localhost:8080/api/tours/1' \
--header 'x-api-key: your-api-key'
```

**Response**: `204 No Content`

### 7. Upload Image

**Endpoint**: `POST /api/upload-image`  
**Description**: Uploads an image to AWS S3 and returns the public URL  
**Authentication**: Required  
**Content-Type**: `multipart/form-data`  
**File Types**: JPEG, PNG, GIF (max 5MB)

**Request**:
```bash
curl --location 'http://localhost:8080/api/upload-image' \
--header 'x-api-key: your-generated-api-key' \
--form 'file=@"/path/to/your/image.jpg"'
```

**Response**:
```json
{
    "imageUrl": "https://tour-images-bucket-262004.s3.ap-south-1.amazonaws.com/image-filename.jpg",
    "message": "Image uploaded successfully"
}
```

## Data Model

### Tour Package Structure
```json
{
    "id": "Integer (auto-generated)",
    "image": "String (URL)",
    "title": "String (required)",
    "description": "String (required)",
    "duration": "String (required)",
    "actualPrice": "String (required)",
    "discountedPrice": "String (required)",
    "discountInPercentage": "String (required)",
    "location": "String (required)"
}
```

## Error Handling

The API returns appropriate HTTP status codes:

- `200 OK`: Successful GET, PUT requests
- `201 Created`: Successful POST requests
- `204 No Content`: Successful DELETE requests
- `400 Bad Request`: Invalid request data
- `401 Unauthorized`: Missing or invalid API key
- `404 Not Found`: Resource not found
- `500 Internal Server Error`: Server errors

## Development

### Project Structure
```
src/main/java/dev/kumar/mapmytour/
├── config/          # Configuration classes
├── controller/      # REST controllers
├── dto/            # Data Transfer Objects
├── mapper/         # MapStruct mappers
├── model/          # JPA entities
├── repo/           # JPA repositories
└── service/        # Business logic services
```

### Key Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- PostgreSQL Driver
- AWS SDK S3
- MapStruct
- Lombok
- SpringDoc OpenAPI

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.