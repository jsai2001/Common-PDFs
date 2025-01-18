import os
import random

# Lists of themes for folder names, now with more variety to ensure 200+ combinations
theme1 = [
    "Nature", "Urban", "Art", "History", "Science", 
    "Literature", "Music", "Dance", "Cinema", "Photography", 
    "Culinary", "Fashion", "Architecture", "Sports", "Games"
]

theme2 = [
    "Adventure", "Mystery", "Fantasy", "Heritage", "Technology", 
    "Journey", "Inspiration", "Discovery", "Innovation", "Tradition", 
    "Revolution", "Evolution", "Mythology", "Culture", "Venture"
]

def generate_themed_name():
    return f"{random.choice(theme1)}_{random.choice(theme2)}"

def rename_folders(parent_folder):
    items = os.listdir(parent_folder)
    folders = [item for item in items if os.path.isdir(os.path.join(parent_folder, item))]
    
    # Dictionary to track used names
    used_names = set()
    
    for folder in folders:
        old_path = os.path.join(parent_folder, folder)
        new_name = generate_themed_name()
        
        # If the generated name has been used, keep trying until we get a unique one
        while new_name in used_names:
            new_name = generate_themed_name()
        
        used_names.add(new_name)
        new_path = os.path.join(parent_folder, new_name)
        
        # Rename the folder
        os.rename(old_path, new_path)
        print(f"Renamed {folder} to {new_name}")

# Example usage:
parent_folder = "D:\\Image_Batches"
rename_folders(parent_folder)