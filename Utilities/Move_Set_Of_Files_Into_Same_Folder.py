# Move Set of Files from different directory into Same directory
import os
import shutil
from pathlib import Path

def iterate_files(directory_path, dest_dir):
    for item in Path(directory_path).rglob('*'):
        if item.is_file():
            dest_file_path = os.path.join(dest_dir, item.name)
            if not os.path.exists(dest_file_path):
                shutil.move(str(item), dest_dir)
                print(f"Moved file: {item.name}")
            else:
                print(f"Skipped file (already exists): {item.name}")

def move_files(parent_dir, dest_dir):
    # Create the destination directory if it doesn't exist
    if not os.path.exists(dest_dir):
        os.makedirs(dest_dir)

    # Move all files from the parent directory and its subdirectories
    iterate_files(parent_dir, dest_dir)

# Example usage
parent_dir = "D:\\Draft_Volumes"
dest_dir = "D:\\Celkon"
move_files(parent_dir, dest_dir)