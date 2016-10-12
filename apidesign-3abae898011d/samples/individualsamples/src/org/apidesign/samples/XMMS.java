package org.apidesign.samples;

// BEGIN: xmms.java.api
public class XMMS {
    public void play() { doPlay(); }
    public void pause() { doPause(); }
    public void addToPlaylist(String file) { doAddToPlaylist(file); }
// FINISH: xmms.java.api

    // BEGIN: xmms.java.spi
    interface Playback {
        public void playback(byte[] data);
    }
    // END: xmms.java.spi
    public void registerPlayback(Playback callback) {
        this.callback = callback;
    }
    private Playback callback;
    
    private void doAddToPlaylist(String file) {
    }

    private void doPause() {
    }

    private void doPlay() {
        callback.playback("Play!".getBytes());
    }
    
    
    
    //
    // Usage
    // 
    
    public static void main(String[] args) {
        XMMS xmms = new XMMS();
        
        // BEGIN: xmms.java.use
        class MyCallbackPrints implements XMMS.Playback {
            public void playback(byte[] data) {
                System.out.println(new String(data));
            }
        }
        xmms.registerPlayback(new MyCallbackPrints());
        // END: xmms.java.use
    }

}
