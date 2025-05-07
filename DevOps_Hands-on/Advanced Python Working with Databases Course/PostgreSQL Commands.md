The content focuses on core PostgreSQL commands, advanced features, and interview-specific scenarios, avoiding redundancy and ensuring clarity. The structure mirrors the MySQL responses for consistency but highlights PostgreSQL-specific features.

---



# PostgreSQL Commands for Interview Preparation

This guide provides a comprehensive set of PostgreSQL commands and concepts to prepare for interviews, covering database management, querying, administration, and advanced features.

## 1. Database Management

- **Create Database**:
  ```sql
  CREATE DATABASE my_database;
  ```
- **Connect to Database**:
  ```sql
  \c my_database
  ```
- **List Databases**:
  ```sql
  \l
  ```
- **Drop Database**:
  ```sql
  DROP DATABASE my_database;
  ```

**Interview Tip**: Discuss database creation options (e.g., ENCODING, TEMPLATE) and the importance of connection management.

## 2. Schema Management

- **Create Schema**:
  ```sql
  CREATE SCHEMA my_schema;
  ```
- **Set Search Path**:
  ```sql
  SET search_path TO my_schema, public;
  ```
- **List Schemas**:
  ```sql
  \dn
  ```
- **Drop Schema**:
  ```sql
  DROP SCHEMA my_schema CASCADE;
  ```

**Interview Tip**: Explain schemas for organizing objects and their role in multi-tenant applications.

## 3. Table Management

- **Create Table**:
  ```sql
  CREATE TABLE employees (
      id SERIAL PRIMARY KEY,
      first_name VARCHAR(50),
      last_name VARCHAR(50),
      email VARCHAR(100) UNIQUE,
      hire_date DATE,
      salary NUMERIC(10,2)
  );
  ```
- **List Tables**:
  ```sql
  \dt
  ```
- **Describe Table**:
  ```sql
  \d employees
  ```
- **Alter Table (Add Column)**:
  ```sql
  ALTER TABLE employees ADD department_id INTEGER;
  ```
- **Alter Table (Modify Column)**:
  ```sql
  ALTER TABLE employees ALTER COLUMN email TYPE VARCHAR(150);
  ```
- **Alter Table (Drop Column)**:
  ```sql
  ALTER TABLE employees DROP COLUMN department_id;
  ```
- **Add Foreign Key**:
  ```sql
  ALTER TABLE employees ADD CONSTRAINT fk_dept FOREIGN KEY (department_id) REFERENCES departments(department_id);
  ```
- **Drop Table**:
  ```sql
  DROP TABLE employees CASCADE;
  ```

**Interview Tip**: Understand constraints (e.g., CHECK, FOREIGN KEY) and CASCADE options.

## 4. Data Manipulation (DML)

- **Insert Data**:
  ```sql
  INSERT INTO employees (first_name, last_name, email, hire_date, salary)
  VALUES ('John', 'Doe', 'john.doe@example.com', '2023-01-15', 50000.00);
  ```
- **Update Data**:
  ```sql
  UPDATE employees SET salary = salary + 5000 WHERE id = 1;
  ```
- **Delete Data**:
  ```sql
  DELETE FROM employees WHERE id = 1;
  ```

**Interview Tip**: Discuss the importance of WHERE clauses to avoid unintended updates/deletes.

## 5. Querying (DQL)

- **Select Query**:
  ```sql
  SELECT first_name, last_name, salary FROM employees WHERE salary > 55000 ORDER BY salary DESC LIMIT 5;
  ```
- **Aggregate Functions**:
  ```sql
  SELECT department_id, COUNT(*) AS emp_count, AVG(salary) AS avg_salary
  FROM employees
  GROUP BY department_id
  HAVING COUNT(*) > 2;
  ```
- **Joins**:
  ```sql
  SELECT e.first_name, d.department_name
  FROM employees e
  LEFT JOIN departments d ON e.department_id = d.department_id;
  ```
- **Subquery**:
  ```sql
  SELECT first_name
  FROM employees
  WHERE salary > (SELECT AVG(salary) FROM employees);
  ```
- **Window Function**:
  ```sql
  SELECT first_name, salary,
         RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) AS rank
  FROM employees;
  ```

**Interview Tip**: Master complex queries with joins, CTEs, and window functions.

## 6. Indexing

- **Create Index**:
  ```sql
  CREATE INDEX idx_email ON employees(email);
  ```
- **Create GIN Index (for JSONB)**:
  ```sql
  CREATE INDEX idx_details ON users USING GIN(details);
  ```
- **List Indexes**:
  ```sql
  \di
  ```
- **Drop Index**:
  ```sql
  DROP INDEX idx_email;
  ```

**Interview Tip**: Explain B-tree vs. GIN indexes and their impact on performance.

## 7. User and Role Management

- **Create Role**:
  ```sql
  CREATE ROLE new_user WITH LOGIN PASSWORD 'password';
  ```
- **Grant Privileges**:
  ```sql
  GRANT SELECT, INSERT ON employees TO new_user;
  ```
- **Revoke Privileges**:
  ```sql
  REVOKE INSERT ON employees FROM new_user;
  ```
- **Drop Role**:
  ```sql
  DROP ROLE new_user;
  ```

**Interview Tip**: Discuss role-based access control and least privilege principles.

## 8. Transactions

- **Begin Transaction**:
  ```sql
  BEGIN;
  INSERT INTO employees (first_name) VALUES ('Alice');
  COMMIT;
  ```
- **Rollback**:
  ```sql
  BEGIN;
  INSERT INTO employees (first_name) VALUES ('Bob');
  ROLLBACK;
  ```

**Interview Tip**: Explain ACID properties and transaction isolation levels (e.g., REPEATABLE READ).

## 9. Views and Materialized Views

- **Create View**:
  ```sql
  CREATE VIEW high_salary_employees AS
  SELECT first_name, salary
  FROM employees
  WHERE salary > 60000;
  ```
- **Create Materialized View**:
  ```sql
  CREATE MATERIALIZED VIEW high_salary_mv AS
  SELECT first_name, salary
  FROM employees
  WHERE salary > 60000
  WITH DATA;
  ```
- **Refresh Materialized View**:
  ```sql
  REFRESH MATERIALIZED VIEW high_salary_mv;
  ```

**Interview Tip**: Compare views (dynamic) vs. materialized views (cached).

## 10. Functions and Procedures

- **Create Function**:
  ```sql
  CREATE OR REPLACE FUNCTION calculate_bonus(salary NUMERIC)
  RETURNS NUMERIC AS $$
  BEGIN
      RETURN salary * 0.1;
  END;
  $$ LANGUAGE plpgsql;
  ```
- **Create Procedure**:
  ```sql
  CREATE OR REPLACE PROCEDURE update_salary(emp_id INTEGER, increase NUMERIC)
  LANGUAGE plpgsql AS $$
  BEGIN
      UPDATE employees SET salary = salary + increase WHERE id = emp_id;
      COMMIT;
  END;
  $$;
  ```

**Interview Tip**: Highlight PL/pgSQL for complex logic.

## 11. Triggers

- **Create Trigger**:
  ```sql
  CREATE OR REPLACE FUNCTION log_insert()
  RETURNS TRIGGER AS $$
  BEGIN
      INSERT INTO audit_log (action, employee_id) VALUES ('INSERT', NEW.id);
      RETURN NEW;
  END;
  $$ LANGUAGE plpgsql;

  CREATE TRIGGER after_insert
  AFTER INSERT ON employees
  FOR EACH ROW EXECUTE FUNCTION log_insert();
  ```

**Interview Tip**: Discuss trigger use cases (e.g., auditing) and performance considerations.

## 12. Performance Optimization

- **Explain Query**:
  ```sql
  EXPLAIN ANALYZE SELECT * FROM employees WHERE salary > 50000;
  ```
- **Vacuum and Analyze**:
  ```sql
  VACUUM ANALYZE employees;
  ```

**Interview Tip**: Explain autovacuum and index maintenance.

## 13. Backup and Restore

- **Backup Database**:
  ```bash
  pg_dump -U postgres my_database > backup.sql
  ```
- **Restore Database**:
  ```bash
  psql -U postgres my_database < backup.sql
  ```

**Interview Tip**: Discuss logical vs. physical backups (e.g., pg_basebackup).

## 14. JSONB Handling

- **Create JSONB Table**:
  ```sql
  CREATE TABLE users (
      id SERIAL PRIMARY KEY,
      details JSONB
  );
  INSERT INTO users (details) VALUES ('{"name": "John", "age": 30}');
  ```
- **Query JSONB**:
  ```sql
  SELECT details->>'name' FROM users;
  ```

**Interview Tip**: Highlight JSONB’s flexibility and indexing support.

## 15. Recursive CTEs

- **Hierarchical Query**:
  ```sql
  WITH RECURSIVE emp_hierarchy AS (
      SELECT id, first_name, manager_id
      FROM employees
      WHERE manager_id IS NULL
      UNION ALL
      SELECT e.id, e.first_name, e.manager_id
      FROM employees e
      INNER JOIN emp_hierarchy eh ON e.manager_id = eh.id
  )
  SELECT * FROM emp_hierarchy;
  ```

**Interview Tip**: Use for tree-like data (e.g., org charts).

## 16. Replication

- **Logical Replication**:
  ```sql
  CREATE PUBLICATION my_pub FOR TABLE employees;
  CREATE SUBSCRIPTION my_sub
  CONNECTION 'host=subscriber_ip dbname=my_database user=rep_user password=password'
  PUBLICATION my_pub;
  ```

**Interview Tip**: Explain streaming vs. logical replication.

## 17. Extensions

- **Install Extension**:
  ```sql
  CREATE EXTENSION postgis;
  ```
- **List Extensions**:
  ```sql
  \dx
  ```

**Interview Tip**: Mention popular extensions (e.g., PostGIS, UUID-OSSP).

## 18. Interview Scenarios

- **Find 2nd Highest Salary**:
  ```sql
  SELECT DISTINCT salary
  FROM employees
  ORDER BY salary DESC
  OFFSET 1 LIMIT 1;
  ```
- **Find Duplicates**:
  ```sql
  SELECT email, COUNT(*)
  FROM employees
  GROUP BY email
  HAVING COUNT(*) > 1;
  ```

**Interview Tip**: Practice on LeetCode or HackerRank for similar problems.



---

### **Interview Preparation Notes**
- **Practice**: Use a local PostgreSQL instance (e.g., via Docker) to test commands.
- **PostgreSQL vs. MySQL**: Emphasize JSONB, window functions, and extensibility.
- **Explain Logic**: Articulate your thought process during interviews.
- **Optimization**: Focus on EXPLAIN ANALYZE, vacuuming, and index types.

---

# Additional PostgreSQL Commands for Interview Preparation

These additional topics enhance your PostgreSQL knowledge for interviews, covering advanced administration, performance tuning, and specialized features.

## 19. Advanced Indexing Techniques

- **Create Partial Index**:
  ```sql
  CREATE INDEX idx_high_salary ON employees(salary) WHERE salary > 60000;
  ```
- **Create Expression Index**:
  ```sql
  CREATE INDEX idx_lower_email ON employees(LOWER(email));
  ```

**Interview Tip**: Explain use cases for partial indexes (e.g., filtering specific data) and expression indexes (e.g., case-insensitive searches).

## 20. Partitioning

- **Create Partitioned Table**:
  ```sql
  CREATE TABLE sales (
      id SERIAL,
      sale_date DATE,
      amount NUMERIC
  ) PARTITION BY RANGE (sale_date);
  CREATE TABLE sales_2023 PARTITION OF sales
  FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');
  ```

**Interview Tip**: Discuss partitioning types (RANGE, LIST, HASH) and benefits for large datasets.

## 21. Error Handling in PL/pgSQL

- **Function with Error Handling**:
  ```sql
  CREATE OR REPLACE FUNCTION safe_insert_employee(p_first_name VARCHAR, p_salary NUMERIC)
  RETURNS TEXT AS $$
  BEGIN
      INSERT INTO employees (first_name, salary) VALUES (p_first_name, p_salary);
      RETURN 'Insert successful';
  EXCEPTION
      WHEN OTHERS THEN
          RETURN 'Error: ' || SQLERRM;
  END;
  $$ LANGUAGE plpgsql;
  ```

**Interview Tip**: Highlight robust error handling for production environments.

## 22. Full-Text Search

- **Create Full-Text Index**:
  ```sql
  CREATE INDEX idx_fts ON employees USING GIN(to_tsvector('english', first_name || ' ' || last_name));
  ```
- **Query Full-Text**:
  ```sql
  SELECT first_name, last_name
  FROM employees
  WHERE to_tsvector('english', first_name || ' ' || last_name) @@ to_tsquery('John & Doe');
  ```

**Interview Tip**: Compare full-text search with LIKE queries and discuss GIN indexes.

## 23. Table Inheritance

- **Create Inherited Table**:
  ```sql
  CREATE TABLE staff (
      id SERIAL PRIMARY KEY,
      name VARCHAR(100)
  );
  CREATE TABLE contractors (
      contract_end DATE
  ) INHERITS (staff);
  ```

**Interview Tip**: Explain inheritance for modeling hierarchical data, though it’s less common in modern PostgreSQL.

## 24. Monitoring and Debugging

- **View Running Queries**:
  ```sql
  SELECT * FROM pg_stat_activity WHERE state = 'active';
  ```
- **Cancel Query**:
  ```sql
  SELECT pg_cancel_backend(pid) FROM pg_stat_activity WHERE query LIKE '%SELECT%';
  ```

**Interview Tip**: Discuss monitoring tools (e.g., pg_stat_statements) for performance tuning.

## 25. Configuration Tuning

- **Show Configuration**:
  ```sql
  SHOW work_mem;
  ```
- **Set Configuration**:
  ```sql
  ALTER SYSTEM SET work_mem = '16MB';
  SELECT pg_reload_conf();
  ```

**Interview Tip**: Explain key parameters (e.g., work_mem, shared_buffers) and their impact on performance.

## 26. Logical Replication Advanced

- **Add Table to Publication**:
  ```sql
  ALTER PUBLICATION my_pub ADD TABLE departments;
  ```
- **Monitor Replication**:
  ```sql
  SELECT * FROM pg_stat_subscription;
  ```

**Interview Tip**: Discuss conflict resolution in logical replication.

## 27. Foreign Data Wrappers (FDW)

- **Create FDW**:
  ```sql
  CREATE EXTENSION postgres_fdw;
  CREATE SERVER remote_server FOREIGN DATA WRAPPER postgres_fdw
  OPTIONS (host 'remote_host', dbname 'remote_db', port '5432');
  CREATE USER MAPPING FOR postgres SERVER remote_server
  OPTIONS (user 'remote_user', password 'remote_pass');
  CREATE FOREIGN TABLE remote_employees (
      id INTEGER,
      first_name VARCHAR(50)
  ) SERVER remote_server OPTIONS (schema_name 'public', table_name 'employees');
  ```

**Interview Tip**: Explain FDW for cross-database querying.

## 28. Advanced Transaction Isolation

- **Set Isolation Level**:
  ```sql
  SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
  BEGIN;
  SELECT * FROM employees WHERE salary > 60000;
  COMMIT;
  ```

**Interview Tip**: Compare isolation levels (READ COMMITTED, SERIALIZABLE) and their trade-offs.

## 29. Common Interview Queries

- **Nth Highest Salary**:
  ```sql
  SELECT DISTINCT salary
  FROM employees
  ORDER BY salary DESC
  OFFSET 1 LIMIT 1;
  ```
- **Cumulative Salary by Department**:
  ```sql
  SELECT first_name, salary,
         SUM(salary) OVER (PARTITION BY department_id ORDER BY salary) AS cumulative_salary
  FROM employees;
  ```

**Interview Tip**: Practice window functions and ranking queries on platforms like LeetCode.



---

### **Are These Enough?**
The original PostgreSQL response (sections 1–18) combined with these additional topics (19–29) provides a robust foundation for interviews at all levels (junior to senior). It covers:
- **Core Commands**: DDL, DML, DQL, indexing, transactions.
- **Advanced Features**: JSONB, partitioning, full-text search, FDW.
- **Administration**: Replication, backups, performance tuning.
- **Interview Scenarios**: Common query patterns and optimization strategies.

To ensure complete preparedness:
- **Practice**: Solve 20–30 SQL problems on LeetCode or HackerRank, focusing on window functions, CTEs, and JSONB queries.
- **Role-Specific Focus**: For DBA roles, emphasize replication, vacuuming, and configuration tuning. For developer roles, focus on complex queries and JSONB.
- **Edge Cases**: Be ready to handle NULLs, optimize slow queries, or explain PostgreSQL’s MVCC (Multiversion Concurrency Control).