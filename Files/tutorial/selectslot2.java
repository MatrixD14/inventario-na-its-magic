public class selectslot2 extends Component {
  private List<SpatialObject> Item = new ArrayList<SpatialObject>();
  private SUIKeyEventListener[] key = new SUIKeyEventListener[30];
  private SUIImage[] spait = new SUIImage[30];
  private int select = -1, checkclick = 0;
  private int TrocaSlot = -1;
  private invent invent;
  private float timeclick = 0;
  private SUIText Name;
  public ObjectFile OBJ;
  private SpatialObject painel, Hand;
  private Color black, blue, Transparet, white;
  private onoffinvent OffInvent;

  void start() {
    black = new Color(0, 0, 0);
    Transparet = new Color(0, 0, 0, 0);
    blue = new Color(255, 0, 0);
    white = new Color();
    OffInvent = myObject.findComponent("onoffinvent");
    Hand = WorldController.findObject("object");
    painel = WorldController.findObject("information");
    Name = painel.findComponent("suitext");
    invent = myObject.findComponent("invent");
    for (int i = 0; i < 30; i++) {
      String obj = i < 5 ? "hotbar" : i < 20 ? "backgroud" : "backgroudCria";
      String child = i < 5 ? "slotH" + (i + 1) : i < 20 ? "slot" + (i - 4) : i < 29 ? "slotCria" + (i - 19) : "output";
      Item.add(i, WorldController.findObject(obj).findChildObject(child));
    }
    for (int i = 0; i < Item.size(); i++) {
      if (Item.get(i).findComponent("SUIKeyEventListener") == null) Item.get(i).addComponent(new SUIKeyEventListener());
      SUIKeyEventListener keys = Item.get(i).findComponent("SUIKeyEventListener");
      keys.setKeyName(Item.get(i).getName());
      key[i] = Item.get(i).findComponent("SUIKeyEventListener");
      spait[i] = Item.get(i).findComponent("SUIImage");
    }
    Name.setText("");
    painel.setEnabled(false);
  }

  void repeat() {
    if (timeclick <= 1) timeclick += 0.01f;
    seleciona();
    UpSlot();
    if (Input.isKeyDown("invent") || Input.keyboard.isKeyDown("r")) {
      if (select > 4) select = -1;
      painel.setEnabled(false);
    }

    if (!OffInvent.onoff && TrocaSlot != -1) {
      painel.setEnabled(false);
      TrocaSlot = -1;
      select = -1;
    }
  }

  private void seleciona() {
    for (int i = 0; i < key.length; i++) {
      if (key[i].isDown()) {
        if (select == i) {
          OffObject("", false, i);
          select = -1;
          onoffSelect(-1);
        } else {
          if (invent.items.get(i) != null && invent.items.get(i).name != null) {
            item2 dados = invent.items.get(i);
            String txt = "\n name: " + dados.name + "\n " + dados.type + ": " + dados.value;
            OffObject(txt, true, i);
          } else OffObject("", false, i);
          select = i;
        }
        break;
      }
    }
  }

  private void OffObject(String value, boolean onoff, int i) {
    Name.setText(value);
    if (OffInvent.onoff) painel.setEnabled(onoff);
    boolean activeinvent = i >= 0 && i < 5;
    Hand.setEnabled(onoff && activeinvent);
    if (Hand.findComponent("ModelRenderer") == null) Hand.addComponent(new ModelRenderer());
    ModelRenderer itemHand = Hand.findComponent("ModelRenderer");
    if (itemHand == null || invent.items.get(i) == null) return;
    itemHand.setModelFile(invent.items.get(i).Vertex);
  }

  private void UpSlot() {
    for (int i = 0; i < key.length; i++) {
      if (key[i].isDown()) {
        if (checkclick == i && timeclick < 0.20f) {
          onoffSelect(i);
        } else {
          checkclick = i;
          timeclick = 0;
        }
        if (TrocaSlot != -1 && TrocaSlot != i) {
          item2 Select = invent.items.get(i);
          item2 Trocado = invent.items.get(TrocaSlot);
          if (Select != null && Trocado != null && Select.name.equals(Trocado.name)) {
            int Limite = Select.MaxTrag;
            int Space = Limite - invent.value[i];
            if (Space > 0) {
              int Transfere = Math.min(invent.value[TrocaSlot], Space);
              invent.value[i] += Transfere;
              invent.value[TrocaSlot] -= Transfere;
              invent.cont[i].setText(invent.value[i] > 0 ? String.valueOf(invent.value[i]) : "");
              invent.cont[TrocaSlot].setText(invent.value[TrocaSlot] > 0 ? String.valueOf(invent.value[TrocaSlot]) : "");

              if (invent.value[TrocaSlot] <= 0) {
                invent.items.set(TrocaSlot, null);
                invent.spait[TrocaSlot].setImage(null);
                invent.spait[TrocaSlot].setColor(Transparet);
                invent.cont[TrocaSlot].setText("");
              }
            }
          } else {
            MoveObjSlot(i, Trocado);
            MoveObjSlot(TrocaSlot, Select);
          }
          onoffSelect(-1);
        }
        break;
      }
      if (Input.isKeyDown("remove") && select == i) RemoveItem(i);
      if (spait[i] == null) continue;
      if (TrocaSlot == i) spait[i].setColor(blue);
      else spait[i].setColor(select == i ? black : white);
    }
  }

  public void onoffSelect(int i) {
    checkclick = -1;
    timeclick = 0;
    TrocaSlot = i;
  }

  private void MoveObjSlot(int i, item2 select) {
    invent.items.set(i, select);

    if (invent.spait[i] != null) {
      invent.spait[i].setImage(select != null && select.ui != null ? select.getAtlas() : null);
      invent.spait[i].setColor(select != null ? new Color() : new Color(0, 0, 0, 0));
    }
    int SelectValue = invent.value[i];
    int WhatValue = invent.value[TrocaSlot];
    invent.value[TrocaSlot] = SelectValue;
    invent.value[i] = WhatValue;
    if (invent.cont[i] != null) {
      invent.cont[i].setText(WhatValue > 0 ? String.valueOf(WhatValue) : "");
    }
  }

  private void RemoveItem(int i) {
      RemoveSlot(i);
    invent.items.set(i, null);
    invent.value[i] = 0;
    if (invent.spait[i] != null) {
      invent.spait[i].setImage(null);
      invent.spait[i].setColor(Transparet);
    }
    if (invent.cont[i] != null) invent.cont[i].setText("");
    onoffSelect(-1);
    OffObject("", false, i);
  }

  private void RemoveSlot(int i) {
    removeItemSlot remove = new removeItemSlot();
    item2 rmItem = invent.items.get(i);
    int quant = invent.value[i];
    if (rmItem == null || quant <= 0) return;
    remove.caractItems(rmItem , quant, Hand, OBJ);
  } 
}
