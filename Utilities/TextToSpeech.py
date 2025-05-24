from flask import Flask, request, send_file, render_template_string
from gtts import gTTS
from pydub import AudioSegment
from pydub.effects import speedup
import os
import uuid
import logging
import time

app = Flask(__name__)

# Set up logging
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# Ensure the static directory exists
STATIC_DIR = "static"
STATIC_PATH = os.path.abspath(STATIC_DIR)
if not os.path.exists(STATIC_PATH):
    os.makedirs(STATIC_PATH)
logger.debug(f"Static directory absolute path: {STATIC_PATH}")

# HTML template with Tailwind CSS
HTML_TEMPLATE = """
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Text to Speech Converter</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <h1 class="text-2xl font-bold mb-6 text-center">Text to AI Voice</h1>
        
        <!-- Text to Speech Section -->
        <h2 class="text-xl font-semibold mb-4">Convert Text to Speech</h2>
        <form action="/" method="post" class="space-y-4">
            <div>
                <label for="text" class="block text-sm font-medium text-gray-700">Enter Text:</label>
                <textarea id="text" name="text" rows="4" class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" required></textarea>
            </div>
            <button type="submit" class="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600">Convert to Speech</button>
        </form>
        {% if error %}
        <div class="mt-6 text-red-600">
            <p>Error: {{ error }}</p>
        </div>
        {% endif %}
        {% if audio_file %}
        <div class="mt-6">
            <p class="text-green-600">Audio generated! Play or download below:</p>
            <audio controls id="audioPlayer" class="w-full mt-2">
                <source src="{{ audio_file }}" type="audio/mp3">
                Your browser does not support the audio element.
            </audio>
            <a href="{{ audio_file }}" download class="block mt-2 text-blue-500 hover:underline">Download Audio</a>
        </div>
        <script>
            const audio = document.getElementById('audioPlayer');
            audio.playbackRate = 1.25;  // Set default playback speed to 1.25x
            audio.addEventListener('loadedmetadata', () => {
                audio.currentTime = 0;
            });
        </script>
        {% endif %}

        <!-- Audio Speed Adjustment Section -->
        <hr class="my-8">
        <h2 class="text-xl font-semibold mb-4">Adjust Audio Speed</h2>
        <form action="/adjust-speed" method="post" enctype="multipart/form-data" class="space-y-4">
            <div>
                <label for="audio-upload" class="block text-sm font-medium text-gray-700">Upload Audio File (MP3/WAV):</label>
                <input type="file" id="audio-upload" name="audio" accept="audio/*" class="mt-1 block w-full p-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500" required>
            </div>
            <button type="submit" class="w-full bg-green-500 text-white p-2 rounded-md hover:bg-green-600">Adjust Speed to 1.25x</button>
        </form>
        {% if speed_error %}
        <div class="mt-6 text-red-600">
            <p>Error: {{ speed_error }}</p>
        </div>
        {% endif %}
        {% if speed_audio_file %}
        <div class="mt-6">
            <p class="text-green-600">Adjusted audio ready! Play or download below:</p>
            <audio controls id="speedAudioPlayer" class="w-full mt-2">
                <source src="{{ speed_audio_file }}" type="audio/mp3">
                Your browser does not support the audio element.
            </audio>
            <a href="{{ speed_audio_file }}" download class="block mt-2 text-blue-500 hover:underline">Download Adjusted Audio</a>
        </div>
        <script>
            const speedAudio = document.getElementById('speedAudioPlayer');
            speedAudio.addEventListener('loadedmetadata', () => {
                speedAudio.currentTime = 0;
            });
        </script>
        {% endif %}
    </div>
</body>
</html>
"""

def preprocess_text(text):
    """Add pauses and punctuation to make the speech sound more natural."""
    if not text.strip().endswith(('.', '!', '?')):
        text = text.strip() + '.'
    words = text.split()
    processed_words = []
    for i, word in enumerate(words):
        processed_words.append(word)
        if (i + 1) % 5 == 0 and i != len(words) - 1:
            processed_words.append(',')
    return ' '.join(processed_words)

@app.route('/', methods=['GET', 'POST'])
def index():
    audio_file = None
    error = None
    speed_audio_file = None
    speed_error = None

    if request.method == 'POST':
        text = request.form.get('text')
        if text:
            try:
                processed_text = preprocess_text(text)
                logger.debug(f"Processed text: {processed_text}")

                filename = f"output_{uuid.uuid4().hex}.mp3"
                output_path = os.path.join(STATIC_DIR, filename)
                absolute_output_path = os.path.abspath(output_path)

                logger.debug(f"Attempting to save audio file to: {absolute_output_path}")

                # Use US English for text-to-speech
                tts = gTTS(text=processed_text, lang='en', tld='us', slow=False)
                tts.save(absolute_output_path)

                time.sleep(1)

                if not os.path.exists(absolute_output_path):
                    raise FileNotFoundError(f"Audio file was not created at {absolute_output_path}")

                logger.debug(f"File exists at: {absolute_output_path}, size: {os.path.getsize(absolute_output_path)} bytes")

                audio_file = f"/serve-audio/{filename}"

            except Exception as e:
                logger.error(f"Error generating audio: {str(e)}")
                error = f"Failed to generate audio: {str(e)}"

    return render_template_string(HTML_TEMPLATE, audio_file=audio_file, error=error, speed_audio_file=speed_audio_file, speed_error=speed_error)

@app.route('/adjust-speed', methods=['POST'])
def adjust_speed():
    audio_file = None
    error = None
    speed_audio_file = None
    speed_error = None

    try:
        if 'audio' not in request.files:
            raise ValueError("No audio file uploaded.")

        file = request.files['audio']
        if file.filename == '':
            raise ValueError("No file selected.")

        # Validate file extension
        if not file.filename.lower().endswith(('.mp3', '.wav')):
            raise ValueError("Unsupported file format. Please upload an MP3 or WAV file.")

        # Log the original filename
        logger.debug(f"Original filename: {file.filename}")

        # Save the uploaded file temporarily
        temp_filename = f"temp_{uuid.uuid4().hex}{os.path.splitext(file.filename)[1]}"
        temp_path = os.path.join(STATIC_DIR, temp_filename)
        absolute_temp_path = os.path.abspath(temp_path)
        file.save(absolute_temp_path)

        logger.debug(f"Uploaded audio saved to: {absolute_temp_path}")

        # Verify the file exists and log its size
        if not os.path.exists(absolute_temp_path):
            raise FileNotFoundError(f"Uploaded audio file not found at {absolute_temp_path}")
        logger.debug(f"Uploaded file size: {os.path.getsize(absolute_temp_path)} bytes")

        # Load the audio file with pydub
        try:
            audio = AudioSegment.from_file(absolute_temp_path)
        except Exception as e:
            raise RuntimeError(f"Failed to load audio file with pydub: {str(e)}. Ensure ffmpeg is installed and added to your system PATH.")

        # Log audio details
        logger.debug(f"Audio loaded: duration {len(audio)/1000.0} seconds, sample rate {audio.frame_rate} Hz")

        # Adjust the speed to 1.25x
        adjusted_audio = speedup(audio, playback_speed=1.25)

        # Generate a new filename for the adjusted audio
        speed_filename = f"speed_{uuid.uuid4().hex}.mp3"
        speed_path = os.path.join(STATIC_DIR, speed_filename)
        absolute_speed_path = os.path.abspath(speed_path)

        # Export the adjusted audio as MP3
        adjusted_audio.export(absolute_speed_path, format="mp3")

        # Verify the adjusted file exists
        if not os.path.exists(absolute_speed_path):
            raise FileNotFoundError(f"Adjusted audio file not created at {absolute_speed_path}")

        logger.debug(f"Adjusted audio saved to: {absolute_speed_path}, size: {os.path.getsize(absolute_speed_path)} bytes")

        speed_audio_file = f"/serve-audio/{speed_filename}"

        # Clean up the temporary uploaded file
        os.remove(absolute_temp_path)

    except Exception as e:
        logger.error(f"Error adjusting audio speed: {str(e)}")
        speed_error = f"Failed to adjust audio speed: {str(e)}"

    return render_template_string(HTML_TEMPLATE, audio_file=audio_file, error=error, speed_audio_file=speed_audio_file, speed_error=speed_error)

@app.route('/serve-audio/<filename>')
def serve_audio(filename):
    file_path = os.path.join(STATIC_DIR, filename)
    absolute_file_path = os.path.abspath(file_path)
    logger.debug(f"Serving file: {absolute_file_path}")
    
    if not os.path.exists(absolute_file_path):
        logger.error(f"File not found: {absolute_file_path}")
        return "File not found", 404
    
    logger.debug(f"File found, size: {os.path.getsize(absolute_file_path)} bytes")
    return send_file(absolute_file_path, mimetype='audio/mp3', as_attachment=False)

if __name__ == '__main__':
    app.run(debug=True)