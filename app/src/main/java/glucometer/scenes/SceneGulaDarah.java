package glucometer.scenes;

import glucometer.dataBase.DbGulaDarah;
import glucometer.models.GulaDarah;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SceneGulaDarah extends Scene {
    private ObservableList<GulaDarah> gulaDarahList;

    public SceneGulaDarah(Stage stage, ObservableList<GulaDarah> gulaDarahList) {
        super(new VBox(), 480, 480);
        this.gulaDarahList = gulaDarahList;

        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));

        Label titleLabel = new Label("Gula Darah");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField gulaDarahTextField = new TextField();
        gulaDarahTextField.setPromptText("Konsentrasi Gula Darah");

        CheckBox beforeBreakfastCheckBox = new CheckBox("Before Breakfast");
        CheckBox afterBreakfastCheckBox = new CheckBox("After Breakfast");
        CheckBox beforeLunchCheckBox = new CheckBox("Before Lunch");
        CheckBox afterLunchCheckBox = new CheckBox("After Lunch");
        CheckBox beforeDinnerCheckBox = new CheckBox("Before Dinner");
        CheckBox afterDinnerCheckBox = new CheckBox("After Dinner");
        CheckBox beforeSleepCheckBox = new CheckBox("Before Sleep");
        CheckBox afterSleepCheckBox = new CheckBox("After Sleep");
        CheckBox fastingCheckBox = new CheckBox("Fasting");
        CheckBox otherCheckBox = new CheckBox("Other");

        TextField catatanTextField = new TextField();
        catatanTextField.setPromptText("Tambah Catatan Disini");
        TextField tanggalTextField = new TextField();
        tanggalTextField.setPromptText("Tanggal");

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        Button tambahButton = new Button("Tambah");
        tambahButton.setOnAction(event -> {
            int gulaDarah = Integer.parseInt(gulaDarahTextField.getText());
            String catatan = catatanTextField.getText();
            String tanggal = tanggalTextField.getText();
            String waktu = "";

            if (beforeBreakfastCheckBox.isSelected()) {
                waktu += "Before Breakfast";
            }
            if (afterBreakfastCheckBox.isSelected()) {
                waktu += "After Breakfast";
            }
            if (beforeLunchCheckBox.isSelected()) {
                waktu += "Before Lunch";
            }
            if (afterLunchCheckBox.isSelected()) {
                waktu += "After Lunch";
            }
            if (beforeDinnerCheckBox.isSelected()) {
                waktu += "Before Dinner";
            }
            if (afterDinnerCheckBox.isSelected()) {
                waktu += "After Dinner";
                if (beforeSleepCheckBox.isSelected()) {
                    waktu += "Before Sleep";
                }
                if (afterSleepCheckBox.isSelected()) {
                    waktu += "After Sleep";
                }
                if (fastingCheckBox.isSelected()) {
                    waktu += "Fasting";
                }
                if (otherCheckBox.isSelected()) {
                    waktu += "Other";
                }

                if (!waktu.isEmpty()) {
                    waktu = waktu.substring(0, waktu.length() - 2);
                }
            }

            GulaDarah gulaDarahObj = new GulaDarah(gulaDarah, waktu, catatan, tanggal);
            gulaDarahList.add(gulaDarahObj);

            // Simpan ke database (TO DO LIST 1)
            DbGulaDarah dbGulaDarah = new DbGulaDarah();
            dbGulaDarah.addData(gulaDarahObj);

            // Clear input fields
            gulaDarahTextField.clear();
            beforeBreakfastCheckBox.setSelected(false);
            afterBreakfastCheckBox.setSelected(false);
            beforeLunchCheckBox.setSelected(false);
            afterLunchCheckBox.setSelected(false);
            beforeDinnerCheckBox.setSelected(false);
            afterDinnerCheckBox.setSelected(false);
            beforeSleepCheckBox.setSelected(false);
            afterSleepCheckBox.setSelected(false);
            fastingCheckBox.setSelected(false);
            otherCheckBox.setSelected(false);
            catatanTextField.clear();
            tanggalTextField.clear();
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setOnAction(v -> {
            TableGulaDarah tableGulaDarah = new TableGulaDarah(stage);
            stage.setScene(tableGulaDarah);
        });

        buttonBox.getChildren().addAll(tambahButton, kembaliButton);

        root.getChildren().addAll(titleLabel, gulaDarahTextField, beforeBreakfastCheckBox, afterBreakfastCheckBox,
                beforeLunchCheckBox, afterLunchCheckBox, beforeDinnerCheckBox, afterDinnerCheckBox, beforeSleepCheckBox,
                afterSleepCheckBox, fastingCheckBox, otherCheckBox, catatanTextField, tanggalTextField, buttonBox);

        setRoot(root);
    }
}
