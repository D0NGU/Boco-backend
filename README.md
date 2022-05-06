<p align="center"><a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend"><img src="assets/logo.svg" width="150"></a></p> 
<h2 align="center"><b>BoCo WS Backend</b></h2>

<p align="center">
  <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/-/commits/main"><img alt="pipeline status" src="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/badges/main/pipeline.svg" /></a>
  <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/-/commits/main"><img alt="coverage report" src="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/badges/main/coverage.svg" /></a> 
</p>

<p align="center">A standalone server application for BoCo utilizing Java Spring Boot (with embedded database).</p>

<hr>
<p align="center">
  <a href="http://idatt2106_2022_08.pages.stud.idi.ntnu.no/backend/allpackages-index.html">JavaDoc</a>
  &bull; <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/frontend/-/wikis/home">Wiki</a> 
  &bull; <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/-/boards">Issue board</a>
  &bull; <a href="#installation">Installation</a>
  &bull; <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/frontend">Frontend</a>
  &bull; <a href="#authors">Authors</a> 
</p>
<hr>

## Requirements

This application requires the following:

 * [Java Development Kit (JDK) 11](https://adoptium.net/?variant=openjdk11) or higher
 * [Apache Maven 3.8.5](https://maven.apache.org/download.cgi) or higher

## Installation

Clone repository locally, browse to project folder and execute:

```cmd
  mvn install
  mvn spring-boot:run
```
For running on the MySQL database instead execute:
```cmd
  mvn spring-boot:run -'Dspring-boot.run.profiles=mysql'
```
To run unit tests execute:
```cmd
  mvn clean test
```

## Authors

- [@minhdn](https://gitlab.stud.idi.ntnu.no/minhdn)
- [@matssg](https://gitlab.stud.idi.ntnu.no/matssg)
- [@dominykm](https://gitlab.stud.idi.ntnu.no/dominykm)
- [@hannagn](https://gitlab.stud.idi.ntnu.no/hannagn)
- [@henriesu](https://gitlab.stud.idi.ntnu.no/henriesu)
- [@joelmt](https://gitlab.stud.idi.ntnu.no/joelmt)
- [@krihaan](https://gitlab.stud.idi.ntnu.no/krihaan)
- [@mathangp](https://gitlab.stud.idi.ntnu.no/mathangp)
- [@oskareid](https://gitlab.stud.idi.ntnu.no/oskareid)