import os
import shutil
from PIL import Image

def is_high_quality(image_path, min_resolution):
    try:
        with Image.open(image_path) as img:
            return img.size[0] >= min_resolution[0] and img.size[1] >= min_resolution[1]
    except Exception as e:
        print(f"Error opening image {image_path}: {e}")
        return False

def categorize_images(folder_path, output_folder):
    resolutions = {
        '1080p': (1920, 1080),
        '720p': (1280, 720),
        '480p': (854, 480),
        'rest': (0, 0)
    }

    for root, _, files in os.walk(folder_path):
        for filename in files:
            if filename.lower().endswith(('.png', '.jpg', '.jpeg', '.tiff', '.bmp', '.gif')):
                image_path = os.path.join(root, filename)
                try:
                    with Image.open(image_path) as img:
                        width, height = img.size
                        if width >= resolutions['1080p'][0] and height >= resolutions['1080p'][1]:
                            category = '1080p'
                        elif width >= resolutions['720p'][0] and height >= resolutions['720p'][1]:
                            category = '720p'
                        elif width >= resolutions['480p'][0] and height >= resolutions['480p'][1]:
                            category = '480p'
                        else:
                            category = 'rest'

                        dest_folder = os.path.join(output_folder, category)
                        os.makedirs(dest_folder, exist_ok=True)
                        shutil.copy(image_path, dest_folder)
                except Exception as e:
                    print(f"Error processing image {image_path}: {e}")

folder_path = 'D:\\Draft_Volumes\\'
output_folder = 'D:\\Categorized_Images\\'
categorize_images(folder_path, output_folder)
print("Images have been categorized and copied.")