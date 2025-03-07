import os
import shutil

def get_file_size(file_path):
    return os.path.getsize(file_path)

def move_videos_to_batches(source_dir, max_size=2 * 1024 * 1024 * 1024):
    # Get list of all video files in the source directory
    video_files = [f for f in os.listdir(source_dir) if os.path.isfile(os.path.join(source_dir, f))]
    
    # Sort files by size in descending order
    video_files.sort(key=lambda f: get_file_size(os.path.join(source_dir, f)), reverse=True)
    
    bucket_num = 1
    current_bucket_size = 0
    current_bucket_files = []
    
    for video in video_files:
        video_path = os.path.join(source_dir, video)
        video_size = get_file_size(video_path)
        
        # Skip files larger than 2GB
        if video_size > max_size:
            print(f"Skipping {video} as it is larger than 2GB")
            continue
        
        # Check if adding this video exceeds the max size
        if current_bucket_size + video_size > max_size:
            # Move current bucket files to a new folder
            bucket_folder = os.path.join(source_dir, f"Utilities_Volume_{bucket_num}")
            os.makedirs(bucket_folder, exist_ok=True)
            for file in current_bucket_files:
                shutil.move(os.path.join(source_dir, file), os.path.join(bucket_folder, file))
            
            # Reset for the next bucket
            bucket_num += 1
            current_bucket_size = 0
            current_bucket_files = []
        
        # Add video to the current bucket
        current_bucket_files.append(video)
        current_bucket_size += video_size
    
    # Move any remaining files in the last bucket
    if current_bucket_files:
        bucket_folder = os.path.join(source_dir, f"Utilities_Volume_{bucket_num}")
        os.makedirs(bucket_folder, exist_ok=True)
        for file in current_bucket_files:
            shutil.move(os.path.join(source_dir, file), os.path.join(bucket_folder, file))

# Example usage
source_directory = 'D:\\Private\\Utilities'
move_videos_to_batches(source_directory)