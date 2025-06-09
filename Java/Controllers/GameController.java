package Controllers;

import DictionaryCommandLine.Word;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    @FXML
    private Label wordLabel, player_score;
    @FXML
    private AnchorPane container;
    @FXML
    private TextField definitionTextField;
    @FXML
    private ToggleButton submitButton;
    @FXML
    private TextArea scoreArea;

    private int score = 0;
    private List<Word> words = new ArrayList<>();
    private int currentWordIndex;

    public GameController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeWords();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        showNextWord();
        player_score.setText(String.valueOf(score));
    }

    private void initializeWords() throws FileNotFoundException {
        words = readWordsFromFile("C:\\Users\\TNC\\IdeaProjects\\BTLTuDien\\DictionaryApp\\src\\main\\resources\\words\\gametuvung.txt");
        Collections.shuffle(words);
    }

    private List<Word> readWordsFromFile(String filePath) throws FileNotFoundException {
        List<Word> words = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String wordTarget = scanner.nextLine().trim();
            if (scanner.hasNextLine()) {
                String wordExplain = scanner.nextLine().trim();
                words.add(new Word(wordTarget, wordExplain));
            }
        }

        scanner.close();
        return words;
    }

    private void showNextWord() {
        if (currentWordIndex < words.size()) {
            Word currentWord = words.get(currentWordIndex);
            wordLabel.setText(currentWord.getWordExplain()); // Show the explanation first
            definitionTextField.clear();
        } else {
            showFinalScore();
        }
    }

    @FXML
    private void handleSubmitButtonClick(ActionEvent event) {
        Word currentWord = words.get(currentWordIndex);
        String userWord = definitionTextField.getText().trim().toLowerCase();
        String correctWord = currentWord.getWordTarget().toLowerCase();

        if (userWord.equals(correctWord)) {
            score++;
            player_score.setText(String.valueOf(score));
        }
        currentWordIndex++;
        showNextWord();
    }

    private void showFinalScore() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Your final score: " + score + " points");
        alert.showAndWait();
    }
}