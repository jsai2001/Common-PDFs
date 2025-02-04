import os
import hashlib
import ffmpeg

def group_files_by_size(directory):
    size_dict = {}
    for root, dirs, files in os.walk(directory):
        for file in files:
            full_path = os.path.join(root, file)
            try:
                size = os.path.getsize(full_path)
                if size in size_dict:
                    size_dict[size].append(full_path)
                else:
                    size_dict[size] = [full_path]
            except FileNotFoundError:
                continue
    return {size: paths for size, paths in size_dict.items() if len(paths) > 1}

# Example usage
directory = "D:\Street Fight\Street Fight"
duplicates = group_files_by_size(directory)
for size, files in duplicates.items():
    print(f"Files with size {size} bytes:")
    for file in files:
        print(f"  - {file}")

def delete_duplicates(duplicates):
    for size, file_paths in duplicates.items():
        if len(file_paths) > 1:
            hashes = {}
            for path in file_paths:
                with open(path, 'rb') as file:
                    file_hash = hashlib.md5(file.read()).hexdigest()
                    if file_hash in hashes:
                        print(f"Deleting duplicate: {path}")
                        os.remove(path)
                    else:
                        hashes[file_hash] = path

# Assuming 'duplicates' is from the previous step
delete_duplicates(duplicates)
for size in duplicates:
    if(len(duplicates[size])>1):
        for item in duplicates[size][1:]:
            os.remove(item)

def remove_low_res_videos(directory):
    for root, _, files in os.walk(directory):
        for file in files:
            if file.lower().endswith(('.mp4', '.mkv', '.avi', '.mov', '.wmv')):  # Add more extensions if needed
                full_path = os.path.join(root, file)
                try:
                    # Probe the video file to get its resolution
                    probe = ffmpeg.probe(full_path)
                    video_stream = next((stream for stream in probe['streams'] if stream['codec_type'] == 'video'), None)
                    if video_stream:
                        height = int(video_stream['height'])
                        if height < 720:
                            print(f"Removing {full_path} because its resolution is less than 720p")
                            os.remove(full_path)
                        else:
                            print(f"Keeping {full_path} as its resolution is 720p or higher")
                    else:
                        print(f"Could not find video stream in {full_path}")
                except Exception as e:
                    print(f"Error processing {full_path}: {e}")

# Example usage
directory = '/path/to/your/video/folder'
remove_low_res_videos(directory)