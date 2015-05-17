# API DOC

## home page
* url: `/home`
* method: get
* response: home page

## products page
* url: `/product`
* method: get
* param

| name      | type   | example     |
| --------- |:------:| -----------:|
| catId     | int    | 0 means all |

* response: product page

## login
* url: `/user/login`
* method: post
* param

| name      | type   | example     |
| --------- |:------:| -----------:|
| username  | string | abc@abc.com |
| pwd       | string |   123       |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : null
}
```

## register
* url: `/user/register`
* method: post
* param

| name      | type   | example     |
| --------- |:------:| -----------:|
| username  | string | abc@abc.com |
| pwd       | string |   123       |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : null
}
```

## query products
* url: `/product/get`
* method: get
* param

| name      | type   | example     |
| --------- |:------:| -----------:|
| catId     | int    | 0 means all |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : [
        {
            "catId" : 1,
            "catName" : "cat",
            "products" : [{
                "productId" : 1,
                "productName" : "foo",
                "catId" : 1,
                "catName" : "cat",
                "price" : 10,
                "desc" : "desc",
                "imageUrl" : "/image/1.jpg"
            }, {
                "productId" : 2,
                "productName" : "foo2",
                "catId" : 1,
                "catName" : "cat",
                "price" : 10,
                "quantity" : 1,
                "desc" : "desc",
                "imageUrl" : "/image/2.jpg"
            }]
        },
        {
            "catId" : 2,
            "catName" : "cat",
            "products" : [{
                "productId" : 1,
                "productName" : "foo",
                "catId" : 2,
                "catName" : "cat",
                "price" : 10,
                "desc" : "desc",
                "imageUrl" : "/image/1.jpg"
            }]
        }
    ]
}
```

## query user cart
* url: `/cart`
* method: get
* param

| name      | type   | example  |
| --------- |:------:| --------:|
| userId    | long   | 123      |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : [{
        "productId" : 1,
        "productName" : "foo",
        "catId" : 2,
        "catName" : "cat",
        "price" : 10,
        "quantity" : 1,
        "desc" : "desc",
        "imageUrl" : "/image/1.jpg"
    }, {
        "productId" : 2,
        "productName" : "foo2",
        "catId" : 2,
        "catName" : "cat",
        "price" : 10,
        "quantity" : 1,
        "desc" : "desc",
        "imageUrl" : "/image/2.jpg"
    }]
}
```

## add to user cart
* url: `/cart/add`
* method: post
* param

| name      | type   | example  |
| --------- |:------:| --------:|
| userId    | long   | 123      |
| productId | long   | 123      |
| quantity  | long   | 1        |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : null
}
```

## remove from user cart
* url: `/cart/remove`
* method: post
* param

| name      | type   | example  |
| --------- |:------:| --------:|
| userId    | long   | 123      |
| productId | long   | 123      |
| quantity  | long   | 1        |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : null
}
```

## commit order
* url: `/cart/commit`
* method: post
* param json

| name      | type   | needed | example  |
| --------- |:------:|:------:| --------:|
| userId    | long   | true   | 123      |
| name      | string | true   | yukri    |
| mobile    | string | true   | 123456   |
| email     | string | true   | a@a.com  |
| address   | string | true   | mars     |
| city      | string | true   | town     |
| zipCode   | string | true   | 123      |

request example
```javascript
{
    "userId" : 1,
    "name" : "yukari",
    "mobile" : "12345678",
    "email" : "foo@email.com",
    "address" : "mars",
    "city" : "san francisco",
    "zipCode" : "94101"
}
```

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed, 1 cart is empty
    "msg" : "ok",
    "data" : {
        "order" : {
            "id" : 123,
            "userId" : 1,
            "name" : "user",
            "mobile" : "123456",
            "email" : "user@email.com",
            "address" : "mars",
            "city" : "san francisco",
            "zipCode" : "94101"
        },
        "products" : [{
            "productId" : 1,
            "productName" : "foo",
            "catId" : 2,
            "catName" : "cat",
            "price" : 10,
            "quantity" : 1,
            "desc" : "desc",
            "imageUrl" : "/image/1.jpg"
        },{
            "productId" : 2,
            "productName" : "foo",
            "catId" : 2,
            "catName" : "cat",
            "price" : 10,
            "quantity" : 1,
            "desc" : "desc",
            "imageUrl" : "/image/2.jpg"
        }]
    }
}
```
