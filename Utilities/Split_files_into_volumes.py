import os
import shutil

# move files from D:\Drafts into created group of folders , where their maximum size of a folder shouldn't exceed 2GB
def get_file_size(file_path):
    return os.path.getsize(file_path)

def create_new_folder(base_path, folder_index):
    new_folder_path = os.path.join(base_path, f"Celkon_{folder_index}")
    os.makedirs(new_folder_path, exist_ok=True)
    return new_folder_path

def move_files_to_volumes(source_folder, base_path, max_volume_size):
    folder_index = 1
    current_volume_size = 0
    current_folder = create_new_folder(base_path, folder_index)

    for root, _, files in os.walk(source_folder):
        for file in files:
            file_path = os.path.join(root, file)
            file_size = get_file_size(file_path)

            if current_volume_size + file_size > max_volume_size:
                folder_index += 1
                current_folder = create_new_folder(base_path, folder_index)
                current_volume_size = 0

            shutil.move(file_path, os.path.join(current_folder, file))
            current_volume_size += file_size

source_folder = "D:\\Celkon"
base_path = "D:\\Draft_Volumes"
max_volume_size = 2 * 1024 * 1024 * 1024  # 2GB

move_files_to_volumes(source_folder, base_path, max_volume_size)