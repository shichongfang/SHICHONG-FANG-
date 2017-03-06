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
