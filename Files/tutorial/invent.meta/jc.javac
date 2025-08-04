public class invent extends Component {
  @Hide public List<SpatialObject> Item = new ArrayList<SpatialObject>();
  @Hide public List<item2> items = new ArrayList<item2>();
  @Hide public SUIImage[] spait = new SUIImage[30];
  @Hide public int[] value = new int[30];
  @Hide public SUIText[] cont = new SUIText[30];
  private SpatialObject button;
  private boolean onoff = false;
  private SUIText infor;

  void start() {
    button = WorldController.findObject("colete");
    infor = WorldController.findObject("HUD").findComponent("suitext");
    slot();
    for (int i = 0; i < cont.length; i++) {
      items.add(i, null);
      if (cont[i] != null) cont[i].setText("");
      if (spait[i] != null) spait[i].setColor(new Color(0, 0, 0, 0));
    }
  }

  private void slot() {
    for (int i = 0; i < 30; i++) {
      String obj = i < 5 ? "slotH" + (i + 1) : i < 20 ? "slot" + (i - 4) : i < 29 ? "slotCria" + (i - 19) : "output";
      String child = i < 5 ? "spait" + (i + 1) : i < 20 ? "item" + (i - 4) : i < 29 ? "item" + (i - 19) : "outputCria";
      Item.add(i, WorldController.findObject(obj).findChildObject(child));
      cont[i] = Item.get(i).findComponent("suitext");
      spait[i] = Item.get(i).findComponent("suiimage");
    } 
  }

  void repeat() {
    laser();
    button.setEnabled(onoff);
  }

  private void laser() {
    SpatialObject came = myObject.findChildObject("vision");
    LaserHit hit = new Laser().trace(came.getGlobalPosition(), came.forward(), 6f);
    if (hit == null || !"item".equals(hit.getObject().tag)) {
      infor.setText();
      onoff = false;
      return;
    }

    item2 objecthit = hit.getObject().findComponent("item2");
    infor.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nname: " + objecthit.name + ", " + objecthit.type + ": " + objecthit.value);
    onoff = true;
    if (!Input.isKeyPressed("coleta") && !Input.keyboard.isKeyDown("e")) return;
    for (int i = 0; i < items.size(); i++) {
      if (items.get(i) == null || items.get(i).name.equals(objecthit.name)) {
        if (value[i] < objecthit.MaxTrag) {
          addItem(objecthit, i);
          hit.getObject().destroy();
          onoff = false;
          break;
        }
      }
    }
  }

  private void addItem(item2 additem, int i) {
    int ArmazInt = ++value[i];
    items.set(i, additem);
    if (cont[i] == null) return;
    cont[i].setText(ArmazInt > 0 ? String.valueOf(ArmazInt) : "1");
    if (i >= spait.length) return;
    spait[i].setImage(additem.getAtlas());
    spait[i].setColor(new Color());
  }
}
