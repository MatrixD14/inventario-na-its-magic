public class removeItem {
  public void CaractItem(item rmItem, int value, SpatialObject objitem,ObjectFile drops) {
    Vector3 mypos = objitem.getGlobalPosition();
    SpatialObject drop = objitem.Instantiate(drops, mypos);
    drop.setName(rmItem.name);
    drop.setStatic(false);
    drop.getPhysics().setPhysicsEntity(new Rigidbody());
    if (drop.findComponent("item") == null) drop.addComponent(new item());
    item addDados = drop.findComponent(item.class);
    if (drop.findComponent("Collider") == null) drop.addComponent(new Collider());
    Collider ItemCollider = drop.findComponent("collider");
    if (drop.findComponent("ModelRenderer") == null) drop.addComponent(new ModelRenderer());
    ModelRenderer ItemType = drop.findComponent("ModelRenderer");
    if (addDados != null) {
      DadosItem(addDados, rmItem);
      addDados.QuatItemGrup = value;
    }
    if (ItemType != null) ItemType.setModelFile(rmItem.vertex);
    if (ItemCollider != null) {
      ItemCollider.setShape(4);
      ItemCollider.setVertexFile(rmItem.vertex);
    }
  }

  private void DadosItem(item armazena, item dados) {
      
    armazena.name = dados.name;
    armazena.ui = dados.ui;
    armazena.vertex = dados.vertex;
    armazena.value = dados.value;
    armazena.typeDC = dados.typeDC;
    armazena.maxgrup = dados.maxgrup;
    armazena.MapSpriteX = dados.MapSpriteX;
    armazena.MapSpriteY = dados.MapSpriteY;
    armazena.logica = dados.logica;
  } 
}
