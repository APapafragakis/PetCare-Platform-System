<summary><strong>Pet Care Management System </strong></summary>

## Description

The Pet Care Management System, also known as "Pet Care," is a web-based information system designed to facilitate pet owners and pet keepers in managing pet care services, much like the popular booking platforms. The system aims to streamline the process of finding, booking, and managing pet care arrangements, ensuring a smooth and convenient experience for both pet owners and pet keepers.

## Features

- **Scenario 1: Keeper Selection**: Pet owners can view all available pet keepers and select preferred dates for pet hosting.
- **Scenario 2: Pet Registration**: Pet owners can register information about their pets, including species, breed, and special requirements.
- **Scenario 3: Keeper Management**: Pet keepers can manage their reservations and access additional information about the pets they will be hosting.
- **Scenario 4: Messaging**: Pet owners can communicate with pet keepers via messaging functionality.
- **Scenario 5: Reviews**: Pet owners can leave reviews and ratings for pet keepers based on their experience.

## User Types

1. **Administrator**: Central administrator with full system control, including user management and access to statistics.
2. **Pet Owner**: Users who own pets and require pet care services.
3. **Pet Keeper**: Users who offer pet care services and manage pet hosting arrangements.
4. **Guest**: Visitors who can view basic information about the system but cannot make reservations or access user-specific features.

## Basic System Functions

### Administrator Functions
- Separate login for administrators.
- User management, including the ability to delete users.
- Access to system statistics.

### Pet Keeper Functions
- Updating profile information and registering details about hosting space.
- Managing reservations and accessing pet information.
- Messaging system for communication with pet owners.
- Viewing statistics related to bookings and earnings.

### Registered User Functions
- Updating profile information.
- Registering pets and specifying care requirements.
- Finding and viewing pet keepers based on various criteria.
- Booking pet hosting services and viewing reservations.
- Leaving reviews and ratings for pet keepers after the hosting period.

### Guest Functions
- Viewing basic information about the system.
- Browsing available pet keepers without the ability to make reservations.

**Note:** Registered users should not have access to other users' information, except for administrators.

## Technologies Used
- **Frontend:** HTML, CSS, JavaScript
- **Backend:** Node.js, Express.js
- **Database:** MongoDB (managed with XAMPP and phpMyAdmin)
- **Authentication:** JSON Web Tokens (JWT)
- **Additional Libraries:** 
    - Socket.IO (for real-time messaging)
    - AJAX (for asynchronous client-server communication)
    - Servlets (for server-side Java programming)

**Note:** We utilized XAMPP and phpMyAdmin for managing the MySQL database. AJAX was employed for asynchronous data exchange between the client and server, while Servlets facilitated server-side Java programming to handle HTTP requests and responses.

## Contributors
- Alexandros Papafragkakis, CSD5084
- Dimitris Dalis, CSD4700

## Extra info
It is the final project of CS-359 Web Development in my university.
