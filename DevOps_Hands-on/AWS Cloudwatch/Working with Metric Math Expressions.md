
# CloudWatch Metric Math Notes

## Overview
- **Feature**: Amazon CloudWatch Metric Math
- **Purpose**: Perform calculations across multiple metrics for real-time analysis.
- **Capabilities**: 
  - Query multiple CloudWatch metrics.
  - Use mathematical expressions to create new time series data streams.
  - Derive insights from existing metrics using mathematical functions.

## Steps to Implement Metric Math in CloudWatch
1. **Navigate to CloudWatch**:
   - Go to "All Metrics" in the CloudWatch console.
2. **Select Metrics**:
   - Choose metrics (e.g., Lambda function metrics by function name).
   - Example: Select "Put Item Function" and its "Error" metric.
3. **Graph Metrics**:
   - Navigate to the "Graph Metrics" tab to visualize selected metrics.
   - Adjust timeframe (e.g., last 4 weeks) if no data is visible.
4. **Add Math Expressions**:
   - Use the "Add Math" option to apply mathematical functions.
   - Example: Apply the `RATE` function to calculate the rate of change of a metric.

## Key Metric Math Functions
### 1. RATE Function
- **Purpose**: Calculates the rate of change of a metric (difference between the latest and previous data points).
- **Steps**:
  - Navigate to "All Functions" and select `RATE`.
  - Assign an ID (e.g., M1 for errors) to reference in expressions.
  - Example: Change aggregation from average to sum for total errors over a period (e.g., 5 minutes).
- **Expression Example**:
  ```plaintext
  RATE(M1)
  ```
  - `M1` refers to the error metric ID.
  - Displays the rate of errors over time.

### 2. DAY Function
- **Purpose**: Filters metrics based on the day of the week.
- **Details**:
  - Day numbers: 1 (Monday) to 7 (Sunday).
  - Use with `IF` statements to filter specific days (e.g., weekends: Saturday and Sunday).
- **Expression Example**:
  ```plaintext
  IF(DAY(M1) >= 6, M1)
  ```
  - Filters errors occurring on Saturday (6) and Sunday (7).

### 3. FILL Function
- **Purpose**: Fills missing data points in a metric to improve graph readability.
- **Details**:
  - Specify the metric ID and a fill value (e.g., 0 for no errors).
  - Useful for line graphs with intermittent data.
- **Expression Example**:
  ```plaintext
  FILL(M1, 0)
  ```
  - Fills missing error data points with 0, creating a continuous line graph.

## Practical Tips
- **Metric IDs**: Each metric is assigned an ID (e.g., M1, M2) for use in expressions.
- **Editing Expressions**:
  - Use the edit button to modify expressions (e.g., replace `METRICS()` with specific IDs like `M1` for targeted calculations).
- **Zooming and Visualization**:
  - Zoom into the graph to focus on specific data points.
  - Reset zoom to view the full dataset.
- **Combining Metrics**:
  - Select multiple metrics (e.g., errors and invocations) but ensure expressions target specific IDs to avoid unintended aggregations.
- **Graph Readability**:
  - Use `FILL` to handle missing data points for clearer visualizations.

## Example Workflow
1. Select the "Error" metric for a Lambda function.
2. Apply the `RATE` function to calculate the error rate:
   ```plaintext
   RATE(M1)
   ```
3. Filter errors for weekends using the `DAY` function:
   ```plaintext
   IF(DAY(M1) >= 6, M1)
   ```
4. Use `FILL` to handle missing data:
   ```plaintext
   FILL(M1, 0)
   ```
5. Adjust aggregation (e.g., sum instead of average) and timeframe for meaningful insights.

## Notes
- **Common Issue**: Missing data points can make line graphs less readable; use `FILL` to address this.
- **Next Steps**: Explore combining multiple metrics and advanced expressions for complex calculations (covered in subsequent clips).


# CloudWatch Custom Metric Math and Dashboards Notes

## Creating Custom Metric Math Expressions

### Overview
- **Purpose**: Generate new data points by combining multiple metrics with custom mathematical operations.
- **Previous Context**: Built-in functions (e.g., `RATE`, `DAY`, `FILL`) were used to process metrics.
- **Focus**: Create custom expressions from scratch using multiple metrics (e.g., errors, invocations, duration).

### Steps to Create Custom Expressions
1. **Select Metrics**:
   - Navigate to CloudWatch > "All Metrics".
   - Choose metrics for a function (e.g., "Put Item Function").
   - Example: Select `Errors` (M1), `Invocations` (M2), and `Duration` (M3).
2. **Access Graph Metrics**:
   - Move to the "Graph Metrics" tab to visualize selected metrics.
3. **Add Custom Math Expression**:
   - Click "Add Math" and select "Start with an empty expression".
   - Write custom operations using metric IDs (e.g., M1, M2, M3).

### Example Custom Expressions
1. **Average Duration per Invocation**:
   - **Goal**: Calculate average duration per invocation.
   - **Expression**:
     ```plaintext
     M3 / M2
     ```
     - `M3`: Duration (milliseconds).
     - `M2`: Invocations.
   - **Result**: Displays average duration in milliseconds.
   - **Steps**:
     - Apply the expression.
     - Unselect other metrics to view only the result.

2. **Error Rate as Percentage**:
   - **Goal**: Calculate error rate (errors per invocation) and convert to percentage.
   - **Expression**:
     ```plaintext
     (M1 / M2) * 100
     ```
     - `M1`: Errors.
     - `M2`: Invocations.
     - Multiplied by 100 to get percentage.
   - **Result**: Shows error rate (e.g., 33% for 1-in-3 failures, 100% when all requests fail).
   - **Naming**:
     - Name the expression (e.g., "Error Rate") for clarity (ID: E1).

3. **Filter Error Rates (≥25%)**:
   - **Goal**: Display error rates ≥25% to focus on significant issues.
   - **Expression**:
     ```plaintext
     IF(E1 >= 25, E1, 0)
     ```
     - `E1`: Error rate percentage (from previous expression).
     - If `E1` ≥ 25, output `E1`; otherwise, output 0.
   - **Notes**:
     - Use uppercase `IF` (case-sensitive).
     - Adjust threshold (e.g., change 25 to 75) to filter stricter conditions.
   - **Result**: Only shows error rates ≥25%, with others set to 0.

### Key Points
- **Metric IDs**: Use IDs (e.g., M1, M2, E1) to reference metrics or expressions.
- **Error Handling**: Ensure correct syntax (e.g., uppercase `IF`) to avoid errors.
- **Use Case**: Custom expressions allow flexible analysis (e.g., focus on high error rates for business needs).
- **Visualization**: Unselect irrelevant metrics to focus on the expression result.

## Adding Metrics to Dashboards

### Overview
- **Purpose**: Save graphs with custom metrics/expressions for persistent access.
- **Problem**: Graphs are not saved if the browser is closed or a different device is used.
- **Solution**: Add graphs to a CloudWatch dashboard.

### Steps to Create/Add to a Dashboard
1. **Navigate to Dashboards**:
   - Go to CloudWatch > "Dashboards".
   - Create a new dashboard (e.g., "Metrics") or edit an existing one.
2. **Add Widgets**:
   - Choose widget type (e.g., line graph, alarm statuses, text, logs).
   - For metrics, select "Line" for time-series data.
3. **Add Metrics to Widget**:
   - **Option 1**: Build from scratch in the dashboard:
     - Select metrics, apply math expressions, and create the widget.
   - **Option 2**: Add from an existing graph:
     - In the metrics graph, go to "Actions" > "Add to Dashboard".
     - Select or create a dashboard (e.g., "Metrics").
     - Choose widget type (e.g., "Line").
     - Set a title (e.g., "MathExpression").
     - Add to the dashboard.
4. **Edit Dashboard**:
   - Modify widgets or add new ones based on business needs.
   - Save changes to persist the dashboard.

### Key Points
- **Widget Types**: Line graphs are ideal for metrics; other options (e.g., text, logs) suit different use cases.
- **Business Needs**: Customize dashboards to reflect specific requirements (e.g., monitoring key metrics).
- **Accessibility**: Dashboards ensure metrics are available across sessions/devices.
- **Flexibility**: Add multiple widgets with different metrics/expressions for comprehensive monitoring.

## Next Steps
- Explore saving and sharing dashboards for team collaboration.
- Investigate advanced dashboard customization for specific use cases.