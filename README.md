
## Application description

Provided application is a simplified version of a restaurant table management app that waiters use. Waiter opens the app and sees all the tables in the restaurant, some of the tables are reserved by customers and others are free. Waiter can click on reserved table and unreserve it, or he can click a free table and a customers screen will appear. Waiter can select only one of the customers that will reserve the previously clicked table.

## Data Flow
By default application will download data from fake server endpoints :
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customers.json
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/reservations.json
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/tables.json

Once data is downloaded, application will continue working with the data in an offline mode, by heavily relying on caching and local storage.

