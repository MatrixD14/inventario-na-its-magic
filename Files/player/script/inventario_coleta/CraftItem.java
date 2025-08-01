public class CraftItem extends Component {
  public Texture Sprite;
  public ArrayList<Receita> receita = new ArrayList<Receita>();
  public VertexFile[] obj = new VertexFile[1];
  private invertore invent;

  void start() {
    invent = myObject.findComponent("invertore");
    ArrayList<item> Items =
        new ArrayList<item>() {
          {
            add(new item("picareta", "Minera", Sprite, obj[0], 5, 1, 1, 0, 0,null));
            add(new item("espada", "Dano", Sprite, obj[0], 10, 1, 1, 1, 0,new arma(new espada())));
            add(new item("machado", "Dano", Sprite, obj[0], 15, 1, 1, 2, 0,null));
            add(new item("tocha", "Luz", Sprite, obj[0], 10, 1, 64, 0, 1,new tocha()));
            add(new item("pão", "Fome", Sprite, obj[1], 20, 1, 64, 1, 1, null));
            add(new item("tabua", "Contrução", Sprite, obj[0], 20, 1, 64, 3, 2, null));
            add(new item("galho", "Dano", Sprite, obj[0], 20, 1, 64, 0, 2, new arma(new espada())));
            add(new item("craftTable", "CraftItem", Sprite, obj[1], 9, 1, 64, 3, 3, null));
            add(new item("fornalha", "assar", Sprite, obj[1], 64, 1, 64, 3, 1, null));
            add(new item("baú", "armazena", Sprite, obj[1], 64, 1, 64, 2, 3, null));
          }
        };
    receita.add(new Receita(new String[] {"pedra", "pedra", "pedra", null, "galho", null, null, "galho", null}, Items.get(0)));
    receita.add(new Receita(new String[] {null, "pedra", null, null, "pedra", null, null, "galho", null}, Items.get(1)));
    receita.add(new Receita(new String[] {"pedra", "galho", null, "pedra", "galho", null, null, "galho", null}, Items.get(2)));
    receita.add(new Receita(new String[] {null, "carvão", null, null, "galho", null, null, null, null}, Items.get(3)));
    receita.add(new Receita(new String[] {null, null, null, "trigo", "trigo", "trigo", null, null, null}, Items.get(4)));
    receita.add(new Receita(new String[] {null, null, null, null, "troco", null, null, null, null}, Items.get(5)));
    receita.add(new Receita(new String[] {null, null, null, null, "tabua", null, null, null, null}, Items.get(6)));
    receita.add(new Receita(new String[] {null, "tabua", "tabua", null, "tabua", "tabua", null, null, null}, Items.get(7)));
    receita.add(new Receita(new String[] {"pedra", "pedra", "pedra", "pedra", null, "pedra", "pedra", "pedra", "pedra"}, Items.get(8)));
    receita.add(new Receita(new String[] {"tabua", "tabua", "tabua", "tabua", null, "tabua", "tabua", "tabua", "tabua"}, Items.get(9)));
  } 

  void repeat() {
    checkItem();
    if (Input.isKeyDown("output") && invent.items.get(29) != null) craftItem();
  }

  public void checkItem() {
    List<item> CheckItem = invent.items.subList(20, 29);
    boolean checoReciete = false;
    for (Receita r : receita) {
      if (r.material(CheckItem)) {
        item checkOneObj = r.getGeraItem();
        checoReciete = true;
        if (invent.items.get(29) == null || !invent.items.get(29).name.equals(checkOneObj.name)) {
          invent.items.set(29, checkOneObj);
          invent.spait[29].setImage(checkOneObj.getSpait());
          invent.spait[29].setColor(new Color());
        }
        invent.cont[29].setText("1");
        break;
      }
    }
    if (!checoReciete) offItem(29, false);
  }

  public void craftItem() {
    List<item> CheckItem = invent.items.subList(20, 29);
    if (invent.items.get(29) == null) return;
    for (Receita r : receita) {
      if (r.material(CheckItem)) {
        item checkOneObj = r.getGeraItem();
        if (!AddItemSlot(checkOneObj)) return;
        boolean[] uso = new boolean[9];
        for (int items = 20; items < 29; items++) {
          String SeeOneObj = r.getItems()[items - 20];
          if (SeeOneObj == null || invent.items.get(items) == null || uso[items - 20]) continue;
          if (!invent.items.get(items).name.equals(SeeOneObj)) continue;
          invent.slotAlmout[items]--;
          if (invent.slotAlmout[items] <= 0) offItem(items, true);
          else invent.cont[items].setText("" + invent.slotAlmout[items]);
          uso[items - 20] = true;
        }
        return;
      }
    }
  }

  public boolean AddItemSlot(item newItem) {
    int Void = -1;
    for (int i = 0; i < 20; i++) {
      item atual = invent.items.get(i);
      if (atual != null) {
        if (invent.items.get(i).name.equals(newItem.name) && atual.maxgrup > invent.slotAlmout[i]) {
          invent.cont[i].setText("" + (++invent.slotAlmout[i]));
          return true;
        }
      } else if (Void == -1) Void = i;
    }
    if (Void != -1) {
      invent.items.set(Void, newItem);
      invent.slotAlmout[Void] = 1;
      invent.spait[Void].setImage(newItem.getSpait());
      invent.spait[Void].setColor(new Color());
      invent.cont[Void].setText("1");
      return true;
    }
    return false;
  }

  private void offItem(int i, boolean off) {
    invent.items.set(i, null);
    invent.spait[i].setImage(null);
    invent.spait[i].setColor(new Color(0, 0, 0, 0));
    if (off) invent.slotAlmout[i] = 0;
    invent.cont[i].setText("");
  }

  public class Receita {
    private String[] items = new String[9];
    private item GeraItem;

    public Receita() {
      super();
    }

    public Receita(String[] items, item GeraItem) {
      super();
      this.items = items;
      this.GeraItem = GeraItem;
    }

    public String[] getItems() {
      return items;
    }

    public item getGeraItem() {
      return GeraItem;
    }

    public boolean material(List<item> inputSlot) {
      if (inputSlot.size() != 9) return false;
      for (int i = 0; i < 9; i++) {
        String requisitos = items[i];
        String atual = inputSlot.get(i) != null ? inputSlot.get(i).name : null;
        if ((requisitos == null && atual != null) || (requisitos != null && !requisitos.equals(atual))) {
          return false;
        }
      }
      return true;
    }
  }
}
