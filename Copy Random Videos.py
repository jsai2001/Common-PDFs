import os
import random
import shutil

def convert_windows_path(path):
    return path.replace('\\', '\\\\') if '\\' in path else path

def copy_random_videos(source_folder, destination_folder, n):
    # Ensure the source folder exists
    if not os.path.exists(source_folder):
        print(f"The source folder '{source_folder}' does not exist.")
        return

    # Get all video files from the source folder
    video_files = [f for f in os.listdir(source_folder) if f.lower().endswith(('.mp4', '.avi', '.mov', '.mkv'))]
    
    if len(video_files) < n:
        print(f"Only {len(video_files)} video files found, which is less than {n}.")
        n = len(video_files)

    # Randomly select 'n' videos
    selected_videos = random.sample(video_files, n)

    # Create the destination folder if it doesn't exist
    if not os.path.exists(destination_folder):
        os.makedirs(destination_folder)

    # Copy the selected videos
    for video in selected_videos:
        source_path = os.path.join(source_folder, video)
        destination_path = os.path.join(destination_folder, video)
        shutil.copy2(source_path, destination_path)  # copy2 preserves metadata

    print(f"Copied {n} videos to {destination_folder}")

# Example usage
source_folder = convert_windows_path("D:\Pendrive (Private)\Street Fight")
destination_folder = convert_windows_path("D:\Pendrive (Private)\Common Sample")
number_of_videos = 30

copy_random_videos(source_folder, destination_folder, number_of_videos)