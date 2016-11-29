Radar radar;


class Radar {

  float xpos=width/4;
  float ypos=height/4;

  int num=4;
  float[]rs=new float[num];
  float spd=0.5;
  float spacing=40;

  float angle=0;
  float theta=PI/3;

  Radar() {
    for (int i=0; i<num; i++) {
      rs[i]=i*spacing;
    }
  }

  void update() {
    for (int i=0; i<num; i++) {
      rs[i]+=spd;
      if (rs[i]>num*spacing) {
        rs[i]=0;
      }
    }

    angle+=0.01;
  }

  void display() {

    //grid(0,width/2,0,height/2,30);
    noFill();

    strokeWeight(1);
    for (int i=0; i<num; i++) {
      for (int j=0; j<spacing; j++) {
        stroke(#FF5136, 120-j*(240/spacing));
        ellipse(xpos, ypos, rs[i]*2-j*2, rs[i]*2-j*2);
      }
    }

    //stroke(#FF5136);
    //line(xpos,ypos,xpos+cos(angle)*num*spacing,ypos+sin(angle)*num*spacing);
    //line(xpos,ypos,xpos+cos(angle+theta)*num*spacing,ypos+sin(angle+theta)*num*spacing);
    pushMatrix();
    translate(0, 0, 1);
    fill(#FF5136, 80);
    noStroke();
    arc(xpos, ypos, num*spacing*2, num*spacing*2, angle, angle+theta);

    float inTheta=map(cos(frameCount*0.05), 0, 1, 0, theta);
    arc(xpos, ypos, num*spacing*2, num*spacing*2, angle, angle+inTheta);
    popMatrix();

    pushStyle();
    fill(#499B57);
    noStroke();
    textAlign(LEFT, CENTER);
    textSize(12);
    text("Range", 20, 110);
    text("Freq", 20, 70);
    text("Granularity", 20, 90);
    text("Boost    ", 20, 150);
    text("Scan    ", 20, 130);
    text(int(noise((frameCount+26000)*0.01)*235),100,150);
    text(int(noise((frameCount+36000)*0.001)*100),100,130);

    for (int i=0; i<3; i++) {
      rect(100, 70+i*20, map(noise(i*2, (frameCount+16000)*0.005), 0, 1, 20, 100), 8);
    }
    popStyle();
  }
}