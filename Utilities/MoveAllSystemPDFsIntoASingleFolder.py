import os
import shutil

def move_all_pdfs(destination_folder, log_file="moved_pdfs.log"):
    with open(log_file, "w", encoding="utf-8") as log:
        for root, dirs, files in os.walk("C:\\"):
            for file in files:
                if file.lower().endswith(".pdf"):
                    source_path = os.path.join(root, file)
                    dest_path = os.path.join(destination_folder, file)
                    # If file with same name exists, add a counter to filename
                    base, ext = os.path.splitext(file)
                    counter = 1
                    while os.path.exists(dest_path):
                        dest_path = os.path.join(destination_folder, f"{base}_{counter}{ext}")
                        counter += 1
                    try:
                        shutil.copy2(source_path, dest_path)
                        if os.path.exists(dest_path) and os.path.getsize(dest_path) == os.path.getsize(source_path):
                            os.remove(source_path)
                            log.write(f"Moved: {source_path} -> {dest_path}\n")
                            print(f"Moved: {source_path} -> {dest_path}")
                        else:
                            log.write(f"Failed: {source_path} -> {dest_path} | Error: Copy verification failed\n")
                            print(f"Failed: {source_path} -> {dest_path} | Error: Copy verification failed")
                            if os.path.exists(dest_path):
                                os.remove(dest_path)
                    except Exception as e:
                        log.write(f"Failed: {source_path} -> {dest_path} | Error: {e}\n")
                        print(f"Failed: {source_path} -> {dest_path} | Error: {e}")

if __name__ == "__main__":
    destination = r"C:\Users\Dell\Common-PDFs\AllPDFs"
    os.makedirs(destination, exist_ok=True)
    move_all_pdfs(destination)