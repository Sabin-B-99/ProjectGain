package com.projectgain.manager;


import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public final class SoundManager {

    private static SoundManager INSTANCE;

    private static final String VOICE_NAME = "kevin16";

    private VoiceManager voiceManager;
    private Voice voice;

    private SoundManager() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(VOICE_NAME);
        setupVoiceSettings();
    }


    public static SoundManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new SoundManager();
        }
        return INSTANCE;
    }

    private void setupVoiceSettings(){
        try {
            voice.allocate();
            voice.setRate(150);
            voice.setPitch(90);
            voice.setVolume(3);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void readTitle(String title){
        try {
            voice.speak(title);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
