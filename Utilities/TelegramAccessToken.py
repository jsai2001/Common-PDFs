# from fyers_apiv3 import fyersModel

# # Replace these with your actual credentials from the FYERS API dashboard
# client_id = "6JO4FX24PZ-100"
# secret_key = "4U4PMVG04I"
# redirect_uri = "https://fyers.in"

# # Initialize the session
# session = fyersModel.SessionModel(
#     client_id=client_id,
#     secret_key=secret_key,
#     redirect_uri=redirect_uri,
#     response_type="code",
#     state="sample_state"
# )

# # Set the auth_code from the URL
# auth_code = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBfaWQiOiI2Sk80RlgyNFBaIiwidXVpZCI6IjA0N2E4ODRhYWI5ZjQxNDY5ZDFkMDg1MGM5YzQxNGQ5IiwiaXBBZGRyIjoiIiwibm9uY2UiOiIiLCJzY29wZSI6IiIsImRpc3BsYXlfbmFtZSI6IllKMDAyOTEiLCJvbXMiOiJSMCIsImhzbV9rZXkiOiIwNTUyZmY0ZDQ5MDY4N2FiM2M0YmY5OWUyZTY5YmE4ZDUzNDIwNmY0NjEwMzEwOWU1ZTgxNzQ5OCIsImlzRGRwaUVuYWJsZWQiOiJOIiwiaXNNdGZFbmFibGVkIjoiTiIsImF1ZCI6IltcImQ6MVwiLFwiZDoyXCIsXCJ4OjBcIixcIng6MVwiLFwieDoyXCJdIiwiZXhwIjoxNzQyOTQ2MjAzLCJpYXQiOjE3NDI5MTYyMDMsImlzcyI6ImFwaS5sb2dpbi5meWVycy5pbiIsIm5iZiI6MTc0MjkxNjIwMywic3ViIjoiYXV0aF9jb2RlIn0.aYl2C1fIMxZFoFrdL3wCg7_pYB-DK8g68rvRSB1N544"
# session.set_token(auth_code)

# # Generate the access token
# response = session.generate_token()
# print(response)
# access_token = response["access_token"]

# print("Your access token is:", access_token)

import requests

# Replace these with your actual credentials from the FYERS API dashboard
client_id = "BNBWBARW6S-100"
secret_key = "PUET7YGTJH"
redirect_uri = "https://fyers.in"
auth_code = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcGkubG9naW4uZnllcnMuaW4iLCJpYXQiOjE3NDMxNzM5NzQsImV4cCI6MTc0MzIwMzk3NCwibmJmIjoxNzQzMTczMzc0LCJhdWQiOlsieDowIiwieDoxIiwieDoyIiwiZDoxIiwiZDoyIiwieDoxIiwieDowIl0sInN1YiI6ImF1dGhfY29kZSIsImRpc3BsYXlfbmFtZSI6IllKMDAyOTEiLCJvbXMiOiJLMSIsImhzbV9rZXkiOm51bGwsIm5vbmNlIjoiIiwiYXBwX2lkIjoiQk5CV0JBUlc2UyIsInV1aWQiOiI4MDZjZmY2ZWM4Yjc0YTU4OGExYjFiZjY5MzRiY2FjNSIsImlwQWRkciI6IjQzLjIyNS4yMS4xMjciLCJzY29wZSI6IiJ9.kN3KaRhfTTkvjxogmJVaCg-Io-cpjuFD6cJvSQvUr74"

# Token endpoint URL
token_url = "https://api.fyers.in/api/v3/token"

# Payload for the POST request
payload = {
 "grant_type": "authorization_code",
 "code": auth_code,
 "client_id": client_id,
 "secret_key": secret_key,
 "redirect_uri": redirect_uri
}

# Make the request
response = requests.post(token_url, data=payload)
# print(response.json())
print(response)
print(response.text)
# access_token = response["access_token"]

# print("Your access token is:", access_token)