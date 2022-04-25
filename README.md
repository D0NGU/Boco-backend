
# BoCo WS Backend

<p align="left">
  <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/-/commits/main"><img alt="pipeline status" src="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/badges/main/pipeline.svg" /></a>
  <a href="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/-/commits/main"><img alt="coverage report" src="https://gitlab.stud.idi.ntnu.no/idatt2106_2022_08/backend/badges/main/coverage.svg" /></a> 
</p>

A standalone server application for BoCo utilizing Java Spring Boot (with embedded database).

**Documentation:** [Wiki](https://linktodocumentation)

## Installation

Clone repository locally, browse to project folder and execute:

```cmd
  mvn install
  mvn spring-boot:run
```
## API Example References

#### Get all products in category

```http
  GET /api/products/${category}
```

| Field | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `category` | `string` | **Required**. The category |

#### Edit product

```http
  PUT /api/products/${productId}/edit
```

|   Field   |  Type    | Description                       |
| :-------- | :------- | :-------------------------------- |
|`productId`| `string` | **Required**. Id of item to edit |
| `product` | `array`  | **Required**. Array of new data.
| `api_key` | `string` | **Required**. Your API key |


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


