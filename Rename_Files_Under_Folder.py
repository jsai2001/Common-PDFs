import os
import random
import string

def generate_random_name(length=10):
    letters = string.ascii_letters
    return ''.join(random.choice(letters) for i in range(length))

def rename_videos(folder_path):
    # Get a list of all files in the folder
    files = os.listdir(folder_path)

    # Iterate through the files and rename them
    for file in files:
        # Construct the new filename with a random name
        new_filename = f"{generate_random_name()}.mp4"

        # Get the full path of the file
        old_filepath = os.path.join(folder_path, file)
        new_filepath = os.path.join(folder_path, new_filename)

        # Rename the file
        os.rename(old_filepath, new_filepath)

if __name__ == "__main__":
    folder_path = "D:\\Utilities"
    rename_videos(folder_path)