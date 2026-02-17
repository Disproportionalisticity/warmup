from pymongo import MongoClient

client = MongoClient("localhost", port=27017)

products_db = client["products-db"]

products = products_db["products"]

products.delete_many({})

# products.insert_one({
#     "name": "Milk",
#     "volume": "1L"
# })

products.insert_many([
    {
        "name": "Milk",
        "price": 5
    },
    {
        "name": "Kefir",
        "price": 7
    }
])


data = products.find({
    "price": {
        "$gt": 6
    }
})
for document in data:
    print(f"{document=}, {type(document)}")
    
    