package io.github.AliAlmasiZ.tillDawn.models.enums;

public enum MusicTrack {
    StrangerThings("audio/musics/stranger-things-124008.mp3", "Stranger Things"),
    A_HERO_OF_80S("audio/musics/a-hero-of-the-80s-126684.mp3", "A hero of 80s")
    ;

    String path;
    String name;

     MusicTrack(String path, String name) {
         this.path = path;
         this.name = name;
     }

    public String getPath() {
        return path;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
