
---

### **1. Database Management**
These commands handle database creation, modification, and deletion.

- **Create a Database**:
  ```sql
  CREATE DATABASE my_database;
  ```
- **Select a Database**:
  ```sql
  USE my_database;
  ```
- **Show All Databases**:
  ```sql
  SHOW DATABASES;
  ```
- **Drop a Database**:
  ```sql
  DROP DATABASE my_database;
  ```
- **Show Database Creation Statement**:
  ```sql
  SHOW CREATE DATABASE my_database;
  ```

**Interview Tip**: Be ready to explain the implications of dropping a database (irreversible data loss) and how to back up before such operations.

---

### **2. Table Management**
Commands for creating, altering, and managing tables.

- **Create a Table**:
  ```sql
  CREATE TABLE employees (
      id INT AUTO_INCREMENT PRIMARY KEY,
      first_name VARCHAR(50),
      last_name VARCHAR(50),
      email VARCHAR(100) UNIQUE,
      hire_date DATE,
      salary DECIMAL(10, 2)
  );
  ```
- **Show All Tables**:
  ```sql
  SHOW TABLES;
  ```
- **Describe Table Structure**:
  ```sql
  DESCRIBE employees;
  -- OR
  SHOW COLUMNS FROM employees;
  ```
- **Alter Table (Add Column)**:
  ```sql
  ALTER TABLE employees ADD department_id INT;
  ```
- **Alter Table (Modify Column)**:
  ```sql
  ALTER TABLE employees MODIFY COLUMN email VARCHAR(150);
  ```
- **Alter Table (Drop Column)**:
  ```sql
  ALTER TABLE employees DROP COLUMN department_id;
  ```
- **Add Primary Key**:
  ```sql
  ALTER TABLE employees ADD PRIMARY KEY (id);
  ```
- **Add Foreign Key**:
  ```sql
  ALTER TABLE employees ADD CONSTRAINT fk_dept FOREIGN KEY (department_id) REFERENCES departments(department_id);
  ```
- **Drop Foreign Key**:
  ```sql
  ALTER TABLE employees DROP FOREIGN KEY fk_dept;
  ```
- **Drop Table**:
  ```sql
  DROP TABLE employees;
  ```
- **Truncate Table (Remove all rows, keep structure)**:
  ```sql
  TRUNCATE TABLE employees;
  ```
- **Rename Table**:
  ```sql
  RENAME TABLE employees TO staff;
  ```

**Interview Tip**: Understand constraints (PRIMARY KEY, FOREIGN KEY, UNIQUE, NOT NULL) and when to use them. Be prepared to discuss CASCADE options for foreign keys.

---

### **3. Data Manipulation Language (DML)**
Commands to insert, update, delete, and retrieve data.

- **Insert Data (Single Row)**:
  ```sql
  INSERT INTO employees (first_name, last_name, email, hire_date, salary)
  VALUES ('John', 'Doe', 'john.doe@example.com', '2023-01-15', 50000.00);
  ```
- **Insert Multiple Rows**:
  ```sql
  INSERT INTO employees (first_name, last_name, email, hire_date, salary)
  VALUES
      ('Jane', 'Smith', 'jane.smith@example.com', '2023-02-10', 60000.00),
      ('Mike', 'Brown', 'mike.brown@example.com', '2023-03-05', 55000.00);
  ```
- **Update Data**:
  ```sql
  UPDATE employees
  SET salary = salary + 5000
  WHERE id = 1;
  ```
- **Delete Data**:
  ```sql
  DELETE FROM employees
  WHERE id = 1;
  ```
- **Select Data (Basic Query)**:
  ```sql
  SELECT * FROM employees;
  ```

**Interview Tip**: Be ready to explain the difference between DELETE and TRUNCATE (DELETE removes specific rows, TRUNCATE resets the table). Discuss how to safely update/delete with WHERE clauses.

---

### **4. Data Query Language (DQL) - SELECT Queries**
Mastering SELECT queries is critical for interviews.

- **Select Specific Columns**:
  ```sql
  SELECT first_name, last_name, salary
  FROM employees;
  ```
- **Filter with WHERE**:
  ```sql
  SELECT * FROM employees
  WHERE salary > 55000;
  ```
- **Order By**:
  ```sql
  SELECT * FROM employees
  ORDER BY salary DESC;
  ```
- **Limit Results**:
  ```sql
  SELECT * FROM employees
  LIMIT 5;
  ```
- **Aggregate Functions**:
  ```sql
  SELECT COUNT(*) AS total_employees,
         AVG(salary) AS avg_salary,
         MAX(salary) AS max_salary
  FROM employees;
  ```
- **Group By**:
  ```sql
  SELECT department_id, COUNT(*) AS emp_count
  FROM employees
  GROUP BY department_id;
  ```
- **Having Clause**:
  ```sql
  SELECT department_id, COUNT(*) AS emp_count
  FROM employees
  GROUP BY department_id
  HAVING emp_count > 2;
  ```
- **Joins**:
  - **Inner Join**:
    ```sql
    SELECT e.first_name, e.last_name, d.department_name
    FROM employees e
    INNER JOIN departments d ON e.department_id = d.department_id;
    ```
  - **Left Join**:
    ```sql
    SELECT e.first_name, e.last_name, d.department_name
    FROM employees e
    LEFT JOIN departments d ON e.department_id = d.department_id;
    ```
  - **Right Join**:
    ```sql
    SELECT e.first_name, e.last_name, d.department_name
    FROM employees e
    RIGHT JOIN departments d ON e.department_id = d.department_id;
    ```
- **Subquery**:
  ```sql
  SELECT first_name, last_name
  FROM employees
  WHERE salary > (SELECT AVG(salary) FROM employees);
  ```
- **Union**:
  ```sql
  SELECT first_name FROM employees
  UNION
  SELECT first_name FROM contractors;
  ```
- **Case Statement**:
  ```sql
  SELECT first_name, salary,
         CASE
             WHEN salary > 60000 THEN 'High'
             WHEN salary > 50000 THEN 'Medium'
             ELSE 'Low'
         END AS salary_category
  FROM employees;
  ```

**Interview Tip**: Be prepared to write complex queries involving multiple joins, subqueries, and aggregations. Practice explaining the difference between INNER and OUTER joins.

---

### **5. Indexing**
Indexes improve query performance but require understanding trade-offs.

- **Create Index**:
  ```sql
  CREATE INDEX idx_email ON employees(email);
  ```
- **Create Composite Index**:
  ```sql
  CREATE INDEX idx_name ON employees(first_name, last_name);
  ```
- **Show Indexes**:
  ```sql
  SHOW INDEX FROM employees;
  ```
- **Drop Index**:
  ```sql
  DROP INDEX idx_email ON employees;
  ```

**Interview Tip**: Explain when to use indexes (e.g., frequently queried columns) and their downsides (e.g., slower INSERT/UPDATE operations).

---

### **6. User and Privilege Management**
Managing users and permissions is a common interview topic for DBAs.

- **Create User**:
  ```sql
  CREATE USER 'new_user'@'localhost' IDENTIFIED BY 'password';
  ```
- **Grant Privileges**:
  ```sql
  GRANT SELECT, INSERT ON my_database.* TO 'new_user'@'localhost';
  ```
- **Grant All Privileges**:
  ```sql
  GRANT ALL PRIVILEGES ON my_database.* TO 'new_user'@'localhost';
  ```
- **Show Grants**:
  ```sql
  SHOW GRANTS FOR 'new_user'@'localhost';
  ```
- **Revoke Privileges**:
  ```sql
  REVOKE INSERT ON my_database.* FROM 'new_user'@'localhost';
  ```
- **Drop User**:
  ```sql
  DROP USER 'new_user'@'localhost';
  ```

**Interview Tip**: Understand privilege levels (global, database, table) and how to secure a database by granting minimal permissions.

---

### **7. Transactions**
Transactions ensure data integrity.

- **Start Transaction**:
  ```sql
  START TRANSACTION;
  ```
- **Commit Transaction**:
  ```sql
  INSERT INTO employees (first_name, last_name) VALUES ('Alice', 'Green');
  COMMIT;
  ```
- **Rollback Transaction**:
  ```sql
  START TRANSACTION;
  INSERT INTO employees (first_name, last_name) VALUES ('Bob', 'White');
  ROLLBACK;
  ```
- **Set Savepoint**:
  ```sql
  SAVEPOINT savepoint1;
  ROLLBACK TO savepoint1;
  ```

**Interview Tip**: Explain ACID properties (Atomicity, Consistency, Isolation, Durability) and when to use transactions (e.g., financial operations).

---

### **8. Views**
Views simplify complex queries and enhance security.

- **Create View**:
  ```sql
  CREATE VIEW high_salary_employees AS
  SELECT first_name, last_name, salary
  FROM employees
  WHERE salary > 60000;
  ```
- **Query View**:
  ```sql
  SELECT * FROM high_salary_employees;
  ```
- **Drop View**:
  ```sql
  DROP VIEW high_salary_employees;
  ```

**Interview Tip**: Discuss the benefits of views (e.g., abstraction, security) and their limitations (e.g., not always updatable).

---

### **9. Stored Procedures and Functions**
These encapsulate logic for reusability.

- **Create Stored Procedure**:
  ```sql
  DELIMITER //
  CREATE PROCEDURE GetHighSalaryEmployees()
  BEGIN
      SELECT * FROM employees WHERE salary > 60000;
  END //
  DELIMITER ;
  ```
- **Call Stored Procedure**:
  ```sql
  CALL GetHighSalaryEmployees();
  ```
- **Create Function**:
  ```sql
  DELIMITER //
  CREATE FUNCTION CalculateBonus(salary DECIMAL(10,2))
  RETURNS DECIMAL(10,2)
  DETERMINISTIC
  BEGIN
      RETURN salary * 0.1;
  END //
  DELIMITER ;
  ```
- **Use Function**:
  ```sql
  SELECT first_name, CalculateBonus(salary) AS bonus FROM employees;
  ```
- **Drop Procedure/Function**:
  ```sql
  DROP PROCEDURE GetHighSalaryEmployees;
  DROP FUNCTION CalculateBonus;
  ```

**Interview Tip**: Explain when to use stored procedures (e.g., complex logic, security) vs. functions (e.g., reusable calculations).

---

### **10. Triggers**
Triggers automate actions based on table events.

- **Create Trigger**:
  ```sql
  DELIMITER //
  CREATE TRIGGER after_employee_insert
  AFTER INSERT ON employees
  FOR EACH ROW
  BEGIN
      INSERT INTO audit_log (action, employee_id, action_date)
      VALUES ('INSERT', NEW.id, NOW());
  END //
  DELIMITER ;
  ```
- **Show Triggers**:
  ```sql
  SHOW TRIGGERS;
  ```
- **Drop Trigger**:
  ```sql
  DROP TRIGGER after_employee_insert;
  ```

**Interview Tip**: Discuss use cases for triggers (e.g., logging, enforcing rules) and potential downsides (e.g., performance impact).

---

### **11. Performance Optimization**
Interviewers often ask about optimizing MySQL performance.

- **Explain Query Plan**:
  ```sql
  EXPLAIN SELECT * FROM employees WHERE salary > 50000;
  ```
- **Analyze Table**:
  ```sql
  ANALYZE TABLE employees;
  ```
- **Optimize Table**:
  ```sql
  OPTIMIZE TABLE employees;
  ```
- **Check Server Status**:
  ```sql
  SHOW STATUS;
  ```
- **Show Process List**:
  ```sql
  SHOW PROCESSLIST;
  ```

**Interview Tip**: Be ready to discuss indexing strategies, query optimization (e.g., avoiding SELECT *), and caching (e.g., query cache, InnoDB buffer pool).

---

### **12. Backup and Restore**
Backup and recovery are critical for DBAs.

- **Backup Database (mysqldump)**:
  ```bash
  mysqldump -u root -p my_database > backup.sql
  ```
- **Restore Database**:
  ```bash
  mysql -u root -p my_database < backup.sql
  ```
- **Export Specific Table**:
  ```bash
  mysqldump -u root -p my_database employees > employees_backup.sql
  ```

**Interview Tip**: Explain the difference between logical backups (mysqldump) and physical backups (e.g., copying data files). Discuss point-in-time recovery.

---

### **13. Common Configuration Commands**
Understand basic server configuration.

- **Show Variables**:
  ```sql
  SHOW VARIABLES LIKE 'innodb_buffer_pool_size';
  ```
- **Set Variable (Session)**:
  ```sql
  SET SESSION sql_mode = 'STRICT_TRANS_TABLES';
  ```
- **Set Variable (Global)**:
  ```sql
  SET GLOBAL max_connections = 200;
  ```

**Interview Tip**: Be familiar with key variables like innodb_buffer_pool_size, max_connections, and query_cache_size.

---

### **14. Advanced Topics for Interviews**
These are often asked in senior-level interviews.

- **Partitioning**:
  ```sql
  CREATE TABLE sales (
      id INT,
      sale_date DATE,
      amount DECIMAL(10,2)
  )
  PARTITION BY RANGE (YEAR(sale_date)) (
      PARTITION p0 VALUES LESS THAN (2020),
      PARTITION p1 VALUES LESS THAN (2021),
      PARTITION p2 VALUES LESS THAN (2022)
  );
  ```
- **Event Scheduler**:
  ```sql
  SET GLOBAL event_scheduler = ON;
  CREATE EVENT delete_old_logs
  ON SCHEDULE EVERY 1 DAY
  DO
      DELETE FROM audit_log WHERE action_date < NOW() - INTERVAL 30 DAY;
  ```
- **Full-Text Search**:
  ```sql
  CREATE FULLTEXT INDEX idx_fulltext ON employees(first_name, last_name);
  SELECT * FROM employees
  WHERE MATCH(first_name, last_name) AGAINST('John Doe');
  ```

**Interview Tip**: Be prepared to discuss partitioning types (RANGE, LIST, HASH) and use cases for full-text search vs. regular indexes.

---

### **15. Common Interview Scenarios**
Practice these practical tasks:

- **Write a query to find the 2nd highest salary**:
  ```sql
  SELECT MAX(salary)
  FROM employees
  WHERE salary < (SELECT MAX(salary) FROM employees);
  ```
- **Find duplicate emails**:
  ```sql
  SELECT email, COUNT(*)
  FROM employees
  GROUP BY email
  HAVING COUNT(*) > 1;
  ```
- **Normalize a table**: Explain how to break a denormalized table into 1NF, 2NF, 3NF.
- **Handle deadlocks**: Discuss how to detect (SHOW ENGINE INNODB STATUS) and prevent deadlocks (consistent locking order).

---

### **Interview Preparation Tips**
1. **Practice Hands-On**: Set up a local MySQL instance (e.g., via Docker) and run these commands.
2. **Understand Theory**: Be ready to explain normalization, indexing, and transaction isolation levels (e.g., READ COMMITTED, SERIALIZABLE).
3. **Mock Interviews**: Practice solving SQL problems on platforms like LeetCode, HackerRank, or SQLZoo.
4. **Explain Your Code**: During interviews, articulate your thought process while writing queries.
5. **Know MySQL Versions**: Familiarize yourself with features in MySQL 8.0 (e.g., window functions, CTEs):
   - **Common Table Expression (CTE)**:
     ```sql
     WITH high_salary AS (
         SELECT * FROM employees WHERE salary > 60000
     )
     SELECT * FROM high_salary WHERE department_id = 1;
     ```
   - **Window Function**:
     ```sql
     SELECT first_name, salary,
            RANK() OVER (PARTITION BY department_id ORDER BY salary DESC) AS salary_rank
     FROM employees;
     ```

---

---

### **16. MySQL Architecture and Storage Engines**
Understand storage engines for different use cases.

- **Check Available Engines**:
  ```sql
  SHOW ENGINES;
  ```
- **Create Table with Specific Engine**:
  ```sql
  CREATE TABLE my_table (id INT) ENGINE = InnoDB;
  ```
- **Change Engine**:
  ```sql
  ALTER TABLE my_table ENGINE = MyISAM;
  ```
- **Interview Tip**: Explain InnoDB (transactional, row-level locking) vs. MyISAM (non-transactional, table-level locking) use cases.

---

### **17. Replication and High Availability**
Basic replication setup for scalability.

- **Configure Replication**:
  ```sql
  CHANGE MASTER TO MASTER_HOST='master_ip', MASTER_USER='repl_user', MASTER_PASSWORD='password', MASTER_LOG_FILE='mysql-bin.000001', MASTER_LOG_POS=123;
  START SLAVE;
  SHOW SLAVE STATUS\G;
  ```
- **Interview Tip**: Discuss master-slave replication, read/write splitting, and handling replication lag.

---

### **18. Recursive Common Table Expressions (CTEs)**
Handle hierarchical data with recursive CTEs.

- **Recursive CTE Example**:
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
- **Interview Tip**: Use for tree-like structures (e.g., employee-manager relationships).

---

### **19. Advanced Window Functions**
Rank and aggregate data within partitions.

- **Window Function Example**:
  ```sql
  SELECT first_name, salary,
         SUM(salary) OVER (PARTITION BY department_id) AS dept_total_salary,
         ROW_NUMBER() OVER (PARTITION BY department_id ORDER BY salary DESC) AS dept_rank
  FROM employees;
  ```
- **Interview Tip**: Master RANK(), DENSE_RANK(), ROW_NUMBER(), and aggregate window functions.

---

### **20. Handling Large Datasets**
Manage large tables efficiently.

- **Batch Delete**:
  ```sql
  DELETE FROM employees
  WHERE hire_date < '2020-01-01'
  LIMIT 1000;
  ```
- **Interview Tip**: Discuss partitioning or archiving for large datasets.

---

### **21. Error Handling in Stored Procedures**
Ensure robust stored procedures.

- **Error Handling Example**:
  ```sql
  DELIMITER //
  CREATE PROCEDURE SafeInsertEmployee(
      IN p_first_name VARCHAR(50),
      IN p_salary DECIMAL(10,2)
  )
  BEGIN
      DECLARE EXIT HANDLER FOR SQLEXCEPTION
      BEGIN
          ROLLBACK;
          SELECT 'Error occurred, transaction rolled back' AS message;
      END;
      START TRANSACTION;
      INSERT INTO employees (first_name, salary) VALUES (p_first_name, p_salary);
      COMMIT;
      SELECT 'Insert successful' AS message;
  END //
  DELIMITER ;
  ```
- **Interview Tip**: Explain graceful error handling for production environments.

---

### **22. MySQL Security Best Practices**
Secure database access.

- **Change User Password**:
  ```sql
  ALTER USER 'root'@'localhost' IDENTIFIED BY 'strong_password';
  FLUSH PRIVILEGES;
  ```
- **Interview Tip**: Discuss removing anonymous users, disabling remote root login, and using SSL.

---

### **23. JSON Data Handling**
Work with JSON data types.

- **JSON Example**:
  ```sql
  CREATE TABLE users (
      id INT PRIMARY KEY,
      details JSON
  );
  INSERT INTO users (id, details)
  VALUES (1, '{"name": "John", "age": 30}');
  SELECT details->>'name' AS name FROM users;
  ```
- **Interview Tip**: Explain JSON use cases (e.g., flexible schemas).

---

### **24. Profiling and Debugging Queries**
Identify slow queries.

- **Enable and Use Profiling**:
  ```sql
  SET profiling = 1;
  SELECT * FROM employees WHERE salary > 50000;
  SHOW PROFILES;
  SHOW PROFILE FOR QUERY 1;
  ```
- **Interview Tip**: Discuss slow query log and profiling for optimization.

---

### **25. MySQL Cluster and Sharding**
Understand scalability options.

- **No Direct Command**: Conceptual knowledge of MySQL Cluster (NDB) or sharding.
- **Interview Tip**: Explain sharding for large datasets and its challenges (e.g., data distribution).

---
