import os
import shutil
import random

# List of adjectives or themes for folder names
themes = ["Nature", "Urban", "Art", "History", "Science", "Technology", "Adventure", "Mystery", "Fantasy", "Heritage"]

def generate_meaningful_name(themes, counter):
    theme = random.choice(themes)
    return f"{theme}_{counter}"

def create_and_move_images(source_folder, parent_folder):
    # Ensure the parent folder exists
    if not os.path.exists(parent_folder):
        os.makedirs(parent_folder)

    images = [f for f in os.listdir(source_folder) if f.lower().endswith(('png', 'jpg', 'jpeg', 'gif', 'bmp'))]
    counter = 1  # Counter for unique folder names

    while images:
        # Create a new folder with a meaningful name
        new_folder_name = generate_meaningful_name(themes, counter)
        new_folder_path = os.path.join(parent_folder, new_folder_name)
        
        if not os.path.exists(new_folder_path):
            os.makedirs(new_folder_path)
            
            # Move up to 100 images to the new folder
            for _ in range(min(100, len(images))):
                image = images.pop(0)  # Remove and get the first image in the list
                shutil.move(os.path.join(source_folder, image), os.path.join(new_folder_path, image))
            counter += 1
        else:
            # If the folder name already exists, increment counter to get a new name
            counter += 1
            continue

    print("All images have been moved.")

# Example usage:
source_folder = "D:\\Street Fight\\Mario"
parent_folder = "D:\\Image_Batches"
create_and_move_images(source_folder, parent_folder)