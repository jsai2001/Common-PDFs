import os
from PIL import Image
from collections import defaultdict
import cv2  # Add OpenCV for video processing

def is_low_quality(image_path, min_resolution=(1280, 720)):
    try:
        with Image.open(image_path) as img:
            # Check if the image resolution is below a certain threshold
            return img.size < min_resolution
    except Exception as e:
        print(f"Error processing image {image_path}: {e}")
        return False  # Assume not low quality if we can't process it

def get_image_dimensions(image_path):
    try:
        with Image.open(image_path) as img:
            return img.size
    except Exception as e:
        print(f"Error getting dimensions for image {image_path}: {e}")
        return None

def get_video_dimensions(video_path):
    try:
        cap = cv2.VideoCapture(video_path)
        if not cap.isOpened():
            raise Exception("Cannot open video file")
        width = int(cap.get(cv2.CAP_PROP_FRAME_WIDTH))
        height = int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))
        cap.release()
        return (width, height)
    except Exception as e:
        print(f"Error getting dimensions for video {video_path}: {e}")
        return None

def clean_images(folder_path):
    # Dictionary to store file sizes and list of filenames
    size_dict = defaultdict(list)
    # List to keep track of low quality images
    low_quality_images = []

    for filename in os.listdir(folder_path):
        file_path = os.path.join(folder_path, filename)
        if os.path.isfile(file_path):
            if filename.lower().endswith(('.png', '.jpg', '.jpeg')):
                dimensions = get_image_dimensions(file_path)
            elif filename.lower().endswith('.mp4'):
                dimensions = get_video_dimensions(file_path)
            else:
                continue

            if dimensions:
                size = os.path.getsize(file_path)
                size_dict[(size, dimensions)].append(filename)
            
            if filename.lower().endswith(('.png', '.jpg', '.jpeg')) and is_low_quality(file_path):
                low_quality_images.append(filename)

    # Delete duplicates, keeping only one copy of each size and dimension
    for (size, dimensions), files in size_dict.items():
        if len(files) > 1:
            for duplicate in files[1:]:
                os.remove(os.path.join(folder_path, duplicate))
                print(f"Duplicate image deleted: {duplicate}")

    # # Delete low quality images
    # for low_quality in low_quality_images:
    #     os.remove(os.path.join(folder_path, low_quality))
    #     print(f"Low quality image deleted: {low_quality}")

    print("Image cleanup completed. Your gallery is now a masterpiece of quality and uniqueness.")

# Specify your folder path here
folder_path = 'D:\\Celkon'
clean_images(folder_path)