public class removeItemSlot {
  public void caractItems(item2 itemRm, int value, SpatialObject obj, ObjectFile drop) {
    Vector3 mypos = obj.globalPosition;
    SpatialObject itemDrop = obj.Instantiate(drop, mypos);
    itemDrop.setName(itemRm.name);
    
    itemDrop.setTag("item");
    
    itemDrop.setStatic(false);
    itemDrop.getPhysics().setPhysicsEntity(new Rigidbody());
    if (itemDrop.findComponent("item2") == null) itemDrop.addComponent(new item2());
    item2 addDados = itemDrop.findComponent("item2");

    if (itemDrop.findComponent("Collider") == null) itemDrop.addComponent(new Collider());
    Collider itemCollider = itemDrop.findComponent("Collider");

    if (itemDrop.findComponent("ModelRenderer") == null) itemDrop.addComponent(new ModelRenderer());
    ModelRenderer itemModel = itemDrop.findComponent("ModelRenderer");

    if (addDados != null) {
      DadosItens(addDados, itemRm);
      addDados.MaxTrag = value;
    }
    if (itemModel != null) itemModel.setModelFile(itemRm.Vertex);
    if (itemCollider != null) {
      itemCollider.setShape(4);
      itemCollider.setVertexFile(itemRm.Vertex);
    } 
  }

  private void DadosItens(item2 arm, item2 dados) {
    arm.name = dados.name;
    arm.ui = dados.ui;
    arm.type = dados.type;
    arm.value = dados.value;
    arm.MaxTrag = dados.MaxTrag;
    arm.Vertex = dados.Vertex;
  }
}
