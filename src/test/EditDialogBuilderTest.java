package test;

import controller.EditDialogBuilder;
import domain.Uebung;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.Pump;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import static javafx.application.Application.launch;
import static org.junit.jupiter.api.Assertions.*;

public class EditDialogBuilderTest {

    private static final Semaphore launchSemaphore = new Semaphore(0);

    static Thread laucher = new Thread() {
        @Override
        public void run() {
            launch(TestApp.class);
        }
    };

    public static void FXReady() {
        launchSemaphore.release();
    }

    @BeforeAll
    static void init() throws InterruptedException {
        laucher.start();
        launchSemaphore.acquire();
    }

    @AfterAll
    static void uninit() throws InterruptedException {
        Platform.exit();
        laucher.join();
    }


    @Test
    public void testBuild() throws InterruptedException {

        Semaphore semaphore = new Semaphore(0);
        final Throwable[] throwables = new Throwable[1];
        throwables[0] = null;

        Platform.runLater(() -> {
            try {
                Uebung uebung = new Uebung();


                EditDialogBuilder<Uebung> editDialogBuilder = new EditDialogBuilder<>();

                assertDoesNotThrow(() -> {
                    editDialogBuilder.setTitle("Übung bearbeiten").setFxmlResource("../fxml/uebung.fxml").setEditableObject(uebung).build();
                });

                assertThrows(Exception.class, () -> {
                    editDialogBuilder.setTitle("oh no").setFxmlResource("../fxml/falsch.fxml").setEditableObject(uebung).build();
                });
            } catch (Throwable e) {
                throwables[0] = e;
            } finally {
                semaphore.release();
            }
        });

        semaphore.acquire();

        assertDoesNotThrow(() -> {if (throwables[0] != null) throw throwables[0];});

    }
}