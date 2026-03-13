# TrackMint – Personal Finance Tracker API

TrackMint is a Spring Boot REST API that helps users manage their personal finances by tracking income, expenses, and budgets.  
It provides analytics such as spending by category, monthly financial summaries, and top spending categories.

The application demonstrates a clean layered architecture using Spring Boot, Spring Security, JPA, and RESTful API design.

## Features

- User authentication with Spring Security
- Create, update, delete and view transactions
- Organize transactions by categories
- Track income and expenses
- View transactions by date range
- Spending analytics by category
- Monthly financial summary (income, expenses, balance)
- Top spending categories
- Global exception handling for clean API responses

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT-based Authentication
- Hibernate
- MySQL / PostgreSQL
- Maven
- Lombok
- Angular (frontend)

## Project Structure

src/main/java/com/example/trackmint

controller → REST controllers  
services → business logic  
repository → database access  
model → JPA entities  
dto → request and response objects  
exception → custom exceptions and global exception handler  
security → authentication configuration

## API Endpoints
| Method | Endpoint | Description |
|------|------|------|
POST | /transactions | Create a transaction |
GET | /transactions | Get all user transactions |
GET | /transactions/{id} | Get transaction by ID |
PUT | /transactions/{id} | Update a transaction |
DELETE | /transactions/{id} | Delete a transaction |
GET | /transactions/date-range | Get transactions by date range |

| Method | Endpoint | Description |
|------|------|------|
GET | /transactions/spending-by-category | Spending per category |
GET | /transactions/monthly-summary | Monthly income/expense summary |
GET | /transactions/top-spending | Top spending categories |

# Categories
| Method | Endpoint | Description |
|------|------|------|
POST | /categories | Create category |
GET | /categories | Get all categories |
GET | /categories/{id}/transactions | Transactions in category |
GET | /categories/{id}/total | Total spent in category |

## Example Request
POST /transactions

{
"amount": 50.00,
"type": "EXPENSE",
"description": "Lunch",
"date": "2026-03-10",
"categoryId": 1
}


## Example Response
{
"id": 1,
"amount": 50.00,
"type": "EXPENSE",
"description": "Lunch",
"date": "2026-03-10",
"categoryId": 1,
"categoryName": "Food"
}

## Running this Application 
git clone https://github.com/yourusername/trackmint.git
cd trackmint
mvn spring-boot:run
http://localhost:8080

## Error Handling

The application uses a global exception handler to return structured error responses.

Example:

{
"error": "Transaction not found"
}

[//]: # (## Future Improvements)

[//]: # ()
[//]: # (- Budget tracking per category)

[//]: # (- Scheduled alerts when budgets are exceeded)

[//]: # (- Dashboard analytics for frontend charts)

[//]: # (- JWT-based authentication)

[//]: # (- Pagination for transaction lists)

## Author

Jane Irungu

Backend Developer | Java | Spring Boot

GitHub: https://github.com/janeirungu01
