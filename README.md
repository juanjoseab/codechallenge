# Xoom Code Challenge
this challenge consists in Develop an Android Mobile Application.
The key features were:
- Create a mobile app that can get from an API the list of all countries where Xoom has disbursement options
- The user can mark countries as favorite by clicking on a start
- If the country is a favorite, this will be order at the top of the list and the country row will have a mark as a favorite.
- The favorites should be persistents even if the user reload or restart the application.
- The list should be loaded as a paginate list.  So the API should supports this requirement enabling sending page and items per page parameters.

## The APP
This app have been developed with:
- Android Studio 3.2 as the IDE
- RetroFit2 as the Rest Handler
- Has been tested on devices with:
 - Android 5.1
 - Android 7
 - Android 8.0
 - Android 9.0
 
It has a config.properties file where all the global parameters can be setted, so that way the applicaction development could be easier

## The Rest API
- PHP 7
- Doctrine 2 ORM
- Mysql

### Rest Methods
BASE URL : http://codechallenge.enminu.be/web/index.php/api/v1/

Method  | URL | Parameters | HTTP Method
------------- | ------------- | ------------- | -------------
Countries  | /countries/{page}/{itemsPerPage}/Device_UUID | - {Page} : Number Of Page - {ItemsPerPage} : the page Size - {device_UUID} : the uuid of the device how consumes the API, non of all are mandatory | GET
Add favorites  | /favorite/{CountryId}/{UUID} |  country Id to set as favorite to uuid device | POST
Remove Favorite | /favorite/{CountryId}/{UUID} | The {CountryId} that will be remove from {UUID} favorites | DELETE
