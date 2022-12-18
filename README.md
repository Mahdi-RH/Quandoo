# Mobile Android Engineer Coding Challenge

## Introduction

Thank you for applying for the Mobile Android Engineer vacancy at Quandoo. In efforts to have an understanding of your skill level, we would like to proceed with an assessment that will give you a chance to show Quandoo that you are a battle proven Engineer!

## Task description
In this task we will kindly ask you to improve an existing codebase and make it readable, testable and maintainable up to modern standards. 

This repository is a starter project which is a fully functional android application. This project contains acceptance tests that cover some main features, but not all of them. We expect you to **add all missing acceptance tests along with some unit tests** that will ensure stability of your own code. 

Though provided project is complete in terms of functionality, it is in a very bad shape. We would expect you to **refactor the code base** and make it easy to read, navigate and test. We expect you to **change the architecture** .

## Application description

Provided application is a simplified version of a restaurant table management app that waiters use. Waiter opens the app and sees all the tables in the restaurant, some of the tables are reserved by customers and others are free. Waiter can click on reserved table and unreserve it, or he can click a free table and a customers screen will appear. Waiter can select only one of the customers that will reserve the previously clicked table.

## Data Flow
By default application will download data from fake server endpoints :
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customers.json
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/reservations.json
  - https://s3-eu-west-1.amazonaws.com/quandoo-assessment/tables.json

Once data is downloaded, application will continue working with the data in an offline mode, by heavily relying on caching and local storage.

## Administrative instructions

Main branch of this project is protected and you cannot modify it.

  - Please fork this project to a **[!]**private**[!]** gitlab repository (go to `Settings -> General -> Visibility, project features, permissions -> Project visibility`).
  - Create a new branch from **master**.
  - Refactor the code gradually by submitting new descriptive commits (we will look into your commit messages).
  - We expect to see missing instrumentation **TESTS** along with unit **TESTS**
  - Share the project with gitlab user *quandoo_recruitment_task* (go to `Settings -> Members -> Invite member`, find the user in `Select members to invite` and set `Choose a role permission` to `Developer`)
  - Send us an **ssh** clone link to the repository.
  - Please do not submit or post your solution anywhere else!
  - **We REALLY want to see how you implement your tests!**

## Tech stack we want you to use
  - Retrofit
  - Hilt/Dagger 2 
  - We would love you to use one of the following architectures 
    - Unidirectional Data Flow
    - Clean Architecture
    - MVVM
  - We welcome RxJava/RxKotlin
  - Using Jetpack Compose is a plus

## Test scenarios

#### Open app in offline mode first time [Not Implemented]
  - Given
    - App is open without any internet connection
    - App is opened the first time after fresh install
  - When 
    - First screen appears
  - Then
    - No internet connection dialog is visible

#### Open app in offline mode in cache mode [Not Implemented]
  - Given
    - App is open without any internet connection
    - App is opened after data was presented at least once
  - When 
    - First screen appears
  - Then
    - List of tables is visible and populated with tables

#### Table reservation [Implemented]
  - Given
    - App is open 
    - List of tables visible
    - There is at least one free table visible
  - When 
    - User clicks on free table
  - Then
    - Screen with users appear
  - When 
    - User clicks on a user
  - Then
    - Screen with users tables appear
    - Previously selected table is marked as reserved by a user name

#### Table reservation cancellation [Implemented]
  - Given
    - App is open 
    - List of tables visible
    - There is at least one reserved table
  - When 
    - User clicks on a reserved table
  - Then
    - Confirmation dialog appears
  - When 
    - User clicks on a accept button
  - Then
    - Previously reserved table is marked as free

#### Reserved table visual feedback [Not Implemented]
  - Given
    - There is at least one reserved table
  - When 
    - Table appears
  - Then
    - Make sure reserved table has a name of reserving customer
    - Make sure reserved table has an image of reserving customer