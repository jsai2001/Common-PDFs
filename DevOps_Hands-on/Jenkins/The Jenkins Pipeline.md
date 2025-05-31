
# Jenkins Pipeline Creation Notes

## Creating a Basic Pipeline Project
- **Steps**:
  1. Log into the Jenkins server.
  2. Click **New Item**, enter a name (e.g., `first pipeline`), select **Pipeline** project type, and click **OK**.
  3. In the **Pipeline** section, select **Try Sample Pipeline** > **Hello World** to insert a basic pipeline script.
  4. Save and click **Build Now** to run the pipeline.
  5. Verify the job completion and explore the Jenkins interface.
- **Sample Hello World Pipeline**:
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Hello') {
              steps {
                  echo 'Hello World'
              }
          }
      }
  }
  ```
  - **Explanation**: This declarative pipeline uses the `any` agent to run on any available executor and includes one stage (`Hello`) with a single `echo` step to print "Hello World".

## Declarative Pipeline Syntax
- **Pipeline Formats**:
  - **Scripted**: Starts with `node`, uses Groovy-based DSL.
  - **Declarative**: Starts with `pipeline`, evolved from scripted DSL for easier configuration as code.
  - **Course Focus**: Declarative pipelines.
- **Required Sections**:
  1. **Agent**: Specifies where the pipeline runs.
     - `any`: Runs on any available executor (common for controller-based Jenkins).
     - `label`: Specifies a specific agent (e.g., for a particular OS or tool).
     - `docker`: Runs inside a Docker container for consistent build environments.
     - `none`: Defers agent selection to individual stages.
  2. **Stages**: Defines parts of the process (e.g., `Build`, `Test`, `Deploy` for CI/CD).
     - Must contain at least one stage.
  3. **Steps**: Contains commands/scripts that perform actions within a stage.
     - Multiple commands can be included per step.
- **Purpose**: Declarative syntax simplifies capturing a project’s full configuration as code.

## Creating a Multi-Stage Declarative Pipeline
- **Steps**:
  1. Use the sample pipeline from the exercise files (available in the course’s GitHub repository).
  2. Copy the pipeline script (e.g., via the stacked squares icon in the README).
  3. Paste it into a new or existing pipeline job in Jenkins.
  4. Save and click **Build Now**.
  5. View the pipeline’s graphical representation, hover over stages to see logs, and click steps for detailed logs.
- **Sample Multi-Stage Pipeline** (from exercise files):
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Build') {
              steps {
                  echo 'Building the application...'
              }
          }
          stage('Test') {
              steps {
                  echo 'Running unit tests...'
                  echo 'Running integration tests...'
                  echo 'Running end-to-end tests...'
              }
          }
          stage('Deploy') {
              steps {
                  echo 'Deploying to staging...'
              }
          }
          stage('Release') {
              steps {
                  echo 'Releasing to production...'
              }
          }
      }
  }
  ```
  - **Explanation**: This pipeline has four stages (`Build`, `Test`, `Deploy`, `Release`). The `Test` stage includes three steps to simulate different testing phases. The `any` agent allows execution on any available Jenkins executor.

## Additional Notes
- **Pipeline Visualization**: Jenkins displays a graphical view of stages, with logs accessible by hovering over stages or steps.
- **Extending Pipelines**: The `echo` step is basic; more complex steps (e.g., running scripts, building artifacts, deploying) will be covered in later lessons.
- **Exercise Files**: Use the GitHub repository’s README for sample pipelines and additional resources.
- **Next Steps**: Explore advanced steps beyond `echo` to build, test, and deploy applications effectively.



# Jenkins Pipeline Snippet Generator and Variables Notes

## Using the Pipeline Snippet Generator
- **Purpose**: Simplifies writing pipeline steps by generating code snippets for Jenkins pipeline scripts.
- **Common Basic Steps**:
  - `echo`: Prints a message to the console.
  - `git`: Checks out code from a Git repository.
  - `sh`: Runs shell scripts or commands on the local system.
  - `archiveArtifacts`: Stores artifacts created by a job.
- **Accessing Steps**:
  - Full list in Jenkins documentation under **Basic Steps Reference** and **Pipeline Steps Reference** (includes plugin-specific steps).
- **Using the Snippet Generator**:
  1. In a pipeline job, click **Pipeline Syntax** link below the pipeline field.
  2. Select a step (e.g., `archiveArtifacts`) from the **Sample Step** dropdown.
  3. Configure parameters (e.g., `*.txt` for archiving all TXT files).
  4. Expand **Advanced** options to set additional configurations (e.g., allow empty archive, archive only on success, fingerprint artifacts).
  5. Click **Generate Pipeline Script** to create a snippet.
  6. Copy and paste the snippet into the pipeline script.
  7. Use **Apply** to check syntax before saving.
  8. Save and click **Build Now** to run the pipeline.
- **Example Pipeline with Snippet**:
  ```groovy
  pipeline {
      agent any
      stages {
          stage('Create File') {
              steps {
                  sh 'echo "Sample report" > report.txt'
              }
          }
          stage('Archive') {
              steps {
                  archiveArtifacts artifacts: '*.txt', allowEmptyArchive: true, onlyIfSuccessful: true, fingerprint: true
              }
          }
      }
  }
  ```
  - **Explanation**: The pipeline creates a `report.txt` file using a shell command and archives all `.txt` files. The `archiveArtifacts` step (generated via snippet generator) allows empty archives, archives only on successful builds, and fingerprints artifacts for tracking.

- **Verification**: Successful archiving is indicated by a blue icon next to the build. Artifacts (e.g., `report.txt`) are accessible from the job’s page after a successful build.

## Using Variables in Pipelines
- **Types of Variables**:
  - **Environment Variables**: Dynamic values for pipeline or stage scope.
  - **Current Build Variables**: Properties of the `currentBuild` object, reflecting build state.
  - **Parameters**: Covered in a later lesson.
- **Environment Variables**:
  - **Naming**: Use all capitals (e.g., `MAX_SIZE`) to distinguish from lowercase keywords.
  - **Scope**:
    - **Global**: Defined in an `environment` block at the pipeline level, accessible to all steps.
    - **Local**: Defined in an `environment` block within a stage, accessible only to that stage’s steps.
  - **Referencing**:
    - Use `env.VARIABLE_NAME` or just `VARIABLE_NAME`.
    - In strings, prefix with `$` (e.g., `$VARIABLE_NAME` or `${VARIABLE_NAME}` for clarity).
    - **Best Practice**: Stay consistent with referencing style throughout the pipeline.
- **Current Build Variables**:
  - Accessed via `currentBuild.PROPERTY` (case-sensitive, CamelCase, e.g., `currentBuild.startTime`, `currentBuild.duration`, `currentBuild.currentResult`).
  - Used for dynamic operations based on build state.
  - In strings, prefix with `$` (e.g., `echo "Build duration: ${currentBuild.duration}"`).
- **Global Variables Reference**:
  - Accessible via **Pipeline Syntax** > **Global Variables Reference** link.
  - Lists all default environment and `currentBuild` variables available to pipelines.

- **Example Pipeline with Variables**:
  ```groovy
  pipeline {
      agent any
      environment {
          MAX_SIZE = '10'
          MIN_SIZE = '1'
      }
      stages {
          stage('Default Scale') {
              steps {
                  echo "Default: Max size is ${MAX_SIZE}, Min size is ${MIN_SIZE}"
              }
          }
          stage('Scale by 10X') {
              environment {
                  MAX_SIZE = '100'
                  MIN_SIZE = '10'
              }
              steps {
                  echo "Scaled: Max size is ${MAX_SIZE}, Min size is ${MIN_SIZE}"
              }
          }
      }
  }
  ```
  - **Explanation**: Defines global environment variables `MAX_SIZE` and `MIN_SIZE`. The `Scale by 10X` stage overrides these with local values. Output shows `Default Scale` using global values (`10`, `1`) and `Scale by 10X` using local values (`100`, `10`).
  - **Viewing Output**: Use **Pipeline Steps** view for a clear breakdown of each step’s output, showing variable values.

## Additional Notes
- **Snippet Generator Benefits**: Simplifies step configuration, especially for plugins with complex parameters.
- **Variable Best Practices**:
  - Use consistent naming and referencing conventions.
  - Leverage `currentBuild` for dynamic build-time decisions.
- **Resources**:
  - Check Jenkins documentation for comprehensive step and variable references.
  - Use exercise files for sample pipelines (e.g., the one with the `sh` step creating `report.txt`).
- **Verification**:
  - Use **Apply** to validate pipeline syntax.
  - Check **Pipeline Steps** or **Console Output** to confirm variable usage and step execution.


# Jenkins Pipeline Parameters Notes

## Overview of Pipeline Parameters
- **Definition**: Parameters are variables in Jenkins pipelines that receive values when a job is triggered.
- **Placement**: Defined in a `parameters` block at the beginning of the pipeline code.
- **Accessing Parameters**:
  - Referenced using `params.PARAMETER_NAME` or just `PARAMETER_NAME`.
  - In strings, use `$PARAMETER_NAME` or `${PARAMETER_NAME}` for clarity.
- **Naming Convention**: Use all capital letters (e.g., `MY_PARAM`) to distinguish from keywords.
- **Required Fields**:
  - **Name**: Identifier for the parameter.
  - **Default Value**: Initial value if none provided.
  - **Description**: Explains the expected value type or purpose.
- **Behavior**:
  - Parameters modify the Jenkins interface but require an initial build to register.
  - First build may fail if default values are invalid or blank.
  - Subsequent builds enable the **Build with Parameters** option in the Jenkins UI.
  - Changes to parameters require one build to take effect.

## Types of Parameters
1. **String**:
   - Single-word text input.
   - Represented as a text field in the Jenkins UI.
2. **Text**:
   - Multi-line text input for longer values.
   - Also uses a text field in the UI.
3. **Boolean**:
   - True/false value, shown as a checkbox in the UI.
4. **Choice**:
   - Dropdown list of predefined options.
   - First option in the list is the default value.
5. **Password**:
   - For sensitive data (e.g., passwords, API keys).
   - Input is masked (hidden with dots) in the UI to prevent exposure.

## Example Pipeline with Parameters
Below is a sample pipeline from the exercise files demonstrating all parameter types:

```groovy
pipeline {
    agent any
    parameters {
        string(name: 'USERNAME', defaultValue: 'guest', description: 'Enter your username')
        text(name: 'BIO', defaultValue: 'No bio provided', description: 'Enter your bio')
        booleanParam(name: 'DEBUG', defaultValue: false, description: 'Enable debug mode')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Select environment')
        password(name: 'API_KEY', defaultValue: 'secret123', description: 'Enter API key')
    }
    stages {
        stage('Display Parameters') {
            steps {
                echo "Username: ${params.USERNAME}"
                echo "Bio: ${params.BIO}"
                echo "Debug Mode: ${params.DEBUG}"
                echo "Environment: ${params.ENVIRONMENT}"
                echo "API Key: ${params.API_KEY}"
            }
        }
    }
}
```

- **Explanation**:
  - Defines five parameters: `USERNAME` (string), `BIO` (text), `DEBUG` (boolean), `ENVIRONMENT` (choice), and `API_KEY` (password).
  - The `Display Parameters` stage uses `echo` to output each parameter’s value.
  - On the first build, the **Build** option is used, and parameters may not be available if defaults are invalid.
  - After the first build, **Build with Parameters** appears, allowing users to input values via the Jenkins UI.
  - The `API_KEY` value is masked in the UI for security.

## Steps to Use Parameters
1. Create a new pipeline project in Jenkins.
2. Add a `parameters` block with desired parameter definitions (e.g., from exercise files).
3. Save and run the pipeline (**Build Now**).
4. On the first build, parameters may not appear, and the build might fail if defaults are invalid.
5. Refresh the Jenkins job page to see **Build with Parameters**.
6. Click **Build with Parameters** to input values:
   - String/text: Enter freeform text.
   - Boolean: Check/uncheck the box.
   - Choice: Select from the dropdown.
   - Password: Enter a value (displayed as dots for security).
7. Run the pipeline multiple times to test different parameter values.

## Additional Notes
- **UI Interaction**:
  - Parameters create input fields in the Jenkins UI after the first build.
  - Password parameters are concealed to prevent exposure.
  - Choice parameters default to the first option in the list.
- **Best Practices**:
  - Use clear descriptions to guide users on expected input.
  - Ensure default values are valid to avoid initial build failures.
  - Maintain consistent naming (all caps) for readability.
- **Troubleshooting**:
  - If parameters don’t appear, run the pipeline once and refresh the job page.
  - Check the **Pipeline Steps** or **Console Output** to verify parameter values.
- **Resources**:
  - Use the exercise files for the sample pipeline with all parameter types.
  - Experiment with adding/changing parameters to observe UI updates.


# Jenkins Conditional Expressions and Manual Approvals Notes

## Overview
- **Purpose**: Use conditional logic to control stage execution and manual approvals to pause pipelines for user interaction.
- **Key Features**:
  - **Conditional Expressions**: Use the `when` directive to determine if a stage should run.
  - **Manual Approvals**: Use the `input` step to pause pipelines and require user confirmation.

## Conditional Expressions
- **Syntax**: Defined using a `when` block inside a stage.
- **Conditions**:
  1. **Branch**: Runs a stage only for specific Git branches.
     - Example: Run a stage only for the `main` branch.
  2. **Environment**: Checks if an environment variable has a specific value.
     - Example: Run a stage if `ENVIRONMENT` is `dev`.
  3. **Expression**: Uses Groovy expressions with parameters/variables for flexible logic.
     - Example: Run a stage if a parameter meets a condition (e.g., `params.ENV != 'prod'`).
- **Behavior**: If the condition evaluates to `true`, the stage runs; otherwise, it is skipped.
- **Resources**: Refer to Apache Groovy documentation for advanced expression syntax.

## Manual Approvals
- **Syntax**: Uses the `input` step to pause a pipeline and wait for user interaction.
- **Options**:
  - Custom message to inform users about the action (e.g., "Confirm production deployment").
  - Buttons to **Proceed** or **Abort** the pipeline.
- **Use Case**: Common for critical stages like production deployments to ensure manual verification.

## Example Pipeline with Conditionals and Approvals
Below is a sample pipeline from the exercise files demonstrating conditional expressions and manual approvals:

```groovy
pipeline {
    agent any
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging', 'prod'], description: 'Select deployment environment')
    }
    stages {
        stage('Build') {
            steps {
                echo "Building for ${params.ENVIRONMENT} environment..."
            }
        }
        stage('Deploy to Non-Production') {
            when {
                expression { params.ENVIRONMENT != 'prod' }
            }
            steps {
                echo "Deploying to ${params.ENVIRONMENT} environment..."
            }
        }
        stage('Deploy to Production') {
            when {
                expression { params.ENVIRONMENT == 'prod' }
            }
            steps {
                input message: 'Confirm deployment to production?', ok: 'Deploy'
                echo "Deploying to production..."
            }
        }
    }
}
```

- **Explanation**:
  - **Parameters**: A `choice` parameter (`ENVIRONMENT`) allows selecting `dev`, `staging`, or `prod`.
  - **Build Stage**: Runs unconditionally, echoing the selected environment.
  - **Deploy to Non-Production Stage**: Uses a `when` condition to run only if `ENVIRONMENT` is not `prod` (i.e., for `dev` or `staging`).
  - **Deploy to Production Stage**:
    - Uses a `when` condition to run only if `ENVIRONMENT` is `prod`.
    - Includes an `input` step that pauses the pipeline, displaying a confirmation dialog with "Deploy" and "Abort" options.
  - **Behavior**:
    - For `dev` or `staging`, the pipeline skips the production stage and auto-deploys to non-production.
    - For `prod`, the non-production stage is skipped, and the pipeline pauses at the production stage until the user clicks "Deploy".
- **Execution**:
  1. Save the pipeline and click **Build with Parameters**.
  2. Select an environment (e.g., `dev`) and click **Build** to test non-production deployment.
  3. Run again, select `prod`, and confirm the deployment via the dialog box.

## Steps to Implement
1. Create a new pipeline job in Jenkins.
2. Copy the pipeline script from the exercise files (or use the example above).
3. Save and run the pipeline (**Build with Parameters** after the first build).
4. Test with different environment values:
   - For `dev` or `staging`, verify the non-production stage runs and production is skipped.
   - For `prod`, confirm the pipeline pauses at the `input` step, then click **Deploy** to proceed.
5. Check **Pipeline Steps** or **Console Output** to verify stage execution and input behavior.

## Additional Notes
- **First Run**: Parameters require an initial build to register in the Jenkins UI.
- **UI Interaction**: The `input` step creates a dialog box when a stage pauses, visible by hovering over the stage in the pipeline view.
- **Best Practices**:
  - Use clear `input` messages to guide users.
  - Test conditions thoroughly to ensure correct stage execution.
  - Leverage Groovy expressions for complex logic in `when` blocks.
- **Resources**: Use exercise files for the sample pipeline and consult Apache Groovy documentation for advanced conditional expressions.


# Parameterized Pipeline Challenge Notes

## Challenge Overview
- **Objective**: Update a Jenkins pipeline to include parameters for environment selection, secure API key input, and a changelog, with conditional deployment and customized reporting.
- **Requirements**:
  1. Add three parameters:
     - `ENVIRONMENT`: Choice parameter with options `development`, `staging`, `production` (default: `development`).
     - `API_KEY`: Password parameter for secure input (default: `123ABC`).
     - `CHANGELOG`: Text parameter for freeform text input.
  2. Update the pipeline with three stages: `Test`, `Deploy`, and `Report`.
  3. Modify the `Deploy` stage to run only for the `production` environment.
  4. Update the `Report` stage to:
     - Use the `CHANGELOG` parameter for report content.
     - Name the report file based on the selected `ENVIRONMENT`.
- **Resources**:
  - Use the pipeline template from the exercise files.
  - Leverage the **Pipeline Syntax Tool** (Declarative Directive Generator) for generating parameter and step snippets.
- **Estimated Time**: 15–20 minutes.

## Solution Approach
1. Create a new pipeline job in Jenkins.
2. Copy the pipeline template from the exercise files.
3. Add a `parameters` block with the specified parameters.
4. Update the `Deploy` stage with a `when` condition to run only for `production`.
5. Modify the `Report` stage to write the `CHANGELOG` to a file named after the `ENVIRONMENT`.
6. Use the **Pipeline Syntax Tool** to generate snippets for parameters and steps (e.g., `archiveArtifacts`).
7. Save and run the pipeline with **Build with Parameters** to test different environments.

## Example Pipeline
Below is a sample pipeline meeting the challenge requirements:

```groovy
pipeline {
    agent any
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['development', 'staging', 'production'], description: 'Select target environment')
        password(name: 'API_KEY', defaultValue: '123ABC', description: 'Enter API key')
        text(name: 'CHANGELOG', defaultValue: '', description: 'Enter changelog for the report')
    }
    stages {
        stage('Test') {
            steps {
                echo "Running tests for ${params.ENVIRONMENT} environment..."
            }
        }
        stage('Deploy') {
            when {
                expression { params.ENVIRONMENT == 'production' }
            }
            steps {
                echo "Deploying to production with API key: ${params.API_KEY}"
            }
        }
        stage('Report') {
            steps {
                sh "echo '${params.CHANGELOG}' > ${params.ENVIRONMENT}_report.txt"
                archiveArtifacts artifacts: "${params.ENVIRONMENT}_report.txt", allowEmptyArchive: true
            }
        }
    }
}
```

- **Explanation**:
  - **Parameters**:
    - `ENVIRONMENT`: Choice parameter with `development` as default.
    - `API_KEY`: Password parameter, masked in the UI, defaulting to `123ABC`.
    - `CHANGELOG`: Text parameter for multi-line input, default empty.
  - **Test Stage**: Runs unconditionally, echoing the selected environment.
  - **Deploy Stage**: Uses a `when` condition to execute only when `ENVIRONMENT` is `production`.
  - **Report Stage**:
    - Writes `CHANGELOG` content to a file named `<ENVIRONMENT>_report.txt` (e.g., `production_report.txt`).
    - Archives the report file using `archiveArtifacts`.
  - **First Run**: Run once to register parameters, then use **Build with Parameters** to input values.

## Steps to Implement
1. Create a new pipeline job in Jenkins.
2. Copy the pipeline template from the exercise files or use the example above.
3. Use the **Pipeline Syntax Tool**:
   - Generate parameter snippets (e.g., `choice`, `password`, `text`) via the **Declarative Directive Generator**.
   - Generate `archiveArtifacts` snippet for the `Report` stage.
4. Save the pipeline and click **Build Now** for the initial run.
5. Refresh the job page and select **Build with Parameters**.
6. Test with different `ENVIRONMENT` values:
   - For `development` or `staging`, verify the `Deploy` stage is skipped.
   - For `production`, confirm the `Deploy` stage runs and the report file is named `production_report.txt`.
7. Check the archived artifacts to ensure the report contains the `CHANGELOG` content.

## Additional Notes
- **UI Behavior**:
  - `API_KEY` is masked (shown as dots) in the Jenkins UI for security.
  - Parameters appear in the UI after the first build.
- **Troubleshooting**:
  - Use **Apply** to validate pipeline syntax before saving.
  - Check **Pipeline Steps** or **Console Output** to verify stage execution and report content.
  - If parameters don’t appear, run the pipeline once and refresh.
- **Best Practices**:
  - Ensure clear parameter descriptions for user guidance.
  - Test conditional logic to confirm correct stage execution.
  - Use the **Pipeline Syntax Tool** to avoid syntax errors.
- **Resources**:
  - Exercise files provide the pipeline template.
  - Jenkins documentation and the **Declarative Directive Generator** assist with parameter and step configuration.


# Parameterized Pipeline Solution Notes

## Challenge Recap
- **Objective**: Enhance a Jenkins pipeline to include parameters for environment selection, secure API key input, and a changelog, with conditional deployment to production and environment-based report naming.
- **Requirements**:
  1. Add three parameters:
     - `ENVIRONMENT`: Choice parameter with options `development`, `staging`, `production` (default: `development`).
     - `API_KEY`: Password parameter with default `123ABC`.
     - `CHANGELOG`: Multi-line text parameter for report content.
  2. Update the `Deploy` stage to run only for the `production` environment.
  3. Modify the `Report` stage to:
     - Use `CHANGELOG` content for the report.
     - Name the report file after the `ENVIRONMENT` (e.g., `production.txt`).
- **Resources**:
  - Pipeline template provided in exercise files.
  - Use **Pipeline Syntax Tool** (Declarative Directive Generator) for parameter snippets.
- **Time**: 15–20 minutes.

## Solution Steps
1. **Create Pipeline**:
   - Start with the template from the exercise files.
   - Create a new Jenkins pipeline job named `parameterized-pipeline`.
   - Paste the template into the pipeline section.
   - Save and run (**Build Now**) to verify the template (includes `Test`, `Deploy`, `Report` stages).
2. **Add Parameters**:
   - Use the **Pipeline Syntax Tool** > **Declarative Directive Generator** to create snippets for:
     - `choice` parameter for `ENVIRONMENT`.
     - `password` parameter for `API_KEY`.
     - `text` parameter for `CHANGELOG`.
   - Copy the generated `parameters` block and add it below `agent any`.
3. **Fix Parameter Syntax**:
   - Ensure `ENVIRONMENT` choices are separate strings (e.g., `['development', 'staging', 'production']`).
   - Run the pipeline once to register parameters, then use **Build with Parameters**.
4. **Update Deploy Stage**:
   - Add a `when` condition to execute only when `ENVIRONMENT` is `production`.
5. **Update Report Stage**:
   - Modify the `sh` step to write `CHANGELOG` to a file named `${params.ENVIRONMENT}.txt`.
   - Archive the report using `archiveArtifacts`.
6. **Test the Pipeline**:
   - Run with `development` to confirm:
     - `Deploy` stage is skipped.
     - Report file is named `development.txt`.
   - Run with `production` to confirm:
     - `Deploy` stage executes.
     - Report file is named `production.txt` and contains `CHANGELOG` content.

## Final Pipeline
```groovy
pipeline {
    agent any
    parameters {
        choice(name: 'ENVIRONMENT', choices: ['development', 'staging', 'production'], description: 'Select an environment for deployment')
        password(name: 'API_KEY', defaultValue: '123ABC', description: 'Enter the API key')
        text(name: 'CHANGELOG', defaultValue: '', description: 'Enter the changelog')
    }
    stages {
        stage('Test') {
            steps {
                echo "Running tests for ${params.ENVIRONMENT}..."
            }
        }
        stage('Deploy') {
            when {
                expression { params.ENVIRONMENT == 'production' }
            }
            steps {
                echo "Deploying to production with API key: ${params.API_KEY}"
            }
        }
        stage('Report') {
            steps {
                sh "echo '${params.CHANGELOG}' > ${params.ENVIRONMENT}.txt"
                archiveArtifacts artifacts: "${params.ENVIRONMENT}.txt", allowEmptyArchive: true
            }
        }
    }
}
```

- **Explanation**:
  - **Parameters**:
    - `ENVIRONMENT`: Choice parameter with `development` as default.
    - `API_KEY`: Password parameter, masked in the UI.
    - `CHANGELOG`: Text parameter for multi-line input.
  - **Test Stage**: Runs unconditionally, echoing the environment.
  - **Deploy Stage**: Uses `when` to run only for `production`, referencing `API_KEY`.
  - **Report Stage**: Writes `CHANGELOG` to `<ENVIRONMENT>.txt` and archives it.
  - **First Run**: Required to register parameters; subsequent runs use **Build with Parameters**.

## Testing and Validation
- **Development Environment**:
  - Select `ENVIRONMENT: development`, enter a `CHANGELOG` (e.g., "Test changelog").
  - Verify `Deploy` stage is skipped.
  - Check for `development.txt` artifact with the correct `CHANGELOG` content.
- **Production Environment**:
  - Select `ENVIRONMENT: production`, enter a `CHANGELOG` (e.g., "I hope this works").
  - Confirm `Deploy` stage runs and references `API_KEY`.
  - Verify `production.txt` artifact contains the `CHANGELOG` content.
- **UI Behavior**:
  - `API_KEY` is masked (shown as dots).
  - `ENVIRONMENT` offers a dropdown with three options.
  - `CHANGELOG` provides a multi-line text field.

## Additional Notes
- **Syntax Fix**: Ensure `choices` in the `choice` parameter are separate strings (e.g., `['development', 'staging', 'production']`), not a single string.
- **Pipeline Syntax Tool**:
  - Use **Declarative Directive Generator** for accurate parameter definitions.
  - Generate `archiveArtifacts` snippet for the `Report` stage.
- **Troubleshooting**:
  - Run the pipeline once after adding parameters to enable **Build with Parameters**.
  - Use **Apply** to check syntax before saving.
  - Verify artifacts and stage execution in **Pipeline Steps** or **Console Output**.
- **Resources**: Exercise files provide the pipeline template; Jenkins documentation supports parameter and step configuration.