# Final project DAVE3615

### Market:
The first mentioned currency is the primary currency.

E.g: BTC-USD. Here BTC is the primary currency 

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

#### Buy:
Secondary currency → Primary currency

E.g: BTC → USD

#### Sell:
Primary currency → Secondary currency 

E.g: USD → BTC