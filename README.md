# DashboardBarbershop App

DashboardBarbershop is a management app for keeping track of customers. The user can register the clients, make reservations, send SMS notifications and view their reservations history.


## Technologies

*  The app is created with Java Spring Boot (back-end) and React (front-end).


* Front-End: React
    * https://github.com/iulian-coder/dashBoardBarberShop
    * Axios
    * Bootstrap's AdminLTE template (for admin dashboard & control panel)
    * React router-dom, hook-form, datepicker, toastify, search-autocomplete, phone-input-2, query-string
    

* Back-End: Spring Boot
    * https://github.com/iulian-coder/dashBoardBarberShopApi
    * Spring Security
      * OAuth2 (Google, Facebook) 
      * Jsonwebtoken JWT
    * Hibernate
        * JPA
        * PostgreSQL
    * SMS service
        * Twilio SDK
    * Lombok


## Features

* Login / Register with social accounts Google, Facebook  as well as email and password
  ![alt text](Screenshots/login.png?raw=true)
* Home page with current month report (new customers , next bookings , confirmed bookings, cancel bookings)
  ![alt text](Screenshots/home.png?raw=true)
* Add new clients
  ![alt text](Screenshots/addclient.png?raw=true)
* Add new booking and send SMS notification
  ![alt text](Screenshots/addbooking.png?raw=true)
* Modify the booking (upcoming, confirm, cancel)
  ![alt text](Screenshots/bookingmodification.png?raw=true)
* Search client with auto-complete suggestions
  ![alt text](Screenshots/search.png?raw=true)
* View all bookings
  ![alt text](Screenshots/bookings.png?raw=true)
* View all clients
  ![alt text](Screenshots/clients.png?raw=true)
