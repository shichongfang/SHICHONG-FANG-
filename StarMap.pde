int HorizontalLines = 10;
int VerticalLines =10 ;
int x;
int y;
float radius=5;
void setup()
{
  size (800,800);
}

void draw()
{
  background (0);
  stroke(255,0,255);

  float distanceBetweenHorizontalLines = (float)height/HorizontalLines;
  float distanceBetweenVerticalLines = (float)width/VerticalLines;

  for(int i = 0; i < HorizontalLines; i++)
  {
    line(0, i*distanceBetweenHorizontalLines, width, i*distanceBetweenHorizontalLines);

  }

  for(int i = 0; i < VerticalLines; i++)
  {
    line (i*distanceBetweenVerticalLines,0,i*distanceBetweenVerticalLines, height);
  }
  fill(255,0,255);
  line(x-radius,y-radius,x,y-radius);
    line(x,y-radius,x+radius,y);
    line(x-radius,y-radius,x,y+radius);
    line(x,y+radius,x+radius,y);
    Stroke(255,0,0);
    ellipse(x,y,radius,radius);
    for()
    {
    Table table = loadTable("HabHYG15ly.csv");
    }
}