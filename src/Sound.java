import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioImpl;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {

	public float soundVolume = 1f;
	public String currentBgMusic = "";
	public String lastSoundEffect = "";
	public HashMap<String, Audio> musicPool = new HashMap<String, Audio>();
	public HashMap<String, Audio> soundPool = new HashMap<String, Audio>();

	public Sound() {
		addSound("hit0", "hitone.wav");
		addSound("hit1", "hittwo.wav");
		addSound("miss", "miss.wav");
		addSound("wall", "hitwall.wav");
		addSound("pause", "pause.wav");
		addSound("win", "gameover.wav");
		addMusic("main", "bg.ogg");
	}

	public void playSound(String sound, float pitch, float vol) {
		try {
			soundPool.get(sound).playAsSoundEffect(pitch, vol, false);
			lastSoundEffect = sound;
		} catch (Exception e) {
		}
	}

	public void playSoundAtEnt(String sound, float pitch, float vol, Entity ent) {
		try {
			soundPool.get(sound).playAsSoundEffect(pitch, vol, false,
					ent.posX / 2, ent.posY / 2, 0F);
		} catch (Exception e) {
		}
	}

	public void playBackgroundMusic(String sound, float pitch, float vol,
			boolean looped) {
		try {

			musicPool.get(sound).playAsMusic(pitch, vol, looped);
			currentBgMusic = sound;
		} catch (Exception e) {
			System.out.println("Could not find sound: " + sound);
		}
	}

	public void stopMusic() {
		try {
			musicPool.get(currentBgMusic).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean silence() {
		boolean result = true;
		try {
			if (soundPool.get(lastSoundEffect).isPlaying()) {
				result = false;
			}
			if (musicPool.get(currentBgMusic).isPlaying()) {
				result = false;
			}
		} catch (Exception e) {
		}
		result = false;
		return result;
	}

	public void addSound(String key, String file) {
		try {
			Audio audio = null;
			if (file.contains(".wav")) {
				audio = AudioLoader.getAudio("WAV",
						ResourceLoader.getResourceAsStream("sound/" + file));
				soundPool.put(key, audio);
			}
			if (file.contains(".ogg")) {
				audio = AudioLoader.getAudio("OGG",
						ResourceLoader.getResourceAsStream("sound/" + file));
				musicPool.put(key, audio);
			}
		} catch (Exception e) {
		}
	}

	public void addMusic(String key, String file) {
		try {
			Audio audio = null;
			if (file.contains(".wav"))
				audio = AudioLoader.getAudio("WAV",
						ResourceLoader.getResourceAsStream("sound/" + file));
			if (file.contains(".ogg"))
				audio = AudioLoader.getAudio("OGG",
						ResourceLoader.getResourceAsStream("sound/" + file));
			musicPool.put(key, audio);
		} catch (Exception e) {
		}
	}
}
