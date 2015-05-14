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
| cat       | int    | 0 means all |

* response: product page

## login
* url: `/login`
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
* url: `/register`
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
| cat       | int    | 0 means all |

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
                "quantity" : 1,
                "desc" : "desc",
                "imageUrl" : "/image/1.jpg"
            }, {
                "productId" : 2,
                "productName" : "foo2",
                "catId" : 1,
                "catName" : "cat",
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
                "quantity" : 1,
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
        "quantity" : 1,
        "desc" : "desc",
        "imageUrl" : "/image/1.jpg"
    }, {
        "productId" : 2,
        "productName" : "foo2",
        "catId" : 2,
        "catName" : "cat",
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
* param

| name      | type   | example  |
| --------- |:------:| --------:|
| userId    | long   | 123      |

* response:
```javascript
{
    "status" : 0,  // 0 success, -1 failed
    "msg" : "ok",
    "data" : {
        "orderId" : 123,
        "email" : "user@email.com",
        "products" : [{
            "productId" : 1,
            "productName" : "foo",
            "catId" : 2,
            "catName" : "cat",
            "quantity" : 1,
            "desc" : "desc",
            "imageUrl" : "/image/1.jpg"
        },{
            "productId" : 2,
            "productName" : "foo",
            "catId" : 2,
            "catName" : "cat",
            "quantity" : 1,
            "desc" : "desc",
            "imageUrl" : "/image/2.jpg"
        }]
    }
}
```
