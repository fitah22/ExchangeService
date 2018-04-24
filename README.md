# Final project DAVE3615
By: John Kasper Svergja - s305089\
Semester 6 - Spring 2018\
Software Engineering - OsloMet

## Table of content
1. Features
2. Run on your local machine
3. Implementation details
    


## Features
The following microservices has been implemented as REST APIs (excluding frontend):

|Service | URL|
|-------|------|
|Frontend | http://finalproject-dave3615.s3-website.eu-central-1.amazonaws.com|
|Login and user API| http://s305089-login.eu-central-1.elasticbeanstalk.com|
|Trade API| http://s305089-trade.eu-central-1.elasticbeanstalk.com|
|History API | http://s305089-history.eu-central-1.elasticbeanstalk.com|

- Frontend
    - Coded with React (16.3.1) with use of its new Context API.
    - Uses several open source components (please see package.json)
- Login and user profile service \*
    - Login and sign up with username and password (assignment 1.a).
    - Password are encrypted (assignment 1.b).
    - New users get 100 BTC and 100 USD (assignment 1.c).
    - Change password and address (assignment 2.a & 2.b)
    - Information about account balance (assignment 2.c)
    - Spring security for authentication of API calls and login
    - Possibility to create new accounts
    - Integration with trading service to withdraw and deposit money accordingly to a trade.
    - MVC architecture
- Trading service
    - Buy and sell currencies (assignment 3.a)
    - List of all active buy and sell orders (assignment 3.b)
    - Order history over all of the users trade
    - Ability to cancel an active bid/ask.
    - Support for multiple markets (markets can easily be added by appending to an Enum)  
    - JUnit tests for core logic.
    - MVC architecture
- History service
    - Domain driven design (i.e. each type is in its own package)
    
\* The login and user profile service were merged to one microservice 
since I consider them to be overlapping enough that 
this would be most functional.

### Additional improvements that could have been done

- One can always work more on the frontend. Some unwanted behavior is still present, e.g. when adding a new order, most of the page is re-rendered.
- Better unit test coverage. I only tested the tradelogic. Optimally I should have tested logic in the user service as well.
- Making `markets`more dynamic then an `Enum`, by using a text file, database or something else that dosn't require the whole application to reboot.
- I was thinking about making a trading bot for a long time - that is; a bot that complete open orders. It would not have been to much work since the `trading API` already had the necessary endpoints.


## Run on your local machine
__For frontend:__\
You need `npm`or `yarn`. `yarn` is recommended.
```commandline
cd frontend
yarn install
yarn start
```
Your frontend is now running on `http://localhost:3000`.

__For backend:__\
Import the root `pom.xml` into an IDE of your choice.\
Run each of the `Main`-classes. Alternatively, IntelliJ has a "Run dashboard"  where all the services can be started (both individual or simultaneously). 


## Implementation details

### Endpoints
|Service |Endpoint | Method | Description |
|--------|---------|------|-------------|
|Login|          |        | |
| | /all         | GET | For debugging: Get all clients/users. |
| | /currencies  | GET | Get all currencies that is valid for an account. |
| | /login       | POST | Check the authentication and returns this client/user. |
| | /signup      | POST | Register new client, and return this. |
| | /user/balance| GET  | Information about all accounts for the authenticated client. |
| | /user/funds/{currency}/{amount} | GET | Check if the authenticated client has more or equal of the given amount. |
| | /user/newAccountAndClaim | POST | Creates an new account with extra funds. |
| | /user/password | PATCH | Updates the clients password.|
| | /user/address  | PATCH | Updates the clients address.  |
| | /payrecords    | POST | |
| | /reservefunds  | POST | |
|Trade|            |       | |
| | /              |GET| Get all orders|
| | /{market}      |GET| Get all orders that match the given market|
| | /{market}/{transactionType} |GET| Get all orders that match the given market and transaction type|
| | /markets        |GET| Get all markets that one can trade with.|
| | /payrecords     |GET |Get all trade transfer history that is persisted in the database.|
| | /               |POST|Create an new order.|
| | /cancel        |POST|Cancel the given order, and refund the outstanding amount/total that has not been traded|
|History |         |      |             |
| | /all/{emali}   | GET  | Get all authentication and trade transfer history. Email optional |
| | /order{email}         | GET    | Get all orders. Email optional.|
| | /payrecords/{email}   | GET    | Get all trade transfer history. Email optional|
| | /user/{email}| GET    | Get all authentication history. Email optional |
| | /error       | GET    | Get all other errors |
| | /order       | POST   | |
| | /payrecords  | POST   | |
| | /user        | POST   | |
| | /error       | POST   | |





### Market:
A market is defined by a primary and secondary currency (also refered to as `main` and `secondary`)
The first mentioned currency is the primary currency.\
E.g: BTC is the primary currency for  `BTC_USD`.\
Two markets are added by default `BTC_USD` and `ETH_USD`.\
More markets can easily be added by adding them to the `Market` enum. 

### Trade
Trading is handled by the trade micro service, with close integration to the user service.

A trade is defined by the following steps:
1. A client post an order to the trade service
2. The trade service check that the client has available funds to make the order.*
3. The orders total amount is reserved on the client account*
4. The order is matched with existing orders that it can be traded with, into an `Transaction`.
5. A `PayRecord` is created for each order in the `Transaction`.
6. Each client that is affected by the trade get money back to their account (as defined by the `payrecords`).*
 
\* = done with an call to the user service 

#### Order
An order is defined by several fields. Here are some of them:

|Field| Description|
|--------|--------|
| userID  | The email of the user|
| price | The price for each unit|
| amount |The amount this order is for|
| amountTraded |How much of this order has been traded|
| Market | Which market|
| Transaction type | `SELL` or `BUY`|
The total price of the order is calculated as `price * amount`.


#### Buy:
A buy (or bid before the order is fulfilled) is defined buy the transaction where the
secondary currency is spent to get more of the primary currency, i.e secondary currency → primary currency.\
E.g: Buy BTC: Spend the order `total` USD → get `amount` in BTC

#### Sell:
Opposite of `buy`\
Primary currency → secondary currency\
E.g: Sell BTC: Spend  the order `amount` of BTC → get the `total` of USD


__PS: The trade engine support partially fulfilling of the order. 
That is, if the order is 15 BTC, you can still buy 5.5 BTC,
and the rest will still be available to trade with__ 