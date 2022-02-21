package com.example.hackthehike;

public class Games {
    int  score=0;
    boolean finish=false;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public  Games()
   {

   }

    public Integer getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
