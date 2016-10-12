
#include "stdio.h"

// BEGIN: xmms.spi
void xmms_register_playback(void (*f)(char*));
// END: xmms.spi

// BEGIN: xmms.api
void xmms_play();
void xmms_pause();
void xmms_add_to_list(char *);
// END: xmms.api


//
// implementation
//

void none() {
}

void (*callback)(char*) = none;
void xmms_register_playback(void (*f)(char*)) {
    callback = f;
}

void xmms_play() {
    callback("Play!");
}

void xmms_pause() {
}

//
// test case
//

// BEGIN: xmms.your.playback
void my_playback_prints(char* text) {
  printf("%s\n", text);
}
// END: xmms.your.playback

int main(char** argsv) {
    xmms_register_playback(my_playback_prints);
    xmms_play();
    xmms_pause();
}

