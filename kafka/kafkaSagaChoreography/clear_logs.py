from app.zzz_helpers.config import LOG_FILE_NAMES

for file_path in LOG_FILE_NAMES.values():
    with open(file_path, "w") as f:
        f.write("")