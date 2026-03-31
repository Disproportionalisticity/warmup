import asyncio

from motor.motor_asyncio import AsyncIOMotorClient
from beanie import Document, init_beanie

class Product(Document):
    name: str
    price: float
    
    # specify that we use products collection
    class Settings:
        name = "products"
    
async def example():
    client = AsyncIOMotorClient("mongodb://localhost:27017")
    
    await init_beanie(database=client["products-db"], document_models=[Product])
    
    protein_bar = Product(name="Protein Name", price=5.99)
    await protein_bar.insert() 
    
    # data = await Product.find({"price": {"$gt": 4}}).to_list()
    data = await Product.find_one({"price": {"$gt": 4}})
    print(f"{data=}")
    
    # product = await Product.find_one(Product.price < 10)
    
    # await product.set({Product.name: "Gold bar"})   

if __name__ == "__main__":
    asyncio.run(example())
