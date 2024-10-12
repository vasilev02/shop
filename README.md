
# SHOP REST API

The shop has only 2 required entities: product and subscriber. Every product must have at least the
following information: name, creation date (timestamp later to be transformed to human readable format
in REST responses) and a field that marks if the product is currently under sale or it is not available. Every
subscriber must have at least the following information: first name, last name, unique identifier and a date
that marks when the subscriber has joined (same requirements as product date). Every product can be
sold to multiple subscribers and of course one subscriber can have multiple purchases.


## API Reference

#### Get a product by its id

```http
  GET /api/products/{id}
```

| Parameter | Type   | Description              |
|:----------|:-------| :----------------------- |
| `id`      | `long` | **Required** |

#### Get all products

```http
  GET /api/products
```

#### Add a product

```http
  POST /api/products
```

| Parameter  | Type      | Description                                            |
|:-----------|:----------|:-------------------------------------------------------|
| `name`     | `String`  | **Required.** Name must be between 3 and 15 characters |
| `isUnderSale` | `Boolean` | **Required.**  True or false                           |


#### Update a product by its id

```http
  PUT /api/products/{id}
```

| Parameter | Type   | Description                       |
| :-------- |:-------| :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

#### Delete a product by its id

```http
  DELETE /api/products/{id}
```

| Parameter | Type   | Description                       |
| :-------- |:-------| :-------------------------------- |
| `id`      | `long` | **Required**. Id of item to fetch |

#### Get the total count of products

```http
  GET /api/products/total
```

#### Get the total count of sold products

```http
  GET /api/products/sold
```

#### Get the total count of active products

```http
  GET /api/products/active
```

#### Get all products ordered by popularity

```http
  GET /api/products/popular
```
#### Get products by creation date within a given range

```http
  GET /api/products/date-range?startDate={startDate}&endDate={endDate}
```

| Parameter   | Type            | Description                      |
|:------------|:----------------| :------------------------------- |
| `startDate` | `LocalDateTime` | **Required**. |
| `endDate`   | `LocalDateTime` | **Required**. |



--------------------------------------------



#### Get a subscriber by its id

```http
  GET /api/subscribers/{id}
```

| Parameter | Type   | Description              |
|:----------|:-------| :----------------------- |
| `id`      | `long` | **Required** |

#### Get all subscribers

```http
  GET /api/subscribers
```

#### Add a subscriber

```http
  POST /api/subscribers
```

| Parameter   | Type     | Description                                                  |
|:------------|:---------|:-------------------------------------------------------------|
| `firstName` | `String` | **Required.** First name must be between 3 and 15 characters |
| `LastName`  | `String` | **Required.** Last name must be between 3 and 15 characters  |


#### Update a subscriber by its id

```http
  PUT /api/subscribers/{id}
```

| Parameter | Type   | Description                       |
|:----------|:-------|:----------------------------------|
| `id`      | `long` | **Required**. Id of item to fetch |

#### Delete a subscriber by its id

```http
  DELETE /api/subscribers/{id}
```

| Parameter | Type   | Description    |
|:----------|:-------|:---------------|
| `id`      | `long` | **Required**. Id of item to fetch  |

#### Get the total count of subscribers

```http
  GET /api/subscribers/total
```

#### Add product to a subscriber

```http
  POST /api/subscribers/{subscriberId}/products/{productId}
```

| Parameter | Type   | Description                 |
|:----------|:-------|:----------------------------|
| `id`      | `long` | **Required.** Subscriber ID |
| `id`      | `long` | **Required.** Product ID    |

### Docker
#### Pull the image from Docker Hub
```http
  docker pull valo123/my-spring-app:latest
```


## Authors

[@Valentin Vasilev](https://github.com/vasilev02)

