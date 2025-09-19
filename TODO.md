# TODO: Complete Pending Tasks

## Frontend Changes
- [x] Remove Home and Dashboard buttons from UserDashboard navigation.
- [x] Update AdminDashboard to fetch and display city usage statistics.

## Backend Changes
- [x] Add city usage logging to WeatherController.
- [x] Add city usage stats endpoint to AdminController.

## End-to-End Testing
- [x] Start Spring Boot backend server
- [x] Start React frontend development server
- [ ] Test admin authentication flow (login as admin)
- [ ] Make multiple weather requests to populate usage data
- [ ] Verify city usage stats display correctly in AdminDashboard
- [ ] Test security (non-admin users cannot access stats)

## UI/UX Review and Improvements
- [ ] Review current list display for readability and responsiveness
- [ ] Implement sorting by usage count (highest first)
- [ ] Improve formatting with better spacing and typography
- [ ] Ensure mobile responsiveness
- [ ] Consider adding simple visualization (progress bars or icons)

## Bug Fixes and Validation
- [ ] Address any issues found during testing
- [ ] Validate error handling for network failures or auth issues
- [ ] Update TODO.md with completion status

## Docker Deployment Setup
- [x] Create Dockerfile for backend (Spring Boot)
- [x] Create Dockerfile for frontend (React)
- [x] Create docker-compose.yml with MySQL, backend, and frontend services
- [x] Update application.properties to use environment variables for database
- [ ] Test docker-compose build and run
- [ ] Verify full-stack functionality in containers
