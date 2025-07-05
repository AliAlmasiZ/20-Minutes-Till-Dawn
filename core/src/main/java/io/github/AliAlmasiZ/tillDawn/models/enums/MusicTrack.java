package io.github.AliAlmasiZ.tillDawn.models.enums;

public enum MusicTrack {
    YOU_GO("You Go, Girl", "musics/04. You Go, Girl.mp3"),
    O_DEATH("'O Death' Theme", "musics/14. 'O Death' Theme.mp3")
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

    public String getName() {
        return name;
    }
}
