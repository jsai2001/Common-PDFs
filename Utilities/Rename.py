import argparse
import os
import secrets
import string
from pathlib import Path

VIDEO_EXTS = {".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".webm", ".m4v", ".3gp"}


def random_word(length: int = 26) -> str:
    alphabet = string.ascii_lowercase
    return "".join(secrets.choice(alphabet) for _ in range(length))


def rename_videos(folder: Path, recursive: bool, dry_run: bool):
    used_names = set()
    moved = 0
    for root, dirs, files in os.walk(folder) if recursive else [(str(folder), [], os.listdir(folder))]:
        for fname in files:
            fpath = Path(root) / fname
            if not fpath.is_file():
                continue
            if fpath.suffix.lower() not in VIDEO_EXTS:
                continue
            # generate unique 26-letter name and use .mp4 extension per request
            for _ in range(1000):
                new_name = random_word(26)
                if new_name in used_names:
                    continue
                new_fname = new_name + ".mp4"
                new_path = fpath.with_name(new_fname)
                if new_path.exists():
                    # collision on filesystem, try another
                    continue
                used_names.add(new_name)
                break
            else:
                print(f"Failed to generate unique name for {fpath}, skipping.")
                continue

            if dry_run:
                print(f"[DRY-RUN] {fpath} -> {new_path}")
            else:
                try:
                    os.rename(fpath, new_path)
                    print(f"{fpath} -> {new_path}")
                    moved += 1
                except Exception as e:
                    print(f"Error renaming {fpath}: {e}")
    print(f"Done. Renamed {moved} files (dry_run={dry_run}).")


def main():
    parser = argparse.ArgumentParser(description="Rename video files under a folder to random 26-letter words (.mp4)")
    parser.add_argument("folder", nargs="?", default=".", help="Folder to process (default: current directory)")
    parser.add_argument("-r", "--recursive", action="store_true", help="Process subdirectories recursively")
    parser.add_argument("--dry-run", action="store_true", help="Show what would be renamed without making changes")
    args = parser.parse_args()

    folder = Path(args.folder).resolve()
    if not folder.exists() or not folder.is_dir():
        print(f"Folder not found or not a directory: {folder}")
        return

    rename_videos(folder, args.recursive, args.dry_run)


if __name__ == "__main__":
    main()