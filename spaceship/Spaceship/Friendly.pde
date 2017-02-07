class Friendly {
  float xpos;
  float ypos;
  float yspeed;
  
  // Constructor
  Friendly(float tempXpos, float tempYpos, float tempYspeed) {
    xpos = tempXpos;
    ypos = tempYpos;
    yspeed = tempYspeed;
  }
  
  void display() {
    stroke(0);

    image(astronaut, xpos, ypos);
  }
  
  void drop() {
    // Set speed
    ypos += yspeed;
    
    if (ypos > height) {
      xpos = (int) random(0, width/100) * 100;
      ypos = (int) random(-15, -5) * 100;
    } // end if()
  } // end drop()
  
  void collision() {
    for (int i = 0; i < friendlies.length; i++) {
      // Check for collision between player and each enemy 
      if ((player.xpos + rocket_width) > friendlies[i].xpos && player.xpos < (friendlies[i].xpos + rocket_width) && (player.ypos + rocket_height) > friendlies[i].ypos && player.ypos < (friendlies[i].ypos + rocket_height)) 
      {
        pickup = minim.loadFile("pickup.aiff");
        pickup.play();
        // Sets new position of astronaut
        friendlies[i].xpos = (int) random(0, width/100) * 100;
        friendlies[i].ypos = (int) random(-15, -5) * 100;
        // Add point to score
        score += 1;
      }
    } // end for()
  } // end collision
} // end friendly
