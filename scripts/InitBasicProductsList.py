import json
import requests

# Load the data from the JSON file
with open("products.json") as f:
    products = json.load(f)

# For each product, make a POST request to the API endpoint
for product in products:
    response = requests.post("http://localhost:8080/product", json=product)

    # Check the response
    if response.status_code != 201:
        print(
            f'Failed to create product {product["name"]}. Status code: {response.status_code}'
        )
