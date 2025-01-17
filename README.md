Assumptions
- There are two classes Order and OrderManager and each item in the cart is taken as a separate order.
- There is no login as such, there can only be one admin and there can be multiple customers. A customer has
to register with their name.
- For becoming a VIP, customers have pay Rs.200.
- To show priority in orders, I have implemented priority queue. Unless the order before is completed, denied or
cancelled, we cannot move on to processing the next order.
- In report generation, while calculating total sales, most popular item, total orders, I did not consider orders
that were denied or cancelled.
- When an order is cancelled, a refund request is sent to the admin and the admin can grant the request.
- For handling special requests, when a customer adds an item to the cart, we ask if they have any special request
which is then stored in a hashmap and admin can see the order and the special request.

-For viewing GUI and running tests, we have to go that class and run it separately
-In GUI, I made a screen with two buttons to switch between menu and pending orders and both are handled through I/O
streaming.
-All the I/O streaming files are updated at the end when we choose exit option in CLI.
