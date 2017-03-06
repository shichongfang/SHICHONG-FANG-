public MoveBallFrame(){
  // create thread object
  //Initialize the ball object by loop
  for( int i = 0; i < 5; i++){
    MoveThread mt1 = new MoveThread (this);
    mt1.x += 100 * i;
    mt1.width = ran.nextInt(26) + 25;
    mt1.start();
    // Add the ball object to the container
    list.add(mt1);
  }
}
// draw a ball
for (int i =0; i<list.size(); i++){
  MoveThread mt = list.get(i);
  g.fillOval(mt.x, mt.y, mt.width, mt.width);
}
//Define the ball coordinates and variables
public int x = 100, y = 100;
//Defines the diameter of the ball
public int width  =60;


public void checkHit（）{
  for(int i = 0; i< MoveBallFrame.list.size(),i++) {
    MoveThread mt1 = MoveBallFrame.list.get(i);
    if(mt1==this){
      continue;
    }
//When the extracted ball MT1 and the this object collide are compared
int xMt1 = mt1.x+mt1.width/2;
int yMt1 = mt1.y+mt1.width/2;

int xThis = this.x + this.width/2;
int yThis = this.y + this.width/2;

int distance = (xMt1-xThis)*(xMt1-xThis)+(yMt1-yMt1-yThis)*(yMt1-yThis);
int length = (mt1.width/2+this.width/2)*(mt1.width/2+this.width/2);

//Compare the size
if(distance<=length){
  this.xSpeed = -this.xSpeed;
  this.ySpeed = -this.ySpeed;
  mt1.xSpeed = -mt1.xSpeed;
  mt1.ySpeed = -mt1.ySpeed;
}
  }
}
