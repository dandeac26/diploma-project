import json
import requests
import random

# Load the data from the JSON file
with open("products.json") as f:
    products = json.load(f)

with open("clients.json") as f:
    clients = json.load(f)

with open("ingredients.json") as f:
    ingredients = json.load(f)

with open("providers.json") as f:
    providers = json.load(f)

with open("recipes.json") as f:
    recipes = json.load(f)

with open("stocks.json") as f:
    stocks = json.load(f)

ingredient_id_list = []
provider_id_list = []
product_id_list = []

# For each product, make a POST request to the API endpoint
for product in products:
    response = requests.post("http://localhost:8080/product", json=product)
    # get the product id
    productId = response.json()["productId"]
    product_id_list.append(productId)
    # Check the response
    if response.status_code != 201:
        print(
            f'Failed to create product {product["name"]}. Status code: {response.status_code}'
        )

for client in clients:
    response = requests.post("http://localhost:8080/client", json=client)

    # Check the response
    if response.status_code != 201:
        print(
            f'Failed to create client {client["firmName"]}. Status code: {response.status_code}'
        )

for ingredient in ingredients:
    response = requests.post("http://localhost:8080/ingredients", json=ingredient)
    # get the ingredient id
    ingredientId = response.json()["ingredientId"]
    ingredient_id_list.append(ingredientId)

    # print(response.json()["ingredientId"])

    # Check the response
    if response.status_code != 201:
        print(
            f'Failed to create ingredient {ingredient["name"]}. Status code: {response.status_code}'
        )

for provider in providers:
    response = requests.post("http://localhost:8080/providers", json=provider)

    # get the provider id
    providerId = response.json()["providerId"]
    provider_id_list.append(providerId)

    # Check the response
    if response.status_code != 201:
        print(
            f'Failed to create provider {provider["name"]}. Status code: {response.status_code}'
        )

for recipe in recipes:
    # response = requests.post("http://localhost:8080/recipe", json=recipe)
    # for the first 10 productIds from the list, create recipe for each ingredient
    for productId in product_id_list[:10]:
        for ingredientId in ingredient_id_list[: random.randint(1, 5)]:
            recipe["productId"] = productId
            recipe["ingredientId"] = ingredientId
            response = requests.post("http://localhost:8080/recipe", json=recipe)

            # Check the response
            # if response.status_code != 201:
            #     print(
            #         f'Failed to create recipe {recipe["productId"]}, {recipe["ingredientId"]}. Status code: {response.status_code}'
            #     )


for stock in stocks:
    # response = requests.post("http://localhost:8080/stock", json=stock)

    # for first 10 ingredientIds from the list, create stock for each provider
    for ingredient_id in ingredient_id_list[:10]:
        for provider_id in provider_id_list[: random.randint(1, 5)]:
            stock["providerId"] = provider_id
            stock["ingredientId"] = ingredient_id
            response = requests.post("http://localhost:8080/stock", json=stock)

    # # Check the response
    # if response.status_code != 201:
    #     print(
    #         f'Failed to create stock {stock["ingredientId"]}, {stock["providerId"]}. Status code: {response.status_code}'
    #     )
