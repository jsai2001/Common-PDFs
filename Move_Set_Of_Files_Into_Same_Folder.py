#Move Set of Files from different directory into Same directory
import os
import shutil
from pathlib import Path

def iterate_files(directory_path,dest_dir):
    for item in Path(directory_path).iterdir():
        if item.is_file():
            shutil.move(item, dest_dir)
            print(f"Found file: {item.name}")

def move_files(source_dir, dest_dir):
    # Create the destination directory if it doesn't exist
    if not os.path.exists(dest_dir):
        os.makedirs(dest_dir)

    # Get a list of all files in the source directory
    iterate_files(source_dir,dest_dir)

# Example usage
for i in range(67, 101):
    source_dir = f'D:\\Chemistry_3\\Chemistry_Sample_{i}'
    dest_dir = 'D:\\Half-Life'
    move_files(source_dir, dest_dir)
