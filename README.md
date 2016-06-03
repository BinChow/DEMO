Demo Outline
============

the demo simulates supermarket business:

  * there are 3 cashiers in the market, each serves customer randomly in 5 - 10 seconds intervals

  * there are 3 categories of products, each category initialized with 15 products

  * the customer comes in 1 - 3 seconds intervals, selects randomly one product(if there is any) and waits for available cashiers

  * the supermarket initializes the cashiers, storage and starts to run. it stops to run after all customers are served, statistic numbers are provided to analyse the sale


Design Outline
============

### the supermarket ###

> 1. supermarket starts to run business by initializing for business, then it starts two threads for cashiers and customers

> 2. each thread synchronizedly access the public resources of `prioritised cashier queue`, `customer waiting list` and `warehouse storage`

> 3. supermarket finishes the sale and do the statistics after the children threads are returned

### cashier thread ###

> 1. time slice in 10 milli seconds interval to check available cashier and serve customer. the reason of time slicing is minimizing the thread number and casting down the overhead thread synchronization

> 2. check the available cashier and serve customer

> 3. once the cashier serves, he/she gets ready for serving next time

> 4. once the customer is serve, the customer and good info is recorded for statistics.

> 5. the thread stops if there is no waiting customers and the warehouse is empty.

### customer thread ###

> 1. randomly generate customer with random choice

> 2. if there is available product, the warehouse storage is update and the customer is waiting in line for cashier

> 3. the thread stops in the warehouse storage is empty.

Wrap up
============

 * the muliti thread programming is clear and simple, each thread handles the resposibility in its own perspective

 * father thread and two children threads option may not be the only alternative, actually it can be solve in two threads option: that is father thread (for supermarket and cashier) and child thread (for customer). but the current solution is more clear for review.

