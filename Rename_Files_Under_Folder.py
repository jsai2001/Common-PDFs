import os

def rename_videos(folder_path):
    # Get a list of all files in the folder
    files = os.listdir(folder_path)

    # Iterate through the files and rename them
    for index, file in enumerate(files, start=246):
        # Construct the new filename (e.g., Video_1, Video_2, etc.)
        new_filename = f"Video_{index}.mp4"

        # Get the full path of the file
        old_filepath = os.path.join(folder_path, file)
        new_filepath = os.path.join(folder_path, new_filename)

        # Rename the file
        os.rename(old_filepath, new_filepath)


if __name__ == "__main__":
    folder_path = "D:\\Backup\\Folder"
    rename_videos(folder_path)