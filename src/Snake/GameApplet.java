
package Snake;


import processing.core.*;


public class GameApplet extends PApplet {

	private static final long serialVersionUID = 1L;
	Snake mySnake;
	Snake mySnake2;
	Snake mySnake3;
	Block myBlock;
	Block myBlock2;
	PFont scoreFont;
	int score;
	int score2;
	boolean gameOver;
    static public void main(String args[]) {
        PApplet.main(new String[] { "Snake.GameApplet" });
    }
	public void setup() {
	  size(600, 600);
	  background(0);
	  
	  mySnake = new Snake(color(255,0,0), width/2 - 100, height/2, 10, false);
	  mySnake2 = new Snake(color(0, 0, 255), width/2 + 100, height/2, 10, false);
	  mySnake.snakeSize = 1;
	  mySnake2.snakeSize = 1;
	  mySnake3 = null;

	  myBlock = new Block(color(255, 0, 0));
	  myBlock2 = new Block(color(0, 0, 255));

	  mySnake.direction = DOWN;
	  mySnake2.direction = DOWN;
	  gameOver = false;

	  score = 0;
	  score2 = 0;
	  scoreFont  = createFont("Arial", 16, true);
	  textAlign(CENTER);
	}

	public void draw() {

	  background(0);
	  myBlock.display();
	  myBlock2.display();
	  mySnake.display();
	  mySnake2.display();
	  if (mySnake3 != null)
	    mySnake3.display();

	  fill(255);

	  textFont(scoreFont, 20);
	  text("Score: " + score, width/2 - 200, 20);
	  text("Score: " + score2, width/2 + 200, 20);

	  if (!gameOver) {

	    for (int i = 0; i < mySnake.snakeSize; i++) {
	      mySnake.oldPositionsX[i] = mySnake.partsX[i];
	      mySnake.oldPositionsY[i] = mySnake.partsY[i];
	    }

	    for (int i = 0; i < mySnake2.snakeSize; i++) {
	      mySnake2.oldPositionsX[i] = mySnake2.partsX[i];
	      mySnake2.oldPositionsY[i] = mySnake2.partsY[i];
	    }

	    if (mySnake3 != null) {
	      for (int i = 0; i < mySnake3.snakeSize; i++) {
	        mySnake3.oldPositionsX[i] = mySnake3.partsX[i];
	        mySnake3.oldPositionsY[i] = mySnake3.partsY[i];
	      }
	    }

	    switch(mySnake.direction) {
	      case(RIGHT):
	      mySnake.moveRight();
	      break;

	      case(UP):
	      mySnake.moveUp();
	      break;

	      case(DOWN):
	      mySnake.moveDown();
	      break;

	      case(LEFT):
	      mySnake.moveLeft();
	      break;
	    }

	    switch(mySnake2.direction) {
	      case(RIGHT):
	      mySnake2.moveRight();
	      break;

	      case(UP):
	      mySnake2.moveUp();
	      break;

	      case(DOWN):
	      mySnake2.moveDown();
	      break;

	      case(LEFT):
	      mySnake2.moveLeft();
	      break;
	    }

	    if (mySnake3 != null) {
	      switch(mySnake3.direction) {
	        case(RIGHT):
	        mySnake3.moveRight();
	        break;

	        case(UP):
	        mySnake3.moveUp();
	        break;

	        case(DOWN):
	        mySnake3.moveDown();
	        break;

	        case(LEFT):
	        mySnake3.moveLeft();
	        break;
	      }
	    }

	    if (mySnake.speed > 0) {
	      for (int i = 0; i < mySnake.snakeSize; i++) { 
	        mySnake.partsX[i+1] = mySnake.oldPositionsX[i];
	        mySnake.partsY[i+1] = mySnake.oldPositionsY[i];
	      }
	    }

	    if (mySnake2.speed > 0) {
	      for (int i = 0; i < mySnake2.snakeSize; i++) { 
	        mySnake2.partsX[i+1] = mySnake2.oldPositionsX[i];
	        mySnake2.partsY[i+1] = mySnake2.oldPositionsY[i];
	      }
	    }

	    if (mySnake3 != null) {
	      if (mySnake3.speed > 0) {
	        for (int i = 0; i < mySnake3.snakeSize; i++) { 
	          mySnake3.partsX[i+1] = mySnake3.oldPositionsX[i];
	          mySnake3.partsY[i+1] = mySnake3.oldPositionsY[i];
	        }
	      }
	    }

	    if (detectHit(mySnake, myBlock)) {
	      myBlock = new Block(color(255, 0, 0));
	      score += 10;
	      mySnake.snakeSize++;
	    }

	    if (detectHit(mySnake2, myBlock2)) {
	      myBlock2 = new Block(color(0, 0, 255));
	      score2 += 10;
	      mySnake2.snakeSize++;
	    }

	    if (mySnake3 != null) {
	      if (detectHit (mySnake, mySnake3)) {
	        mySnake.speed = 0; 


	        for (int i = 0; i < mySnake.snakeSize; i++) { 
	          mySnake.partsX[i] = mySnake.oldPositionsX[i];
	          mySnake.partsY[i] = mySnake.oldPositionsY[i];
	        }
	      }
	      if (detectHit(mySnake2, mySnake3)) {
	        mySnake2.speed = 0;

	        for (int i = 0; i < mySnake2.snakeSize; i++) { 
	          mySnake2.partsX[i] = mySnake2.oldPositionsX[i];
	          mySnake2.partsY[i] = mySnake2.oldPositionsY[i];
	        }
	      }
	    }


	    if (myBlock.passedTime == 0) {
	      myBlock = new Block(color(255, 0, 0));
	    }

	    if (myBlock2.passedTime == 0) {
	      myBlock2 = new Block(color(0, 0, 255));
	    }

	    if (detectHit(mySnake, mySnake2)) {
	      mySnake.speed = 0;

	      for (int i = 0; i < mySnake.snakeSize; i++) { 
	        mySnake.partsX[i] = mySnake.oldPositionsX[i];
	        mySnake.partsY[i] = mySnake.oldPositionsY[i];
	      }

	      mySnake3 = new Snake(color(0, 255, 0), 0, 0, 10, true);
	      mySnake3.snakeSize = 20;
	      mySnake3.direction = RIGHT;
	    }

	    if (detectHit(mySnake2, mySnake)) {
	      mySnake2.speed = 0;

	      for (int i = 0; i < mySnake2.snakeSize; i++) { 
	        mySnake2.partsX[i] = mySnake2.oldPositionsX[i];
	        mySnake2.partsY[i] = mySnake2.oldPositionsY[i];
	      } 

	      mySnake3 = new Snake(color(0, 255, 0), 0, 0, 10, true);
	      mySnake3.snakeSize = 20;
	      mySnake3.direction = RIGHT;
	    }

	    if (score > score2 && mySnake2.speed == 0) 
	      gameOver = true;

	    if (score2 > score && mySnake.speed == 0)
	      gameOver = true;

	    if (mySnake.speed == 0 && mySnake2.speed == 0)
	      gameOver = true;
	  }
	  else {
	    text("GAME OVER", width/2, height/2);
	    text("(Press Spacebar to Continue)", width/2, height/2 + 20);
	  }
	}

	public void keyPressed() {

	  switch(key) {
	    case('w'):
	    mySnake.direction = UP;
	    break;

	    case('a'):
	    mySnake.direction = LEFT;
	    break;

	    case('s'):
	    mySnake.direction = DOWN;
	    break;

	    case('d'):
	    mySnake.direction = RIGHT;
	    break;

	    case(' '):
	    setup();
	  }

	  if (key == CODED) {
	    if (keyCode == LEFT)
	      mySnake2.direction = LEFT;
	    else if (keyCode == RIGHT)
	      mySnake2.direction = RIGHT;
	    else if (keyCode == UP)
	      mySnake2.direction = UP;
	    else if (keyCode == DOWN)
	      mySnake2.direction = DOWN;
	  }
	}

	class Snake {

	  int c;
	  int xpos;
	  int ypos;
	  float speed;
	  int direction;
	  int snakeSize;
	  int [] partsX;
	  int [] partsY;
	  int[] partDirection;
	  int [] oldPositionsX = new int[550];
	  int [] oldPositionsY = new int[550];
	  boolean isGhost;

	  Snake(int tempC, int tempXpos, int tempYpos, float tempspeed, boolean isGhost) { 
	    c = tempC;
	    xpos = tempXpos;
	    ypos = tempYpos;
	    speed = tempspeed;
	    snakeSize = 1;
	    partsX = new int[550];
	    partsX[0] = xpos;
	    partsY = new int[550];
	    partsY[0] = ypos;
	    partDirection = new int[550];
	    partDirection[0] = DOWN;
	    this.isGhost = isGhost;
	  }

	  void display() {
	    stroke(0);
	    fill(c);

	    for (int i = 1; i < snakeSize; i++) {
	      rect(partsX[i], partsY[i], 10, 10);
	    }

	    fill(color(255));
	    rect(partsX[0], partsY[0], 10, 10);
	    fill(c);
	  }
	  void moveRight() {
	    partsX[0] += speed;

	    if (partsX[0] > width-10) {
	      if (isGhost) {
	        partsX[0] -= speed;
	        direction = DOWN;
	      }

	      else
	        partsX[0] = 0;
	    }
	  }

	  void moveLeft() {
	    partsX[0] -= speed;

	    if (partsX[0] < 0) {
	      if (isGhost) {
	        partsX[0] += speed;
	        direction = UP;
	      }

	      else
	        partsX[0] = width-10;
	    }
	  }

	  void moveUp() {
	    partsY[0] -= speed;

	    if (partsY[0] < 0) {
	      if (isGhost) {
	        partsY[0] += speed;
	        direction = RIGHT;
	      }

	      else
	        partsY[0] = height-10;
	    }
	  }

	  void moveDown() {
	    partsY[0] += speed;
	    if (partsY[0] > height-10) {
	      if (isGhost) {
	        partsY[0] -= speed;
	        direction = LEFT;
	      }

	      else
	        partsY[0] = 0;
	    }
	  }
	}

	class Block {
	  int displayColor;
	  int xpos;
	  int ypos;
	  int savedTime;
	  int totalTime = 10000;
	  int passedTime = 0;

	  Block(int c) { 
	    displayColor = c;
	    xpos = (int)random(width-10);
	    ypos = (int)random(height-30) + 20;
	    savedTime = millis();
	  }

	  void display() {
	    stroke(0);
	    fill(displayColor);
	    rect(xpos, ypos, 10, 10);

	    passedTime = (totalTime - (millis() - savedTime))/1000;

	    textFont(scoreFont, 10);
	    fill(0);
	    text(passedTime, xpos+6, ypos+9);
	  }
	}

	boolean detectHit(Snake a, Block b) {

	  if (a.partsX[0] + 10 < b.xpos)
	    return false;
	  else if (a.partsX[0] > b.xpos + 10)
	    return false;
	  else if (a.partsY[0] + 10 < b.ypos) 
	    return false;
	  else if (a.partsY[0] > b.ypos + 10)
	    return false;

	  else {
	    switch(a.direction) {
	      case(RIGHT):
	      a.partsX[a.snakeSize] = a.partsX[a.snakeSize - 1]-10;
	      a.partsY[a.snakeSize] = a.partsY[a.snakeSize-1];
	      break;

	      case(UP):
	      a.partsX[a.snakeSize] = a.partsX[a.snakeSize - 1];
	      a.partsY[a.snakeSize] = a.partsY[a.snakeSize-1]-10;
	      break;

	      case(DOWN):
	      a.partsX[a.snakeSize] = a.partsX[a.snakeSize - 1];
	      a.partsY[a.snakeSize] = a.partsY[a.snakeSize-1] + 10;
	      break;

	      case(LEFT):
	      a.partsX[a.snakeSize] = a.partsX[a.snakeSize - 1]+10;
	      a.partsY[a.snakeSize] = a.partsY[a.snakeSize-1];
	      break;
	    }
	    return true;
	  }
	}

	boolean detectHit(Snake a, Snake b) {

	  for (int i = 0; i < b.snakeSize; i++) {

	    boolean hit = true;

	    if (a.partsX[0] + 10 <= b.partsX[i])
	      hit = false;
	    else if (a.partsX[0] >= b.partsX[i] + 10) 
	      hit = false;
	    else if (a.partsY[0] + 10 <= b.partsY[i])
	      hit = false;
	    else if (a.partsY[0] >= b.partsY[i] + 10)
	      hit = false;

	    if (hit)
	      return true;
	  }  

	  return false;
	}
}
