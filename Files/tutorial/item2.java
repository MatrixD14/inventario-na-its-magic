public class item2 extends Component {
  public String name;
  public Texture ui;
  private Texture newSprite;
  public String type;
  public int value;
  public int MaxTrag;
  public int CoordX, CoordY;
  private int Height =32, Width = 32;
  public VertexFile Vertex;

  public item2() {}

  public item2(String name, Texture ui, String type, int value, int MaxTrag, VertexFile Vertex) {
    this.name = name;
    this.ui = ui;
    this.type = type;
    this.value = value;
    this.MaxTrag = MaxTrag;
    this.Vertex = Vertex;
  } 

  public Texture getNewSprite() {
    if (ui == null) return null;
    if (newSprite == null) newSprite = getAtlas();
    return newSprite;
  }

  public Texture getAtlas() {
    if (ui == null) return null;
    Texture newTexture = new Texture(Height, Width, true);
    //newTexture.setFilter(0);
    int eixoX = Height * CoordX;
    int eixoY = Width * CoordY;
    for (int y = 0; y < Height; y++) {
      for (int x = 0; x < Width; x++) {
        Color CheckColor = ui.get(x + eixoX, y + eixoY);
        newTexture.setPixel(x, y, CheckColor);
      }
    }
    newTexture.apply();
    return newTexture;
  }
}
