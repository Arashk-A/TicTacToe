package com.zero.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zero.tictactoe.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateTitleLabel(GameManager.getPlayer() + " turn to Play");
    }

    public void dropInAction(View sender) {
        if (GameManager.getIsGameOver()) { return; }

        ImageView img = (ImageView) sender;

        if (img == null) { return; }

        int tag = Integer.parseInt(img.getTag().toString());
        if (!GameManager.isValidIndex(tag)) { return; }

        if (GameManager.getBoardTile(tag) != -1) { return; }
        GameManager.setBoardTile(tag);

        resetAnimations(img);

        if (GameManager.getIsCrossTurn() == 1) {
            img.setImageResource(R.drawable.cross);
            GameManager.setIsCrossTurn(0);
        } else {
            img.setImageResource(R.drawable.circle);
            GameManager.setIsCrossTurn(1);
        }

        updateTitleLabel(GameManager.getPlayer() + " turn to Play");

        img.animate().translationY(0f).translationX(0f).rotation(360).setDuration(400);


        GameManager.checkForWinner(new GameManager.Callback() {
            @Override
            public void setData(String text) {
                updateUIForRestart(text);

            }
        });


        if (GameManager.getHasWinner()) { return; }
        if (GameManager.scanForDraw()) {
            updateUIForRestart("It's a draw");
        }
    }

    public void restartGame(View sender) {
        GameManager.resetGameManager();

        updateTitleLabel(GameManager.getPlayer() + " turn to Play");

        Button btn = findViewById(R.id.reset);
        btn.setVisibility(View.GONE);

        resetgameBoard();
    }

    void updateUIForRestart(String text) {
        GameManager.setIsGameOver(true);

        Button btn = findViewById(R.id.reset);
        btn.setVisibility(View.VISIBLE);

        updateTitleLabel(text);
    }

    private void updateTitleLabel(String text) {
        TextView titleLabel = findViewById(R.id.titleLabel);
        titleLabel.setText(text);
    }

    void resetgameBoard() {
        android.support.v7.widget.GridLayout board = (android.support.v7.widget.GridLayout)findViewById(R.id.gridLayout3);


        for (int i = 0; i < board.getChildCount(); i++) {
            ImageView child = (ImageView) board.getChildAt(i);
            if (child == null) { continue; }

            child.setImageDrawable(null);
        }

    }

    void resetAnimations(ImageView image) {
        image.setTranslationX(-1000f);
        image.setTranslationY(-1000f);
        image.setRotation(0);
    }
}
