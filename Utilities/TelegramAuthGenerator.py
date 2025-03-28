from fyers_apiv3 import fyersModel

# Replace these with your actual credentials from the FYERS API dashboard
client_id = "BNBWBARW6S-100"
secret_key = "PUET7YGTJH"
redirect_uri = "https://fyers.in"

# Initialize the session
session = fyersModel.SessionModel(
    client_id=client_id,
    secret_key=secret_key,
    redirect_uri=redirect_uri,
    response_type="code",
    state="sample_state"
)

url = session.generate_authcode()
print(url)

# # Set the auth_code from the URL
# auth_code = input()
# session.set_token(auth_code)

# # Generate the access token
# response = session.generate_token()
# print(response)
# access_token = response["access_token"]

# print("Your access token is:", access_token)