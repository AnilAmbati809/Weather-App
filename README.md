# Weather Dashboard

A full-stack weather dashboard application with user authentication, weather data retrieval, and admin functionality.

## Features

- User registration and authentication with JWT
- Weather data retrieval from OpenWeatherMap API
- User dashboard with weather information
- Admin dashboard for user management
- Responsive React frontend
- Spring Boot backend with MySQL database

## Prerequisites

- Java 17 or higher
- Node.js 16 or higher
- MySQL 8.0 or higher (for local development)
- Docker and Docker Compose (for containerized deployment)

## Setup Instructions

### Option 1: Docker Deployment (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd weather-dashboard
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080
   - MySQL: localhost:3307

4. **Stop services**
   ```bash
   docker-compose down
   ```

### Option 2: Local Development

#### Backend Setup

1. **Install MySQL locally**
   - Download and install MySQL 8.0 from https://dev.mysql.com/downloads/mysql/
   - Create a database named `weatherdb`
   - Create a user with appropriate permissions:
     ```sql
     CREATE DATABASE weatherdb;
     CREATE USER 'weatheruser'@'localhost' IDENTIFIED BY 'weatherpass';
     GRANT ALL PRIVILEGES ON weatherdb.* TO 'weatheruser'@'localhost';
     FLUSH PRIVILEGES;
     ```

2. **Configure environment variables** (optional)
   ```bash
   # Set these if you want to override defaults
   export SPRING_DATASOURCE_USERNAME=weatheruser
   export SPRING_DATASOURCE_PASSWORD=weatherpass
   export DB_HOST=localhost
   ```

3. **Build and run the backend**
   ```bash
   cd weather-backend/backend
   ./mvnw clean package -DskipTests
   ./mvnw spring-boot:run
   ```

#### Frontend Setup

1. **Install dependencies**
   ```bash
   cd weather-frontend
   npm install
   ```

2. **Start the development server**
   ```bash
   npm start
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080

## Environment Configuration

The application automatically detects the environment:

- **Docker**: Uses `mysql` host (Docker service name)
- **Local**: Uses `localhost` host (local MySQL instance)

You can override the default behavior using environment variables:

- `DB_HOST`: Database host (defaults to `mysql` in Docker, `localhost` locally)
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `SPRING_DATASOURCE_URL`: Complete JDBC URL (overrides other settings)

## API Endpoints

### Authentication
- `POST /api/auth/signup` - User registration
- `POST /api/auth/login` - User login

### Weather
- `GET /api/weather?city={city}` - Get weather data for a city

### Admin (requires ADMIN role)
- `GET /api/admin/users` - Get all users
- `POST /api/admin/promote/{email}` - Promote user to admin
- `POST /api/admin/demote/{email}` - Demote admin to user

## Database Schema

The application uses the following main entities:
- `User`: User accounts with roles
- `Role`: User roles (USER, ADMIN)
- `UserPreferences`: User-specific settings

## Development

### Running Tests
```bash
# Backend tests
cd weather-backend/backend
./mvnw test

# Frontend tests
cd weather-frontend
npm test
```

### Building for Production
```bash
# Backend
cd weather-backend/backend
./mvnw clean package

# Frontend
cd weather-frontend
npm run build
```

## Troubleshooting

### Common Issues

1. **Database connection failed**
   - Ensure MySQL is running
   - Check database credentials
   - Verify database exists

2. **Port conflicts**
   - Backend uses port 8080
   - Frontend uses port 3000
   - MySQL uses port 3306 (local) or 3307 (Docker)

3. **Environment detection issues**
   - Set `DB_HOST` environment variable explicitly
   - Use `SPRING_DATASOURCE_URL` for complete control

### Logs

View application logs:
```bash
# Docker logs
docker-compose logs backend
docker-compose logs frontend

# Local logs (backend)
# Logs appear in console when running ./mvnw spring-boot:run
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.
