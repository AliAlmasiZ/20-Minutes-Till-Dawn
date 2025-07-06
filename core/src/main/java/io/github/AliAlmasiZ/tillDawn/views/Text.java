package io.github.AliAlmasiZ.tillDawn.views;

public enum Text {
    //signup menu
    CREATE_ACCOUNT("Create account", "sakht akant"),
    USERNAME("Username", "name karbari"),
    SCORE("Score", "emtiaz"),
    ENTER_USERNAME("Enter username", "name karbari vared konid"),
    PASSWORD("Password", "ramz"),
    ENTER_PASSWORD("Enter password", "ramz vared konid"),
    SECURITY_ANSWER("Security Answer", "porsesh amniaty"),
    ENTER_ANSWER("Enter Answer", "pasokh vared konid"),
    SIGNUP("Signup", "sabte nam"),
    PLAY_AS_GUEST("Play as Guest", "vorood be onvan mehman"),
    GOTO_LOGIN_MENU("Go to login menu", "raftan be menue login"),
    //LOGIN
    LOGIN("Login", "vorood"),
    FORGET_PASSWORD("Forget Password", "faramooshi ramz"),
    ENTER_YOUR_SECURITY_ANSWER("Enter your security answer", "pasokh amniati khod ro vared konid"),
    SUBMIT("Submit", "sabt"),
    USERNAME_NOT_FOUND("username not found", "name karbari peyda nashod"),
    SECURITY_ANSWER_DOESNT_MATCH("security answer doesnt match", "javab soal amniaty dorost nist"),
    YOUR_PASS_IS("your password is: ", "ramz shoma hast: "),
    INCORRECT_PASSWORD("password is incorrect!", "password nadorost ast"),
    USER_LOGGED_IN("user logged in successfully!", "ba movafaghiat vared shodid!"),
    //MAIN MENU
    SETTINGS("Settings", "tanzimat"),
    PROFILE("Profile", "Profile"),
    PRE_GAME("Pre-Game", "pish az bazi"),
    SCOREBOARD("Scoreboard", "emtiazat"),
    LOGOUT("Logout", "khorooj"),
    CONTINUE_SAVED_GAME("Continue Saved Game", "edame bazi"),

    //PROFILE MENU
    GO_BACK("go back", "bazgasht"),
    CHOOSE_PICTURE("choose picture", "entekhabe aks"),

    //SETTINGS MENU
    SFX_VOLUME("sfx volume", "sedaye sfx"),
    MUSIC_VOLUME("music volume", "sedaye musiqi"),
    MUSIC_TRACK("music track", "tracke musiqi"),
    CHANGE_INPUTS("change inputs", "taghir dokme ha"),
    AUTO_RELOAD("auto reload", "reload khodkar"),
    BLACK_AND_WHITE("black and white", "siah o sefid"),
    ENTER_KEY("enter a key", "yek klid vared konid"),

    //PAUSE
    RESUME("resume", "edame"),
    SHOW_CHEATCODES("show cheatcodes", "namayesh cheatcode ha"),
    SHOW_ABILITIES("show abilities", "namayesh tavanaei ha"),
    GIVE_UP("giveUp", "taslim"),
    SAVE_AND_EXIT("Save and Exit", "exit"),


    //GAME
    TIME("Time", "Zaman"),
    HEALTH("Health", "salamat"),
    LEVEL("Level", "sath"),
    WEAPON("Weapon", "selah"),
    AMMO("Ammo", "kheshab"),
    AUTO_AIM("Auto-Aim", "aim khodkar"),
    RELOADING("reloading", "dar hal reload"),
    ON("ON", "roshan"),
    OFF("OFF", "khamoosh"),


    ;

    public static boolean isFirstActive = true;

    private final String firstLang, secondLang;

    Text(String firstLang, String secondLang) {
        this.firstLang = firstLang;
        this.secondLang = secondLang;
    }

    public String getText() {
        if(isFirstActive)
            return firstLang;
        return secondLang;
    }

    @Override
    public String toString() {
        return getText();
    }
}
