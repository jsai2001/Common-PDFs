# API Development Notes

## Retrieving a Single Planet's Details (GET Request)

**Overview**: This section explains how to create a Flask route to retrieve details of a single planet using its `planet_id`. The route uses SQLAlchemy to query the database and returns serialized data or a 404 error if the planet is not found.

**Steps**:
1. Define a Flask route `/planet_details/<int:planet_id>` that accepts GET requests.
2. Use SQLAlchemy to query the `Planet` model, filtering by `planet_id`.
3. Serialize the result using a singular `planet_schema`.
4. Return the serialized data with a 200 status code if found, or a 404 error with a message if not found.
5. Test the route in Postman with valid and invalid `planet_id` values.

**Code Snippet**:
```python
@app.route('/planet_details/<int:planet_id>', methods=['GET'])
def planet_details(planet_id: int):
    planet = Planet.query.filter_by(planet_id=planet_id).first()
    if planet:
        result = planet_schema.dump(planet)
        return jsonify(result.data)
    return jsonify({"message": "that planet does not exist"}), 404
```

**Testing**:
- GET `http://localhost:5000/planet_details/1` → Returns Mercury's details (200 OK).
- GET `http://localhost:5000/planet_details/100` → Returns "that planet does not exist" (404 Not Found).

## Adding Planets with a POST Method

**Overview**: This section describes creating a Flask route to add a new planet via a POST request. The route checks for duplicate planet names, collects form data, creates a new `Planet` record, and commits it to the database.

**Steps**:
1. Define a Flask route `/add_planet` that accepts POST requests.
2. Check if a planet with the submitted `planet_name` already exists using SQLAlchemy.
3. If it exists, return a 409 conflict error.
4. If not, collect form data for `planet_name`, `planet_type`, `home_star`, `mass`, `radius`, and `distance`.
5. Create a new `Planet` instance with the collected data.
6. Add the record to the database session and commit it.
7. Return a success message with a 201 status code.
8. Test the route in Postman with valid and duplicate planet names.

**Code Snippet**:
```python
@app.route('/add_planet', methods=['POST'])
def add_planet():
    planet_name = request.form['planet_name']
    test = Planet.query.filter_by(planet_name=planet_name).first()
    if test:
        return jsonify({"message": "There is already a planet by that name"}), 409
    planet_type = request.form['planet_type']
    home_star = request.form['home_star']
    mass = float(request.form['mass'])
    radius = float(request.form['radius'])
    distance = float(request.form['distance'])
    new_planet = Planet(
        planet_name=planet_name,
        planet_type=planet_type,
        home_star=home_star,
        mass=mass,
        radius=radius,
        distance=distance
    )
    db.session.add(new_planet)
    db.session.commit()
    return jsonify({"message": "You added a planet"}), 201
```

**Testing**:
- POST `http://localhost:5000/add_planet` with form data (e.g., `planet_name=Neptune`) → Returns "You added a planet" (201 Created).
- POST with `planet_name=Mercury` (existing planet) → Returns "There is already a planet by that name" (409 Conflict).

**Notes**:
- The route is initially unprotected for easier testing; JWT protection can be added later.
- Form data fields must match the expected keys (`planet_name`, `planet_type`, etc.).

# API Security and Update Notes

## Securing the Add Planet Endpoint

**Overview**: This section explains how to secure the `add_planet` endpoint using JSON Web Tokens (JWT). A `@jwt_required` decorator is added to ensure only authenticated users can access the endpoint.

**Steps**:
1. Add the `@jwt_required` decorator below the `@app.route` definition for the `add_planet` endpoint.
2. Test the endpoint in Postman without a token to confirm a 401 Unauthorized response.
3. Log in via the `/login` endpoint (POST request) to obtain a valid JWT.
4. Add the JWT as a Bearer Token in Postman’s Authorization tab.
5. Test the endpoint again with the token to confirm a 201 Created response.

**Code Snippet**:
```python
@app.route('/add_planet', methods=['POST'])
@jwt_required
def add_planet():
    planet_name = request.form['planet_name']
    test = Planet.query.filter_by(planet_name=planet_name).first()
    if test:
        return jsonify({"message": "There is already a planet by that name"}), 409
    planet_type = request.form['planet_type']
    home_star = request.form['home_star']
    mass = float(request.form['mass'])
    radius = float(request.form['radius'])
    distance = float(request.form['distance'])
    new_planet = Planet(
        planet_name=planet_name,
        planet_type=planet_type,
        home_star=home_star,
        mass=mass,
        radius=radius,
        distance=distance
    )
    db.session.add(new_planet)
    db.session.commit()
    return jsonify({"message": "You added a planet"}), 201
```

**Testing**:
- POST `http://localhost:5000/add_planet` without token → Returns "Missing Authorization Header" (401 Unauthorized).
- POST `http://localhost:5000/login` with `email=test@test.com`, `password=Password` → Returns JWT.
- POST `http://localhost:5000/add_planet` with token and form data (e.g., `planet_name=Pluto`) → Returns "You added a planet" (201 Created).

## Updating a Planet Using a PUT Method

**Overview**: This section describes creating a Flask route to update an existing planet’s details using a PUT request. The endpoint checks for the planet’s existence and updates its attributes.

**Steps**:
1. Define a Flask route `/update_planet` that accepts PUT requests.
2. Retrieve `planet_id` from form data and query the `Planet` model to check if it exists.
3. If the planet exists, update its attributes (`planet_name`, `planet_type`, `home_star`, `mass`, `radius`, `distance`) with form data.
4. Commit the changes to the database and return a 202 Accepted response.
5. If the planet doesn’t exist, return a 404 Not Found response.
6. Test the unprotected route in Postman, then add `@jwt_required` for protection.
7. Handle expired tokens by re-authenticating via the `/login` endpoint.

**Code Snippet**:
```python
@app.route('/update_planet', methods=['PUT'])
@jwt_required
def update_planet():
    planet_id = int(request.form['planet_id'])
    planet = Planet.query.filter_by(planet_id=planet_id).first()
    if planet:
        planet.planet_name = request.form['planet_name']
        planet.planet_type = request.form['planet_type']
        planet.home_star = request.form['home_star']
        planet.mass = float(request.form['mass'])
        planet.radius = float(request.form['radius'])
        planet.distance = float(request.form['distance'])
        db.session.commit()
        return jsonify({"message": "You updated a planet"}), 202
    return jsonify({"message": "that planet does not exist"}), 404
```

**Testing**:
- PUT `http://localhost:5000/update_planet` with form data (e.g., `planet_id=1`, `planet_name=Mercury`, `planet_type=class Z`, `home_star=Alpha Centauri`) → Returns "You updated a planet" (202 Accepted).
- PUT with invalid `planet_id` → Returns "that planet does not exist" (404 Not Found).
- After adding `@jwt_required`, test without token → Returns "Missing Authorization Header" (401 Unauthorized).
- Test with expired token → Returns "Token has expired"; re-authenticate via `/login` and retry → Returns 202 Accepted.

**Notes**:
- Initially test the `update_planet` endpoint without JWT protection to verify functionality.
- Use DB Browser to verify database changes (refresh required).
- Ensure form data keys match expected fields; typos (e.g., `home_start` vs. `home_star`) cause errors.
- PUT is the standard verb for updates, but some teams use POST; consider supporting both for flexibility.

