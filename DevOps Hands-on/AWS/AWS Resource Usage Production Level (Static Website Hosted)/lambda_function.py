import json

def lambda_handler(event, context):
    # Log the entire event
    print("Event: ", json.dumps(event))
    
    # Extract bucket name and object key from the event
    bucket_name = event['Records'][0]['s3']['bucket']['name']
    object_key = event['Records'][0]['s3']['object']['key']
    
    # Log the bucket name and object key
    print(f"Bucket: {bucket_name}, Object: {object_key}")

    print("Processing S3 object... Custom info , which will be shown in the cloudwatch logs")
    
    # Perform additional processing if needed
    # For example, you could read the object content, process it, etc.
    
    return {
        'statusCode': 200,
        'body': json.dumps(f'Processed object {object_key} from bucket {bucket_name}')
    }