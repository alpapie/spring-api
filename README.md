# Spring Boot CRUD API with MongoDB

This project is a RESTful CRUD API built with Spring Boot and MongoDB. It provides user management and product management functionalities with authentication and authorization.

## Features

- User registration and management
- Product management
- Token-based authentication
- Role-based authorization
- RESTful API design
- MongoDB integration
- Password hashing and salting
- Input validation
- Error handling

## API Endpoints

### User Management

1. **Register User**
   - Endpoint: `POST /register`
   - Access: Public
   - Description: Register a new user

2. **Get All Users**
   - Endpoint: `GET /api/admin/users`
   - Access: Admin only
   - Description: Retrieve a list of all users

3. **Delete User**
   - Endpoint: `DELETE /api/admin/user/{id}`
   - Access: Admin only
   - Description: Delete a user by ID

4. **Update User**
   - Endpoint: `PUT /api/user/{id}`
   - Access: Admin or the user themselves
   - Description: Update user information

### Product Management

1. **Get All Products**
   - Endpoint: `GET /products`
   - Access: Public
   - Description: Retrieve a list of all products

2. **Get Product by ID**
   - Endpoint: `GET /api/admin/product/{id}`
   - Access: Admin only
   - Description: Retrieve a product by ID

3. **Create Product**
   - Endpoint: `POST /api/products`
   - Access: Authenticated users
   - Description: Create a new product

4. **Update Product**
   - Endpoint: `PUT /api/product/{id}`
   - Access: Product owner and Admin
   - Description: Update a product by ID

5. **Delete Product**
   - Endpoint: `DELETE /api/admin/product/{id}`
   - Access: Admin only
   - Description: Delete a product by ID

6. **Get Products by User ID**
   - Endpoint: `GET /api/user/product/{userId}`
   - Access: Admin or the user themselves
   - Description: Retrieve products owned by a specific user

## Authorization

The API uses role-based authorization:

- **Public endpoints**: Accessible without authentication
- **User endpoints**: Require user authentication
- **Admin endpoints**: Require admin role
- **Owner-specific endpoints**: Accessible by the resource owner or admin

## Authentication

This project uses Basic Authentication. Users must authenticate using their email and password. Depending on the user role (ADMIN or USER), access to certain API endpoints may be restricted.

## Security Measures

- Passwords are hashed and salted before storage
- Input validation to prevent MongoDB injection attacks
- Sensitive user information is protected
- HTTPS is used for data protection in transit

## Getting Started
1. Clone the repository:

   ```bash 
    git clone https://github.com/alpapie/lets-play.git
    cd your-repo
   ```

2. Configure the MongoDB connection in the 'application.properties' or 'application.yml' file:

    ```bash
        spring.data.mongodb.uri=mongodb://localhost:27017/your-database
    ```

3. Build the project using Maven:

    ```bash 
        mvn clean install
    ```

## Running the Project
Once the project is set up and MongoDB is running, you can start the application using:

```bash 
    mvn spring-boot:run
```

Alternatively, you can run the generated JAR file:

 ```bash 
    java -jar target/your-app-name.jar
```