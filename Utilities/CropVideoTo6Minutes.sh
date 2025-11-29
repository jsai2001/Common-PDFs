#!/usr/bin/env bash
set -uo pipefail

# ---- Colors ----
GREEN="\033[1;32m"
YELLOW="\033[1;33m"
BLUE="\033[1;34m"
RED="\033[1;31m"
RESET="\033[0m"

if [ "$#" -ne 1 ]; then
  echo -e "${RED}Usage:${RESET} $0 <input_folder>"
  exit 1
fi

RAW_INPUT="$1"
echo -e "${BLUE}Input received:${RESET} $RAW_INPUT"

# ---- Auto convert Windows path to Linux style ----
case "$RAW_INPUT" in
  [A-Za-z]:\\*|[A-Za-z]:/*)
    drive=$(printf '%s' "$RAW_INPUT" | cut -c1 | tr 'A-Z' 'a-z')
    rest=$(printf '%s' "$RAW_INPUT" | cut -c3-)
    rest="${rest//\\//}"
    INPUT_DIR="/$drive/$rest"
    ;;
  *)
    INPUT_DIR="$RAW_INPUT"
    ;;
esac

INPUT_DIR="${INPUT_DIR%/}"

echo -e "${BLUE}Resolved working folder:${RESET} $INPUT_DIR"

BASE_NAME="$(basename "$INPUT_DIR")"
PARENT_DIR="$(dirname "$INPUT_DIR")"
OUTPUT_DIR="${PARENT_DIR}/${BASE_NAME}_modified"

mkdir -p "$OUTPUT_DIR"
echo -e "${GREEN}Output folder created:${RESET} $OUTPUT_DIR\n"

COUNT_TOTAL=0
COUNT_TRIMMED=0
COUNT_COPIED=0
COUNT_FAILED=0

# ---- IMPORTANT: no pipe here; use process substitution ----
while IFS= read -r FILE; do
  COUNT_TOTAL=$((COUNT_TOTAL + 1))

  REL_PATH="${FILE#$INPUT_DIR}"
  REL_PATH="${REL_PATH#/}"
  OUT_PATH="$OUTPUT_DIR/$REL_PATH"

  mkdir -p "$(dirname "$OUT_PATH")"

  DURATION_RAW=$(ffprobe -v error -show_entries format=duration -of default=nw=1:nk=1 "$FILE" 2>/dev/null) || {
    echo -e "${RED}FAILED (metadata):${RESET} $FILE"
    COUNT_FAILED=$((COUNT_FAILED + 1))
    continue
  }

  DURATION=${DURATION_RAW%.*}

  if [ -z "$DURATION" ]; then
    echo -e "${RED}FAILED (no duration):${RESET} $FILE"
    COUNT_FAILED=$((COUNT_FAILED + 1))
    continue
  fi

  if [ "$DURATION" -gt 360 ]; then
    echo -e "${YELLOW}TRIMMING (6 min):${RESET} $REL_PATH"
    if ffmpeg -loglevel error -y -i "$FILE" -t 00:06:00 -c copy "$OUT_PATH"; then
      COUNT_TRIMMED=$((COUNT_TRIMMED + 1))
    else
      echo -e "${RED}FAILED (ffmpeg):${RESET} $FILE"
      COUNT_FAILED=$((COUNT_FAILED + 1))
    fi
  else
    echo -e "${GREEN}COPYING (unchanged):${RESET} $REL_PATH"
    if cp -n "$FILE" "$OUT_PATH"; then
      COUNT_COPIED=$((COUNT_COPIED + 1))
    else
      echo -e "${RED}FAILED (copy):${RESET} $FILE"
      COUNT_FAILED=$((COUNT_FAILED + 1))
    fi
  fi
done < <(find "$INPUT_DIR" -type f \( \
           -iname '*.mp4' -o -iname '*.mkv' -o -iname '*.mov' -o -iname '*.avi' -o -iname '*.m4v' \
         \))

echo
echo -e "${BLUE}-------------- SUMMARY --------------${RESET}"
echo -e "${GREEN}Total processed :${RESET} $COUNT_TOTAL"
echo -e "${YELLOW}Trimmed         :${RESET} $COUNT_TRIMMED"
echo -e "${GREEN}Copied as-is    :${RESET} $COUNT_COPIED"
echo -e "${RED}Failed          :${RESET} $COUNT_FAILED"
echo -e "${BLUE}--------------------------------------${RESET}"
echo -e "${GREEN}Output folder:${RESET} $OUTPUT_DIR"
