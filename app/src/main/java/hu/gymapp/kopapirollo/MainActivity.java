package hu.gymapp.kopapirollo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Random rnd;
    private ImageView[] computerHearts;
    private ImageView[] userHearts;

    private ImageView computerChoiceView, userChoiceView;

    private Button rockBtn, paperBtn, scissorsBtn;

    private TextView countOfDrawView;
    private int countOfDraw;
    private int countOfWin;
    private int countOfLoose;


    private AlertDialog.Builder alerDialog;

    private enum Choices {
        Rock,
        Paper,
        Scissors
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        rnd = new Random();
        alerDialog = new AlertDialog.Builder(this);
        alerDialog.setMessage("Szeretne új játékot játszani?");

        alerDialog.setNegativeButton("nem", (d, i) -> finish());
        alerDialog.setPositiveButton("igen", (d,i) -> setGameToDefault());
        alerDialog.setCancelable(false);

        computerHearts = new ImageView[3];
        userHearts = new ImageView[3];

        computerHearts[0] = findViewById(R.id.cumputerHeart1);
        computerHearts[1] = findViewById(R.id.cumputerHeart2);
        computerHearts[2] = findViewById(R.id.cumputerHeart3);

        userHearts[0] = findViewById(R.id.userHeart1);
        userHearts[1] = findViewById(R.id.userHeart2);
        userHearts[2] = findViewById(R.id.userHeart3);

        computerChoiceView = findViewById(R.id.computerChoiceView);
        userChoiceView = findViewById(R.id.userChoiceView);

        rockBtn = findViewById(R.id.rockBtn);
        paperBtn = findViewById(R.id.paperBtn);
        scissorsBtn = findViewById(R.id.scissorsBtn);

        countOfDrawView = findViewById(R.id.countOfDrawView);
        setGameToDefault();

        rockBtn.setOnClickListener(this);
        paperBtn.setOnClickListener(this);
        scissorsBtn.setOnClickListener(this);

    }

    private void setGameToDefault() {
        countOfDrawView.setText("Döntetlenek száma: 0");
        countOfDraw = 0;
        countOfLoose = 0;
        countOfWin = 0;
        for (int i = 0; i < computerHearts.length; i++) {
            computerHearts[i].setImageResource(R.drawable.heart2);
            userHearts[i].setImageResource(R.drawable.heart2);
        }

        userChoiceView.setImageResource(R.drawable.rock);
        computerChoiceView.setImageResource(R.drawable.rock);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rockBtn:
                duel(Choices.Rock, getComputerChoice());
                break;
            case R.id.paperBtn:
                duel(Choices.Paper, getComputerChoice());
                break;
            case R.id.scissorsBtn:
                duel(Choices.Scissors, getComputerChoice());
                break;
        }

    }

    private Choices getComputerChoice() {
        int choice = rnd.nextInt(3);
        Choices result = null;
        switch (choice) {
            case 0:
                result = Choices.Rock;
                break;
            case 1:
                result = Choices.Paper;
                break;
            case 2:
                result = Choices.Scissors;
                break;
        }
        return result;
    }

    private void duel(Choices userChoice, Choices computerChoice) {
        setImageByChoice(userChoiceView, userChoice);
        setImageByChoice(computerChoiceView, computerChoice);

        String msg = "";
        if (userChoice == computerChoice) {
            countOfDraw++;
            msg = "Döntetlen";
            countOfDrawView.setText("Döntetlenek száma: " + countOfDraw);
        } else {
            if (isUerWined(userChoice, computerChoice)) {
                computerHearts[countOfWin].setImageResource(R.drawable.heart1);
                countOfWin++;
                msg = "Te nyerted a kört!";
            } else {
                userHearts[(3 - countOfLoose) - 1].setImageResource(R.drawable.heart1);
                countOfLoose++;
                msg = "A gép nyerte a kört!";
            }
        }
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        t.show();
        if (countOfWin == 3) {
            alerDialog.setTitle("Győzelem");
            alerDialog.show();
        } else if(countOfLoose == 3) {
            alerDialog.setTitle("Vesztettél");
            alerDialog.show();
        }
    }

    private boolean isUerWined(Choices userChoice, Choices computerChoice) {
        boolean isUserWin = false;
        switch (userChoice) {
            case Rock:
                if (computerChoice == Choices.Scissors) {
                    isUserWin = true;
                } else {
                    isUserWin = false;
                }
                break;
            case Scissors:
                if (computerChoice == Choices.Paper) {
                    isUserWin = true;
                } else {
                    isUserWin = false;
                }
                break;
            case Paper:
                if (computerChoice == Choices.Rock) {
                    isUserWin = true;
                } else {
                    isUserWin = false;
                }
                break;
        }
        return isUserWin;
    }

    private void setImageByChoice(ImageView v, Choices c) {
        switch (c) {
            case Rock:
                v.setImageResource(R.drawable.rock);
                break;
            case Paper:
                v.setImageResource(R.drawable.paper);
                break;
            case Scissors:
                v.setImageResource(R.drawable.scissors);
                break;
        }
    }


}