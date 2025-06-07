
# CloudWatch Logs Insights Notes

## Overview
- **Purpose**: Use CloudWatch Logs Insights to query logs and diagnose application issues.
- **Difference from Metrics**: Metrics show *how often* something happens; logs reveal *what* happened.
- **Access**: Navigate to CloudWatch Console > Logs > Log Insights.

## Steps to Query Logs
1. **Select Timeframe**:
   - Default: Last 1 hour.
   - Adjust timeframe based on when the error occurred (use "Custom" for non-default options).
2. **Select Log Group**:
   - Each AWS service sends logs to a specific log group (e.g., `CloudWatchcourseAPI` for CodeBuild, Lambda).
   - Example: Choose the log group for the "Put Item Function" (Lambda).
3. **Write and Run Query**:
   - Use the query editor to specify fields, filters, and aggregations.
   - Limit results to reduce query costs.

## Common Query Operations
### 1. Displaying Fields
- **Purpose**: Select specific fields to display in query results.
- **Query Example**:
  ```plaintext
  fields @message
  ```
  - Displays the `@message` field from the logs.
- **Best Practice**: Use `limit` to restrict the number of results (e.g., `limit 100`).
  ```plaintext
  fields @message | limit 100
  ```

### 2. Filtering Logs
- **Purpose**: Narrow down logs to find specific errors (e.g., by error message).
- **Query Example**:
  ```plaintext
  fields @message
  | filter @message like /exception/
  ```
  - Filters logs containing the word "exception" in the `@message` field.
  - Example result: `Validation exception: One or more parameter values were invalid, missing key ID in the payload`.
- **Details**:
  - Use `|` (pipe) to chain operations.
  - Combine with `like` to search for keywords in the message.
  - Expand results to view error details, including stack traces for precise error location.

### 3. Aggregating Data
- **Purpose**: Perform calculations (e.g., sum, average) on log data.
- **Query Example (Count Errors)**:
  ```plaintext
  fields @message
  | filter @message like /exception/
  | stats count(*) as error_count
  ```
  - Counts the total number of logs with "exception" (e.g., `error_count: 66`).
- **Grouping by Time**:
  - Group results into time intervals (e.g., 5-minute bins) for better visualization.
  - **Query Example**:
    ```plaintext
    fields @message
    | filter @message like /exception/
    | stats count(*) as error_count by bin(5m)
    ```
    - Groups error counts by 5-minute intervals, returning timestamps and counts (e.g., 5 rows with timestamps and error counts).

## Practical Tips
- **Generate Test Data**: If no results appear, use local testing tools to initiate requests (successful and failed) to populate logs.
- **Cost Management**: Limit query results to reduce costs, as more results increase query expenses.
- **Error Diagnosis**:
  - Use stack traces in expanded log results to pinpoint error locations.
  - Filter by specific error messages (e.g., "validation exception") to focus on relevant issues.
- **Visualization**: Grouping by time intervals (e.g., `bin(5m)`) enhances visualization of error trends.

## Key Commands
- **Fields**: `fields @message` (select fields to display).
- **Filter**: `filter @message like /keyword/` (filter by keyword).
- **Stats**: `stats count(*) as error_count` (aggregate data).
- **Bin**: `by bin(5m)` (group by time intervals).

## Use Case
- **Scenario**: Application bug causes a "validation exception" due to missing ID parameter.
- **Action**:
  - Query logs with `filter @message like /exception/`.
  - Use stack traces to identify the error source.
  - Aggregate errors over time (`stats count(*) by bin(5m)`) to analyze error frequency.

## Next Steps
- Explore additional Log Insights commands (e.g., `sum`, `avg`, `max`, `min`) for advanced analysis.
- Integrate log queries with dashboards for ongoing monitoring.


# CloudWatch Log Metrics Notes

## Overview
- **Purpose**: Convert application logs into CloudWatch metrics to visualize and analyze error occurrences over time.
- **Benefit**: Metrics provide a quicker, more visual way to track errors compared to querying logs repeatedly.
- **Use Case**: Track specific errors (e.g., `ValidationException`) in an API to understand application performance.

## Steps to Create Log-Based Metrics
1. **Navigate to Log Groups**:
   - In CloudWatch Console, go to Logs > Log Groups.
   - Select the relevant log group (e.g., for a Lambda function like `putItem`).
2. **Verify Log Data**:
   - Check log streams for recent events.
   - If no events exist, use the demo app to generate requests (e.g., enable "Fail all requests" to produce errors).
3. **Create a Metric Filter**:
   - In the selected log group, go to "Metric Filters" > Create Metric Filter.
4. **Define Filter Pattern**:
   - Specify a pattern to match log events (e.g., errors with `ValidationException`).
   - Options:
     - Match terms (e.g., `error`, `warning`).
     - Match JSON properties (e.g., `errorType = ValidationException`).
     - Match status codes (e.g., `statusCode = 500`).
   - **Example Pattern**:
     ```plaintext
     { $.errorType = "ValidationException" }
     ```
     - Matches logs where `errorType` is `ValidationException`.
5. **Test the Pattern**:
   - Copy a sample log entry containing the error (e.g., from log streams).
   - Paste into the "Test Pattern" section, replacing demo data.
   - Verify at least one row matches the pattern.
6. **Configure Metric Details**:
   - **Filter Name**: e.g., `ValidationExceptions`.
   - **Namespace**: Create or select a namespace (e.g., `MetricFilters`).
   - **Metric Name**: e.g., `ValidationExceptions`.
   - **Metric Value**: Specify the value to increment when the filter matches (default: `1`).
     - Use `1` for counting occurrences.
     - Other values possible based on metric type.
   - **Default Value**: Optional (e.g., `0`); if unset, no value is pushed when no match occurs (cost-efficient).
   - **Unit**: Optional (e.g., `Seconds`); leave unset if not applicable.
   - **Dimensions**: Optional; leave as default for simple metrics.
7. **Review and Create**:
   - Review settings and create the metric filter.
8. **Generate and Verify Metric Data**:
   - Use the demo app to generate requests (e.g., 100 requests with errors).
   - Navigate to CloudWatch > Metrics > Namespace (e.g., `MetricFilters`) to view the metric (e.g., `ValidationExceptions`).

## Example Filter Pattern
```plaintext
{ $.errorType = "ValidationException" }
```
- **Explanation**:
  - Matches JSON log events where the `errorType` property equals `ValidationException`.
  - Used to count occurrences of validation errors in the `putItem` Lambda function.

## Key Points
- **Log Groups**: Each AWS service sends logs to a specific log group (e.g., Lambda functions have dedicated groups).
- **Filter Logic**: The filter acts like an `if` statement, incrementing the metric value when a log matches the pattern.
- **Testing**: Always test the filter pattern with real or copied log data to ensure accuracy.
- **Metric Value**: Default to `1` for counting errors; adjust for other use cases (e.g., duration in seconds).
- **Cost Efficiency**: Avoid setting a default value unless necessary to reduce costs.
- **Visualization**: Use the created metric in graphs or dashboards to monitor application performance.

## Practical Tips
- **Error Generation**: Use the demo app’s "Fail all requests" option to generate errors for testing.
- **Naming**: Use meaningful names for filters and metrics in production (e.g., `APIValidationErrors` instead of `ValidationExceptions`).
- **Namespace Organization**: Group related metrics in a custom namespace for easier management.
- **Metric Integration**: Combine log-based metrics with other CloudWatch metrics for comprehensive analysis.

## Next Steps
- Add the metric to a CloudWatch dashboard for persistent monitoring.
- Explore advanced filter patterns (e.g., matching multiple error types or status codes).
- Use metrics in alarms to trigger notifications for high error rates.