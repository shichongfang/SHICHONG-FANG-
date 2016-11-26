Meter meter;
PFont myFont;

class Meter {
  float xpos=width/2;
  float ypos=height/2;

  float unit=10;

  float leftV, rightV;
  
  float level=3.235;
  int speed=int(level*100);

  Meter() {
  }

  void update() {
    leftV=noise(frameCount*0.01);
    rightV=noise((frameCount+10000)*0.01);
  }

  void display() {

    fill(255, 100);
    noStroke();
    ellipse(xpos, ypos, unit*20, unit*20);

    float cv, sv;
    color cl;
    for (float i=PI/2-PI/10; i>-PI/3; i-=0.1) {
      cv=cos(i);
      sv=sin(i);

      if (map(i, PI/2, -PI/3, 0, 1)<rightV) {
        cl=color(map(i, PI/2, -PI/3, 180, 0), 100, 200);
        strokeWeight(8);
        stroke(cl);
      } else {
        stroke(200);
        strokeWeight(0.5);
      }
      line(xpos+cv*unit*9, ypos+sv*unit*9, xpos+cv*unit*10, ypos+sv*unit*10);


      if (map(i, PI/2, -PI/3, 0, 1)<leftV) {
        cl=color(map(i, PI/2, -PI/3, 180, 0), 100, 200);
        strokeWeight(8);
        stroke(cl);
      } else {
        stroke(200);
        strokeWeight(0.5);
      }
      line(xpos-cv*unit*9, ypos+sv*unit*9, xpos-cv*unit*10, ypos+sv*unit*10);
    }

    noFill();
    stroke(255);
    strokeWeight(1);
    ellipse(xpos, ypos, unit*17, unit*17);

    float arc1=map(noise((frameCount+20000)*0.01), 0, 1, -PI/2, 0);
    arc(xpos, ypos, unit*16, unit*16, arc1, arc1+HALF_PI);
    arc(xpos, ypos, unit*16, unit*16, arc1+PI, arc1+HALF_PI+PI);

    arc1=map(noise((frameCount+30000)*0.005), 0, 1, 0, PI/2);
    arc(xpos, ypos, unit*15, unit*15, arc1, arc1+HALF_PI);
    arc(xpos, ypos, unit*15, unit*15, arc1+PI, arc1+HALF_PI+PI);

    arc1=map(noise((frameCount+40000)*0.008), 0, 1, -PI/6, 0);
    for (int i=14; i>=10; i--) {
      arc(xpos, ypos, unit*i, unit*i, arc1, arc1+PI/6);
      arc(xpos, ypos, unit*i, unit*i, arc1+PI, arc1+PI/6+PI);
    }
    line(xpos+cos(arc1+PI/12)*unit*10,ypos+sin(arc1+PI/12)*unit*10,
    xpos-cos(arc1+PI/12)*unit*10,ypos-sin(arc1+PI/12)*unit*10);
    line(xpos,ypos,xpos+cos(arc1+PI/12+PI/2)*unit*4,
    ypos+sin(arc1+PI/12+PI/2)*unit*4);
    
    float wide=10;
    noStroke();
    
    for(int i=0;i<20;i++){
      if(i<level){
      fill(map(i,0,20,180,0),100,200);
      }else{
        fill(0,0,100,150);
      }
      rect(width/2-wide/2,height-20-i*12,wide,6,3);
      wide*=1.14;
    }
    
    pushStyle();
    fill(255);
    textAlign(CENTER,CENTER);
    //textFont(myFont);
    textSize(48);
    text(speed,width/2,height/2-30);
    
    textSize(24);
    text(int(noise((frameCount+50000)*0.01)*10000),width/2,height/2+30);
    popStyle();
  }
  
  float onSpeed(){
    if(mouseX>width/2-30 && mouseX<width/2+30 &&
    mouseY>480 && mouseY<700){
       level=map(mouseY,480,700,20,0);
      speed=int(level*100);
      return level;
    }else{
      return -1;
    }
  }
}