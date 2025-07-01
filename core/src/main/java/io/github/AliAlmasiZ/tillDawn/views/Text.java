package io.github.AliAlmasiZ.tillDawn.views;

public enum Text {
    //signup menu
    CREATE_ACCOUNT("Create account", "sakht akant"),
    USERNAME("Username", "name karbari"),
    ENTER_USER_NAME("Enter username", "name karbari vared konid"),
    PASSWORD("Password", "ramz"),
    ENTER_PASSWORD("Enter password", "ramz vared konid"),
    SECURITY_ANSWER("Security Answer", "porsesh amniaty"),
    ENTER_ANSWER("Enter Answer", "pasokh vared konid"),
    SIGNUP("Signup", "sabte nam"),
    PLAY_AS_GUEST("Play as Guest", "vorood be onvan mehman"),
    GOTO_LOGIN_MENU("Go to login menu", "raften be menue login"),


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
