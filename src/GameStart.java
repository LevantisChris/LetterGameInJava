/* ΞΕΚΙΝΗΜΑ ΤΟΥ ΠΑΙΧΝΙΔΙΟΥ */
public class GameStart {
    public GameStart() {
        LogoScreen logoScreen = new LogoScreen(); // το πρώτο πράγμα που θα βλέπει ο παίχτης θα είναι το welcomeScreen
        try {
            Thread.sleep(2000); // μετά από δύο δευτερόλεπτα το welcome screen θα κλείνει ...
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        logoScreen.dispose(); // ... εδώ κλείνει το logo screen
        WelcomeScreen welcomeScreen = new WelcomeScreen();// ... και εδω ανοίγει το welcome screen
    }
}
