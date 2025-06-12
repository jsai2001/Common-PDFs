
# AWS CloudWatch RUM Notes

## Overview
- **AWS CloudWatch RUM** (Real User Monitoring) is a service for collecting and analyzing web application performance data from the end-user perspective.
- Provides a more accurate view of application performance compared to internal network testing.

## Data Collected by CloudWatch RUM
1. **Page Load Times**
   - Measures how long it takes for users to load webpages.
   - Helps identify if users experience delays in app loading.
2. **JavaScript Errors**
   - Captures JavaScript errors occurring during user interactions.
   - Aids in debugging and improving application stability.
3. **HTTP Requests**
   - Collects data on failed HTTP requests only.
   - Provides request details to analyze and resolve issues.
4. **Browsers and Devices**
   - Automatically gathers browser and device information.
   - Identifies which devices/browsers are most used and detects specific issues tied to them.
5. **User Behavior**
   - Tracks user interactions, such as time spent on pages, navigation patterns, and exit points.
   - Helps debug performance issues and improve user experience.

## How CloudWatch RUM Works
- **RUM SDK**: A small piece of code injected into web applications.
  - Collects performance data and sends it to CloudWatch RUM.
  - Example of integrating RUM SDK (conceptual snippet, not production-ready):
    ```javascript
    <script>
      (function(n,t,e,r){/* AWS RUM SDK initialization code */
        var s = document.createElement("script");
        s.src = "https://<aws-rum-endpoint>/rum.js";
        s.async = true;
        document.head.appendChild(s);
      })(window, document, "aws-rum", "<your-app-id>");
    </script>
    ```
- **Data Storage and Analysis**:
  - CloudWatch RUM stores the collected data.
  - Provides tools for visualizing and analyzing performance metrics.

## Use Cases
- Debug performance issues.
- Understand user interactions and navigation.
- Enhance overall user experience based on real user data.

## Next Steps
- Integrate AWS CloudWatch RUM with a demo application to observe real-time data collection and analysis.


# Enabling AWS CloudWatch RUM Notes

## Overview
- This guide covers the installation and configuration of AWS CloudWatch RUM for a client application to monitor real user interactions.
- Demo application: Built using Vue.js and TypeScript, named `CloudwatchRum`.

## Steps to Enable AWS CloudWatch RUM

### 1. Create an App Monitor in AWS CloudWatch
- Navigate to **CloudWatch Management Console** > **Application and Monitoring** > **RUM**.
- Click **Add app monitor** and configure:
  - **Name**: e.g., `demoapp`.
  - **Application Domain**: Specify the domain (e.g., `localhost` for development; use production URL in production).
  - **Data Collection**: Default settings to collect performance telemetry, JavaScript errors, and HTTP errors.
  - **Cookies**: Enable/disable based on use case (default for demo).
  - **Session Sampling**: Default percentage for data collection (adjust lower for cost optimization in production).
  - **Authorization**: Select or create an **Identity Pool** (e.g., create a new one for demo, bypassing Amazon Cognito setup).
- Click **Create** to finalize the app monitor.

### 2. Install AWS RUM Web Package
- In the demo application (`CloudwatchRum`), install the AWS RUM package:
  ```bash
  npm install aws-rum-web
  ```
- Ensure all NPM packages are installed:
  ```bash
  npm install
  ```

### 3. Configure RUM in the Application
- Configuration is handled in `registerRum.ts`.
- Copy the RUM SDK code from the **App Monitor** in the CloudWatch console and paste it into the `registerRum.ts` function.
- Example RUM SDK configuration (simplified, not production-ready):
  ```javascript
  import { AwsRum } from 'aws-rum-web';

  export function registerRum() {
    const config = {
      appId: '<your-app-monitor-id>',
      appVersion: '1.0.0',
      identityPoolId: '<your-identity-pool-id>',
      endpoint: 'https://<aws-rum-endpoint>',
      telemetries: ['performance', 'errors', 'http']
    };
    const rum = new AwsRum(config);
  }
  ```
- Save changes to `registerRum.ts`.

### 4. Run the Application
- Start the development server:
  ```bash
  npm run serve
  ```
- Access the app at `http://localhost:8080`.
- The demo app simulates user interactions:
  - Makes requests to a public JSON API placeholder.
  - Configurable number of requests (e.g., 100).
  - Every third request fails and throws an error to simulate real user scenarios.

### 5. Verify Data Collection
- Navigate back to **CloudWatch Management Console** > **RUM** > **App Monitor**.
- Check the **Overview** page for data (may take ~5 minutes to appear).
- If no data appears:
  - Open the browser’s **Network Console** to inspect requests to the app monitor.
  - If requests fail, check the **Preview** tab for error details and troubleshoot accordingly.

## Notes
- **Development Errors**: Uncaught runtime errors may appear in the Vue dev server; this is normal and does not affect RUM data collection.
- **Data Visualization**: The next video will cover analyzing the collected data.

## Next Steps
- Explore the collected data (performance, errors, user behavior) in the CloudWatch RUM console.


# Understanding AWS CloudWatch RUM Data and Metrics Notes

## Overview
- AWS CloudWatch RUM collects and displays data from an application monitor connected to a client app.
- Data includes performance metrics, errors, HTTP requests, sessions, events, browsers/devices, and user journeys.
- Demo app: Configured to make requests and generate errors for testing.

## Accessing Data
- Navigate to **CloudWatch Management Console** > **RUM** > **App Monitor** (`demoapp`).
- Default timeframe: 1 week (adjustable).

## Data and Metrics Breakdown

### 1. Performance
- **Metrics**:
  - Page load times.
  - Errors.
  - Average load time by time of day (shows peak request times).
- **Web Vitals** (similar to Google Lighthouse):
  - Largest Contentful Paint (LCP).
  - First Input Delay (FID).
  - Cumulative Layout Shift (CLS).
  - Page load steps over time.
- **Interpretation**:
  - Red metrics indicate issues to fix.
  - Better metrics improve app performance and SEO (e.g., Google search rankings).
- **Use Case**: Identify performance bottlenecks as more data is collected.

### 2. Errors
- Lists errors grouped by page ID (e.g., `demo error for RUM`).
- **Demo Error Example**:
  - In `HomeView.vue`, `submitMultiplePosts` function intentionally throws errors:
    ```javascript
    async submitMultiplePosts(numberOfRequests) {
      for (let i = 0; i < numberOfRequests; i++) {
        if (i % 3 === 0) {
          throw new Error('Demo error for RUM'); // Intentional error
        }
        // Normal and failed requests logic
      }
    }
    ```
- **Error Details**:
  - Metadata, user details (via Amazon Cognito), user ID, session ID.
  - Event details: Error message, file name, stack trace.
- **Use Case**: Debug errors with detailed stack traces and user context.

### 3. HTTP Requests
- Only failed HTTP requests are collected.
- Example: A `PUT` request fails with a `404 Not Found`.
- **Metrics**:
  - Example: 68 requests across 2 sessions and 2 users in a day.
- **Use Case**: Analyze failed requests to identify API or network issues.

### 4. Sessions
- Each app instance creates a new session.
- Example: 2 sessions from 2 app instances, same user.
- **Details**: Navigate to session ID for in-depth data (page views, events).
- **Use Case**: Track user sessions to understand engagement.

### 5. Events
- Types: HTTP events, JavaScript error events, and other telemetries.
- Configured in `registerRum.ts`:
  ```javascript
  import { AwsRum } from 'aws-rum-web';
  
  export function registerRum() {
    const config = {
      appId: '<your-app-monitor-id>',
      telemetries: ['performance', 'errors', 'http'] // Collect performance, errors, HTTP
    };
    const rum = new AwsRum(config);
  }
  ```
- **Use Case**: Monitor specific event types for performance and error tracking.

### 6. Browsers and Devices
- Tracks devices and browsers used (e.g., Chrome in demo).
- **Use Case**: Identify performance issues specific to certain devices/browsers once the app is live.

### 7. User Journeys
- Tracks navigation across pages (e.g., start page to other pages).
- Demo limitation: Empty due to single-page app; requires additional configuration for multi-page tracking.
- **Use Case**: Analyze user navigation patterns to optimize user experience.

## Key Insights
- **Red Metrics**: Indicate issues needing immediate attention.
- **Data Usage**:
  - Debug errors with detailed metadata.
  - Optimize performance using web vitals.
  - Track user behavior via sessions and journeys.
- **Limitations**:
  - Only failed HTTP requests are tracked.
  - User journey tracking requires proper configuration for multi-page apps.

## Next Steps
- Create alarms in CloudWatch to receive notifications for performance issues or errors.


# Setting Up Alerts for AWS CloudWatch RUM Metrics

## Overview
- Alerts in AWS CloudWatch RUM notify users when application issues occur by creating alarms based on RUM metrics.
- Alarms are configured to trigger notifications (e.g., via SNS) when specific thresholds are exceeded.

## Steps to Create an Alarm

### 1. Navigate to Alarms
- In the **CloudWatch Management Console**, go to the **RUM** section for the demo app (`demoapp`).
- Scroll to **Configuration** > **Alarms** > **Create a new alarm**.
- Note: The process is identical to creating alarms from the CloudWatch alarms page.

### 2. Select Metric
- Choose **AWS/RUM** under CloudWatch metrics.
- Filter by **Application Name** (e.g., `demoapp`).
- Select a metric, e.g., `JsErrorCount` (suitable for the demo app generating JavaScript errors).
- Example metrics available:
  - `JsErrorCount`
  - Other metrics depend on use case; multiple alarms can be created.

### 3. Configure Metric and Condition
- **Statistic**: Change from `Average` to `Sum`.
- **Period**: Set to 1 minute (e.g., ~9 errors per minute observed).
- **Condition**: Set threshold, e.g., trigger if `JsErrorCount > 5` in 1 minute.
  - Example configuration:
    ```plaintext
    Metric: JsErrorCount
    Statistic: Sum
    Period: 1 minute
    Threshold: > 5 errors
    ```

### 4. Configure Notification
- Select an existing **SNS topic** (e.g., `API admins`) or create a new one.
- SNS topic sends notifications to subscribed users (e.g., via email or SMS).

### 5. Name and Create Alarm
- Name the alarm, e.g., `rumdemo`.
- Preview and create the alarm.

### 6. Test the Alarm
- In the demo app, initiate 100 requests to generate errors:
  - Example: The app is configured to throw JavaScript errors periodically.
- Wait 1–2 minutes for the alarm to trigger.
- Verify in CloudWatch: Alarm should transition to **In Alarm** state, notifying the SNS topic subscribers.

## Troubleshooting
- **Issue**: Alarm shows **Insufficient Data** after initiating requests.
  - **Cause**: CloudWatch RUM may ignore sessions on `localhost` due to repeated testing.
  - **Solution**:
    1. Open the app in an **incognito window**.
    2. Open the browser’s **Network Console** (`Right-click > Inspect > Network`).
    3. Restart requests and verify requests are sent to the AWS RUM app monitor.
    - Example network request URL:
      ```plaintext
      https://<aws-rum-endpoint>/appmonitor/<app-monitor-id>
      ```
- **Validation**: Ensure requests appear in the Network tab; if not, check RUM configuration.

## Key Notes
- Alarms are critical for proactive monitoring and issue resolution.
- Metric selection and thresholds depend on application behavior and monitoring goals.
- Incognito mode helps bypass session-related issues during local testing.

## Next Steps
- Monitor alarm performance and refine thresholds or notifications as needed.
- Explore additional RUM metrics for comprehensive monitoring.