# Online-shop project [Backend and Telegram Bot with ChatGPT]
[ ⚡Here you can see the video Swagger test⚡](https://youtu.be/OlRL9_uWFu4)
[![img_2.png](img_2.png)](https://youtu.be/OlRL9_uWFu4)
## Links to the [layout](https://github.com/vytiaganets/onlineshop/blob/main/src/main/resources/db/bd.jpg), [technical specifications](https://github.com/vytiaganets/onlineshop/blob/main/src/main/resources/db/tz.txt) or requirements, [deployed implementation](https://github.com/vytiaganets/onlineshop/blob/main/src/main/resources/db/rest.txt)
## The application allows customers to select a product from the catalog, add it to the cart, place an order and track its status in real time. For administrators and employees, the application provides tools for managing the product catalog, orders, promotions and sales analytics.
## Ask a question to the artificial intelligence (AI). The user can ask questions and get answers from our AI, which will help them make decisions about their buy.
## Screenshot of the project's main page
![img_1.png](img_1.png)
# Стек технологий
Git, Apache Maven, Docker, Spring Framework, Lombok, JUnit
## List of technologies used and what they were used for
Java SDK, SQL, Spring Boot
# Author of the project
[Andrii](https://github.com/vytiaganets/)
*I am constantly working to improve our bot and will be happy to receive your feedback and suggestions.*
## Database structure of online-shop

### Table Users (Users details)

| Column name   | Type         | Description                                   |
|---------------|--------------|-----------------------------------------------|
| UserID        | bigint       | id key of row - unique, not null, primary key | 
| Name          | varchar(255) | name of user                                  |
| Email         | varchar(255) | email of user                                 |
| PhoneNumber   | varchar(20)  | phone number of user                          |
| PasswordHash  | varchar(255) | password                                      |
| Role          | ENUM         | role                                          |

### Table Products (Products details)

| Column name       | Type          | Description                                   |
|-------------------|---------------|-----------------------------------------------|
| ProductID         | bigint        | id key of row - unique, not null, primary key |
| Name              | varchar(255)  | name of product                               |
| Description       | varchar(255)  | description of product                        |
| Price             | decimal(10,2) | price of product                              |
| ImageURL          | varchar(255)  | url of photo                                  |
| CreatedAt         | timestamp     | time of created                               |
| UpdatedAt         | timestamp     | time of updated                               |
| DiscountPrice     | decimal       | discount price                                |
| CategoryID        | bigint        | id of category                                |

### Table Carts (Carts details)

| Column name  | Type    | Description                                   |
|--------------|---------|-----------------------------------------------|
| CartID       | bigint  | id key of row - unique, not null, primary key |
| UserID       | bigint  | id username                                   | 


### Table Cart items (Cart items details)

| Column name  | Type    | Description                                   |
|--------------|---------|-----------------------------------------------|
| 	CartItemID  | bigint  | id key of row - unique, not null, primary key | 
| 	CartID      | bigint  | id card                                       | 
| 	ProductID   | bigint  | id product                                    | 
| 	Quantity    | integer | quantity                                      | 



### Table Categories (Categories details)

| Column name      | Type         | Description                                   |
|------------------|--------------|-----------------------------------------------|
| 	CategoryID      | bigint       | id key of row - unique, not null, primary key | 
| 	Name            | varchar(255) | name of categories                            |

### Table Favorites (Favorites details)

| Column name | Type     | Description                                   |
|-------------|----------|-----------------------------------------------|
| FavoriteID  | bigint   | id key of row - unique, not null, primary key |
| UserID      | bigint   | id of user                                    |         
| ProductID   | bigint   | id of product                                 |                   

### Table Order items (Order items details)

| Column name     | Type    | Description                                   |
|-----------------|---------|-----------------------------------------------|
| OrderItemID     | bigint  | id key of row - unique, not null, primary key |
| OrderID         | bigint  | id of order                                   |                     
| ProductID       | bigint  | id of product                                 |                    
| Quantity        | integer | quantity                                      | 
| PriceAtPurchase | decimal | price at purchase                             | 

### Table orders (Orders details)

| Column name     | Type         | Description                                   |
|-----------------|--------------|-----------------------------------------------|
| OrderID         | bigint       | id key of row - unique, not null, primary key |
| UserID          | bigint       | id of user                                    |
| DeliveryAddress | varchar(255) | delivery address                              |
| DeliveryMethod  | varchar(255) | delivery method                               |
| ContactPhone    | varchar(255) | contact phone                                 |
| Status          | enum         | status                                        |
| CreatedAt       | timestamp    | time of created                               |
| UpdatedAt       | timestamp    | time of updated                               |

### Table Product pending (Product pending details)

| Column name     | Type         | Description                                   |
|-----------------|--------------|-----------------------------------------------|
| ProductID       | bigint       | id key of row - unique, not null, primary key |
| Name            | varchar(255) | name of product                               |         
| Count           | decimal      | count                                         |
| CreatedAt       | timestamp    | time of created                               |

### Table Product profit (Product profit details)

| Column name | Type           | Description                                   |
|-------------|----------------|-----------------------------------------------|
| ProductID   | bigint         | id key of row - unique, not null, primary key |
| Period      | varchar(255)   | period                                        |         
| Sum         | decimal(10, 2) | sum                                           |


### Table Product count (Product count details)

| Column name | Type           | Description                                   |
|-------------|----------------|-----------------------------------------------|
| ProductID   | bigint         | id key of row - unique, not null, primary key |
| Name        | varchar(255)   | name of product                               |         
| Count       | decimal        | count                                         |
| Sum         | decimal(10, 2) | sum                                           |



