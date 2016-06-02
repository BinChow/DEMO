# DEMO

Demo Outline
============

the demo simulates supermarket business:
there are 3 cashiers in the market, each serves customer randomly in 5 - 10 seconds intervals
there are 3 categories of products, each category initialized with 15 products
the customer comes in 1 - 3 seconds intervals, selects randomly one product(if there is any) and waits for available cashiers
the supermarket initializes the cashiers, storage and starts to run, it stops to run after the customer is served, statistic numbers are provided to analyse the sale


Design Outline
============
the supermarket starts to run business with initialization for business. then it starts two threads for cashiers and customers, each thread synchronizedly access the public resources of prioritised cashier queue, customer waiting list and warehouse storage. supermarket finishes the sale and do the statistics after the children threads are returned

cashier thread
rounds robin in 10 milli seconds interval to check available cashier and serve customer. the reason of round robin is minimizing the thread number and casting down the overhead thread synchronization
check the available cashier and serve customer, once the cashier serves, he/she gets ready for serving next time, once the customer is serve, the customer and good info is recorded for statistics. the thread stops if there is no waiting customers and the warehouse is empty.

customer thread
randomly generate customer with random choice. if there is available product, the warehouse storage is update and the customer is waiting in line for cashier. the thread stops in the warehouse storage is empty.


