package tests;

import app.Application;
import org.junit.Before;

/*
 *  Класс был взят из курса
 */
public class TestBase {
    public static ThreadLocal<Application> t1App = new ThreadLocal<>();
    public Application app;

    @Before
    public void start() {
        if (t1App.get() != null) {
            app = t1App.get();
            return;
        }
        app = new Application();
        t1App.set(app);
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    app.quit();
                    app = null;
                }));
    }
}
